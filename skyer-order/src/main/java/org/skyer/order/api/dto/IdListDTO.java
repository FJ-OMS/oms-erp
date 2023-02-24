package org.skyer.order.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * @author: lzh
 * @date: 2022-04-02
 * @description:
 */
@Data
public class IdListDTO {

	@Encrypt
	private Long id;

	@Encrypt
	private List<Long> idList;
}
