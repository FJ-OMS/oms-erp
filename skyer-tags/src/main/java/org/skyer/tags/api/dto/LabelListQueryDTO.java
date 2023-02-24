package org.skyer.tags.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @author zlb
 */
@Data
public class LabelListQueryDTO {

    @ApiModelProperty(value = "是否系统标签")
    private Boolean systemLabelFlag;

}
