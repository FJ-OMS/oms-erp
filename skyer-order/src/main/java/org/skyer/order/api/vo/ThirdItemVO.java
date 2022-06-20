package org.skyer.order.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * @author zlb
 */
@Data
public class ThirdItemVO {
    @ApiModelProperty("主键id")
    @Id
    @GeneratedValue
    private Long id;
    @ApiModelProperty(value = "平台订单号")
    private String outerOrderNo;
    @ApiModelProperty(value = "内部订单号")
    private String innerOrderNo;
    @ApiModelProperty(value = "平台商品明细ID")
    private Long outerItemId;
    @ApiModelProperty(value = "商品编码")
    private String spuCode;
    @ApiModelProperty(value = "商品类型1.实物2虚拟")
    private Integer spuType;
    @ApiModelProperty(value = "SKU编码")
    private String skuCode;
    @ApiModelProperty(value = "SKU名称")
    private String skuName;
    @ApiModelProperty(value = "数量")
    private Long quantity;
    @ApiModelProperty(value = "套餐标识：0-单品，1-套餐")
    private Integer setFlag;
    @ApiModelProperty(value = "赠品标识：0-卖品，1-赠品")
    private Integer giftFlag;
    @ApiModelProperty(value = "子母件标识：0-普通单品，1-子母件")
    private Integer multiFlag;
    @ApiModelProperty(value = "异常标识：0-正常，1-异常")
    private Integer exceptionFlag;
    @ApiModelProperty(value = "异常单原因（转单因商品的原因异常）")
    private String exceptionReason;
    @ApiModelProperty(value = "系统字段：删除标识")
    private Integer deleteFlag;
    @ApiModelProperty(value = "spu名称")
    private String spuName;
    @ApiModelProperty(value = "skuId")
    private Long skuId;
    @ApiModelProperty(value = "该商品如果是赠品-标明是主商品")
    private String mainItemCode;
    @ApiModelProperty(value = "订单商品code")
    private String orderItemCode;
    @ApiModelProperty("外部销售总价")
    private BigDecimal outerSaleAmount;
    @ApiModelProperty("外部销售单价")
    private BigDecimal outerSingleSaleAmount;

}
