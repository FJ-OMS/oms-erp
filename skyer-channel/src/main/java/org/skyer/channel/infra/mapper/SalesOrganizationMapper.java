package org.skyer.channel.infra.mapper;


import java.util.List;

import org.skyer.channel.api.dto.SalesOrganizationListDTO;
import org.skyer.channel.domain.entity.SalesOrganization;
import org.skyer.channel.domain.vo.SalesOrganizationListVO;
import org.skyer.mybatis.common.BaseMapper;

/**
 * <p>
 *  销售组织Mapper
 * </p>
 *
 * @author lzh
 * @since 2021-06-18
 */
public interface SalesOrganizationMapper extends BaseMapper<SalesOrganization> {

	/**
	 * 获取经销商列表
	 * @param salesOrganizationListDTO
	 * @return
	 */
	List<SalesOrganizationListVO> pageSalesOrganizationList(SalesOrganizationListDTO salesOrganizationListDTO);
}
