package org.skyer.channel.domain.vo;

import lombok.Data;

import java.util.Date;

import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * @author: lzh
 * @date: 2021-06-25
 * @description:
 */
@Data
public class SalesOrganizationByTenantIdVO {

	@Encrypt
	private Long id;
	private String orgName;

}
