package org.skyer.goods.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description
 * @Author wusc
 * @create 2021/6/23 4:52 下午
 */
@Data
@ApiModel(description = "统计分类个数")
public class TotalCategoryResVO {

    @ApiModelProperty("全部分类编码")
    private Integer allCategoryNum;

    @ApiModelProperty("一级分类编码")
    private Integer firstCategoryNum;

    @ApiModelProperty("二级分类编码")
    private Integer secondCategoryNum;

    @ApiModelProperty("三级分类编码")
    private Integer thirdCategoryNum;

    @ApiModelProperty("四级分配编码")
    private Integer forthCategoryNum;

    @ApiModelProperty("五级分配编码")
    private Integer fifthCategoryNum;
}
