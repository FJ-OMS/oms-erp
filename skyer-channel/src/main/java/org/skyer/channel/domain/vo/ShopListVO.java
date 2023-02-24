package org.skyer.channel.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import org.skyer.boot.platform.lov.annotation.LovValue;
import org.skyer.starter.keyencrypt.core.Encrypt;
@Data
@ApiModel("店铺结果集")
public class ShopListVO {

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

    @ApiModelProperty(value = "店铺类别：区分网店还是实体店 网店：OnlineShop 门店：PhysicalShop")
    private String categoryType;

    @ApiModelProperty("所属公司id")
    @Encrypt
    private Long salesOrganizingId;
}
