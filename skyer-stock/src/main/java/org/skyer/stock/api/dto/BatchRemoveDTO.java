package org.skyer.stock.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * @author: lzh
 * @date: 2021-08-20
 * @description:
 */
@ApiModel("批量删除入参类")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BatchRemoveDTO {
	@ApiModelProperty("主键集合")
	@Encrypt
	private List<Long> ids;
}
