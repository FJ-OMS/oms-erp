package org.skyer.order.app.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.skyer.order.api.vo.OrderItemLineVO;
import org.skyer.order.domain.entity.*;
import org.skyer.order.domain.repository.*;
import org.skyer.order.infra.convertor.OrderItemLineConvertor;
import org.skyer.order.infra.enums.PriceTypeEnum;

import org.skyer.boot.platform.lov.annotation.ProcessLovValue;
import org.skyer.order.domain.entity.Item;
import org.skyer.order.domain.entity.ReceiverInfo;
import org.skyer.order.domain.repository.ItemRepository;
import org.skyer.order.domain.repository.ReceiverInfoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import org.skyer.core.domain.Page;
import org.skyer.order.api.es.OrderEsModel;
import org.skyer.order.api.es.SearchOrderEsParam;
import org.skyer.order.api.vo.OrderPageVo;
import org.skyer.order.app.service.OrderEsService;
import org.skyer.order.infra.common.CommonConstants;
import org.skyer.order.infra.convertor.OrderEsConvertor;
import org.skyer.order.infra.util.EsBoolQueryUtil;

/**
 * @author: lzh
 * @date: 2022-01-21
 * @description:
 */
@Service
@Slf4j
public class OrderEsServiceImpl implements OrderEsService {

	@Autowired
	private RestHighLevelClient client;
	@Autowired
	private HeaderRepository headerRepository;
	@Autowired
	private OrderEsConvertor orderEsConvertor;
	@Autowired
	private ReceiverInfoRepository receiverInfoRepository;
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private ItemLineRepository itemLineRepository;
	@Autowired
	private OrderItemLineConvertor orderItemLineConvertor;
	@Autowired
	private PriceRepository priceRepository;

	@Override
	public void batchInsertOrderEs(List<OrderEsModel> orderEsModelList) {
		BulkRequest request = new BulkRequest();
		log.info("开始批量插入订单到es,{}", JSON.toJSONString(orderEsModelList));
		orderEsModelList.forEach(order -> {
			IndexRequest indexRequest = new IndexRequest(CommonConstants.EsConstant.ORDER_INDEX);
			String toJSONString = JSON.toJSONString(order);
			indexRequest.source(toJSONString, XContentType.JSON);
			indexRequest.id(String.valueOf(order.getId()));
			request.add(indexRequest);
		});
		try {
			BulkResponse bulk = client.bulk(request, RequestOptions.DEFAULT);
			log.info("批量插入订单到es,{}", JSON.toJSONString(bulk));
		} catch (IOException e) {
			log.info("订单ES插入异常" + e.getMessage());
		}
	}

	@Override
	@ProcessLovValue
	public Page<OrderPageVo> queryOrderPageEs(SearchOrderEsParam searchOrderEsParam) throws Exception {
		Page<OrderPageVo> vos = new Page<>();
		int size = searchOrderEsParam.getSize();
		vos.setSize(size);
		vos.setNumber(searchOrderEsParam.getPage() + 1);
		// 判断索引库是否存在
		if (!isExistsIndex(CommonConstants.EsConstant.ORDER_INDEX)){
			return new Page<>();
		}
		try {
			// 构建Es检索请求
			SearchRequest searchRequest = buildSearchRequest(searchOrderEsParam);
			// 执行检索请求
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			SearchHits hits = searchResponse.getHits();
			long totalElements = hits.getTotalHits().value;
			int total = (int) totalElements;
			int page = total / size;
			// 构建检索hits结果
			List<OrderPageVo> orderPageVos = buildSearchResult(searchResponse, searchOrderEsParam);
			vos.setContent(orderPageVos);
			vos.setTotalElements(totalElements);
			vos.setTotalPages(page);
		} catch (IOException e) {
			log.info(e.getMessage());
		}
		return vos;
	}

	/**
	 * 构建查询请求
	 *
	 * @param searchOrderEsParam
	 * @return
	 */
	private SearchRequest buildSearchRequest(SearchOrderEsParam searchOrderEsParam){
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		// 构建bool-query
		BoolQueryBuilder builder = QueryBuilders.boolQuery();
		builder = getBoolQueryBuilder( builder, searchOrderEsParam);
		// 封装所有的查询条件
		searchSourceBuilder.query(builder);
		// 排序
		searchSourceBuilder.sort("creationDate", SortOrder.DESC);
		// 分页
		EsBoolQueryUtil.page(searchSourceBuilder, searchOrderEsParam.getPage(), searchOrderEsParam.getSize());
		searchSourceBuilder.trackTotalHits(true);
		return new SearchRequest(new String[]{CommonConstants.EsConstant.ORDER_INDEX}, searchSourceBuilder);
	}

	/**
	 * 构建结果集
	 *
	 * @param response
	 * @param searchOrderEsParam
	 * @return
	 */
	private List<OrderPageVo> buildSearchResult(SearchResponse response, SearchOrderEsParam searchOrderEsParam) {

		List<OrderPageVo> resultList = Lists.newArrayList();
		//1、返回的所有查询到的商品
		SearchHits hits = response.getHits();
		if (hits.getHits() != null && hits.getHits().length > 0) {
			for (SearchHit hit : hits.getHits()) {
				OrderPageVo pageVo = new OrderPageVo();
				OrderEsModel orderEsModel = JSON.parseObject(hit.getSourceAsString()).toJavaObject(OrderEsModel.class);
				BeanUtils.copyProperties(orderEsModel,pageVo);
				pageVo.setTagsList(orderEsModel.getTagsList());
				resultList.add(pageVo);
			}
		}
		return resultList;
	}


	/**
	 * 请求参数
	 *
	 * @return
	 */
	public BoolQueryBuilder getBoolQueryBuilder(BoolQueryBuilder builder, SearchOrderEsParam searchOrderEsParam) {
		// o2o订单tab页
		if (Objects.nonNull(searchOrderEsParam.getBusinessType())) {
			builder.must(QueryBuilders.termQuery("businessType",searchOrderEsParam.getBusinessType()));
		}
		// 订单状态tab页切换
		if (Objects.nonNull(searchOrderEsParam.getOrderStatus())) {
			builder.filter(QueryBuilders.termQuery("orderStatus",searchOrderEsParam.getOrderStatus()));
		}
		if (Objects.nonNull(searchOrderEsParam.getOrderNo())) {
			List<String> orderNoItem = Arrays.asList(searchOrderEsParam.getOrderNo().split(","));
			String [] arr = new String[] {"outerOrderNo","innerOrderNo"};
			builder = EsBoolQueryUtil.inMultiFieldPhraseQuery(builder, orderNoItem,arr);
		}
		if (Objects.nonNull(searchOrderEsParam.getChannel())) {
			builder.must(QueryBuilders.termQuery("channel",searchOrderEsParam.getChannel()));
		}
		if (Objects.nonNull(searchOrderEsParam.getStoreIdList())) {
			builder = EsBoolQueryUtil.inTermQuery(builder, "storeId", searchOrderEsParam.getStoreIdList());
		}
		if (Objects.nonNull(searchOrderEsParam.getExpressNo())) {
			builder.must(QueryBuilders.termQuery("orderItemLineList.expressNo", searchOrderEsParam.getExpressNo()));
		}
		if (Objects.nonNull(searchOrderEsParam.getSpuName())) {
			List<String> arrayList = Lists.newArrayList();
			String [] arr = new String[] {"orderItemLineList.spuCode","orderItemLineList.spuName"};
			arrayList.add(searchOrderEsParam.getSpuName());
			builder = EsBoolQueryUtil.inMultiFieldPhraseQuery(builder, arrayList,arr);
		}
		if (Objects.nonNull(searchOrderEsParam.getSkuCode())) {
			builder = EsBoolQueryUtil.andStringQuery(builder, "orderItemLineList.skuCode",searchOrderEsParam.getSkuCode());
		}

		if (Objects.nonNull(searchOrderEsParam.getActionType())) {
			builder.must(QueryBuilders.termQuery("actionType",searchOrderEsParam.getActionType()));
		}
		if (Objects.nonNull(searchOrderEsParam.getIsGift())) {
			builder.must(QueryBuilders.termQuery("isGift",searchOrderEsParam.getIsGift()));
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
		if (Objects.nonNull(searchOrderEsParam.getBuyerRemarks())) {
			builder.must(QueryBuilders.matchPhraseQuery("buyerRemarks",searchOrderEsParam.getBuyerRemarks()));
		}
		if (Objects.nonNull(searchOrderEsParam.getOrderRemarks())) {
			builder.must(QueryBuilders.matchPhraseQuery("orderRemarks",searchOrderEsParam.getOrderRemarks()));
		}
		if (Objects.nonNull(searchOrderEsParam.getServiceRemarks())) {
			builder.must(QueryBuilders.matchPhraseQuery("serviceRemarks",searchOrderEsParam.getServiceRemarks()));
		}
		if (Objects.nonNull(searchOrderEsParam.getTags())) {
			BoolQueryBuilder shouldBuilder = new BoolQueryBuilder();
			for(String value : searchOrderEsParam.getTags()) {
				shouldBuilder = shouldBuilder.should(new MatchPhraseQueryBuilder("tagsList", value));
			}
			builder = builder.must(shouldBuilder);
		}
		if (Objects.nonNull(searchOrderEsParam.getIsBuyerRemarks())) {
			builder.must(QueryBuilders.matchPhraseQuery("isBuyerRemarks",searchOrderEsParam.getIsBuyerRemarks()));
		}
		if (Objects.nonNull(searchOrderEsParam.getIsOrderRemarks())) {
			builder.must(QueryBuilders.matchPhraseQuery("isOrderRemarks",searchOrderEsParam.getIsOrderRemarks()));
		}
		if (Objects.nonNull(searchOrderEsParam.getIsServiceRemarks())) {
			builder.must(QueryBuilders.matchPhraseQuery("isServiceRemarks",searchOrderEsParam.getIsServiceRemarks()));
		}
		if (Objects.nonNull(searchOrderEsParam.getDeliveryStatus())) {
			builder.must(QueryBuilders.termQuery("deliveryStatus",searchOrderEsParam.getDeliveryStatus()));
		}
		if (Objects.nonNull(searchOrderEsParam.getReceiveStatus())) {
			builder.must(QueryBuilders.termQuery("receivingStatus",searchOrderEsParam.getReceiveStatus()));
		}
		if (Objects.nonNull(searchOrderEsParam.getDeliveryStatusList())) {
			builder = EsBoolQueryUtil.inStringQuery(builder, "deliveryStatus", searchOrderEsParam.getDeliveryStatusList());
		}
		if (Objects.nonNull(searchOrderEsParam.getPreemptionStatus())) {
			builder.must(QueryBuilders.termQuery("preemptionStatus",searchOrderEsParam.getPreemptionStatus()));
		}
		if (Objects.nonNull(searchOrderEsParam.getSourceStatus())) {
			builder.must(QueryBuilders.termQuery("sourceStatus",searchOrderEsParam.getSourceStatus()));
		}
		if (Objects.nonNull(searchOrderEsParam.getSuspendStatus())) {
			builder.must(QueryBuilders.termQuery("suspendStatus",searchOrderEsParam.getSuspendStatus()));
		}
		if (Objects.nonNull(searchOrderEsParam.getStockFlag())) {
			builder.must(QueryBuilders.termQuery("stockFlag",searchOrderEsParam.getStockFlag()));
		}
		if (Objects.nonNull(searchOrderEsParam.getSyncPreempStatus())) {
			builder.must(QueryBuilders.termQuery("syncPreempStatus",searchOrderEsParam.getSyncPreempStatus()));
		}
		if (Objects.nonNull(searchOrderEsParam.getSyncDeliveryStatus())) {
			builder.must(QueryBuilders.termQuery("syncDeliveryStatus",searchOrderEsParam.getSyncDeliveryStatus()));
		}
		if (Objects.nonNull(searchOrderEsParam.getOrderStartTime()) && Objects.nonNull(searchOrderEsParam.getOrderEndTime()) ) {
			builder.must(QueryBuilders.rangeQuery("orderTime").gte(searchOrderEsParam.getOrderStartTime().getTime()).lt(searchOrderEsParam.getOrderEndTime().getTime()));
		}
		if (Objects.nonNull(searchOrderEsParam.getPayStartTime()) && Objects.nonNull(searchOrderEsParam.getPayEndTime()) ) {
			builder.must(QueryBuilders.rangeQuery("payTime").gte(searchOrderEsParam.getPayStartTime().getTime()).lt(searchOrderEsParam.getPayEndTime().getTime()));
		}
		if (Objects.nonNull(searchOrderEsParam.getCreationStartDate()) && Objects.nonNull(searchOrderEsParam.getCreationEndDate()) ) {
			builder.must(QueryBuilders.rangeQuery("creationDate").gte(searchOrderEsParam.getCreationStartDate().getTime()).lt(searchOrderEsParam.getCreationEndDate().getTime()));
		}
		if (Objects.nonNull(searchOrderEsParam.getPredictDeliveryStartTime()) && Objects.nonNull(searchOrderEsParam.getPredictDeliveryEndTime()) ) {
			builder.must(QueryBuilders.rangeQuery("predictDeliveryTime").gte(searchOrderEsParam.getPredictDeliveryStartTime().getTime()).lt(searchOrderEsParam.getPredictDeliveryEndTime().getTime()));
		}
		if (Objects.nonNull(searchOrderEsParam.getDeliveryStartTime()) && Objects.nonNull(searchOrderEsParam.getDeliveryEndTime()) ) {
			builder.must(QueryBuilders.rangeQuery("deliveryTime").gte(searchOrderEsParam.getDeliveryStartTime().getTime()).lt(searchOrderEsParam.getDeliveryEndTime().getTime()));
		}
		if (Objects.nonNull(searchOrderEsParam.getReceivedStartTime()) && Objects.nonNull(searchOrderEsParam.getReceivedEndTime()) ) {
			builder.must(QueryBuilders.rangeQuery("receivedTime").gte(searchOrderEsParam.getReceivedStartTime()).lt(searchOrderEsParam.getReceivedEndTime()));
		}
		if (Objects.nonNull(searchOrderEsParam.getTotalAmountMin()) && Objects.nonNull(searchOrderEsParam.getTotalAmountMax()) ) {
			builder.must(QueryBuilders.rangeQuery("totalAmount").gte(searchOrderEsParam.getTotalAmountMin()).lte(searchOrderEsParam.getTotalAmountMax()));
		}else if (Objects.nonNull(searchOrderEsParam.getTotalAmountMin()) && Objects.isNull(searchOrderEsParam.getTotalAmountMax())){
			builder.must(QueryBuilders.rangeQuery("totalAmount").gte(searchOrderEsParam.getTotalAmountMin()));
		}else if (Objects.isNull(searchOrderEsParam.getTotalAmountMin()) && Objects.nonNull(searchOrderEsParam.getTotalAmountMax())){
			builder.must(QueryBuilders.rangeQuery("totalAmount").lte(searchOrderEsParam.getTotalAmountMax()));
		}
		if (Objects.nonNull(searchOrderEsParam.getBuyerPayMin()) && Objects.nonNull(searchOrderEsParam.getBuyerPayMax()) ) {
			builder.must(QueryBuilders.rangeQuery("buyerPay").gte(searchOrderEsParam.getBuyerPayMin()).lte(searchOrderEsParam.getBuyerPayMax()));
		}else if (Objects.nonNull(searchOrderEsParam.getBuyerPayMin()) && Objects.isNull(searchOrderEsParam.getBuyerPayMax())){
			builder.must(QueryBuilders.rangeQuery("buyerPay").gte(searchOrderEsParam.getBuyerPayMin()));
		}else if (Objects.isNull(searchOrderEsParam.getBuyerPayMin()) && Objects.nonNull(searchOrderEsParam.getBuyerPayMax())){
			builder.must(QueryBuilders.rangeQuery("buyerPay").lte(searchOrderEsParam.getBuyerPayMax()));
		}
		if (Objects.nonNull(searchOrderEsParam.getCostPriceMin()) && Objects.nonNull(searchOrderEsParam.getCostPriceMax()) ) {
			builder.must(QueryBuilders.rangeQuery("costPrice").gte(searchOrderEsParam.getCostPriceMin()).lte(searchOrderEsParam.getCostPriceMax()));
		}else if (Objects.nonNull(searchOrderEsParam.getCostPriceMin()) && Objects.isNull(searchOrderEsParam.getCostPriceMax())){
			builder.must(QueryBuilders.rangeQuery("costPrice").gte(searchOrderEsParam.getCostPriceMin()));
		}else if (Objects.isNull(searchOrderEsParam.getCostPriceMin()) && Objects.nonNull(searchOrderEsParam.getCostPriceMax())){
			builder.must(QueryBuilders.rangeQuery("costPrice").lte(searchOrderEsParam.getCostPriceMax()));
		}
		return builder;
	}

	@Override
	public void batchInsertOrderToEs(List<String> innerOrderNoList) {
		List<Header> headerList = headerRepository.queryHeaderByInnerOrderNo(innerOrderNoList);
		Map<String, List<ItemLine>> itemLineMap = itemLineRepository.queryItemLineByInner(innerOrderNoList);
		Map<String, ReceiverInfo> receiverInfoMap = receiverInfoRepository.queryReceiveInfoListByInner(innerOrderNoList);
		List<OrderEsModel> orderEsModelList = headerList.parallelStream().map(header -> {
			Map<String, List<Item>> itemMap = itemRepository.queryItemByInner(innerOrderNoList);
			List<Item> items = itemMap.get(header.getInnerOrderNo());
			ReceiverInfo receiverInfo = receiverInfoMap.get(header.getInnerOrderNo());
			OrderEsModel orderEsModel = orderEsConvertor.orderToEs(header, receiverInfo);
			String address ="";
			if (Objects.nonNull(receiverInfo.getProvince())) {
				address = receiverInfo.getProvince() + receiverInfo.getCity() + receiverInfo.getDistrict() + receiverInfo.getAddress();
			}
			orderEsModel.setIsBuyerRemarks(ObjectUtil.isNotNull(orderEsModel.getBuyerRemarks()));
			orderEsModel.setIsOrderRemarks(ObjectUtil.isNotNull(orderEsModel.getOrderRemarks()));
			orderEsModel.setIsServiceRemarks(ObjectUtil.isNotNull(orderEsModel.getServiceRemarks()));
			if(ObjectUtil.isNotEmpty(header.getTags())){
				List<String> tagsList = Arrays.asList(header.getTags().split(","));
				orderEsModel.setTagsList(tagsList);
			}
			orderEsModel.setIsGift(false);
			items.forEach(item -> {
				if (Integer.valueOf(1).equals(item.getGiftFlag())) {
					orderEsModel.setIsGift(true);
				}
			});

			if (itemLineMap !=null){
				List<ItemLine> itemLines = itemLineMap.get(header.getInnerOrderNo());
				List<OrderItemLineVO> orderItemLines = orderItemLineConvertor.itemLineToEsItemLineDTOList(itemLines);
				orderEsModel.setOrderItemLineList(orderItemLines);
			}
			//设置金额
			Map<String, Price> priceMap = priceRepository.queryOrderPrice(header.getInnerOrderNo());
			Price price = Optional.ofNullable(priceMap.get(PriceTypeEnum.GOODS_TOTAL_PRICE.getType())).orElse(new Price());
			Price realPrice = Optional.ofNullable(priceMap.get(PriceTypeEnum.BUYER_REAL_PRICE.getType())).orElse(new Price());
			Price needPrice = Optional.ofNullable(priceMap.get(PriceTypeEnum.BUYER_NEED_PRICE.getType())).orElse(new Price());
			Price costPrice = Optional.ofNullable(priceMap.get(PriceTypeEnum.COST_TOTAL_PRICE.getType())).orElse(new Price());
			orderEsModel.setBuyerRealPrice(realPrice.getAmount());
			orderEsModel.setBuyerPay(needPrice.getAmount());
			orderEsModel.setTotalAmount(price.getAmount());
			orderEsModel.setCostPrice(costPrice.getAmount());
			orderEsModel.setAddress(address);
			return orderEsModel;
		}).collect(Collectors.toList());
		this.batchInsertOrderEs(orderEsModelList);
	}

	@Override
	public void batchInsertOrderAndAuditToEs(List<String> innerOrderNoList) {
		// 插入es
		this.batchInsertOrderToEs(innerOrderNoList);
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
