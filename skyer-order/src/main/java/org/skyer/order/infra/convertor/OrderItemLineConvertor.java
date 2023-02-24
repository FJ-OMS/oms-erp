package org.skyer.order.infra.convertor;

import org.mapstruct.MapMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.skyer.order.api.es.OrderEsModel;
import org.skyer.order.api.vo.OrderItemLinePageVO;
import org.skyer.order.api.vo.OrderItemLineVO;
import org.skyer.order.app.dto.ResultRecordDTO;
import org.skyer.order.domain.entity.AfterSaleItemLine;
import org.skyer.order.domain.entity.Item;
import org.skyer.order.domain.entity.ItemLine;
import org.skyer.order.domain.entity.ItemSnapshot;
import org.skyer.order.infra.common.CommonConstants;
import org.skyer.order.infra.feign.vo.RpcGoodsResVO;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description
 * @Author wusc
 * @create 2022/1/19 11:20 上午
 */
@Mapper(componentModel="spring")
public interface OrderItemLineConvertor {

    @Mappings({
            @Mapping(ignore = true,target = "creationDate"),
            @Mapping(ignore = true,target = "createdBy"),
            @Mapping(ignore = true,target = "lastUpdateDate"),
            @Mapping(ignore = true,target = "lastUpdatedBy"),
            @Mapping(ignore = true,target = "objectVersionNumber"),
            @Mapping(ignore = true,target = "tableId"),
            @Mapping(ignore = true,target = "_token"),
            @Mapping(ignore = true,target = "flex"),
            @Mapping(ignore = true,target = "id"),
            @Mapping(ignore = true,target = "deleteFlag"),
            @Mapping(ignore = true,target = "_innerMap"),
            @Mapping(ignore = true,target = "_tls"),
            @Mapping(target = "outerOrderNo",source = "item.outerOrderNo"),
            @Mapping(target = "innerOrderNo",source = "item.innerOrderNo"),
            @Mapping(target = "spuCode",source = "itemSnapshot.spuCode"),
            @Mapping(target = "spuType",source = "itemSnapshot.spuType"),
            @Mapping(target = "skuCode",source = "itemSnapshot.skuCode"),
            @Mapping(target = "skuName",source = "itemSnapshot.skuName"),
            @Mapping(target = "giftFlag",source = "item.giftFlag"),
            @Mapping(target = "setFlag",source = "itemSnapshot.setFlag"),
            @Mapping(target = "multiFlag",source = "itemSnapshot.multiFlag"),
            @Mapping(constant = CommonConstants.RefundFlag.NO,target = "refundFlag"),
            @Mapping(constant = CommonConstants.ReturnFlag.NO,target = "returnFlag"),
            @Mapping(constant = CommonConstants.EndorseStatus.NORMAL,target = "endorseStatus"),
            @Mapping(constant = CommonConstants.PreemptionStatus.ITEM_LINE_PREEMPTION_WAIT,target = "preemptionStatus"),
            @Mapping(constant = CommonConstants.SourceStatus.ITEM_LINE_SOURCING_WAIT,target = "sourceStatus"),
            @Mapping(constant = CommonConstants.SuspendStatus.ITEM_LINE_NORMAL,target = "suspendStatus"),
            @Mapping(constant = CommonConstants.SendStatus.ITEM_LINE_SEND_INIT,target = "deliveryStatus"),
            @Mapping(constant = CommonConstants.PreemptionStatus.ITEM_LINE_PREEMPTION_WAIT,target = "currentStatus"),
            @Mapping(constant = CommonConstants.SyncPreempStatus.ITEM_LINE_PREEMPTION_WRITE_WAIT,target = "syncPreempStatus"),
            @Mapping(constant = CommonConstants.SyncSendStatus.ITEM_LINE_PREEMPTION_WRITE_WAIT,target = "syncDeliveryStatus"),
            @Mapping(constant = CommonConstants.ReceivingStatus.ITEM_LINE_RECEIVING_INIT,target = "receivingStatus"),
            @Mapping(target = "spuName",source = "item.spuName"),
            @Mapping(target = "costPrice",source = "itemSnapshot.costPrice"),
            @Mapping(target = "skuId",source = "item.skuId")

    })
    ItemLine itemToItemLine(Item item, ItemSnapshot itemSnapshot);


    @Mappings({
            @Mapping(ignore = true,target = "creationDate"),
            @Mapping(ignore = true,target = "createdBy"),
            @Mapping(ignore = true,target = "lastUpdateDate"),
            @Mapping(ignore = true,target = "lastUpdatedBy"),
            @Mapping(ignore = true,target = "objectVersionNumber"),
            @Mapping(ignore = true,target = "tableId"),
            @Mapping(ignore = true,target = "_token"),
            @Mapping(ignore = true,target = "flex"),
            @Mapping(ignore = true,target = "id"),
            @Mapping(ignore = true,target = "deleteFlag"),
            @Mapping(ignore = true,target = "_innerMap"),
            @Mapping(ignore = true,target = "_tls"),
            @Mapping(target = "outerOrderNo",source = "item.outerOrderNo"),
            @Mapping(target = "innerOrderNo",source = "item.innerOrderNo"),
            @Mapping(target = "spuCode",source = "rpcGoodsResVO.spuCode"),
            @Mapping(target = "spuType",source = "rpcGoodsResVO.spuType"),
            @Mapping(target = "skuCode",source = "rpcGoodsResVO.skuCode"),
            @Mapping(target = "skuName",source = "rpcGoodsResVO.skuName"),
            @Mapping(target = "giftFlag",source = "item.giftFlag"),
            @Mapping(target = "setFlag",source = "rpcGoodsResVO.setFlag"),
            @Mapping(target = "multiFlag",source = "rpcGoodsResVO.multiFlag"),
            @Mapping(constant = CommonConstants.RefundFlag.NO,target = "refundFlag"),
            @Mapping(constant = CommonConstants.ReturnFlag.NO,target = "returnFlag"),
            @Mapping(constant = CommonConstants.SuspendStatus.ITEM_LINE_NORMAL,target = "suspendStatus"),
            @Mapping(constant = CommonConstants.EndorseStatus.NORMAL,target = "endorseStatus"),
            @Mapping(constant = CommonConstants.PreemptionStatus.ITEM_LINE_PREEMPTION_WAIT,target = "preemptionStatus"),
            @Mapping(constant = CommonConstants.SourceStatus.ITEM_LINE_SOURCING_WAIT,target = "sourceStatus"),
            @Mapping(constant = CommonConstants.SendStatus.ITEM_LINE_SEND_INIT,target = "deliveryStatus"),
            @Mapping(constant = CommonConstants.PreemptionStatus.ITEM_LINE_PREEMPTION_WAIT,target = "currentStatus"),
            @Mapping(constant = CommonConstants.SyncPreempStatus.ITEM_LINE_PREEMPTION_WRITE_WAIT,target = "syncPreempStatus"),
            @Mapping(constant = CommonConstants.SyncSendStatus.ITEM_LINE_PREEMPTION_WRITE_WAIT,target = "syncDeliveryStatus"),
            @Mapping(target = "spuName",source = "rpcGoodsResVO.spuName"),
            @Mapping(target = "costPrice",source = "rpcGoodsResVO.costPrice"),
            @Mapping(target = "skuId",source = "rpcGoodsResVO.skuId"),
            @Mapping(target = "outerSaleAmount",source = "rpcGoodsResVO.salesPrice"),
            @Mapping(target = "innerSaleAmount",source = "rpcGoodsResVO.salesPrice"),
    })
    ItemLine rpcItemToItemLine(Item item, RpcGoodsResVO rpcGoodsResVO);

    OrderItemLineVO itemLineToEsItemLineDTO(ItemLine itemLine);


    List<OrderItemLineVO> itemLineToEsItemLineDTOList(List<ItemLine> itemLineList);

    @Mapping(constant = "1",target = "quantity")
    OrderItemLinePageVO itemLineToPageVo(ItemLine itemLine);


    List<OrderItemLinePageVO> itemLineTOPageVOList(List<ItemLine> itemLines);


    @Mappings({
            @Mapping(source = "resultCode",target = "deliveryResultNo"),
            @Mapping(source = "shippingNo",target = "expressNo"),
            @Mapping(source = "outLogicWarehouseName",target = "logicalWarehouseName"),
            @Mapping(source = "outLogicWarehouseCode",target = "logicalWarehouseCode"),
            @Mapping(source = "outLogicWarehouseId",target = "logicalWarehouseId"),
            @Mapping(source = "wmsCode",target = "wmsCode"),
            @Mapping(source = "logisticCompany",target = "logisticsCompany"),
            @Mapping(source = "logisticCompanyCode",target = "logisticsCompanyCode"),
            @Mapping(source = "logisticBusiness",target = "logisticsBusiness")
    })
    ItemLine resultRecordDTOToItemLine(ResultRecordDTO resultRecordDTO);

    /**
     * 将订单行转换为售后订单行
     * @param itemLine
     * @return
     */
    @Mappings({
            @Mapping(target = "creationDate", ignore = true ),
            @Mapping(target = "createdBy", ignore = true ),
            @Mapping(target = "lastUpdateDate", ignore = true ),
            @Mapping(target = "lastUpdatedBy", ignore = true ),
            @Mapping(target = "objectVersionNumber", ignore = true ),
            @Mapping(target = "tableId", ignore = true ),
            @Mapping(target = "id", ignore = true )
    })
    AfterSaleItemLine changeToAfterSaleItemLine(ItemLine itemLine);
}
