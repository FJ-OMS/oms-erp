package org.skyer.channel.domain.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * @author: lzh
 * @date: 2021-09-07
 * @description:
 */
@ApiModel("公司层级关系树")
@Data
public class ListOfTreeVO {

	@Encrypt
	private Long id;

	private String orgName;

	private List<ChildrenVO> children;
}
