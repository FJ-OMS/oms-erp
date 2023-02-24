package org.skyer.channel.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import org.skyer.starter.keyencrypt.core.Encrypt;

@ApiModel("网店设置出参类")
@Data
public class OnlineShopSettingVO {
    @ApiModelProperty("表ID，主键，供其他表做外键")
    @Encrypt
    private Long id;

    @ApiModelProperty("网店名称")
    private String name;

    @ApiModelProperty("自动确认发货（天）")
    private Integer autoConfirmDay;

    @ApiModelProperty("自动下载商品 true是 false否")
    private Boolean autoDownloadGoods;

    @ApiModelProperty("自动同步库存 true是 false否")
    private Boolean autoSyncInventory;

    @ApiModelProperty("版本号")
    private Long objectVersionNumber;
}
