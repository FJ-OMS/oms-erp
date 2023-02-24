package org.skyer.goods.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import org.skyer.starter.keyencrypt.core.Encrypt;
/**
 * 审核动作
 * @author lixiaoyang
 * @date 2022/1/4 13:37
 */
@Data
public class AuditDTO {

    @Encrypt
    private Long id;

    @ApiModelProperty(value = "true 通过 false驳回")
    private Boolean flag;

    @ApiModelProperty(value = "审核意见")
    private String auditIdea;
}
