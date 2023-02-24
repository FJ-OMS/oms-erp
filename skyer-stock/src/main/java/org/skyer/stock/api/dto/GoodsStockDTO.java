package org.skyer.stock.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Transient;
import java.math.BigDecimal;

import org.skyer.stock.infra.constant.ObjectConstant;


/**
 * 仓库库存表
 *
 * @author lixiaoyang 2021-08-03 16:41:30
 */
@ApiModel("仓库库存表")
@Data
public class GoodsStockDTO {

    //
    // 业务方法(按public protected private顺序排列)
    // ------------------------------------------------------------------------------

    //
    // 数据库字段
    // ------------------------------------------------------------------------------
    @ApiModelProperty(value = "租户Id")
    private Long tenantId;
    @ApiModelProperty(value = "仓库id")
    private Long warehouseId;
    @ApiModelProperty(value = "sku编码")
    private String skuCode;
    @ApiModelProperty(value = "在库库存")
    private BigDecimal stockNum;

    public GoodsStockDTO(){}

    public GoodsStockDTO(Long warehouseId, String skuCode, String warehouseType) {
        this.warehouseId = warehouseId;
        this.skuCode = skuCode;
    }

}
