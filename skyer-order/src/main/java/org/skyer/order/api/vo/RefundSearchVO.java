package org.skyer.order.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.starter.keyencrypt.core.Encrypt;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author zhanglongbing
 */
@Data
public class RefundSearchVO {

    @ApiModelProperty("主键id")
    @Id
    @Encrypt
    @GeneratedValue
    private Long id;
    @ApiModelProperty(value = "内部订单号")
    private String innerOrderNo;
    @ApiModelProperty(value = "平台订单号")
    private String outerOrderNo;
    @ApiModelProperty(value = "第三方退款/退货单号")
    private String outerRefundNo;
    @ApiModelProperty(value = "内部退款/退货单号")
    private String innerRefundNo;
    @ApiModelProperty(value = "售后类型 1-退款单  2- 退货退款")
    private String type;
    @ApiModelProperty(value = "渠道")
    private String channel;
    @ApiModelProperty(value = "店铺id")
    private Long storeId;
    @ApiModelProperty(value = "店铺名称")
    private String storeName;
    @ApiModelProperty(value = "店铺编码")
    private String storeCode;
    @ApiModelProperty(value = "买家昵称")
    private String buyerNick;
    @ApiModelProperty(value = "普通转单、手工单、复制单、导入单")
    private String actionType;
    @ApiModelProperty(value = "退款类型/退货退款类型：1-未发货仅退款  1-退货退款")
    private String refundType;
    @ApiModelProperty(value = "三方退款/退回退款时间")
    private Date outReturnTime;
    @ApiModelProperty(value = "退款/退货原因")
    private String reason;
    @ApiModelProperty(value = "退款/退货客服备注")
    private String remark;
    @ApiModelProperty(value = "退款/退货阶段   0-售中  1-售后")
    private String stage;
    @ApiModelProperty(value = "结算方式")
    private String payType;
    @ApiModelProperty(value = "退货人姓名")
    private String receiverName;
    @ApiModelProperty(value = "收货人手机号码")
    private String mobile;
    @ApiModelProperty(value = "收货人详细地址")
    private String address;
    @ApiModelProperty(value = "退货数量")
    private Long goodsQuantity;
    @ApiModelProperty(value = "退款总金额")
    private BigDecimal refundAmount;
    @ApiModelProperty(value = "标签code 多个,分割")
    private String tags;
    @ApiModelProperty(value = "完成时间")
    private Date completeDate;
    @ApiModelProperty(value = "收货仓库")
    private Long receivingWarehouse;
    @ApiModelProperty(value = "收货仓库名称")
    private String receivingWarehouseName;
    @ApiModelProperty(value = "仓库收货状态")
    private String receivingWarehouseStatus;
    @ApiModelProperty(value = "退货物流")
    private String returnLogisticsCompany;
    @ApiModelProperty(value = "退货快递单号")
    private String returnLogisticsNo;
    @ApiModelProperty(value = "状态")
    private String status;


}
