package org.skyer.stock.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

import org.skyer.boot.platform.lov.annotation.LovValue;
import org.skyer.common.SkyerCacheKey;
import org.skyer.core.cache.CacheValue;
import org.skyer.core.cache.Cacheable;
import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * @description
 * @Author wusc
 * @create 2021/8/9 4:18 下午
 */
@Data
public class AdjustRecordPageResVO implements Cacheable {

    @ApiModelProperty("主键id")
    @Encrypt
    private Long adjustId;

    @ApiModelProperty("调整单编号")
    private String adjustCode;

    @ApiModelProperty("仓库编码")
    private String warehouseCode;

    @ApiModelProperty("仓库名")
    private String warehouseName;

    @ApiModelProperty("状态")
    @LovValue("STOCK.RECORD_STATUS")
    private String status;

    @ApiModelProperty("创建人员")
    private Long createdBy;

    @ApiModelProperty("创建人员名称")
    @CacheValue(
            key = SkyerCacheKey.USER,
            primaryKey = "createdBy",
            searchKey = "realName",
            structure = CacheValue.DataStructure.MAP_OBJECT
    )
    private String createdName;

    @ApiModelProperty("操作人员")
    private Long lastUpdatedBy;

    @ApiModelProperty("操作人员名称")
    @CacheValue(
            key = SkyerCacheKey.USER,
            primaryKey = "lastUpdatedBy",
            searchKey = "realName",
            structure = CacheValue.DataStructure.MAP_OBJECT
    )
    private String lastUpdateName;

    @ApiModelProperty("创建时间")
    private Date createDate;

    @ApiModelProperty("最后操作时间")
    private Date lastUpdateDate;
}
