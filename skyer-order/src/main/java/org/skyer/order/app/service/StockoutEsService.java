package org.skyer.order.app.service;


import org.skyer.core.domain.Page;
import org.skyer.order.api.dto.StatsStockoutDTO;
import org.skyer.order.api.es.SearchStockoutEsParam;
import org.skyer.order.api.vo.StatsStockoutVO;
import org.skyer.order.api.vo.StockoutPageVo;

import java.util.List;

/**
 * 查询或者更新es
 * @description
 * @Author lzh
 * @create 2022/01/21
 */
public interface StockoutEsService {

	/**
	 * 缺货管理
	 * @param searchOrderEsParam
	 * @return
	 */
	Page<StockoutPageVo> stockoutPage(SearchStockoutEsParam searchOrderEsParam) throws Exception;

	/**
	 * 统计缺货
	 * @param dto 统计条件
	 * @return {@link StatsStockoutVO}集合
	 */
	List<StatsStockoutVO> statsStockout(StatsStockoutDTO dto);
}
