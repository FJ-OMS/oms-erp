package org.skyer.order.app.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.skyer.boot.platform.lov.annotation.ProcessLovValue;
import org.skyer.core.domain.Page;
import org.skyer.mybatis.domian.Condition;
import org.skyer.mybatis.util.Sqls;
import org.skyer.order.api.es.SearchThirdOrderEsParam;
import org.skyer.order.api.es.ThirdOrderEsModel;
import org.skyer.order.api.vo.ThirdOrderPageVo;
import org.skyer.order.app.service.ThirdOrderEsService;
import org.skyer.order.domain.entity.ThirdHeader;
import org.skyer.order.domain.entity.ThirdReceiverInfo;
import org.skyer.order.domain.repository.ThirdHeaderRepository;
import org.skyer.order.domain.repository.ThirdItemRepository;
import org.skyer.order.domain.repository.ThirdReceiverInfoRepository;
import org.skyer.order.infra.common.CommonConstants;
import org.skyer.order.infra.convertor.ThirdHeaderConvertor;
import org.skyer.order.infra.util.EsBoolQueryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author zlb
 */
@Service
@Slf4j
public class ThirdOrderEsServiceImpl implements ThirdOrderEsService {
    @Autowired
    private RestHighLevelClient client;
    @Autowired
    private ThirdHeaderRepository thirdHeaderRepository;
    @Autowired
    private ThirdReceiverInfoRepository thirdReceiverInfoRepository;
    @Autowired
    private ThirdItemRepository thirdItemRepository;
    @Autowired
    private ThirdHeaderConvertor thirdHeaderConvertor;


    @Override
    @ProcessLovValue
    public Page<ThirdOrderPageVo> getThirdOrderPage(SearchThirdOrderEsParam searchThirdOrderEsParam) throws Exception {
        Page<ThirdOrderPageVo> vos = new Page<>();
        int size = searchThirdOrderEsParam.getSize();
        vos.setSize(size);
        vos.setNumber(searchThirdOrderEsParam.getPage() + 1);
        // 判断索引库是否存在
        if (!isExistsIndex(CommonConstants.EsConstant.ORDER_THIRD_INDEX)){
            return new Page<>();
        }
        try {
            // 构建Es检索请求
            SearchRequest searchRequest = this.buildSearchRequest(searchThirdOrderEsParam);
            // 执行检索请求
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits hits = searchResponse.getHits();
            long totalElements = hits.getTotalHits().value;
            int total = (int) totalElements;
            int page = total / size;
            // 构建检索hits结果
            List<ThirdOrderPageVo> orderPageVos = this.buildSearchResult(searchResponse);
            vos.setContent(orderPageVos);
            vos.setTotalElements(totalElements);
            vos.setTotalPages(page);
        } catch (IOException e) {
            log.info(e.getMessage());
        }
        return vos;
    }

    /**
     * 构建结果集
     *
     * @param response response
     * @return List<ThirdOrderPageVo>
     */
    private List<ThirdOrderPageVo> buildSearchResult(SearchResponse response) {

        List<ThirdOrderPageVo> resultList = Lists.newArrayList();
        //返回的所有查询到的三方订单
        SearchHits hits = response.getHits();
        if (hits.getHits() != null && hits.getHits().length > 0) {
            for (SearchHit hit : hits.getHits()) {
                ThirdOrderEsModel orderEsModel = JSON.parseObject(hit.getSourceAsString()).toJavaObject(ThirdOrderEsModel.class);
                ThirdOrderPageVo pageVo = thirdHeaderConvertor.toThirdOrderPageVo(orderEsModel);
                resultList.add(pageVo);
            }
        }
        return resultList;
    }
    /**
     * 构建查询请求
     *
     * @param searchOrderEsParam searchOrderEsParam
     * @return SearchRequest
     */
    private SearchRequest buildSearchRequest(SearchThirdOrderEsParam searchOrderEsParam){
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 构建bool-query
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        builder = getBoolQueryBuilder(builder, searchOrderEsParam);
        // 封装所有的查询条件
        searchSourceBuilder.query(builder);
        // 排序
        searchSourceBuilder.sort("creationDate", SortOrder.DESC);
        // 分页
        EsBoolQueryUtil.page(searchSourceBuilder, searchOrderEsParam.getPage(), searchOrderEsParam.getSize());
        searchSourceBuilder.trackTotalHits(true);
        return new SearchRequest(new String[]{CommonConstants.EsConstant.ORDER_THIRD_INDEX}, searchSourceBuilder);
    }

    /**
     * 请求参数
     * @param builder builder
     * @param searchOrderEsParam searchOrderEsParam
     * @return BoolQueryBuilder
     */
    public BoolQueryBuilder getBoolQueryBuilder(BoolQueryBuilder builder, SearchThirdOrderEsParam searchOrderEsParam) {

        if (ObjectUtil.isNotEmpty(searchOrderEsParam.getOrderNo())){
            List<String> orderNoItem = Arrays.asList(searchOrderEsParam.getOrderNo().split(","));
            String [] arr = new String[] {"outerOrderNo","innerOrderNo"};
            builder = EsBoolQueryUtil.inMultiFieldPhraseQuery(builder, orderNoItem,arr);
        }
        if (ObjectUtil.isNotEmpty(searchOrderEsParam.getChannel())){
            builder.must(QueryBuilders.termQuery("channel",searchOrderEsParam.getChannel()));
        }
        if (ObjectUtil.isNotEmpty(searchOrderEsParam.getStoreIdList())) {
            builder = EsBoolQueryUtil.inTermQuery(builder, "storeId", searchOrderEsParam.getStoreIdList());
        }
        if (Objects.nonNull(searchOrderEsParam.getBusinessType())) {
            builder.must(QueryBuilders.termQuery("businessType",searchOrderEsParam.getBusinessType()));
        }
        if (Objects.nonNull(searchOrderEsParam.getSyncStatus())) {
            builder.must(QueryBuilders.termQuery("syncStatus",searchOrderEsParam.getSyncStatus()));
        }
        if (Objects.nonNull(searchOrderEsParam.getBuyerNick())) {
            builder.must(QueryBuilders.matchPhraseQuery("buyerNick",searchOrderEsParam.getBuyerNick()));
        }
        if (Objects.nonNull(searchOrderEsParam.getReceiverName())) {
            builder.must(QueryBuilders.matchPhraseQuery("receiverName",searchOrderEsParam.getReceiverName()));
        }
        if (Objects.nonNull(searchOrderEsParam.getMobile())) {
            builder.must(QueryBuilders.termQuery("mobile",searchOrderEsParam.getMobile()));
        }
        if (Objects.nonNull(searchOrderEsParam.getOrderStartTime()) && Objects.nonNull(searchOrderEsParam.getOrderEndTime()) ) {
            builder.must(QueryBuilders.rangeQuery("orderTime").gte(searchOrderEsParam.getOrderStartTime().getTime()).lt(searchOrderEsParam.getOrderEndTime().getTime()));
        }
        if (Objects.nonNull(searchOrderEsParam.getPayStartTime()) && Objects.nonNull(searchOrderEsParam.getPayEndTime()) ) {
            builder.must(QueryBuilders.rangeQuery("payTime").gte(searchOrderEsParam.getPayStartTime().getTime()).lt(searchOrderEsParam.getPayEndTime().getTime()));
        }

        return builder;
    }

    @Override
    public void batchInsertOrderToEs(List<String> outOrderNoList,List<String> innerOrderNoList) {
        List<ThirdHeader> thirdHeaderList ;
        if (ObjectUtil.isNotEmpty(outOrderNoList) && ObjectUtil.isNotEmpty(innerOrderNoList)){
            thirdHeaderList = thirdHeaderRepository.selectByCondition(Condition.builder(ThirdHeader.class)
                    .andWhere(Sqls.custom().andIn(ThirdHeader.FIELD_OUTER_ORDER_NO,outOrderNoList)).build());
        }else {
            thirdHeaderList = thirdHeaderRepository.selectByCondition(Condition.builder(ThirdHeader.class)
                    .andWhere(Sqls.custom().andIn(ThirdHeader.FIELD_INNER_ORDER_NO,innerOrderNoList)).build());
        }

        List<String> orderNoList = thirdHeaderList.stream().map(ThirdHeader::getOuterOrderNo).collect(Collectors.toList());

        //查询收货人信息
        Map<String, ThirdReceiverInfo> receiverInfoMap = thirdReceiverInfoRepository.queryThirdReceiverInfoByOuterNo(orderNoList);

        List<ThirdOrderEsModel> thirdOrderEsModels = thirdHeaderList.parallelStream().map(thirdHeader -> {
            ThirdReceiverInfo thirdReceiverInfo = receiverInfoMap.get(thirdHeader.getOuterOrderNo());
            ThirdOrderEsModel thirdOrderEsModel = thirdHeaderConvertor.toThirdOrderEsModel(thirdHeader,thirdReceiverInfo);
            String address ="";
            if (Objects.nonNull(thirdReceiverInfo.getProvince())) {
                address = thirdReceiverInfo.getProvince() + thirdReceiverInfo.getCity() + thirdReceiverInfo.getDistrict() + thirdReceiverInfo.getAddress();
            }
            thirdOrderEsModel.setAddress(address);

            return thirdOrderEsModel;
        }).collect(Collectors.toList());
        this.batchInsertThirdOrderEs(thirdOrderEsModels);
    }

    private  void batchInsertThirdOrderEs(List<ThirdOrderEsModel> orderEsModelList) {
        BulkRequest request = new BulkRequest();
        orderEsModelList.forEach(orderItem -> {
            IndexRequest indexRequest = new IndexRequest(CommonConstants.EsConstant.ORDER_THIRD_INDEX);
            String toJsonString = JSON.toJSONString(orderItem);
            indexRequest.source(toJsonString, XContentType.JSON);
            indexRequest.id(String.valueOf(orderItem.getId()));
            request.add(indexRequest);
        });
        try {
            client.bulk(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.info("三方订单ES插入异常" + e.getMessage());
        }
    }

    /**
     * 判断一个索引是否存在
     * @param indexName indexName
     * @return boolean
     */
    private  boolean isExistsIndex(String indexName) throws Exception{
        GetIndexRequest request = new GetIndexRequest(indexName);
        return  client.indices().exists(request, RequestOptions.DEFAULT);
    }
}
