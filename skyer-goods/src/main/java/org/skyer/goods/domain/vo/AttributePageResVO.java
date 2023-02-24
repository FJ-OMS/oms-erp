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
 * @create 2021/6/24 2:28 下午
 */
@Data
@ApiModel("属性分页返回参数")
public class AttributePageResVO {

    @ApiModelProperty("主键")
    @Encrypt
    private Long id;

    @ApiModelProperty("属性编码")
    private String code;

    @ApiModelProperty("属性名称")
    private String attributeName;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("属性值")
    private List<AttributeValueResVO> attributeValueResVOList;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("创建人员")
    private String createdBy;

    @ApiModelProperty("操作人员")
    private String lastUpdatedBy;

    @ApiModelProperty("创建时间")
    private Date createDate;

    @ApiModelProperty("版本号")
    private Long objectVersionNumber;

    @ApiModelProperty("类别 1单选2复选3文本4下拉")
    private Integer type;

    @ApiModelProperty("是否用于生成sku")
    private Boolean isSku;

    @ApiModelProperty("状态")
    private Integer status;
}
