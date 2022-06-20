package org.skyer.channel.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

import org.skyer.boot.platform.lov.annotation.LovValue;
import org.skyer.starter.keyencrypt.core.Encrypt;
@ApiModel("网店入参类")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OnlineShopDTO {

    @ApiModelProperty("表ID，主键，供其他表做外键")
    @Encrypt
    private Long id;

    @ApiModelProperty("编码")
    private String code;

    @ApiModelProperty("网店名称")
    @NotNull
    private String name;

    @ApiModelProperty("渠道类型")
    @NotNull
    private String channelTypeCode;

    @ApiModelProperty("网店类型")
    @NotNull
    private String onlineStoreTypeCode;

    @ApiModelProperty("负责人")
    @NotNull
    private String manager;

    @ApiModelProperty("网店地址(url)")
    @NotNull
    private String url;

    @ApiModelProperty("销售组织")
    @NotNull
    @Encrypt
    private Long salesOrganizingId;

    @ApiModelProperty("发票种类")
    @NotNull
    private String invoiceTypeCode;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("渠道仓使用状态 true是")
    private Boolean channelWarehouseStatus;

    @ApiModelProperty("渠道仓名称")
    private String channelWarehouseName;

    @ApiModelProperty("网店关联的门店id")
    @Encrypt
    private Long relationPhysicalShopId;

    @ApiModelProperty("版本号")
    private Long objectVersionNumber;
}
