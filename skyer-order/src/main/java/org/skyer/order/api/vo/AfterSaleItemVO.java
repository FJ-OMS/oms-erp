package org.skyer.order.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.starter.keyencrypt.core.Encrypt;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * @author zhangliang
 */
@Data
public class AfterSaleItemVO {
    @ApiModelProperty("原itemId")
    @Encrypt
    private Long originItemId;
    @ApiModelProperty(value = "平台订单号")
    private String outerOrderNo;
    @ApiModelProperty(value = "内部订单号")
    private String innerOrderNo;
    @ApiModelProperty(value = "商品编码")
    private String spuCode;
    @ApiModelProperty(value = "商品类型1.实物2虚拟")
    private Integer spuType;
    @ApiModelProperty(value = "SKU编码")
    private String skuCode;
    @ApiModelProperty(value = "SKU名称")
    private String skuName;
    @ApiModelProperty(value = "spu名称")
    private String spuName;
    @ApiModelProperty(value = "skuId")
    private Long skuId;
    @ApiModelProperty(value = "商品行数量")
    private Long totalQuantity;
    @ApiModelProperty(value = "已退款数量")
    private Long quantityRefunded;
    @ApiModelProperty(value = "未发货数量")
    private Long undeliveredQuantity;
    @ApiModelProperty(value = "可退款数量")
    private Long refundableQuantity;
    @ApiModelProperty(value = "已发货数量")
    private Long quantityShipped;
    @ApiModelProperty(value = "已退货数量")
    private Long quantityReturned;
    @ApiModelProperty(value = "可退货数量")
    private Long returnableQuantity;
    @ApiModelProperty(value = "退款单价(均摊商品行单价)")
    private BigDecimal unitPrice;
    @ApiModelProperty(value = "赠品标识：0-卖品，1-赠品")
    private Integer giftFlag;

    private Long objectVersionNumber;
}
