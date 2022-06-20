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
 * 子母商品sku表
 *
 * @author chenzz 2021-08-17 15:49:14
 */
@ApiModel("子母商品sku表")
@VersionAudit
@ModifyAudit
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "sgoo_spu_son_mum_sku")
public class SpuSonMumSku extends AuditDomain {

    public static final String FIELD_ID = "id";
    public static final String FIELD_SPU_ID = "spuId";
    public static final String FIELD_SPU_CODE = "spuCode";
    public static final String FIELD_SON_SKU_ID = "sonSkuId";
    public static final String FIELD_SON_SKU_CODE = "sonSkuCode";
    public static final String FIELD_AMOUNT = "amount";
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
    @ApiModelProperty(value = "子商品sku_id", required = true)
    @NotNull
    private Long sonSkuId;
    @ApiModelProperty(value = "子商品sku_code", required = true)
    @NotBlank
    private String sonSkuCode;
    @ApiModelProperty(value = "数量", required = true)
    @NotNull
    private Long amount;
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
     * @return 子商品sku_id
     */
    public Long getSonSkuId() {
        return sonSkuId;
    }

    public void setSonSkuId(Long sonSkuId) {
        this.sonSkuId = sonSkuId;
    }

    /**
     * @return 子商品sku_code
     */
    public String getSonSkuCode() {
        return sonSkuCode;
    }

    public void setSonSkuCode(String sonSkuCode) {
        this.sonSkuCode = sonSkuCode;
    }

    /**
     * @return 数量
     */
    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
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
