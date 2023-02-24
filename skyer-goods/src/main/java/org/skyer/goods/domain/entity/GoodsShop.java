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

/**
 * 商品在店铺中的上下架关系表
 *
 * @author chenzz 2021-07-06 10:35:45
 */
@ApiModel("商品在店铺中的上下架关系表")
@VersionAudit
@ModifyAudit
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "sgoo_goods_shop")
public class GoodsShop extends AuditDomain {

    public static final String FIELD_ID = "id";
    public static final String FIELD_GOODS_ID = "spuId";
    public static final String FIELD_SPU_CODE = "spuCode";
    public static final String FIELD_SHOP_ID = "shopId";
    public static final String FIELD_STATUS = "status";
    public static final String FIELD_DELETE_FLAG = "deleteFlag";
    public static final String FIELD_TENANT_ID = "tenantId";

    //
    // 业务方法(按public protected private顺序排列)
    // ------------------------------------------------------------------------------

    //
    // 数据库字段
    // ------------------------------------------------------------------------------


    @ApiModelProperty("id")
    @Id
    @GeneratedValue
    private Long id;
    @ApiModelProperty(value = "商品id")
    private Long spuId;
    @ApiModelProperty(value = "spu")
    private String spuCode;
    @ApiModelProperty(value = "店铺id")
    private Long shopId;
    @ApiModelProperty(value = "状态，上下架。1上架，0下架")
    private Integer status;
    @ApiModelProperty(value = "删除状态")
    private Integer deleteFlag;
    @ApiModelProperty(value = "租户Id")
    private Long tenantId;

    public GoodsShop() {
        this.deleteFlag = 0;
    }
    //
    // 非数据库字段
    // ------------------------------------------------------------------------------

    //
    // getter/setter
    // ------------------------------------------------------------------------------

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return 商品id
     */
    public Long getSpuId() {
        return spuId;
    }

    public void setSpuId(Long spuId) {
        this.spuId = spuId;
    }

    /**
     * @return spu
     */
    public String getSpuCode() {
        return spuCode;
    }

    public void setSpuCode(String spuCode) {
        this.spuCode = spuCode;
    }

    /**
     * @return 店铺id
     */
    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    /**
     * @return 状态，上下架。1上架，0下架
     */
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return 删除状态
     */
    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
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
