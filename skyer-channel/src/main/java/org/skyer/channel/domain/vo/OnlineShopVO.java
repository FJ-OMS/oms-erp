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

@ApiModel("网店出参类")
@Data
public class OnlineShopVO implements Cacheable {

    @ApiModelProperty("表ID，主键，供其他表做外键")
    @Encrypt
    private Long id;

    @ApiModelProperty("编码")
    private String code;

    @ApiModelProperty("网店名称")
    private String name;

    @ApiModelProperty("渠道类型编码")
    @LovValue(lovCode = "SCHL.CHANNEL_TYPE", meaningField = "channelTypeMeaning")
    private String channelTypeCode;

    @ApiModelProperty("渠道类型")
    private String channelTypeMeaning;

    @ApiModelProperty("状态 true启用")
    private Boolean status;

    @ApiModelProperty("销售组织")
    private String orgName;

    @ApiModelProperty("店铺类型编码")
    @LovValue(lovCode = "SCHL.SHOP_TYPE", meaningField = "onlineStoreTypeMeaning")
    private String onlineStoreTypeCode;

    @ApiModelProperty("店铺类型")
    private String onlineStoreTypeMeaning;

    @ApiModelProperty("网店地址(url)")
    private String url;

    @ApiModelProperty("渠道仓使用状态 true禁用")
    private Boolean channelWarehouseStatus;

    @ApiModelProperty("渠道仓名称")
    private String channelWarehouseName;

    @ApiModelProperty("网店关联的门店名称")
    private String relationPhysicalShopName;

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
}
