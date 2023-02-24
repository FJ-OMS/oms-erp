package org.skyer.goods.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

import org.skyer.starter.keyencrypt.core.Encrypt;

import java.util.List;

/**
 * <p>
 * 商品素材
 * </p>
 *
 * @author chenzz
 * @since 2021-06-23
 */
@Getter
@Setter
@ToString
@ApiModel("商品素材")
public class MaterialDTO {

    @ApiModelProperty("id,添加时不需要设置")
    @Encrypt
    private Long id;

    /**
     * 编码
     */
    @ApiModelProperty("编码")
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
    private List<String> mainImage;

    /**
     * 详情图
     */
    @ApiModelProperty("详情图")
    private List<String> detailImage;


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

}
