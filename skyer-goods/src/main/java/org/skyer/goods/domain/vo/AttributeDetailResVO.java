package org.skyer.goods.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @description
 * @Author wusc
 * @create 2021/7/21 2:15 下午
 */
@Data
@ApiModel("属性详情查询参数")
public class AttributeDetailResVO extends AttributePageResVO{

    @ApiModelProperty("查询属性对应属性值")
    private List<AttributeValueResVO> attributeValueResVOList;
}
