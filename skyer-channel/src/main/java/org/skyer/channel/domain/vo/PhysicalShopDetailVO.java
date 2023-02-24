package org.skyer.channel.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import org.skyer.boot.platform.lov.annotation.LovValue;
import org.skyer.mybatis.domain.AuditDomain;
import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * <p>
 * 门店
 * </p>
 *
 * @author lixiaoyang
 * @since 2021-06-16
 */
@ApiModel("门店")
@Data
public class PhysicalShopDetailVO extends AuditDomain {
    public static final String PHYSICAL_SHOP = "PhysicalShop";

    @ApiModelProperty("表ID，主键，供其他表做外键")
    @Encrypt
    private Long id;

    @ApiModelProperty("编码")
    private String code;

    @ApiModelProperty("租户ID(入参不填)")
    private Long tenantId;

    @ApiModelProperty("门店名称")
    private String name;

    @ApiModelProperty("门店类型编码")
    @LovValue(lovCode = "SCHL.SHOP_TYPE", meaningField = "typeCodeMeaning")
    private String typeCode;

    @ApiModelProperty("门店类型(入参不填)")
    private String typeCodeMeaning;

    @ApiModelProperty("负责人")
    private String manager;

    @ApiModelProperty("销售组织")
    @Encrypt
    private Long salesOrganizingId;

    @ApiModelProperty("发票种类编码")
    @LovValue(lovCode = "SCHL.INVOICE_TYPE", meaningField = "invoiceTypeCodeMeaning")
    private String invoiceTypeCode;

    //非数据库字段
    @ApiModelProperty("发票种类(入参不填)")
    private String invoiceTypeCodeMeaning;

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
