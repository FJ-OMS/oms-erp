package org.skyer.tags.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.starter.keyencrypt.core.Encrypt;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author xurongfa 2022-01-22 11:20
 */
@ApiModel(value = "批量修改状态")
@Data
public class BatchUpdateStatusDTO {

    @ApiModelProperty(value = "修改的id集合", required = true)
    @Encrypt
    @NotEmpty
    private List<Long> ids;

    @ApiModelProperty(value = "状态", required = true)
    @NotNull
    private Boolean status;
}
