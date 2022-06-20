package org.skyer.stock.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * @author xurongfa 2022-04-24 9:35
 */
@Data
public class LogisticsResultVO {
    @ApiModelProperty("仓库id, 按物流商统计时，值为空")
    @Encrypt
    private Long warehouseId;
    @ApiModelProperty("仓库名称, 按物流商统计时，值为空")
    private String warehouseName;
    @ApiModelProperty("物流商id")
    @Encrypt
    private Long logisticsCompanyId;
    @ApiModelProperty("物流商名称")
    private String logisticsCompanyName;
    @ApiModelProperty("发货单数")
    private Long shippingQty;
    @ApiModelProperty("包裹数")
    private Long packageQty;
    @ApiModelProperty("发货商品数")
    private Long goodsQty;
    @ApiModelProperty("运费总计")
    private BigDecimal postageTotal;
}
