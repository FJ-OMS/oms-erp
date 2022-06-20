package org.skyer.order.app.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @description
 * @Author wusc
 * @create 2022/2/14 1:26 下午
 */
@Data
public class OrderInfoDTO {


    @ApiModelProperty(value = "预占通知单ID")
    private Long preempNoticeId;

    @ApiModelProperty(value = "订单ID(内部)")
    private Long orderId;

    @ApiModelProperty(value = "订单编号(内部)")
    private String orderCode;

    @ApiModelProperty(value = "网店名称")
    private String shopName;

    @ApiModelProperty(value = "网店ID")
    private Long shopId;

    @ApiModelProperty(value = "下单时间")
    private Date orderCreationDate;

    @ApiModelProperty(value = "平台订单号编码(第三方)")
    private String platformOrderCode;

    @ApiModelProperty(value = "平台订单号ID(第三方)")
    private Long platformOrderId;

    @ApiModelProperty(value = "订单详情")
    private List<OrderDetailDTO> detailList;
}
