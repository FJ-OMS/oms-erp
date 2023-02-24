package org.skyer.order.app.service;

import java.lang.reflect.Field;
import java.util.List;

import org.skyer.core.domain.Page;
import org.skyer.order.api.dto.OrderPageDTO;
import org.skyer.order.api.es.OrderEsModel;
import org.skyer.order.api.es.SearchOrderEsParam;
import org.skyer.order.api.vo.OrderPageVo;

/**
 * 查询或者更新es
 * @description
 * @Author lzh
 * @create 2022/01/21
 */
public interface OrderEsService {


	/**
	 * 订单批量导入Es任务
	 * @param orderEsModelList
	 */
	void batchInsertOrderEs(List<OrderEsModel> orderEsModelList);

	/**
	 * 根据orderId批量更新es
	 * @param innerOrderNoList
	 */
	void batchInsertOrderToEs(List<String> innerOrderNoList);

	/**
	 * 批量更新es
	 * @param innerOrderNoList
	 */
	void batchInsertOrderAndAuditToEs(List<String> innerOrderNoList);

	/**
	 * 订单Es分页查询
	 * @param searchOrderEsParam
	 * @return
	 * @throws Exception
	 */
	Page<OrderPageVo> queryOrderPageEs(SearchOrderEsParam searchOrderEsParam) throws Exception;
}
