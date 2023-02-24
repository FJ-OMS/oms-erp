package org.skyer.goods.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.starter.keyencrypt.core.Encrypt;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @description
 * @Author wusc
 * @create 2021/6/24 10:44 上午
 */
@Data
@ApiModel("新增或者编辑商品")
public class GoodsReqDTO {

    @ApiModelProperty("主键id")
    @Encrypt
    private Long id;

    @ApiModelProperty("商品编码")
    private String code;

    @ApiModelProperty("类别")
    @NotNull(message = "类别不能为空")
    private Integer type;

    @ApiModelProperty("商品名称")
    @NotBlank(message = "名称不能为空")
    private String name;

    @ApiModelProperty("类别id")
    @NotNull(message = "类别id不能为空")
    @Encrypt
    private Long categoryId;

    @ApiModelProperty("品牌Id")
    @Encrypt
    private Long brandId;

    @ApiModelProperty("素材Id")
    @Encrypt
    private Long materialId;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("状态")
    private Integer status;
}
