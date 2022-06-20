package org.skyer.channel.domain.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.skyer.mybatis.annotation.ModifyAudit;
import org.skyer.mybatis.annotation.VersionAudit;
import org.skyer.mybatis.domain.AuditDomain;

/**
 * @author lixiaoyang 2021-12-10 13:27:14
 */
@ApiModel("店铺实体类")
@VersionAudit
@ModifyAudit
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "channel_shop")
public class Shop extends AuditDomain {
    /**
     * 门店对应的渠道类型编码
     */
    public static final String PHYSICAL_SHOP_CHANNEL_TYPE_CODE = "999";
    /**
     * 门店对应的categoryType编码
     */
    public static final String PHYSICAL_SHOP_CATEGORY_TYPE = "PhysicalShop";

    public static final String FIELD_ID = "id";
    public static final String FIELD_CODE = "code";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_TENANT_ID = "tenantId";
    public static final String FIELD_TYPE_CODE = "typeCode";
    public static final String FIELD_CATEGORY_TYPE = "categoryType";
    public static final String FIELD__WAREHOUSE_STATUS = "channelWarehouseStatus";
    public static final String FIELD__WAREHOUSE_NAME = "channelWarehouseName";
    public static final String FIELD_MANAGER = "manager";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_STATUS = "status";
    public static final String FIELD_DELETE_FLAG = "deleteFlag";
    public static final String FIELD_SALES_ORGANIZING_ID = "salesOrganizingId";
    public static final String FIELD_INVOICE_TYPE_CODE = "invoiceTypeCode";
    public static final String FIELD_ADDRESS = "address";
    public static final String FIELD_COUNTRY_ID = "countryId";
    public static final String FIELD_PROVINCE_ID = "provinceId";
    public static final String FIELD_CITY_ID = "cityId";
    public static final String FIELD_COUNTY_ID = "countyId";
    public static final String FIELD_URL = "url";
    public static final String FIELD__TYPE_CODE = "channelTypeCode";
    public static final String FIELD_AUTO_CONFIRM_DAY = "autoConfirmDay";
    public static final String FIELD_AUTO_DOWNLOAD_GOODS = "autoDownloadGoods";
    public static final String FIELD_AUTO_SYNC_INVENTORY = "autoSyncInventory";
    public static final String FIELD_RELATION_PHYSICAL_SHOP_ID = "relationPhysicalShopId";
    public static final String FIELD_SELF_PICKUP_FLAG = "selfPickupFlag";


    //
    // 业务方法(按public protected private顺序排列)
    // ------------------------------------------------------------------------------

    //
    // 数据库字段
    // ------------------------------------------------------------------------------


    @ApiModelProperty("主键")
    @Id
    @GeneratedValue
    private Long id;
    @ApiModelProperty(value = "编码")
    private String code;
    @ApiModelProperty(value = "店铺名称")
    private String name;
    @ApiModelProperty(value = "租户Id")
    private Long tenantId;
    @ApiModelProperty(value = "店铺类型")
    private String typeCode;
    @ApiModelProperty(value = "店铺类别：区分网店还是实体店")
    private String categoryType;
    @ApiModelProperty(value = "0否 1是")
    private Boolean channelWarehouseStatus;
    @ApiModelProperty(value = "渠道仓名称")
    private String channelWarehouseName;
    @ApiModelProperty(value = "负责人")
    private String manager;
    @ApiModelProperty(value = "描述")
    private String description;
    @ApiModelProperty(value = "状态 1启用 0禁用")
    private Boolean status;
    @ApiModelProperty(value = "1删除")
    private Boolean deleteFlag;
    @ApiModelProperty(value = "销售组织")
    private Long salesOrganizingId;
    @ApiModelProperty(value = "发票种类")
    private String invoiceTypeCode;
    @ApiModelProperty(value = "详细地址")
    private String address;
    @ApiModelProperty(value = "国家ID")
    private Long countryId;
    @ApiModelProperty(value = "省ID")
    private Long provinceId;
    @ApiModelProperty(value = "市ID")
    private Long cityId;
    @ApiModelProperty(value = "县ID")
    private Long countyId;
    @ApiModelProperty(value = "网店地址(url)")
    private String url;
    @ApiModelProperty(value = "渠道类型")
    private String channelTypeCode;
    @ApiModelProperty(value = "自动确认发货（天）")
    private Integer autoConfirmDay;
    @ApiModelProperty(value = "自动下载商品")
    private Boolean autoDownloadGoods;
    @ApiModelProperty(value = "自动同步库存")
    private Boolean autoSyncInventory;

    @ApiModelProperty(value = "网店关联的门店")
    private Long relationPhysicalShopId;

    @ApiModelProperty(value = "o2o自提门店")
    private Boolean selfPickupFlag;

    //
    // 非数据库字段
    // ------------------------------------------------------------------------------

}
