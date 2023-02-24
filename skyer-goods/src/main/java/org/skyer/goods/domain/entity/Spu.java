package org.skyer.goods.domain.entity;


import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.skyer.mybatis.annotation.ModifyAudit;
import org.skyer.mybatis.annotation.MultiLanguage;
import org.skyer.mybatis.annotation.VersionAudit;
import org.skyer.mybatis.domain.AuditDomain;
import org.skyer.starter.keyencrypt.core.Encrypt;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * <p>
 * 商品表
 * </p>
 *
 * @author chenzz
 * @since 2021-06-25
 */
@ModifyAudit
@JsonInclude(JsonInclude.Include.NON_NULL)
@Accessors(chain = true)
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "sgoo_spu")
@ApiModel(description = "商品表")
public class Spu extends AuditDomain {


    public static final String ID = "id";

    public static final String NAME = "name";

    public static final String CATEGORY_ID = "categoryId";

    public static final String TENANT_ID = "tenantId";

    public static final String DELETE_FLAG = "deleteFlag";

    public static final String BRAND_ID = "brandId";

    public static final String SPU_CODE = "spuCode";

    public static final String MATERIAL_ID = "materialId";

    public static final String STATUS = "status";

    /**
     * 套餐标识：1-套餐
     */
    @Transient
    public static final Integer SET_FLAG_TRUE = 1;


    @ApiModelProperty("表ID，主键，供其他表做外键")
    @Id
    @GeneratedValue
    @Encrypt
    private Long id;

    @ApiModelProperty(value = "编码")
    private String brandCode;

    @ApiModelProperty(value = "品牌id")
    private Long brandId;

    @ApiModelProperty(value = "分类编码")
    private String categoryCode;

    @ApiModelProperty(value = "分类id")
    private Long categoryId;

    @ApiModelProperty(value = "素材编码")
    private String materialCode;

    @ApiModelProperty(value = "素材id")
    private Long materialId;

    @ApiModelProperty(value = "商品名称")
    private String name;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "spu")
    private String spuCode;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "类型1.实物2虚拟")
    private Integer type;

    @ApiModelProperty("套餐标识：0-单品，1-套餐")
    private Integer setFlag;

    @ApiModelProperty(value = "删除状态")
    private Boolean deleteFlag;


    public Spu(){
        this.deleteFlag = false;
    }

    public void setDeleteFlag() {
        this.deleteFlag = deleteFlag == null ? false : deleteFlag;
    }
}
