package org.skyer.goods.domain.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.skyer.mybatis.annotation.ModifyAudit;
import org.skyer.mybatis.annotation.VersionAudit;
import org.skyer.mybatis.domain.AuditDomain;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 安装服务
 *
 * @author chenzz 2021-08-18 15:42:47
 */
@ApiModel("安装服务")
@VersionAudit
@ModifyAudit
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "sgoo_install_service")
public class InstallService extends AuditDomain {

    public static final String FIELD_ID = "id";
    public static final String FIELD_SERVICE_CODE = "serviceCode";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_TYPE = "type";
    public static final String FIELD_PEOPLE_AMOUNT = "peopleAmount";
    public static final String FIELD_MONEY = "money";
    public static final String FIELD_REMARK = "remark";
    public static final String FIELD_SERVICE_ZONE = "serviceZone";
    public static final String FIELD_DELETE_FLAG = "deleteFlag";
    public static final String FIELD_TENANT_ID = "tenantId";

    //
    // 业务方法(按public protected private顺序排列)
    // ------------------------------------------------------------------------------

    //
    // 数据库字段
    // ------------------------------------------------------------------------------


    @ApiModelProperty("主键id")
    @Id
    @GeneratedValue
    private Long id;
    @ApiModelProperty(value = "安装服务编码", required = true)
    @NotBlank
    private String serviceCode;
    @ApiModelProperty(value = "服务名称", required = true)
    @NotBlank
    private String name;
    @ApiModelProperty(value = "安装类别", required = true)
    @NotBlank
    private String type;
    @ApiModelProperty(value = "配备安装人数", required = true)
    @NotNull
    private Long peopleAmount;
    @ApiModelProperty(value = "费用", required = true)
    @NotNull
    private BigDecimal money;
    @ApiModelProperty(value = "备注", required = true)
    @NotBlank
    private String remark;
    @ApiModelProperty(value = "服务地区，json数据", required = true)
    @NotBlank
    private String serviceZone;
    @ApiModelProperty(value = "删除状态")
    private Boolean deleteFlag;
    @ApiModelProperty(value = "租户Id")
    private Long tenantId;

    //
    // 非数据库字段
    // ------------------------------------------------------------------------------

    //
    // getter/setter
    // ------------------------------------------------------------------------------

    /**
     * @return 主键id
     */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return 安装服务编码
     */
    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    /**
     * @return 服务名称
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return 安装类别
     */
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return 配备安装人数
     */
    public Long getPeopleAmount() {
        return peopleAmount;
    }

    public void setPeopleAmount(Long peopleAmount) {
        this.peopleAmount = peopleAmount;
    }

    /**
     * @return 费用
     */
    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    /**
     * @return 备注
     */
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return 服务地区，json数据
     */
    public String getServiceZone() {
        return serviceZone;
    }

    public void setServiceZone(String serviceZone) {
        this.serviceZone = serviceZone;
    }

    /**
     * @return 删除状态
     */
    public Boolean getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    /**
     * @return 租户Id
     */
    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

}
