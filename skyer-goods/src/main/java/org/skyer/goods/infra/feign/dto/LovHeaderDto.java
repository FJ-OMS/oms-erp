package org.skyer.goods.infra.feign.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author yuelinsoft
 */
@Data
public class LovHeaderDto implements Serializable {

    @ApiModelProperty("租户ID")
    private Long tenantId;
    @Size(
            max = 30
    )
    @ApiModelProperty("值集代码")
    private String lovCode;
    @ApiModelProperty("值集名称")
    private String lovName;
    @Size(
            max = 30
    )
    @ApiModelProperty("值集类型")
    private String lovTypeCode;

    @ApiModelProperty("是否必须分页")
    private Integer mustPageFlag;

    @NotNull
    @Range(min = 0, max = 1)
    @ApiModelProperty("是否启用")
    private Integer enabledFlag;
}
