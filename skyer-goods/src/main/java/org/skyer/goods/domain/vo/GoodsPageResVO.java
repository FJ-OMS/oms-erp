package org.skyer.goods.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.starter.keyencrypt.core.Encrypt;

import java.util.Date;
import java.util.List;

/**
 * @description
 * @Author wusc
 * @create 2021/6/24 11:15 上午
 */
@Data
@ApiModel("商品分类分页查询")
public class GoodsPageResVO {

    @ApiModelProperty("主键")
    @Encrypt
    private Long id;

    private String spuCode;

    @ApiModelProperty("商品名称")
    private String name;

    @ApiModelProperty("商品类别")
    private Integer type;

    @ApiModelProperty("套餐标识：0-单品，1-套餐")
    private Integer setFlag;

    @ApiModelProperty("分类id")
    @Encrypt
    private Long categoryId;

    @ApiModelProperty("分类名称")
    private String categoryName;

    @ApiModelProperty("品牌id")
    @Encrypt
    private Long brandId;

    @ApiModelProperty("商品品牌")
    private String brandName;

    @ApiModelProperty("素材id")
    @Encrypt
    private Long materialId;

    @ApiModelProperty("素材名称")
    private String materialName;

    @ApiModelProperty("商品主图")
    private List<String> mainImage;

    @ApiModelProperty("操作人员")
    private String lastUpdatedBy;

    @ApiModelProperty("操作时间")
    private String lastUpdateDate;

    @ApiModelProperty("创建人员")
    private String createdBy;

    @ApiModelProperty("创建时间")
    private Date createDate;

    @ApiModelProperty("版本号")
    private Long objectVersionNumber;

    @ApiModelProperty("商品状态")
    private Integer status;

    @ApiModelProperty("备注")
    private String remark;

}
