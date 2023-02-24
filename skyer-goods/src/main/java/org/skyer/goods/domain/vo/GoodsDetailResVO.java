package org.skyer.goods.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.starter.keyencrypt.core.Encrypt;

import java.util.List;

/**
 * @description
 * @Author wusc
 * @create 2021/6/24 1:43 下午
 */
@Data
@ApiModel(description = "查询商品详情")
public class GoodsDetailResVO {

    @ApiModelProperty("主键")
    @Encrypt
    private Long id;

    @ApiModelProperty("商品编码")
    private String code;
    @ApiModelProperty("商品名称")
    private String name;

    @ApiModelProperty("商品类别")
    private String type;

    @ApiModelProperty("套餐标识：0-单品，1-套餐")
    private Integer setFlag;

    @ApiModelProperty("分类id")
    private Long categoryId;

    @ApiModelProperty("分类名称")
    private String categoryName;

    @ApiModelProperty("品牌名称")
    private String brandName;

    @ApiModelProperty("素材id")
    @Encrypt
    private Long materialId;

    @ApiModelProperty("素材名称")
    private String materialName;

    @ApiModelProperty("商品主图")
    private List<String> mainImage;

    @ApiModelProperty("商品详情图")
    private List<String> imageList;

    @ApiModelProperty("描述")
    private String detailHtml;

}
