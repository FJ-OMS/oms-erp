package org.skyer.afterSales.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.starter.keyencrypt.core.Encrypt;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 物流商
 *
 * @author chenzz 2021-08-16 15:04:38
 */
@ApiModel("物流商")
@Data
public class LogisticsCompanyDTO {

    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty(value = "物流商编码")
    private String code;

    @ApiModelProperty(value = "物流商名称", required = true)
    @NotBlank
    private String name;

    @ApiModelProperty(value = "启用标志", required = true)
    @NotNull
    private Boolean enableFlag;

    private Long objectVersionNumber;

}
