package org.skyer.channel.domain.service;


import java.util.List;

import org.skyer.channel.api.dto.SalesOrganizationDTO;
import org.skyer.channel.api.dto.SalesOrganizationListDTO;
import org.skyer.channel.api.dto.ShopDeleteDTO;
import org.skyer.channel.domain.entity.SalesOrganization;
import org.skyer.channel.domain.vo.ListOfTreeVO;
import org.skyer.channel.domain.vo.SalesOrganizationByTenantIdVO;
import org.skyer.channel.domain.vo.SalesOrganizationListVO;
import org.skyer.core.domain.Page;
import org.skyer.mybatis.pagehelper.domain.PageRequest;

/**
 * <p>
 *  经销商组织
 * </p>
 *
 * @author lzh
 * @since 2021-06-18
 */
public interface SalesOrganizationService  {
	/**
	 * 分页查询销售组织
	 * @param pageRequest
	 * @return
	 */
	Page<SalesOrganizationListVO> pageSalesOrganizationList(PageRequest pageRequest, SalesOrganizationListDTO salesOrganizationListDTO);

	/**
	 * 修改销售组织
	 * @param salesOrganization
	 * @return
	 */
	Boolean updateSalesOrganization(SalesOrganizationDTO salesOrganization);

	/**
	 * 新增销售组织
	 * @param salesOrganization
	 * @return
	 */
	Boolean createSalesOrganization(SalesOrganization salesOrganization);

	/**
	 * 删除销售组织
	 * @param id
	 * @return
	 */
	Boolean deleteById(Long id);

	/**
	 * 获取销售组织详情
	 * @param id
	 * @return
	 */
	SalesOrganization getSalesOrganizationDetail(Long id);

	/**
	 * 禁用 OR 启用
	 * @param id
	 * @param flag
	 * @return
	 */
	boolean operation(Long id, Boolean flag);

	/**
	 * 根据租户Id查询
	 * @param tenantId
	 * @return
	 */
	List<SalesOrganizationByTenantIdVO> getsalesOrganizationByTenantId(Long tenantId);

	/**
	 * 批量删除
	 * @param shopDeleteDTO
	 * @return
	 */
	Boolean deletes(ShopDeleteDTO shopDeleteDTO);

	/**
	 * 公司上下级树形结构
	 * @return
	 */
	List<ListOfTreeVO> listOfTree(Boolean flag);
}
