package org.skyer.stock.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

import org.skyer.boot.platform.lov.annotation.LovValue;
import org.skyer.common.SkyerCacheKey;
import org.skyer.core.cache.CacheValue;
import org.skyer.core.cache.Cacheable;

@Data
public class StockChangeListVO implements Cacheable {

    @ApiModelProperty(value = "商品编码")
    private String spuCode;

    @ApiModelProperty(value = "商品名称")
    private String spuName;

    @ApiModelProperty(value = "商品SKU编码")
    private String skuCode;

    @ApiModelProperty(value = "商品分类")
    private String categoryName;

    @ApiModelProperty(value = "仓库编码")
    private String warehouseCode;

    @ApiModelProperty(value = "仓库名称")
    private String warehouseName;

    @ApiModelProperty(value = "业务类型编码,STOCK.RECORD_TYPE")
    @LovValue(lovCode = "STOCK.RECORD_TYPE",meaningField = "recordTypeMeaning")
    private String recordTypeCode;

    @ApiModelProperty(value = "业务类型")
    private String recordTypeMeaning;

    @ApiModelProperty(value = "仓库类型编码,STOCK.WAREHOUSE_TYPE")
    @LovValue(lovCode = "STOCK.WAREHOUSE_TYPE",meaningField = "warehouseTypeMeaning")
    private String warehouseTypeCode;

    @ApiModelProperty(value = "业务类型")
    private String warehouseTypeMeaning;

    @ApiModelProperty(value = "仓库库存类型编码,STOCK.QTY_TYPE")
    @LovValue(lovCode = "STOCK.QTY_TYPE",meaningField = "qtyTypeMeaning")
    private String qtyTypeCode;

    @ApiModelProperty(value = "仓库库存类型")
    private String qtyTypeMeaning;

    @ApiModelProperty(value = "变更前实际库存数量")
    private BigDecimal preRealNum;

    @ApiModelProperty(value = "变更数量")
    private BigDecimal changeNum;

    @ApiModelProperty(value = "变更后实际库存数量")
    private BigDecimal aftRealNum;

    @ApiModelProperty("创建人员")
    private Long createdBy;

    @ApiModelProperty("创建时间")
    private Date creationDate;

    @ApiModelProperty(value = "创建人员名称")
    @CacheValue(
            key = SkyerCacheKey.USER,
            primaryKey = "createdBy",
            searchKey = "realName",
            structure = CacheValue.DataStructure.MAP_OBJECT
    )
    private String createdName;

    @ApiModelProperty(value = "相关单据")
    private String recordCode;
}
