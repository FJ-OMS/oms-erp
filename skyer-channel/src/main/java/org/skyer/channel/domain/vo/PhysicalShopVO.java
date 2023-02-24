package org.skyer.channel.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

import org.skyer.boot.platform.lov.annotation.LovValue;
import org.skyer.common.SkyerCacheKey;
import org.skyer.core.cache.CacheValue;
import org.skyer.core.cache.Cacheable;
import org.skyer.starter.keyencrypt.core.Encrypt;

@ApiModel("门店出参类")
@Data
public class PhysicalShopVO implements Cacheable {

    @ApiModelProperty("表ID，主键，供其他表做外键")
    @Encrypt
    private Long id;

    @ApiModelProperty("编码")
    private String code;

    @ApiModelProperty("网店名称")
    private String name;

    @ApiModelProperty("状态 true启用")
    private Boolean status;

    @ApiModelProperty("销售组织")
    private String orgName;

    @ApiModelProperty("店铺类型")
    @LovValue(lovCode = "SCHL.SHOP_TYPE", meaningField = "typeCodeMeaning")
    private String typeCode;

    @ApiModelProperty("店铺类型")
    private String typeCodeMeaning;

    @ApiModelProperty("店铺地址")
    private String address;

    @ApiModelProperty("渠道仓使用状态")
    private Boolean channelWarehouseStatus;

    @ApiModelProperty("渠道仓名称")
    private String channelWarehouseName;

    @ApiModelProperty(value = "o2o自提门店")
    private Boolean selfPickupFlag;

    @ApiModelProperty("创建人")
    private Long createdBy;

    @ApiModelProperty("创建人用户名")
    @CacheValue(
            key = SkyerCacheKey.USER,
            primaryKey = "createdBy",
            searchKey = "realName",
            structure = CacheValue.DataStructure.MAP_OBJECT
    )
    private String createdName;

    @ApiModelProperty("创建时间")
    private Date creationDate;

    @ApiModelProperty("版本号")
    private Long objectVersionNumber;

    @ApiModelProperty("负责人")
    private String manager;
}
