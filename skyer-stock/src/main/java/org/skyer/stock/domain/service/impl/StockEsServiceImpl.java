package org.skyer.stock.domain.service.impl;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.compress.utils.Lists;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.VersionType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.skyer.core.domain.Page;
import org.skyer.core.exception.CommonException;
import org.skyer.core.oauth.CustomUserDetails;
import org.skyer.core.oauth.DetailsHelper;
import org.skyer.stock.api.dto.EsStockVersionDTO;
import org.skyer.stock.api.dto.GoodsStockDTO;
import org.skyer.stock.api.dto.StockEsDTO;
import org.skyer.stock.api.dto.StockQueryReqDTO;
import org.skyer.stock.domain.entity.Warehouse;
import org.skyer.stock.domain.repository.WarehouseRepository;
import org.skyer.stock.domain.service.StockEsService;
import org.skyer.stock.domain.vo.GoodsStockResVO;
import org.skyer.stock.domain.vo.RpcGoodsResVO;
import org.skyer.stock.infra.constant.ObjectConstant;
import org.skyer.stock.infra.convertor.StockEsConvertor;
import org.skyer.stock.infra.feign.GoodsSkuRemoteService;
import org.skyer.stock.infra.util.EsBoolQueryUtil;

/**
 * @description
 * @Author wusc
 * @create 2021/8/5 3:30 下午
 */
@Service
public class StockEsServiceImpl implements StockEsService {
    private static final Logger logger = LoggerFactory.getLogger(StockEsServiceImpl.class);

    @Autowired
    private RestHighLevelClient client;
    @Autowired
    private StockEsConvertor stockEsConvertor;
    @Autowired
    private WarehouseRepository warehouseRepository;
    @Autowired
    private GoodsSkuRemoteService goodsSkuRemoteService;

    @Override
    public void createOrUpdateStockEs(List<GoodsStockDTO> goodsStockList, Long warehouseId) {
        Warehouse warehouse = warehouseRepository.selectByPrimaryKey(warehouseId);
        if (Objects.isNull(warehouse)) {
            throw new CommonException("仓库不存在:" + warehouseId);
        }
        // 获取sku集合
        List<String> skuCodeList = goodsStockList.stream().map(GoodsStockDTO::getSkuCode).collect(Collectors.toList());
        List<EsStockVersionDTO> esStockVersions = queryEsStockVersionList(skuCodeList, warehouse.getCode());
        Map<String, Long> versionMap = new HashMap<>();
        if (!ObjectUtils.isEmpty(esStockVersions)) {
            versionMap = esStockVersions.stream().collect(Collectors.toMap(EsStockVersionDTO::getId, EsStockVersionDTO::getVersion, (obj1, obj2) -> obj2));
        }
        BulkRequest request = new BulkRequest();
        ResponseEntity<List<RpcGoodsResVO>> rpcGoodsListEntity = goodsSkuRemoteService.queryRPCGoodsList(goodsStockList.stream().map(GoodsStockDTO::getSkuCode).distinct().collect(Collectors.toList()));
        logger.info("库存同步ES，RPC商品信息=====>" + rpcGoodsListEntity);

        List<RpcGoodsResVO> body = Optional.ofNullable(rpcGoodsListEntity.getBody()).orElse(new ArrayList<>());
        Map<String, RpcGoodsResVO> rpcGoodsMap = body.stream().collect(Collectors.toMap(RpcGoodsResVO::getSkuCode, Function.identity(), (o1, o2) -> o2));
        Map<String, Long> finalVersionMap = versionMap;
        goodsStockList.forEach(goodsStock -> {
            IndexRequest indexRequest = new IndexRequest(ObjectConstant.EsKey.STOCK_ES);
            StockEsDTO stockEsDTO = stockEsConvertor.stockToEsDTO(goodsStock);
            RpcGoodsResVO rpcGoodsResVO = Optional.ofNullable(rpcGoodsMap.get(goodsStock.getSkuCode())).orElse(new RpcGoodsResVO());
            stockEsDTO.setSkuName(rpcGoodsResVO.getSkuName());
            stockEsDTO.setSpuCode(rpcGoodsResVO.getSpuCode());
            stockEsDTO.setSpuName(rpcGoodsResVO.getSpuName());
            stockEsDTO.setSpuType(rpcGoodsResVO.getSpuType());
            stockEsDTO.setCategoryName(rpcGoodsResVO.getCategoryStr());
            stockEsDTO.setCategoryId(rpcGoodsResVO.getLastCategoryId());
            stockEsDTO.setWarehouseTypeCode(warehouse.getTypeCode());
            stockEsDTO.setWarehouseCode(warehouse.getCode());
            stockEsDTO.setBrandName(rpcGoodsResVO.getBrandName());
            stockEsDTO.setBrandId(rpcGoodsResVO.getBrandId());
            if (stockEsDTO.getExceedZero() == null) {
                stockEsDTO.setExceedZero(stockEsDTO.getStockNum() > 0);
            }
            String stockEsStr = JSONObject.toJSONString(stockEsDTO);
            indexRequest.source(stockEsStr, XContentType.JSON);
            String id = warehouse.getTypeCode() + "_" + warehouse.getCode() + "_" + goodsStock.getSkuCode();
            indexRequest.id(id);
            Long version = finalVersionMap.get(id);
            if (ObjectUtils.isEmpty(version)) {
                version = 1L;
            } else {
                version++;
            }
            indexRequest.version(version);
            indexRequest.versionType(VersionType.EXTERNAL);
            request.add(indexRequest);
        });
        Boolean flag = execute(request, 10);
        if (flag) {
            throw new CommonException("同步es失败");
        }

    }

    /**
     * 执行es落库
     * false 表示为成功 ture 表示为失败
     *
     * @author lixiaoyang
     * @date 2022/4/7 16:52
     */
    private Boolean execute(BulkRequest request, int num) {
        try {
            BulkResponse response = client.bulk(request, RequestOptions.DEFAULT);
            // ture 等于失败
            boolean flag = response.hasFailures();
            if (flag) {
                BulkItemResponse[] items = response.getItems();
                if (!ObjectUtils.isEmpty(items)) {
                    for (BulkItemResponse item : items) {
                        RestStatus status = item.getFailure().getStatus();
                        if (RestStatus.CONFLICT.equals(status)) {
                            // 乐观锁冲突跳过
                            return false;
                        }
                    }
                }
                logger.info("同步失败重试===" + num);
                if (num > 1) {
                    num--;
                    return execute(request, num);
                }
            }
            return flag;
        } catch (Exception e) {
            logger.error("库存写入ES异常=======>", e);
            logger.error(Arrays.toString(e.getStackTrace()));
            if (num > 1) {
                num--;
                return execute(request, num);
            }
        }
        return false;
    }

    /**
     * 获取ES 中数据ID对应的乐观锁
     *
     * @author lixiaoyang
     * @date 2022/5/16 10:27
     */
    private List<EsStockVersionDTO> queryEsStockVersionList(List<String> skuCodeList, String warehouseCode) {
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        builder = EsBoolQueryUtil.inStringQuery(builder, "skuCode", skuCodeList);
        builder = EsBoolQueryUtil.andStringQuery(builder, "warehouseCode", warehouseCode);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.version(true);
        searchSourceBuilder.query(builder);
        SearchRequest searchRequest = new SearchRequest(ObjectConstant.EsKey.STOCK_ES);
        searchRequest.source(searchSourceBuilder);
        try {
            GetIndexRequest indexRequest = new GetIndexRequest(ObjectConstant.EsKey.STOCK_ES);
            if (!client.indices().exists(indexRequest, RequestOptions.DEFAULT)) {
                return new ArrayList<>();
            }
            SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits hits = search.getHits();
            List<EsStockVersionDTO> goodsStockResList = Arrays.stream(hits.getHits()).map(stock -> {
                EsStockVersionDTO dto = new EsStockVersionDTO();
                String id = stock.getId();
                long version = stock.getVersion();
                dto.setId(id);
                dto.setVersion(version);
                return dto;
            }).collect(Collectors.toList());
            return goodsStockResList;
        } catch (IOException e) {
            logger.info("库存ES查询异常", e);
        }
        return new ArrayList<>();
    }


    @Override
    public Page<GoodsStockResVO> queryStockEs(StockQueryReqDTO stockQueryReqDTO) {
        Long tenantId = Optional.ofNullable(DetailsHelper.getUserDetails()).map(CustomUserDetails::getTenantId).orElse(0L);
        Page<GoodsStockResVO> goodsStockResVOS = new Page<>();
        int size = stockQueryReqDTO.getSize();
        goodsStockResVOS.setSize(size);
        goodsStockResVOS.setNumber(stockQueryReqDTO.getPage() + 1);
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        builder = getBoolQueryBuilder(stockQueryReqDTO, builder);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(builder);
        searchSourceBuilder.trackTotalHits(true);
        EsBoolQueryUtil.page(searchSourceBuilder, stockQueryReqDTO.getPage(), stockQueryReqDTO.getSize());
        SearchRequest searchRequest = new SearchRequest(ObjectConstant.EsKey.STOCK_ES);
        searchRequest.source(searchSourceBuilder);
        try {
            GetIndexRequest indexRequest = new GetIndexRequest(ObjectConstant.EsKey.STOCK_ES);
            if (!client.indices().exists(indexRequest, RequestOptions.DEFAULT)) {
                return goodsStockResVOS;
            }

            SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits hits = search.getHits();
            Long totalElements = hits.getTotalHits().value;
            goodsStockResVOS.setTotalElements(totalElements);
            int total = totalElements.intValue();
            int page = total / size;
            goodsStockResVOS.setTotalPages(page);
            List<GoodsStockResVO> goodsStockResList = Arrays.stream(hits.getHits()).map(stock -> {
                GoodsStockResVO goodsStockResVO = JSONObject.parseObject(stock.getSourceAsString()).toJavaObject(GoodsStockResVO.class);
                if (goodsStockResVO.getWarehouseCode() != null) {
                    Warehouse warehouse = warehouseRepository.getWarehouseByCondition(goodsStockResVO.getWarehouseCode(), tenantId);
                    if (!ObjectUtils.isEmpty(warehouse)) {
                        goodsStockResVO.setWarehouseId(warehouse.getId());
                        goodsStockResVO.setWarehouseName(warehouse.getName());
                        goodsStockResVO.setTypeCode(warehouse.getTypeCode());
                    }
                }
                return goodsStockResVO;
            }).collect(Collectors.toList());
            //拼接仓库名称
            goodsStockResVOS.setContent(goodsStockResList);
        } catch (IOException e) {
            logger.info("库存ES查询异常", e);
        }

        return goodsStockResVOS;
    }

    public BoolQueryBuilder getBoolQueryBuilder(StockQueryReqDTO stockQueryReqDTO, BoolQueryBuilder builder) {
        if (Objects.nonNull(stockQueryReqDTO.getSpuName())) {
            builder.must(QueryBuilders.matchPhraseQuery("spuName", stockQueryReqDTO.getSpuName()));
        }
        if (Objects.nonNull(stockQueryReqDTO.getSpuCode())) {
            builder = EsBoolQueryUtil.andStringQuery(builder, "spuCode", stockQueryReqDTO.getSpuCode());
        }
        if (Objects.nonNull(stockQueryReqDTO.getUsableExcludeZeroFlag())) {
            if (Boolean.TRUE.equals(stockQueryReqDTO.getUsableExcludeZeroFlag())) {
                builder = EsBoolQueryUtil.andRangeQuery(builder, "usableNum", 0, null);
            }
        }

        if (Objects.nonNull(stockQueryReqDTO.getSkuCode())) {
            builder = EsBoolQueryUtil.andStringQuery(builder, "skuCode", stockQueryReqDTO.getSkuCode());
        }
        if (Objects.nonNull(stockQueryReqDTO.getExceedZero())) {
            builder = EsBoolQueryUtil.andTermQuery(builder, "exceedZero", stockQueryReqDTO.getExceedZero());
        }
        if (Objects.nonNull(stockQueryReqDTO.getWarehouseCode())) {
            builder = EsBoolQueryUtil.andStringQuery(builder, "warehouseCode", stockQueryReqDTO.getWarehouseCode());
        }
        if (Objects.nonNull(stockQueryReqDTO.getWarehouseId())) {
            builder = EsBoolQueryUtil.inTermQuery(builder, "warehouseId", stockQueryReqDTO.getWarehouseId());
        }
        if (Objects.nonNull(stockQueryReqDTO.getWarehouseTypeCode())) {
            builder = EsBoolQueryUtil.inStringQuery(builder, "warehouseTypeCode", stockQueryReqDTO.getWarehouseTypeCode());
        }

        List<Long> categoryIdList = stockQueryReqDTO.getCategoryIdList();
        if (Objects.nonNull(categoryIdList)) {
            builder = EsBoolQueryUtil.inTermQuery(builder, "categoryId", categoryIdList);
        }

        return builder;
    }

    @Override
    public void delStockEs(List<GoodsStockDTO> goodsStockList) {
        List<String> keys = Lists.newArrayList();
        goodsStockList.forEach(stock -> {
            Warehouse warehouse = warehouseRepository.selectByPrimaryKey(stock.getWarehouseId());
            if (Objects.isNull(warehouse)) {
                throw new CommonException("仓库不存在:" + stock.getWarehouseId());
            }
            keys.add(warehouse.getCode() + "_" + stock.getSkuCode());
        });
        DeleteByQueryRequest request = new DeleteByQueryRequest(ObjectConstant.EsKey.STOCK_ES);
        BoolQueryBuilder queryBuilder = new BoolQueryBuilder();
        queryBuilder = EsBoolQueryUtil.inStringQuery(queryBuilder, "id", keys);
        request.setQuery(queryBuilder);
        request.setTimeout(TimeValue.timeValueMinutes(2));
        try {
            client.deleteByQuery(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delStockEsAll() {
        DeleteByQueryRequest request = new DeleteByQueryRequest(ObjectConstant.EsKey.STOCK_ES);
        BoolQueryBuilder queryBuilder = new BoolQueryBuilder();
        request.setQuery(queryBuilder);
        request.setTimeout(TimeValue.timeValueMinutes(2));
        try {
            client.deleteByQuery(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
