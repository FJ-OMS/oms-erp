package org.skyer.order.infra.mapper;

import org.apache.ibatis.annotations.Param;

import org.skyer.order.api.dto.*;
import org.skyer.order.api.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

import org.skyer.order.app.vo.ChannelSaleOrderVO;
import org.skyer.order.domain.entity.AfterSaleHeader;
import org.skyer.mybatis.common.BaseMapper;


/**
 * 售后单Mapper
 *
 * @author lzh 2022-03-16 10:55:39
 */
public interface AfterSaleHeaderMapper extends BaseMapper<AfterSaleHeader> {

	/**
	 * 退货退款分页
	 * @param refundPageDTO
	 * @return
	 */
	List<RefundPageVO> refundPage(@Param("refundPageDTO") RefundPageDTO refundPageDTO);

	/**
	 * 退货退款审核页面
	 * @param refundPageDTO
	 * @return
	 */
	List<RefundPageVO> refundAuditPage(@Param("refundPageDTO") RefundAuditPageDTO refundPageDTO);

	/**
	 *  仅退款分页查询
	 * @param refundOnlyPageDto refundOnlyPageDto
	 * @return List<RefundOnlyPageVO>
	 */
	List<RefundOnlyPageVO> refundOnlyPage(@Param("param")RefundOnlyPageDTO refundOnlyPageDto);

	/**
	 * @author lixiaoyang
	 * @date 2022/3/25 9:54
	 */
	List<AfterSaleDetailVO> getDetail(@Param("refundCode")String refundCode);

	/**
	 * 查询售后单报表
	 * @param orderSaleDTO
	 * @return
	 */
	List<OrderSaleReportVO> queryAfterHeaderToSaleReport(@Param("orderSaleDTO") OrderSaleDTO orderSaleDTO);

	List<ChannelSaleOrderVO> queryParReverseChannelSaleReport(@Param("startTime") Date startDate, @Param("endTime") Date endDate, @Param("storeCodeList") List<String> storeCodeList);

	List<OrderSaleDateVO> queryAfterOrderSaleDateReport(@Param("dto") OrderSaleDTO orderSaleDTO);
}
