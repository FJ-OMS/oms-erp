package org.skyer.channel.domain.vo;

import lombok.Data;

import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * @author: lzh
 * @date: 2021-09-07
 * @description:
 */
@Data
public class ChildrenVO {

	@Encrypt
	private Long id;

	private String orgName;
}
