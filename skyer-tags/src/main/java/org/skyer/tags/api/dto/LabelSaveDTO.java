package org.skyer.tags.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.EAN;
import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * @author xurongfa 2022-01-22 14:09
 */
@Data
@ApiModel("标签保存对象")
public class LabelSaveDTO {
    @ApiModelProperty("id")
    @Encrypt
    private Long id;

    @ApiModelProperty(value = "标签名称")
    private String labelName;

    @ApiModelProperty(value = "标签类型")
    private String labelType;

    @ApiModelProperty(value = "模块")
    private String moduleCode;

    @ApiModelProperty(value = "标签code")
    private String labelCode;

    @ApiModelProperty(value = "状态")
    private Boolean status;

    @ApiModelProperty(value = "标签文本内容")
    private String textContent;

    @ApiModelProperty(value = "标签文本颜色")
    private String textColor;

    @ApiModelProperty(value = "是否系统标签")
    private Boolean systemLabelFlag;

    @ApiModelProperty(value = "是否可挪移")
    private Boolean changeFlag;
}
