package org.skyer.order.infra.convertor;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

import org.skyer.order.api.dto.ThirdPartyReceiveInfoDTO;
import org.skyer.order.api.dto.ThirdPartyRefundBaseInfo;
import org.skyer.order.domain.entity.*;
import org.skyer.order.infra.common.CommonConstants;

@Mapper(componentModel="spring")
public interface ThirdPartyAfterSaleConvertor {
	/**
	 * ThirdPartyRefundBaseInfo转为ThirdPartyAfterSaleHeader
	 * @param refundBaseInfo
	 * @return
	 */

	ThirdPartyAfterSaleHeader BaseInfoconvertToHeader(ThirdPartyRefundBaseInfo refundBaseInfo);

	/**
	 * ThirdPartyAfterReceiverInfo转换为ThirdPartyReceiveInfoDTO
	 * @param thirdPartyReceiveInfo
	 * @return
	 */
	ThirdPartyAfterReceiverInfo converToReceiverInfo(ThirdPartyReceiveInfoDTO thirdPartyReceiveInfo);

	/**
	 * ThirdPartyReturnLogistics转换为ThirdPartyReceiveInfoDTO
	 * @param thirdPartyAfterSaleLogisticsList
	 * @return
	 */
	ThirdPartyReturnLogistics converToLogistics(ThirdPartyReturnLogistics thirdPartyAfterSaleLogisticsList);

	/**
	 * 三方退货售后单转换为内部售后单
	 * @param baseInfo
	 * @return
	 */
	@Mappings({
			@Mapping(target = "creationDate", ignore = true ),
			@Mapping(target = "createdBy", ignore = true ),
			@Mapping(target = "lastUpdateDate", ignore = true ),
			@Mapping(target = "lastUpdatedBy", ignore = true ),
			@Mapping(target = "objectVersionNumber", ignore = true ),
			@Mapping(target = "tableId", ignore = true ),
			@Mapping(target = "id", ignore = true ),
			@Mapping(target = "refundAmount", source = "totalAmount" ),
			@Mapping(target = "actionType", constant = CommonConstants.AfterSaleActionType.GENERAL),
			@Mapping(target = "orderRefundType", constant = "0")
	})
	AfterSaleHeader thirdChangeInnerReturnHeader(ThirdPartyAfterSaleHeader baseInfo);

}
