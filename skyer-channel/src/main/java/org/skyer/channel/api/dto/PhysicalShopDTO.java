package org.skyer.channel.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

import org.skyer.mybatis.domain.AuditDomain;
import org.skyer.starter.keyencrypt.core.Encrypt;

@Data
public class PhysicalShopDTO extends AuditDomain {
    @ApiModelProperty("表ID，主键，供其他表做外键")
    @Encrypt
    private Long id;

    @ApiModelProperty("编码")
    private String code;

    @ApiModelProperty("租户ID(入参不填)")
    private Long tenantId;

    @ApiModelProperty("门店名称")
    @NotNull
    private String name;

    @ApiModelProperty("门店类型编码")
    @NotNull
    private String typeCode;

    @ApiModelProperty("负责人")
    @NotNull
    private String manager;

    @ApiModelProperty("销售组织")
    @NotNull
    @Encrypt
    private Long salesOrganizingId;

    @ApiModelProperty("发票种类编码")
    @NotNull
    private String invoiceTypeCode;

    @ApiModelProperty("详细地址")
    private String address;

    @ApiModelProperty("国家ID")
    private Long countryId;

    @ApiModelProperty("省ID")
    private Long provinceId;

    @ApiModelProperty("市ID")
    private Long cityId;

    @ApiModelProperty("县ID")
    private Long countyId;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("状态 true启用")
    private Boolean status;

    @ApiModelProperty("渠道仓使用状态")
    private Boolean channelWarehouseStatus;

    @ApiModelProperty("渠道仓名称")
    private String channelWarehouseName;

    @ApiModelProperty("删除标识 true删除")
    private Boolean deleteFlag;

    @ApiModelProperty(value = "o2o自提门店")
    private Boolean selfPickupFlag;
}
