package org.skyer.goods.infra.feign.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.skyer.mybatis.domain.AuditDomain;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author yuelinsoft
 */
@Data
@ApiModel("值集value")
public class LovValueDto extends AuditDomain implements Serializable {

    @ApiModelProperty("值集值ID")
    private Long lovValueId;
    @ApiModelProperty("值集ID")
    private Long lovId;
    @ApiModelProperty("值集代码")
    private String lovCode;
    @ApiModelProperty("值集值")
    private String value;
    @ApiModelProperty("含义")
    private String meaning;
    @ApiModelProperty("描述")
    private String description;
    @ApiModelProperty("租户ID")
    private Long tenantId;
    @NotNull
    @ApiModelProperty("排序号")
    private Integer orderSeq;
    @NotNull
    @Range(min = 0, max = 1)
    @ApiModelProperty("生效标识")
    private Integer enabledFlag;
}
