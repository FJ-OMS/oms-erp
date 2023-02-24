package org.skyer.order.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import org.skyer.mybatis.domain.Audit;
import org.skyer.mybatis.domain.AuditDomain;
import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * @author: lzh
 * @date: 2022-01-17
 * @description:
 */
@Data
public class ProcessDefinePageVO extends AuditDomain {
	@ApiModelProperty(value = "流程名称")
	private String processName;

	@ApiModelProperty("流程定义id")
	private String processDefineId;

	@ApiModelProperty("流程文件名称")
	private String processFileName;

	@ApiModelProperty(value = "实际流程生成id")
	private String processId;

	@Encrypt
	private Long id;

	@ApiModelProperty("状态")
	private Integer status;
}
