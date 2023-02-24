package org.skyer.tags.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.starter.keyencrypt.core.Encrypt;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author xurongfa 2022-01-22 12:17
 */
@Data
@ApiModel("ids")
public class IdsDTO {
    @Encrypt
    @ApiModelProperty(value = "主键集合", required = true)
    @NotEmpty
    private List<Long> ids;
}
