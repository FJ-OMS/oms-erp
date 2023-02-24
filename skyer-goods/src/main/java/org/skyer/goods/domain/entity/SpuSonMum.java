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

/**
 * 子母商品
 *
 * @author chenzz 2021-08-17 15:49:14
 */
@ApiModel("子母商品")
@VersionAudit
@ModifyAudit
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "sgoo_spu_son_mum")
public class SpuSonMum extends AuditDomain {

    public static final String FIELD_ID = "id";
    public static final String FIELD_SPU_ID = "spuId";
    public static final String FIELD_SPU_CODE = "spuCode";
    public static final String FIELD_SPU_NAME = "spuName";
    public static final String FIELD_IS_ALL_STORE = "isAllStore";
    public static final String FIELD_CATEGORY_ID = "categoryId";
    public static final String FIELD_REMARK = "remark";
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
    @ApiModelProperty(value = "spu_id(主)", required = true)
    @NotNull
    private Long spuId;
    @ApiModelProperty(value = "spu编码(主)", required = true)
    @NotBlank
    private String spuCode;
    @ApiModelProperty(value = "商品名称", required = true)
    @NotBlank
    private String spuName;
    @ApiModelProperty(value = "是否适用于全部店铺")
    private Boolean isAllStore;
    @ApiModelProperty(value = "目录id")
    private Long categoryId;
    @ApiModelProperty(value = "备注")
    private String remark;
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
     * @return spu_id(主)
     */
    public Long getSpuId() {
        return spuId;
    }

    public void setSpuId(Long spuId) {
        this.spuId = spuId;
    }

    /**
     * @return spu编码(主)
     */
    public String getSpuCode() {
        return spuCode;
    }

    public void setSpuCode(String spuCode) {
        this.spuCode = spuCode;
    }

    /**
     * @return 商品名称
     */
    public String getSpuName() {
        return spuName;
    }

    public void setSpuName(String spuName) {
        this.spuName = spuName;
    }

    /**
     * @return 是否适用于全部店铺
     */
    public Boolean getIsAllStore() {
        return isAllStore;
    }

    public void setIsAllStore(Boolean isAllStore) {
        this.isAllStore = isAllStore;
    }

    /**
     * @return 目录id
     */
    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
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
