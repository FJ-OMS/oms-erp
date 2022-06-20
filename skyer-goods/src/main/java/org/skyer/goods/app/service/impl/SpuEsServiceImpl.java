package org.skyer.goods.app.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.stream.CollectorUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
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
import org.skyer.goods.api.dto.GoodsPageReqDTO;
import org.skyer.goods.api.dto.SpuListDTO;
import org.skyer.goods.app.service.SpuEsService;
import org.skyer.goods.domain.entity.Brand;
import org.skyer.goods.domain.entity.Material;
import org.skyer.goods.domain.entity.Spu;
import org.skyer.goods.domain.entity.SpuAttribute;
import org.skyer.goods.domain.repository.BrandRepository;
import org.skyer.goods.domain.repository.CategoryRepository;
import org.skyer.goods.domain.repository.MaterialRepository;
import org.skyer.goods.domain.repository.SpuRepository;
import org.skyer.goods.domain.vo.GoodsPageResVO;
import org.skyer.goods.domain.vo.SpuEsModel;
import org.skyer.goods.infra.constant.ObjectConstant;
import org.skyer.goods.infra.util.EsBoolQueryUtil;
import org.skyer.goods.infra.util.ListUtils;
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
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author: lzh
 * @date: 2022-01-06
 * @description:
 */
@Service
@Slf4j
public class SpuEsServiceImpl  implements SpuEsService {
	@Autowired
	private RestHighLevelClient client;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private BrandRepository brandRepository;
	@Autowired
	private MaterialRepository materialRepository;
	@Autowired
	private SpuRepository spuRepository;
	@Autowired
	private TransactionalProducer transactionalProducer;

	/**
	 *
	 * @param spuListDTO 含有spuList的数据
	 */
	@Override
	@Saga(description = "同步商品到es", code = "sync-spu-es", inputSchemaClass = SpuListDTO.class)
	public SpuListDTO syncSpuEsSage(SpuListDTO spuListDTO){
		return transactionalProducer.applyAndReturn(
				StartSagaBuilder.newBuilder()
						.withSagaCode("sync-spu-es").withLevel(ResourceLevel.ORGANIZATION)
						.withPayloadAndSerialize(spuListDTO).withRefType("listSpuDTO"),
				build -> {
					build.withPayloadAndSerialize(spuListDTO)
							.withRefId(spuListDTO.hashCode() + " time:" + System.currentTimeMillis());
					return spuListDTO;
				});
	}

	@Override
	@SagaTask(description = "同步商品到es sage task",
			code = "sync-spu-es-sage-task",
			sagaCode = "sync-spu-es",
			concurrentLimitNum = 2,
			concurrentLimitPolicy = SagaDefinition.ConcurrentLimitPolicy.NONE,
			seq = 1)
	public SpuListDTO syncSpuEsSageTask(String spuListDTOStr) throws IOException {

		SpuListDTO spuListDTO = JSON.parseObject(spuListDTOStr, SpuListDTO.class);
		List<Spu> spuList = spuListDTO.getSpuList();
		List<SpuEsModel> esModels = ListUtils.listConvert(spuList, SpuEsModel.class);
		if (spuListDTO.getDeleteFlag().equals(true)){
			List<String> keys = Lists.newArrayList();
			spuList.forEach(item->{
				keys.add(String.valueOf(item.getId()));
			});
			DeleteByQueryRequest request = new DeleteByQueryRequest(ObjectConstant.EsKey.SPU_INDEX);
			BoolQueryBuilder queryBuilder = new BoolQueryBuilder();
			queryBuilder = EsBoolQueryUtil.inStringQuery(queryBuilder, "id", keys);
			request.setQuery(queryBuilder);
			request.setTimeout(TimeValue.timeValueMinutes(2));
			client.deleteByQuery(request, RequestOptions.DEFAULT);
		}else {
			BulkRequest bulkRequest = new BulkRequest();
			esModels.forEach(x -> {
				//构造保存请求
				String jsonString = JSON.toJSONString(x);
				IndexRequest indexRequest = new IndexRequest(ObjectConstant.EsKey.SPU_INDEX);
				indexRequest.id(x.getId().toString());
				indexRequest.source(jsonString, XContentType.JSON);
				bulkRequest.add(indexRequest);
			});
			client.bulk(bulkRequest, RequestOptions.DEFAULT);
		}
		return spuListDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void createOrUpdateSpuEs(Spu spu) {
		try {
			String spuStr = JSON.toJSONString(spu);
			IndexRequest indexRequest = new IndexRequest(ObjectConstant.EsKey.SPU_INDEX);
			indexRequest.id(spu.getId().toString());
			indexRequest.source(spuStr, XContentType.JSON);
			client.index(indexRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			log.error(spu.getName()+"ES创建或修改异常", e);
		}
	}


	@Override
	public Page<GoodsPageResVO> querySpuEs(GoodsPageReqDTO goodsPageReqDTO) throws Exception {

		// 判断索引库是否存在
		if (!isExistsIndex(ObjectConstant.EsKey.SPU_INDEX)){
			return new Page<>();
		}

		log.info("Spu查询es");
		Page<GoodsPageResVO> vos = new Page<>();
		int size = goodsPageReqDTO.getSize();
		vos.setSize(size);
		vos.setNumber(goodsPageReqDTO.getPage() + 1);
		// 构建Es检索请求
		SearchRequest searchRequest = buildSearchRequest(goodsPageReqDTO);
		try {
			// 执行检索请求
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			SearchHits hits = searchResponse.getHits();
			Long totalElements = hits.getTotalHits().value;
			int total = totalElements.intValue();
			int page = total / size;
			List<GoodsPageResVO> goodsSpuResList =buildSearchResult(searchResponse,goodsPageReqDTO);
			vos.setContent(goodsSpuResList);
			vos.setTotalElements(goodsSpuResList.size());
			vos.setTotalPages(page);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return vos;
	}


	@Override
	public void delSpuEs(List<Spu> spuList) {
		List<String> keys = Lists.newArrayList();
		spuList.forEach(item->{
			keys.add(String.valueOf(item.getId()));
		});
		DeleteByQueryRequest request = new DeleteByQueryRequest(ObjectConstant.EsKey.SPU_INDEX);
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
	public void delSpuEsAll() {
		DeleteByQueryRequest request = new DeleteByQueryRequest(ObjectConstant.EsKey.SPU_INDEX);
		BoolQueryBuilder queryBuilder = new BoolQueryBuilder();
		request.setQuery(queryBuilder);
		request.setTimeout(TimeValue.timeValueMinutes(2));
		try {
			client.deleteByQuery(request, RequestOptions.DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void productStatusUp() throws IOException {
		List<Spu> spuList = spuRepository.selectAll();
		List<SpuEsModel> esModels = ListUtils.listConvert(spuList, SpuEsModel.class);
		try {
			BulkRequest bulkRequest = new BulkRequest();
			esModels.forEach(x->{
				//构造保存请求
				IndexRequest indexRequest = new IndexRequest(ObjectConstant.EsKey.SPU_INDEX);
				indexRequest.id(x.getId().toString());
				String jsonString = JSON.toJSONString(x);
				indexRequest.source(jsonString, XContentType.JSON);
				bulkRequest.add(indexRequest);
			});
			client.bulk(bulkRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 准备检索请求
	 * @param goodsPageReqDTO
	 * @return
	 */
	private SearchRequest buildSearchRequest(GoodsPageReqDTO goodsPageReqDTO) {

		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		// 构建bool-query
		BoolQueryBuilder builder = QueryBuilders.boolQuery();
//		// 过滤已删除
//		builder.filter(QueryBuilders.matchQuery("deleteFlag",false));
		if (Objects.nonNull(goodsPageReqDTO.getSpuCode())) {
			builder.must(QueryBuilders.termQuery("spuCode",goodsPageReqDTO.getSpuCode()));
		}
		if (Objects.nonNull(goodsPageReqDTO.getName())) {
			builder.must(QueryBuilders.matchPhraseQuery("name",goodsPageReqDTO.getName()));
		}
		if (Objects.nonNull(goodsPageReqDTO.getType())) {
			builder.filter(QueryBuilders.matchQuery("type",goodsPageReqDTO.getType()));
		}
		if (Objects.nonNull(goodsPageReqDTO.getSetFlag())) {
			builder.filter(QueryBuilders.matchQuery("setFlag",goodsPageReqDTO.getSetFlag()));
		}else {
			builder.mustNot(QueryBuilders.matchQuery("setFlag",Spu.SET_FLAG_TRUE));
		}
//		if (Objects.nonNull(goodsPageReqDTO.getStatus())) {
//			builder.filter(QueryBuilders.matchQuery("status",goodsPageReqDTO.getStatus()));
//		}
		if (CollUtil.isNotEmpty(goodsPageReqDTO.getCategoryIdList())) {
			List<Long> laterCategoryId = categoryRepository.getLaterCategoryId(goodsPageReqDTO.getCategoryIdList());
			builder = EsBoolQueryUtil.inTermQuery(builder, "categoryId", laterCategoryId);
		}
		// 封装所有的查询条件
		searchSourceBuilder.query(builder);
//		// 排序
//		searchSourceBuilder.sort("creationDate",SortOrder.DESC);
		// 分页
		EsBoolQueryUtil.page(searchSourceBuilder, goodsPageReqDTO.getPage(), goodsPageReqDTO.getSize());
		searchSourceBuilder.trackTotalHits(true);
		return new SearchRequest(new String[]{ObjectConstant.EsKey.SPU_INDEX},searchSourceBuilder);
	}

	/**
	 *	构建结果集
	 * @param response
	 * @return
	 */
	private List<GoodsPageResVO> buildSearchResult(SearchResponse response,GoodsPageReqDTO goodsPageReqDTO) {
		List<SpuEsModel> resultList = Lists.newArrayList();
		Map<Long,String> categoryMap = Maps.newHashMap();
		//1、返回的所有查询到的商品
		SearchHits hits = response.getHits();
		if (hits.getHits() != null && hits.getHits().length > 0){
			for (SearchHit hit : hits.getHits()) {
//				GoodsPageResVO goodsPageResVO = new GoodsPageResVO();
				SpuEsModel spu = JSON.parseObject(hit.getSourceAsString()).toJavaObject(SpuEsModel.class);
//				BeanUtils.copyProperties(spu,goodsPageResVO);
//				goodsPageResVO.setCode(spu.getSpuCode());
				resultList.add(spu);
			}
		}
		List<Long> collect = resultList.stream().map(SpuEsModel::getId).collect(Collectors.toList());
		if (CollUtil.isEmpty(collect)){
			return Collections.emptyList();
		}
		List<Spu> spuList = spuRepository.selectByCondition(Condition.builder(Spu.class)
				.andWhere(Sqls.custom()
						.andIn(Spu.ID, collect)
						.andEqualTo(Spu.STATUS, goodsPageReqDTO.getStatus(), true)
						.andEqualTo(Spu.DELETE_FLAG, false))
				.orderByDesc("creationDate").build());
		List<GoodsPageResVO> vos = ListUtils.listConvert(spuList, GoodsPageResVO.class);
		vos.forEach(x->{

			String categoryName = categoryMap.get(x.getCategoryId());
			if (StringUtils.isBlank(categoryName)){
				categoryName = categoryRepository.getCategoryNameById(x.getCategoryId());
				categoryMap.put(x.getCategoryId(),categoryName);
			}
			x.setCategoryName(categoryName);
			Material material = materialRepository.selectByPrimaryKey(x.getMaterialId());
			if (material !=null) {
				x.setMainImage(Arrays.asList(Optional.ofNullable(material.getMainImage()).orElse("").split(",")));
				x.setMaterialName(material.getMaterialName());
			}
			Brand brand = brandRepository.selectByPrimaryKey(x.getBrandId());
			if (brand != null){
				x.setBrandName(brand.getName());
			}
		});
		return vos;
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
