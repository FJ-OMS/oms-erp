package org.skyer.order.infra.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.skyer.order.api.dto.AccountCheckingOrderDTO;
import org.skyer.order.api.dto.AccountCheckingOrderQueryDTO;
import org.skyer.order.api.dto.OrderPageDTO;
import org.skyer.order.api.dto.OrderSaleDTO;
import org.skyer.order.api.vo.OrderPageVo;
import org.skyer.order.api.vo.OrderSaleDateVO;
import org.skyer.order.api.vo.OrderSaleReportVO;
import org.skyer.order.app.dto.SaleOrderOrderApiDTO;
import org.skyer.order.app.vo.ChannelSaleOrderVO;
import org.skyer.order.domain.entity.Header;
import org.skyer.mybatis.common.BaseMapper;

/**
 * 内部订单头Mapper
 *
 * @author wushaochuan 2022-01-18 14:34:53
 */
public interface HeaderMapper extends BaseMapper<Header> {

	/**
	 * 订单分页列表
	 * @param orderPageDTO
	 * @return
	 */
	List<OrderPageVo> getListPage(@Param("orderPageDTO") OrderPageDTO orderPageDTO);

	/**
	 * 根据第三方订单编码集合获取（远程）
	 * @author lixiaoyang
	 * @date 2022/3/23 13:34
	 */
	List<AccountCheckingOrderDTO> getOrderList(@Param("dto") AccountCheckingOrderQueryDTO dto);

	List<AccountCheckingOrderDTO> getAfterSaleList(@Param("dto") AccountCheckingOrderQueryDTO dto);

	List<OrderSaleReportVO> queryPartHeaderToSaleReport(@Param("dto") OrderSaleDTO dto);

	List<ChannelSaleOrderVO> queryPartChannelSaleReport(@Param("startTime") Date startDate,@Param("endTime") Date endDate,@Param("storeCodeList") List<String> storeCodeList);

	List<OrderSaleDateVO> queryOrderSaleDateReport(@Param("dto") OrderSaleDTO orderSaleDTO);

}
