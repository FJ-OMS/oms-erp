package org.skyer.goods.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sun.org.apache.xpath.internal.operations.Bool;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.skyer.mybatis.annotation.ModifyAudit;
import org.skyer.mybatis.annotation.VersionAudit;
import org.skyer.mybatis.domain.AuditDomain;
import org.skyer.starter.keyencrypt.core.Encrypt;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 商品素材
 * </p>
 *
 * @author chenzz
 * @since 2021-06-23
 */
@VersionAudit
@ModifyAudit
@JsonInclude(JsonInclude.Include.NON_NULL)
@Accessors(chain = true)
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "sgoo_material")
public class Material extends AuditDomain {


    public static final String FIELD_ID = "id";
    public static final String FIELD_CODE = "code";
    public static final String FIELD_DETAIL_HTML = "detailHtml";
    public static final String FIELD_DETAIL_IMAGE = "detailImage";
    public static final String FIELD_MAIN_IMAGE = "mainImage";
    public static final String FIELD_MATERIAL_NAME = "materialName";
    public static final String FIELD_REMARK = "remark";
    public static final String FIELD_DELETE_FLAG = "deleteFlag";

    public Material() {
        this.deleteFlag = false;
    }

    @ApiModelProperty("表ID，主键，供其他表做外键")
    @Id
    @GeneratedValue
    @Encrypt
    private Long id;

    /**
     * 编码
     */
    @ApiModelProperty("编码")
    @NotNull
    private String code;

    /**
     * 素材名称
     */
    @ApiModelProperty("素材名称")
    @NotNull
    private String materialName;

    /**
     * 主图
     */
    @ApiModelProperty("主图")
    private String mainImage;

    /**
     * 详情图
     */
    @ApiModelProperty("详情图")
    private String detailImage;

    /**
     * 详情内容
     */
    @ApiModelProperty("详情内容")
    private String detailHtml;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

    @JsonIgnore
    @ApiModelProperty("删除标识")
    private Boolean deleteFlag;

    public void setDeleteFlag(Boolean deleteFlag) {
        this.deleteFlag = deleteFlag == null ? false : deleteFlag;
    }
}
