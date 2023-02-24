package org.skyer.order.infra.convertor;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

import org.skyer.order.api.dto.OrderRefundInfoDTO;
import org.skyer.order.api.dto.RefundInfoDTO;
import org.skyer.order.api.dto.RefundLogisticsDTO;
import org.skyer.order.api.dto.UpdateRefundDTO;
import org.skyer.order.api.vo.RefundBaseInfoVO;
import org.skyer.order.api.vo.RefundLogisticsInfoVO;
import org.skyer.order.api.vo.RefundsLogisticsVO;
import org.skyer.order.domain.entity.AfterSaleHeader;
import org.skyer.order.domain.entity.AfterSaleLogistics;
import org.skyer.order.infra.common.CommonConstants;

/**
 * @author: lzh
 * @date: 2022-03-16
 * @description:
 */
@Mapper(componentModel="spring")
public interface RefundConvertor {


	/**
	 * 创建售后单DTO转换
	 * @param orderRefundInfoDTO
	 * @param refundInfoDTO
	 * @param dto
	 * @return
	 */
	@Mappings({
			@Mapping(target = "serviceRemarks",source = "refundInfoDTO.serviceRemarks"),
			@Mapping(target = "buyerRemarks",source = "orderRefundInfoDTO.buyerRemarks"),
			@Mapping(target = "orderRefundType",source = "refundInfoDTO.orderRefundType"),
			@Mapping(target = "actionType",constant = CommonConstants.OrderActionType.HAND_ORDER)
	})
	AfterSaleHeader orderTORefund(OrderRefundInfoDTO orderRefundInfoDTO, RefundInfoDTO refundInfoDTO, RefundLogisticsDTO dto);

	/**
	 * 详情基础信息
	 * @param afterSaleHeader
	 * @return
	 */
	RefundBaseInfoVO changeDetailsBase(AfterSaleHeader afterSaleHeader);

	/**
	 * 详情物流信息
	 * @param afterSaleHeader
	 * @return
	 */
	RefundLogisticsInfoVO dtoToLogistics(AfterSaleHeader afterSaleHeader);

	/**
	 * 修改基础信息
	 * @param updateRefundDTO
	 * @return
	 */
	AfterSaleHeader toSale(UpdateRefundDTO updateRefundDTO);

	/**
	 * 物流信息
	 * @param afterSaleLogistics
	 * @return
	 */
	List<RefundsLogisticsVO> dtoToLogisticsVO(List<AfterSaleLogistics> afterSaleLogistics);
}
