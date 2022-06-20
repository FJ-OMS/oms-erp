package org.skyer.channel.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import org.skyer.mybatis.pagehelper.domain.PageRequest;
import org.skyer.starter.keyencrypt.core.Encrypt;

@Data
public class QueryShopListDTO extends PageRequest {

    @ApiModelProperty("店铺名称")
    private String name;
    @ApiModelProperty("店铺编码")
    private String code;

    @ApiModelProperty("渠道编码")
    private String channelTypeCode;

    @ApiModelProperty("o2o销售店铺id")
    @Encrypt
    private Long o2oSalesStoreId;

    @ApiModelProperty(value = "网店或门店", hidden = true)
    private String categoryType;

    @ApiModelProperty(value = "店铺id", hidden = true)
    @Encrypt
    private Long id;

    @ApiModelProperty(value = "网店关联的门店id", hidden = true)
    @Encrypt
    private Long relationPhysicalShopId;

    @ApiModelProperty(value = "是否o2o自提店铺", hidden = true)
    private Boolean selfPickupFlag;

}
