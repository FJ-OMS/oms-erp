package org.skyer.order.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.order.domain.entity.AfterSaleItem;
import org.skyer.starter.keyencrypt.core.Encrypt;

import java.util.List;

/**
 * @author zhanglongbing
 */
@Data
public class AddRefundOnlyDTO {

    @ApiModelProperty(value = "id")
    @Encrypt
    private Long id;
    @ApiModelProperty(value = "内部订单号")
    private String innerOrderNo;
    @ApiModelProperty(value = "平台订单号")
    private String outerOrderNo;
    @ApiModelProperty(value = "第三方退款/退货单号")
    private String outerRefundNo;
    @ApiModelProperty(value = "内部退款/退货单号")
    private String innerRefundNo;

    @ApiModelProperty(value = "商品")
    private List<AfterSaleItem> items;


}
