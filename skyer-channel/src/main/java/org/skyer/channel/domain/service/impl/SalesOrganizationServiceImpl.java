package org.skyer.channel.domain.service.impl;


import org.apache.poi.ss.formula.functions.T;
import org.skyer.channel.infra.feign.UserClient;
import org.skyer.core.exception.CommonException;
import org.skyer.core.util.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.skyer.channel.api.dto.SalesOrganizationDTO;
import org.skyer.channel.api.dto.SalesOrganizationListDTO;
import org.skyer.channel.api.dto.ShopDeleteDTO;
import org.skyer.channel.domain.entity.Dealer;
import org.skyer.channel.domain.entity.SalesOrganization;
import org.skyer.channel.domain.repository.SalesOrganizationReposotory;
import org.skyer.channel.domain.service.SalesOrganizationService;
import org.skyer.channel.domain.vo.ChildrenVO;
import org.skyer.channel.domain.vo.ListOfTreeVO;
import org.skyer.channel.domain.vo.SalesOrganizationByTenantIdVO;
import org.skyer.channel.domain.vo.SalesOrganizationListVO;
import org.skyer.channel.infra.mapper.SalesOrganizationMapper;
import org.skyer.channel.infra.util.ListUtil;
import org.skyer.core.cache.ProcessCacheValue;
import org.skyer.core.domain.Page;
import org.skyer.core.oauth.CustomUserDetails;
import org.skyer.core.oauth.DetailsHelper;
import org.skyer.mybatis.pagehelper.PageHelper;
import org.skyer.mybatis.pagehelper.domain.PageRequest;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lzh
 * @since 2021-06-18
 */
@Service
public class SalesOrganizationServiceImpl  implements SalesOrganizationService {

	private static final Logger logger = LoggerFactory.getLogger(SalesOrganizationServiceImpl.class);

	@Autowired
	private SalesOrganizationReposotory salesOrganizationReposotory;

	@Autowired
	private SalesOrganizationMapper salesOrganizationMapper;

	@Autowired
	private UserClient userClient;

	@Override
	@ProcessCacheValue
	public Page<SalesOrganizationListVO> pageSalesOrganizationList(PageRequest pageRequest, SalesOrganizationListDTO salesOrganizationListDTO) {
		return PageHelper.doPage(pageRequest,()-> salesOrganizationMapper.pageSalesOrganizationList(salesOrganizationListDTO));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean updateSalesOrganization(SalesOrganizationDTO salesOrganization) {
		SalesOrganization organization = salesOrganizationReposotory.selectByPrimaryKey(salesOrganization.getId());
		BeanUtils.copyProperties(salesOrganization,organization);
		return salesOrganizationReposotory.updateByPrimaryKey(organization)>0;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean createSalesOrganization(SalesOrganization salesOrganization) {
		if (ObjectUtils.isEmpty(salesOrganization.getOrgCode())){
			salesOrganization.setOrgCode(UUID.randomUUID().toString());
		}
		CustomUserDetails userDetails = DetailsHelper.getUserDetails();
		logger.info("获取当前用户==============》" + userDetails);
		logger.info("获取当前用户租户ID==============》" + userDetails.getTenantId());
		Long tenantId = userDetails.getTenantId();
		salesOrganization.setTenantId(tenantId);
		return salesOrganizationReposotory.insertSelective(salesOrganization)>0;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean deleteById(Long id) {
		ResponseEntity response=  userClient.companyUser(Collections.singletonList(id));
		if (!response.getStatusCode().is2xxSuccessful()) {
			throw new CommonException("组织已存在用户，无法删除");
		}
		SalesOrganization organization = salesOrganizationReposotory.selectByPrimaryKey(id);
		organization.setDeleteFlag(true);
		return salesOrganizationReposotory.updateByPrimaryKey(organization)>0;
	}

	@Override
	public SalesOrganization getSalesOrganizationDetail(Long id) {
		return salesOrganizationReposotory.selectByPrimaryKey(id);
	}

	@Override
	public boolean operation(Long id, Boolean flag) {
		SalesOrganization organization = salesOrganizationReposotory.selectByPrimaryKey(id);
		organization.setStatus(flag);
		return salesOrganizationReposotory.updateByPrimaryKey(organization) > 0;
	}

	@Override
	public List<SalesOrganizationByTenantIdVO> getsalesOrganizationByTenantId(Long tenantId) {
		SalesOrganization salesOrganization = new SalesOrganization();
		salesOrganization.setTenantId(tenantId);
		List<SalesOrganization> list = salesOrganizationReposotory.select(salesOrganization);
		List<SalesOrganizationByTenantIdVO> vos = ListUtil.listConvert(list, SalesOrganizationByTenantIdVO.class);
		return vos;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean deletes(ShopDeleteDTO shopDeleteDTO) {
		List<Long> ids = shopDeleteDTO.getIds();
		ResponseEntity response=  userClient.companyUser(ids);
		if (!response.getStatusCode().is2xxSuccessful()) {
			throw new CommonException("组织已存在用户，无法删除");
		}
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < ids.size(); i++) {
			stringBuilder.append(ids.get(i));
			if (i != (ids.size() - 1)) {
				stringBuilder.append(",");
			}
		}
		List<SalesOrganization> list = salesOrganizationReposotory.selectByIds(stringBuilder.toString());
		for (SalesOrganization salesOrganization : list) {
			salesOrganization.setDeleteFlag(true);
		}
		// 执行软删
		salesOrganizationReposotory.batchUpdateByPrimaryKey(list);
		return true;
	}

	@Override
	public List<ListOfTreeVO> listOfTree(Boolean flag) {

		// 获取所有公司记录
		SalesOrganization organization = new SalesOrganization();
		organization.setDeleteFlag(false);
		if (flag==true){
			organization.setStatus(true);
		}
		List<SalesOrganization> allCompanyList = salesOrganizationReposotory.select(organization);
		List<SalesOrganization> oneList = allCompanyList.stream().filter(model -> model.getPid() == null)
				.map((menu) -> {
					menu.setChildren(find(menu, allCompanyList));
					return menu;
				}).collect(Collectors.toList());
//		List<SalesOrganization> twoList = allCompanyList.stream().filter(model -> model.getPid() != null).collect(Collectors.toList());
//		Map<Long, SalesOrganization> oneMap = oneList.stream().collect(Collectors.toMap(SalesOrganization::getId, Function.identity()));
//
//		Map<Long, SalesOrganization> twoMap = twoList.stream().collect(Collectors.toMap(SalesOrganization::getId, Function.identity()));
//		List<SalesOrganization> list = doDataProcessing(allCompanyList, oneMap, twoMap);



		return ListUtil.listConvert(oneList,ListOfTreeVO.class);
	}
	/**
	 *递归查找所有菜单的子菜单
	 */
	private List<SalesOrganization> find(SalesOrganization root, List<SalesOrganization> all) {

		return all.stream().filter(menuEntity ->
						menuEntity.getPid() != null  &&
			 menuEntity.getPid().equals(root.getId())
		).map(item -> {
			//1、找到子菜单(递归)
			item.setChildren(find(item, all));
			return item;
		}).collect(Collectors.toList());

	}


//	/**
//	 * 执行数据处理 树结构构成
//	 *
//	 * @param moduleConfigSysList 所有菜单集合
//	 * @param oneDataMap          一级菜单集合
//	 * @param twoDataMap          其它级菜单集合
//	 * @return List<ModuleConfigVO>
//	 * @author lixiaoyang
//	 * @date 2021/6/8 16:46
//	 */
//	private List<SalesOrganization> doDataProcessing(List<SalesOrganization> moduleConfigSysList, Map<Long, SalesOrganization> oneDataMap, Map<Long, SalesOrganization> twoDataMap) {
//		List<SalesOrganization> leaveOutList = new ArrayList<>();
//		for (SalesOrganization moduleConfig : moduleConfigSysList) {
//			Long parentId = moduleConfig.getPid();
//			if (!ObjectUtils.isEmpty(parentId) && parentId != 0) {
//				//获取父菜单
//				SalesOrganization parentModuleConfigVO = oneDataMap.get(parentId);
//				if (ObjectUtils.isEmpty(parentModuleConfigVO)) {
//
//					parentModuleConfigVO = twoDataMap.get(parentId);
//					if (ObjectUtils.isEmpty(parentModuleConfigVO)) {
//						leaveOutList.add(moduleConfig);
//						continue;
//					}
//				}
//				List<SalesOrganization> moduleConfigVOList = parentModuleConfigVO.getChildren();
//				if (ObjectUtils.isEmpty(moduleConfigVOList)) {
//					moduleConfigVOList = new ArrayList<>();
//				}
//				moduleConfigVOList.add(moduleConfig);
//				parentModuleConfigVO.setChildren(moduleConfigVOList);
//				twoDataMap.put(moduleConfig.getId(), moduleConfig);
//			} else {
//				oneDataMap.put(moduleConfig.getId(), moduleConfig);
//			}
//		}
//		// 防止因顺序问题造成菜单无法挂钩父类
//		if (leaveOutList.size() > 0) {
//			boolean flag = false;
//			while (leaveOutList.iterator().hasNext()) {
//				SalesOrganization moduleConfig = leaveOutList.iterator().next();
//				Long parentId = moduleConfig.getPid();
//				SalesOrganization one = oneDataMap.get(parentId);
//				SalesOrganization two = twoDataMap.get(parentId);
//				if (!ObjectUtils.isEmpty(one) || !ObjectUtils.isEmpty(two)) {
//					flag = true;
//					break;
//				}
//			}
//			if (flag) {
//				doDataProcessing(leaveOutList, oneDataMap, twoDataMap);
//			}
//		}
//		//菜单分配完成最后整合
//		Set<Long> longs = oneDataMap.keySet();
//		List<SalesOrganization> result = new ArrayList<>();
//		longs.forEach(l -> {
//			result.add(oneDataMap.get(l));
//		});
//		return result;
//	}
}
