package org.skyer.tags.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author xurongfa 2022-01-22 12:44
 */
@Data
@ApiModel("标签查询条件")
public class LabelQueryDTO {
    @ApiModelProperty(value = "标签名称")
    private String labelName;

    @ApiModelProperty(value = "标签类型")
    private String labelType;

    @ApiModelProperty(value = "状态")
    private Boolean status;

    @ApiModelProperty(value = "创建时间起始")
    private Date beginCreationDate;

    @ApiModelProperty(value = "创建时间终止")
    private Date endCreationDate;

    @ApiModelProperty(value = "是否系统标签")
    @NotNull
    private Boolean systemLabelFlag;

}
