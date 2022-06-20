package org.skyer.order;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import springfox.documentation.spring.web.json.Json;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.skyer.order.api.es.OrderEsModel;
import org.skyer.order.app.service.OrderEsService;
import org.skyer.order.domain.entity.Header;
import org.skyer.order.domain.repository.HeaderRepository;
import org.skyer.order.infra.common.CommonConstants;
import org.skyer.order.infra.util.EsBoolQueryUtil;

/**
 * @author: lzh
 * @date: 2022-01-22
 * @description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderApplication.class)
public class EsTest {

	@Autowired
	private OrderEsService orderEsService;
	@Autowired
	private HeaderRepository headerRepository;
	@Autowired
	private RestHighLevelClient client;



	@Test
	public void getOrderEsById() throws IOException {
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		// 构建bool-query
		BoolQueryBuilder builder = QueryBuilders.boolQuery();
		searchSourceBuilder.query(builder);
		searchSourceBuilder.trackTotalHits(true);
		SearchRequest searchRequest = new SearchRequest(new String[]{CommonConstants.EsConstant.ORDER_INDEX}, searchSourceBuilder);
		SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
		SearchHits hits = searchResponse.getHits();
		for (SearchHit hit : hits.getHits()) {
			System.out.println(JSON.toJSONString(hit));
		}
	}
}
