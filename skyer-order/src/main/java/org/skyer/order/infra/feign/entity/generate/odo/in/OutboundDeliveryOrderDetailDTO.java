package org.skyer.order.infra.feign.entity.generate.odo.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class OutboundDeliveryOrderDetailDTO {
    @ApiModelProperty(value = "商品SKU编码")
    private String skuCode;

    @ApiModelProperty(value = "商品编码")
    private String spuCode;

    @ApiModelProperty(value = "商品名称")
    private String spuName;

    @ApiModelProperty(value = "商品分类名称")
    private String categoryName;

    @ApiModelProperty(value = "订单出库数量")
    private Integer orderQty;

    @ApiModelProperty(value = "发货逻辑仓ID")
    private Long outLogicWarehouseId;

    @ApiModelProperty(value = "物流公司")
    private String logisticCompany;

    @ApiModelProperty(value = "物流编号")
    private String logisticCompanyCode;

    @ApiModelProperty(value = "物流业务")
    private String logisticBusiness;

    @ApiModelProperty(value = "商品行")
    List<Long> orderGoodsLineIds;
}
