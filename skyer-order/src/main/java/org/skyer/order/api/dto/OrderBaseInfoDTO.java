package org.skyer.order.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.starter.keyencrypt.core.Encrypt;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 订单创建基础信息dto
 * @description
 * @Author wusc
 * @create 2022/1/18 1:18 下午
 */
@Data
public class OrderBaseInfoDTO {

    @ApiModelProperty("渠道 0 天猫")
    @NotBlank(message = "渠道不能为空")
    private String channel;

    @ApiModelProperty("店铺id")
    @Encrypt
    @NotBlank(message = "店铺id不能为空")
    private Long storeId;

    @ApiModelProperty("店铺code")
    @NotBlank(message = "店铺编码不能为空")
    private String storeCode;

    @ApiModelProperty("店铺名称")
    @NotBlank(message = "店铺名称不能为空")
    private String storeName;

    @ApiModelProperty("外部订单号")
    @NotBlank(message = "第三方订单号不能为空")
    private String outerOrderNo;

    @ApiModelProperty("业务类型 1 普通单")
    @NotBlank(message = "订单类型不能为空")
    private String businessType;

    @ApiModelProperty("下单时间")
    @NotBlank(message = "三方订单不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date orderTime;

    @ApiModelProperty("支付状态")
    @NotBlank(message = "支付状态不能为空")
    private Integer payStatus;

    @ApiModelProperty("卖家留言")
    private String orderRemarks;

    @ApiModelProperty("订单创建类型")
    private String actionType;

    @ApiModelProperty("复制来源")
    private String copyOrigin;

    @ApiModelProperty(value = "提货店铺Id")
    @Encrypt
    private Long takingDeliveryStoreId;

    @ApiModelProperty(value = "提货店铺名称")
    private String takingDeliveryStoreName;

    @ApiModelProperty(value = "销售员工")
    private String salesEmployeeRealName;

    @ApiModelProperty(value = "提货方式")
    private String takingDeliveryWay;

    @ApiModelProperty(value = "员工ID")
    @Encrypt
    private Long salesEmployeeId;
}
