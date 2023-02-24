package org.skyer.goods.app.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.*;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.skyer.core.domain.Page;
import org.skyer.core.iam.ResourceLevel;
import org.skyer.goods.api.dto.GoodsSkuPageDTO;
import org.skyer.goods.app.service.SkuEsService;
import org.skyer.goods.domain.entity.Material;
import org.skyer.goods.domain.entity.Sku;
import org.skyer.goods.domain.entity.SkuAttributeValue;
import org.skyer.goods.domain.entity.Spu;
import org.skyer.goods.domain.repository.*;
import org.skyer.goods.domain.vo.GoodsSkuPageVO;
import org.skyer.goods.domain.vo.SpuEsModel;
import org.skyer.goods.infra.constant.ObjectConstant;
import org.skyer.goods.infra.util.EsBoolQueryUtil;
import org.skyer.mybatis.domain.AuditDomain;
import org.skyer.mybatis.domian.Condition;
import org.skyer.mybatis.util.Sqls;
import org.skyer.saga.saga.SagaDefinition;
import org.skyer.saga.saga.annotation.Saga;
import org.skyer.saga.saga.annotation.SagaTask;
import org.skyer.saga.saga.producer.StartSagaBuilder;
import org.skyer.saga.saga.producer.TransactionalProducer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.skyer.goods.domain.entity.Sku.*;

/**
 * @author zhanglb
 */
@Slf4j
@Service
public class SkuEsServiceImpl implements SkuEsService {

    @Autowired
    private RestHighLevelClient client;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private SkuRepository skuRepository;
    @Autowired
    private SpuRepository spuRepository;
    @Autowired
    private MaterialRepository materialRepository;
    @Autowired
    private SkuAttributeValueRepository skuAttributeValueRepository;
    @Autowired
    private TransactionalProducer transactionalProducer;


    @Override
    @Saga(description = "同步skuList到saga", code = "sync-sku-list-es")
    public List<Sku> syncSkuListEsSage(List<Sku> skuList) {
        List<String> skuCodeList = skuList.stream().map(Sku::getSkuCode).collect(Collectors.toList());
        List<Sku> skuEsList = skuRepository.selectByCondition(Condition.builder(Sku.class)
                .andWhere(Sqls.custom().andIn(FIELD_SKU_CODE,skuCodeList)).build());

        return transactionalProducer.applyAndReturn(
                StartSagaBuilder.newBuilder()
                        .withSagaCode("sync-sku-list-es").withLevel(ResourceLevel.ORGANIZATION)
                        .withRefType("listSku")
                        .withSourceId(1L),
                build -> {
                    build.withPayloadAndSerialize(skuEsList)
                            .withRefId(skuEsList.hashCode() + " time:" + System.currentTimeMillis());
                    return skuEsList;
                });
    }

    @SagaTask(description = "同步skuList到es sage task",
            code = "sync-sku-es-list-sage-task",
            sagaCode = "sync-sku-list-es",
            concurrentLimitNum = 2,
            concurrentLimitPolicy = SagaDefinition.ConcurrentLimitPolicy.NONE,
            seq = 1)
    @Override
    public List<Sku> syncSkuListEsSageTask(String skuListJsonStr) throws IOException {
        List<Sku> skuList = JSON.parseArray(skuListJsonStr, Sku.class);
        BulkRequest bulkRequest = new BulkRequest();
        skuList.forEach(x -> {
            //构造保存请求
            String jsonString = JSON.toJSONString(x);
            IndexRequest indexRequest = new IndexRequest(ObjectConstant.EsKey.SKU_INDEX);
            indexRequest.id(x.getId().toString());
            indexRequest.source(jsonString, XContentType.JSON);
            bulkRequest.add(indexRequest);
        });
        client.bulk(bulkRequest, RequestOptions.DEFAULT);
        return skuList;
    }






    @Override
    public Page<GoodsSkuPageVO> listPage(GoodsSkuPageDTO goodsSkuPageDTO) throws Exception {

        // 判断索引库是否存在
        if (!isExistsIndex(ObjectConstant.EsKey.SKU_INDEX)){
            return new Page<>();
        }

        if (CollUtil.isNotEmpty(goodsSkuPageDTO.getCategoryIdList())) {
            goodsSkuPageDTO.setCategoryIdList(categoryRepository.getLaterCategoryId(goodsSkuPageDTO.getCategoryIdList()));
        }
        Page<GoodsSkuPageVO> skuPage = new Page<>();
        int size = goodsSkuPageDTO.getSize();
        skuPage.setSize(size);
        skuPage.setNumber(goodsSkuPageDTO.getPage() + 1);
        SearchRequest searchRequest = this.buildSearchRequest(goodsSkuPageDTO);
        try {
            SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits hits = search.getHits();
            long totalElements = hits.getTotalHits().value;
            skuPage.setTotalElements(totalElements);
            int total = (int) totalElements;
            int page = total / size;
            skuPage.setTotalPages(page);
            List<Sku> esSkuList = Arrays.stream(hits.getHits()).map(s -> JSON.parseObject(s.getSourceAsString()).toJavaObject(Sku.class)).collect(Collectors.toList());
            if (ObjectUtil.isNotEmpty(esSkuList)){
                List<Long> skuIdList = esSkuList.stream().map(Sku::getId).collect(Collectors.toList());
                //查询数据库获取最新数据
                List<Sku> skuList = skuRepository.selectByCondition(Condition.builder(Sku.class)
                        .andWhere(Sqls.custom().andIn(FIELD_ID,skuIdList)).orderByDesc(AuditDomain.FIELD_CREATION_DATE).build());
                skuPage.setContent(this.getPageList(skuList));
            }else {
                skuPage.setContent(new ArrayList<>());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return skuPage;
    }


    private List<GoodsSkuPageVO> getPageList(List<Sku> skuList) {
        List<GoodsSkuPageVO> pageList = new ArrayList<>();
        if (CollUtil.isNotEmpty(skuList)) {
            // 找出所有的商品数据
            Set<Long> goodsIds = skuList.stream().map(Sku::getSpuId).collect(Collectors.toSet());
            List<Spu> spuList = spuRepository.selectByCondition(Condition.builder(Spu.class)
                    .andWhere(Sqls.custom()
                            .andIn(org.skyer.goods.domain.entity.Spu.ID, goodsIds, true)
                            .andEqualTo(org.skyer.goods.domain.entity.Spu.DELETE_FLAG, false))
                    .build());
            // 找出所有素材
            Set<Long> materialIds = spuList.stream().map(Spu::getMaterialId).collect(Collectors.toSet());
            List<Material> materialList = materialRepository.selectByCondition(Condition.builder(Material.class)
                    .andWhere(Sqls.custom()
                            .andIn("id", materialIds, true)
                            .andEqualTo(org.skyer.goods.domain.entity.Spu.DELETE_FLAG, false))
                    .build());
            //属性
            List<Long> skuIdList = skuList.stream().map(Sku::getId).collect(Collectors.toList());
            List<SkuAttributeValue> skuAttrValueRelationList = skuAttributeValueRepository.selectByCondition(Condition.builder(SkuAttributeValue.class)
                    .andWhere(Sqls.custom().andIn(SkuAttributeValue.FIELD_SKU_ID, skuIdList, true))
                    .build());

            //组装返回数据
            Map<Long,String> categoryMap = Maps.newHashMap();
            skuList.forEach(o -> {
                GoodsSkuPageVO skuPageVO = new GoodsSkuPageVO();
                //复制id 价格等信息
                BeanUtils.copyProperties(o, skuPageVO);
                //找到与sku匹配的spu
                Spu tempSpu = spuList.stream().filter(xx -> xx.getId().longValue() == o.getSpuId().longValue()).findFirst().orElse(null);
                if (ObjectUtil.isNotNull(tempSpu)) {
                    //设置sku属性字段
                    skuPageVO.setMaterialId(tempSpu.getMaterialId());
                    skuPageVO.setSpuName(tempSpu.getName());
                    skuPageVO.setSpuType(tempSpu.getType());
                    skuPageVO.setCategoryCode(tempSpu.getCategoryCode());
                    skuPageVO.setSpuCode(tempSpu.getSpuCode());
                    if (ObjectUtil.isNotNull(tempSpu.getRemark())) {
                        skuPageVO.setGoodsRemark(tempSpu.getRemark());
                    }

                    String categoryName = categoryMap.get(tempSpu.getCategoryId());
                    if (StringUtils.isEmpty(categoryName)){
                        categoryName = categoryRepository.getCategoryNameById(tempSpu.getCategoryId());
                        categoryMap.put(tempSpu.getCategoryId(),categoryName);
                    }
                    skuPageVO.setCategoryName(categoryName);
                    //匹配素材
                    if (ObjectUtil.isNotNull(tempSpu.getMaterialId())) {
                        skuPageVO.setMaterialId(tempSpu.getMaterialId());
                        Material tempMaterial = materialList.stream().filter(xx -> xx.getId().longValue() == skuPageVO.getMaterialId().longValue()).findFirst().orElse(null);
                        if (ObjectUtil.isNotNull(tempMaterial)) {
                            skuPageVO.setMaterialMainImages(Arrays.asList(Optional.ofNullable(tempMaterial.getMainImage()).orElse("").split(",")));
                            skuPageVO.setMaterialName(tempMaterial.getMaterialName());
                        }
                    }
                    //查询属性值 是带入初始属性 否放入到额外属性
                    List<SkuAttributeValue> skuAttrValueRelationLists=  skuAttrValueRelationList.stream().filter(xx -> xx.getSkuId().longValue() == o.getId().longValue()).collect(Collectors.toList());

                    if (CollUtil.isNotEmpty(skuAttrValueRelationLists)) {
                        List<SkuAttributeValue> originalAttributeList = skuAttrValueRelationLists.stream().filter(SkuAttributeValue::getIsSku).collect(Collectors.toList());
                        skuPageVO.setSkuAttributeList(originalAttributeList);

                        List<SkuAttributeValue> attachedAttributeList = skuAttrValueRelationLists.stream().filter(f -> !f.getIsSku()).collect(Collectors.toList());
                        skuPageVO.setOtherAttributeList(attachedAttributeList);
                    }
                }
                pageList.add(skuPageVO);
            });
        }
        return pageList;
    }

    @Override
    @Saga(description = "删除skuList到saga", code = "sync-del-sku-list-es")
    public List<Sku> syncDelSkuListEsSage(List<Sku> skuList) {
        return transactionalProducer.applyAndReturn(
                StartSagaBuilder.newBuilder()
                        .withSagaCode("sync-del-sku-list-es").withLevel(ResourceLevel.ORGANIZATION)
                        .withRefType("listSku")
                        .withSourceId(1L),
                build -> {
                    build.withPayloadAndSerialize(skuList)
                            .withRefId(skuList.hashCode() + " time:" + System.currentTimeMillis());
                    return skuList;
                });
    }

    @SagaTask(description = "同步skuList到es sage task",
            code = "sync-del-sku-es-list-sage-task",
            sagaCode = "sync-del-sku-list-es",
            concurrentLimitNum = 2,
            concurrentLimitPolicy = SagaDefinition.ConcurrentLimitPolicy.NONE,
            seq = 1)
    @Override
    public List<Sku> syncDelSkuListEsSageTask(String skuListJsonStr) throws IOException {
        List<Sku> skuList = JSON.parseArray(skuListJsonStr, Sku.class);
        List<String> keys = Lists.newArrayList();
        skuList.forEach(item -> keys.add(String.valueOf(item.getId())));
        DeleteByQueryRequest request = new DeleteByQueryRequest(ObjectConstant.EsKey.SKU_INDEX);
        BoolQueryBuilder queryBuilder = new BoolQueryBuilder();
        queryBuilder = EsBoolQueryUtil.inStringQuery(queryBuilder, "id", keys);
        request.setQuery(queryBuilder);
        request.setTimeout(TimeValue.timeValueMinutes(2));
        client.deleteByQuery(request, RequestOptions.DEFAULT);
        return skuList;
    }

    @Override
    public void delSkuEsAll() throws Exception {
        if (isExistsIndex(ObjectConstant.EsKey.SKU_INDEX)) {
            DeleteByQueryRequest request = new DeleteByQueryRequest(ObjectConstant.EsKey.SKU_INDEX);
            BoolQueryBuilder queryBuilder = new BoolQueryBuilder();
            request.setQuery(queryBuilder);
            request.setTimeout(TimeValue.timeValueMinutes(2));
            client.deleteByQuery(request, RequestOptions.DEFAULT);
        }
    }


    @Override
    public void skuStatusUp() throws IOException {
        List<Sku> skuList = skuRepository.selectAll();
        BulkRequest bulkRequest = new BulkRequest();
        skuList.forEach(x -> {
            //构造保存请求
            IndexRequest indexRequest = new IndexRequest(ObjectConstant.EsKey.SKU_INDEX);
            indexRequest.id(x.getId().toString());
            String jsonString = JSON.toJSONString(x);
            indexRequest.source(jsonString, XContentType.JSON);
            bulkRequest.add(indexRequest);
        });
        BulkResponse bulk = client.bulk(bulkRequest, RequestOptions.DEFAULT);
    }


    /**
     * 获取ES查询编辑器
     *
     * @param goodsSkuPageDTO
     * @return
     */
    private SearchRequest buildSearchRequest(GoodsSkuPageDTO goodsSkuPageDTO) {
        BoolQueryBuilder builder = QueryBuilders.boolQuery();

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        if (ObjectUtil.isNotEmpty(goodsSkuPageDTO.getCategoryIdList())||
                ObjectUtil.isNotEmpty(goodsSkuPageDTO.getSpuName()) ||
                ObjectUtil.isNotEmpty(goodsSkuPageDTO.getSpuCode()) ||
                ObjectUtil.isNotEmpty(goodsSkuPageDTO.getSpuType())) {
            List<Long> spuIds = this.esQuerySpuIds(goodsSkuPageDTO);
            if (ObjectUtil.isEmpty(spuIds)){
                builder = EsBoolQueryUtil.andTermQuery(builder, "spuId", "-1");
            }else {
                builder = EsBoolQueryUtil.inTermQuery(builder, "spuId", spuIds);
            }
        }

        if (ObjectUtil.isNotEmpty(goodsSkuPageDTO.getSkuCode())) {
            builder = EsBoolQueryUtil.andStringQuery(builder, "skuCode",goodsSkuPageDTO.getSkuCode());
        }
        if (ObjectUtil.isNotEmpty(goodsSkuPageDTO.getSkuName())) {
            builder.must(QueryBuilders.matchPhraseQuery("skuName",goodsSkuPageDTO.getSkuName()));
        }
        if (Objects.nonNull(goodsSkuPageDTO.getSetFlag())) {
            builder = EsBoolQueryUtil.andTermQuery(builder, "setFlag", goodsSkuPageDTO.getSetFlag());
        }
        if (ObjectUtil.isNotEmpty(goodsSkuPageDTO.getStatus())) {
            builder = EsBoolQueryUtil.inTermQuery(builder, "status", goodsSkuPageDTO.getStatus());
        }


        searchSourceBuilder.query(builder);
        // 排序
		searchSourceBuilder.sort("creationDate",SortOrder.DESC);
        searchSourceBuilder.trackTotalHits(true);
        EsBoolQueryUtil.page(searchSourceBuilder, goodsSkuPageDTO.getPage(), goodsSkuPageDTO.getSize());
        return new SearchRequest(new String[]{ObjectConstant.EsKey.SKU_INDEX}, searchSourceBuilder);
    }

    /**
     * 在es中查询复核条件的spuId
     *
     * @param goodsSkuPageDTO 查询条件
     * @return spuIdList
     */
    private List<Long> esQuerySpuIds(GoodsSkuPageDTO goodsSkuPageDTO) {

        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        if (ObjectUtil.isNotEmpty(goodsSkuPageDTO.getCategoryIdList())) {
            builder = EsBoolQueryUtil.inTermQuery(builder, "categoryId", goodsSkuPageDTO.getCategoryIdList());
        }
        if (ObjectUtil.isNotEmpty(goodsSkuPageDTO.getSpuName())) {
            builder.must(QueryBuilders.matchPhraseQuery("name",goodsSkuPageDTO.getSpuName()));
        }
        if (ObjectUtil.isNotEmpty(goodsSkuPageDTO.getSpuCode())) {
            builder = EsBoolQueryUtil.andStringQuery(builder, "spuCode",goodsSkuPageDTO.getSpuCode());
        }
        if (ObjectUtil.isNotEmpty(goodsSkuPageDTO.getSpuType())) {
            builder = EsBoolQueryUtil.andStringQuery(builder, "type", goodsSkuPageDTO.getSpuType());
        }

        searchSourceBuilder.size(9000);
        searchSourceBuilder.query(builder);
        searchSourceBuilder.trackTotalHits(true);
        SearchRequest searchRequest = new SearchRequest(ObjectConstant.EsKey.SPU_INDEX);
        searchRequest.source(searchSourceBuilder);
        searchRequest.scroll(TimeValue.timeValueMinutes(2L));

        List<Long> spuIds = Lists.newArrayList();
        try {
            // 执行检索请求
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            String scrollId = searchResponse.getScrollId();

            SearchHits hits = searchResponse.getHits();
            if (hits.getHits() != null && hits.getHits().length > 0) {
                for (SearchHit hit : hits.getHits()) {
                    SpuEsModel spu = JSON.parseObject(hit.getSourceAsString()).toJavaObject(SpuEsModel.class);
                    spuIds.add(spu.getId());
                }
            }
            while (true) {
                // 5、循环创建SearchScrollRequest
                SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
                //6、再指定scroll的生存时间，若不指定它会归零
                scrollRequest.scroll(TimeValue.timeValueMinutes(2L));
                //7、执行查询获取结果
                SearchResponse scrollResp = client.scroll(scrollRequest, RequestOptions.DEFAULT);
                //8、判断是否查询到了数据输出
                SearchHit[] scrollHits = scrollResp.getHits().getHits();
                if (scrollHits != null && scrollHits.length > 0) {
                    for (SearchHit hit : hits) {
                        SpuEsModel spu = JSON.parseObject(hit.getSourceAsString()).toJavaObject(SpuEsModel.class);
                        spuIds.add(spu.getId());
                    }
                } else {
                    //9、若无数据则退出
                    break;
                }
            }
            //10、创建ClearScrollRequest
            ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
            //11、指定scrollId
            clearScrollRequest.addScrollId(scrollId);
            //12、删除scrollId
            ClearScrollResponse clearScrollResponse = client.clearScroll(clearScrollRequest, RequestOptions.DEFAULT);
            //13、根据它返回判断删除成功没
            clearScrollResponse.isSucceeded();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return spuIds;
    }

    /**
     * 判断一个索引是否存在
     * @param indexName
     * @return
     */
    private  boolean isExistsIndex(String indexName) throws Exception{
        GetIndexRequest request = new GetIndexRequest(indexName);
        return  client.indices().exists(request, RequestOptions.DEFAULT);
    }
}
