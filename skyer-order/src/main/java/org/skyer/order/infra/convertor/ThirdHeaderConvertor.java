package org.skyer.order.infra.convertor;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.skyer.order.api.dto.*;
import org.skyer.order.api.es.ThirdOrderEsModel;
import org.skyer.order.api.vo.*;
import org.skyer.order.domain.entity.ThirdHeader;
import org.skyer.order.domain.entity.ThirdItem;
import org.skyer.order.domain.entity.ThirdReceiverInfo;
import org.skyer.order.infra.common.CommonConstants;

/**
 * @author zlb
 */
@Mapper(componentModel="spring")
public interface ThirdHeaderConvertor {

    /**
     *  将ThirdOrderBaseInfoDTO转换为ThirdHeader
     * @param baseInfoDTO baseInfoDTO
     * @param buyerInfoDTO buyerInfoDTO
     * @param paymentInfoDTO paymentInfoDTO
     * @param discountInfoDTO discountInfoDTO
     * @return ThirdHeader
     */
    @Mappings({
            @Mapping(target = "payStatus",source = "paymentInfoDTO.payStatus"),
            @Mapping(target = "buyerPayAmount",source = "paymentInfoDTO.payAmount"),
            @Mapping(target = "amountReceivable",source = "paymentInfoDTO.payAmount")
    })
    ThirdHeader toThirdHeader(ThirdOrderBaseInfoDTO baseInfoDTO, ThirdOrderBuyerInfoDTO buyerInfoDTO, ThirdOrderPaymentInfoDTO paymentInfoDTO, ThirdOrderDiscountInfoDTO discountInfoDTO);

    /**
     * 将ThirdHeader转换为ThirdOrderInfoVO
     * @param thirdHeader thirdHeader
     * @return ThirdOrderInfoVO
     */
    ThirdOrderInfoVO toThirdOrderInfoVO(ThirdHeader thirdHeader);

    /**
     * 将ThirdHeader转换为ThirdPaymentInfoVO
     * @param thirdHeader thirdHeader
     * @return ThirdPaymentInfoVO
     */
    ThirdAmountInfoVO toThirdAmountInfoVO(ThirdHeader thirdHeader);

    /**
     * 将ThirdHeader转换为ThirdPaymentInfoVO
     * @param thirdHeader thirdHeader
     * @return ThirdPaymentInfoVO
     */
    @Mapping(target = "payAmount",source = "thirdHeader.buyerPayAmount")
    ThirdPaymentInfoVO toThirdPaymentInfoVO(ThirdHeader thirdHeader);

    /**
     * 将ThirdReceiverInfo转换为ThirdReceiverInfoVO
     * @param thirdReceiverInfo thirdReceiverInfo
     * @return ThirdReceiverInfoVO
     */
    @Mapping(target = "buyerNick",source = "thirdReceiverInfo.receiverName")
    ThirdReceiverInfoVO toThirdReceiverInfoVO(ThirdReceiverInfo thirdReceiverInfo);

    /**
     * 将thirdOrderBuyerInfo转换为ThirdReceiverInfo
     * @param thirdOrderBuyerInfo thirdOrderBuyerInfo
     * @return ThirdOrderInfoVO
     */
    ThirdReceiverInfo toThirdReceiverInfo(ThirdOrderBuyerInfoDTO thirdOrderBuyerInfo);

    /**
     * 将thirdOrderItem转换为ThirdItem
     * @param thirdOrderItemDTO thirdOrderItemDTO
     * @return ThirdItem
     */
    ThirdItem toThirdItem(ThirdOrderItemDTO thirdOrderItemDTO);

    /**
     * 将ThirdItem转换为ThirdItemVO
     * @param thirdItem thirdItem
     * @return ThirdItemVO
     */
    ThirdItemVO toThirdItemVO(ThirdItem thirdItem);

    /**
     * 转换为ThirdOrderEsModel
     * @param thirdHeader thirdHeader
     * @param thirdReceiverInfo thirdReceiverInfo
     * @return ThirdOrderEsModel
     */
    @Mappings({
            @Mapping(target = "id",source = "thirdHeader.id"),
            @Mapping(target = "outerOrderNo",source = "thirdHeader.outerOrderNo"),
            @Mapping(target = "innerOrderNo",source = "thirdHeader.innerOrderNo"),
            @Mapping(target = "creationDate",source = "thirdHeader.creationDate"),
            @Mapping(target = "createdBy",source = "thirdHeader.createdBy"),
            @Mapping(target = "lastUpdateDate",source = "thirdHeader.lastUpdateDate"),
            @Mapping(target = "lastUpdatedBy",source = "thirdHeader.lastUpdatedBy"),
            @Mapping(target = "goodsTotalAmount",source = "thirdHeader.totalAmount")
    })
    ThirdOrderEsModel toThirdOrderEsModel(ThirdHeader thirdHeader,ThirdReceiverInfo thirdReceiverInfo);

    /**
     * 将ThirdOrderEsModel转换为ThirdOrderPageVo
     * @param thirdOrderEsModel thirdOrderEsModel
     * @return ThirdOrderPageVo
     */
    ThirdOrderPageVo toThirdOrderPageVo(ThirdOrderEsModel thirdOrderEsModel);

    /**
     * 三方基础信息转内部dto
     * @param thirdOrderBaseInfoDto thirdOrderBaseInfoDto
     * @return OrderBaseInfoDTO
     */
    OrderBaseInfoDTO toOrderBaseInfoDTO(ThirdOrderBaseInfoDTO thirdOrderBaseInfoDto);

    /**
     * 三方买家信息转内部dto
     * @param thirdOrderBuyerInfoDto thirdOrderBuyerInfoDto
     * @return OrderBuyerInfoDTO
     */
    OrderBuyerInfoDTO toOrderBuyerInfoDTO(ThirdOrderBuyerInfoDTO thirdOrderBuyerInfoDto);

    /**
     * 三方支付信息转内部dto
     * @param thirdOrderPaymentInfoDto thirdOrderPaymentInfoDto
     * @return OrderPaymentInfoDTO
     */
    OrderPaymentInfoDTO toOrderPaymentInfoDTO (ThirdOrderPaymentInfoDTO thirdOrderPaymentInfoDto);

    /**
     * 转化为内部item
     * @param thirdOrderItemDTO thirdOrderItemDTO
     * @return OrderItemDTO
     */
    OrderItemDTO toOrderItemDTO(ThirdOrderItemDTO thirdOrderItemDTO);
}
