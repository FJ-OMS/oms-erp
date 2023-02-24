package org.skyer.order.api.dto;

import com.mysql.jdbc.Blob;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * @author: lzh
 * @date: 2022-01-15
 * @description:
 */
@Data
public class ProcessDefineDTO {

	@ApiModelProperty(value = "流程名称")
	private String processName;
	@ApiModelProperty(value = "流程定义文件xml")
	private String xmlString;

	@ApiModelProperty("流程定义id")
	private String processDefineId;

	@ApiModelProperty("流程文件名称")
	private String processFileName;

	@ApiModelProperty(value = "实际流程生成id")
	private String processId;

	@Encrypt
	private Long id;
}
