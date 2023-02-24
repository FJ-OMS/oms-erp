package org.skyer.stock.domain.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

import org.skyer.mybatis.annotation.ModifyAudit;
import org.skyer.mybatis.domain.AuditDomain;
import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * 仓储区域表
 *
 * @author lixiaoyang 2021-08-02 16:18:08
 */
@ApiModel("仓储区域表")
@ModifyAudit
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "sstock_storage_area")
public class StorageArea extends AuditDomain {

    public static final String FIELD_ID = "id";
    public static final String FIELD_DELETE_FLAG = "deleteFlag";
    public static final String FIELD_TENANT_ID = "tenantId";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_WAREHOUSE_ID = "warehouseId";
    public static final String FIELD_ADDRESS_CODE = "addressCode";
    public static final String FIELD_PROVINCE_CODE = "provinceCode";
    public static final String FIELD_PROVINCE_NAME = "provinceName";
    public static final String FIELD_CITY_CODE = "cityCode";
    public static final String FIELD_CITY_NAME = "cityName";
    public static final String FIELD_DISTRICT_CODE = "districtCode";
    public static final String FIELD_DISTRICT_NAME = "districtName";
    public static final String FIELD_DETAIL_ADDRESS = "detailAddress";
    public static final String FIELD_LONGITUDE = "longitude";
    public static final String FIELD_LATITUDE = "latitude";
    public static final String FIELD_ENABLE_FLAG = "enableFlag";

    //
    // 业务方法(按public protected private顺序排列)
    // ------------------------------------------------------------------------------

    //
    // 数据库字段
    // ------------------------------------------------------------------------------


    @ApiModelProperty("主键")
    @Id
    @GeneratedValue
    @Encrypt
    private Long id;
    @ApiModelProperty(value = "删除状态")
    private Boolean deleteFlag;
    @ApiModelProperty(value = "租户Id")
    private Long tenantId;
    @ApiModelProperty(value = "仓储名称")
    @NotBlank
    private String name;
    @ApiModelProperty(value = "编码", required = true)
    @NotBlank
    private String code;
    @ApiModelProperty(value = "仓库id")
    @Encrypt
    private Long warehouseId;
    @ApiModelProperty(value = "省份编码", required = true)
    @NotBlank
    private Long provinceCode;
    @ApiModelProperty(value = "省份名称", required = true)
    @NotBlank
    private String provinceName;
    @ApiModelProperty(value = "城市编码", required = true)
    @NotBlank
    private Long cityCode;
    @ApiModelProperty(value = "城市名称", required = true)
    @NotBlank
    private String cityName;
    @ApiModelProperty(value = "区县编码", required = true)
    @NotBlank
    private Long districtCode;
    @ApiModelProperty(value = "区县名称", required = true)
    @NotBlank
    private String districtName;
    @ApiModelProperty(value = "详细地址", required = true)
    @NotBlank
    private String detailAddress;
    @ApiModelProperty(value = "经度")
    private BigDecimal lng;
    @ApiModelProperty(value = "纬度")
    private BigDecimal lat;
    @ApiModelProperty(value = "联系人姓名")
    private String contactName;
    @ApiModelProperty(value = "联系人电话")
    private String contactPhone;
    @ApiModelProperty(value = "描述")
    private String description;
    @ApiModelProperty(value = "可用状态：0禁用，1启用")
    private Boolean enableFlag;

    //
    // 非数据库字段
    // ------------------------------------------------------------------------------

}
