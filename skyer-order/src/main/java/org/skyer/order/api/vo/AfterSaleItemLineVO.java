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
public class AfterSaleItemLineVO {
    @ApiModelProperty("内部itemLineId")
    @Encrypt
    private Long originItemLineId;
    @ApiModelProperty(value = "内部itemId")
    @Encrypt
    private String originItemId;
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
    @ApiModelProperty(value = "发（收）货状态")
    private String deliveryStatus;
    @ApiModelProperty(value = "寻源状态")
    private String sourceStatus;
    @ApiModelProperty(value = "预占状态")
    private String preemptionStatus;
    @ApiModelProperty(value = "赠品标识：0-卖品，1-赠品")
    private Integer giftFlag;
    @ApiModelProperty(value = "退款单价(均摊商品行单价)")
    private BigDecimal unitPrice;

}
