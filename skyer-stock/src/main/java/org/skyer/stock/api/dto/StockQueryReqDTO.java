package org.skyer.stock.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * @description
 * @Author wusc
 * @create 2021/8/6 1:50 下午
 */
@Data
public class StockQueryReqDTO extends PageReqDTO{

    @ApiModelProperty("商品编码")
    private String spuCode;

    @ApiModelProperty("商品名称")
    private String spuName;

    @ApiModelProperty("仓库编码")
    private String warehouseCode;

    @ApiModelProperty("仓库名称")
    @Encrypt
    private List<Long> warehouseId;

    @ApiModelProperty("sku编码")
    private String skuCode;

    @ApiModelProperty("分类id列表")
    @Encrypt
    private List<Long> categoryIdList;

    @ApiModelProperty("仓库类型编码值集STOCK.WAREHOUSE_TYPE")
    private List<String> warehouseTypeCode;

    @ApiModelProperty("库存是否大于零")
    private Boolean exceedZero;

    @ApiModelProperty("可用库存是否大于零")
    private Boolean usableExcludeZeroFlag;

}
