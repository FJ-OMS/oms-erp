package org.skyer.channel.domain.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.UUID;

import org.skyer.channel.api.dto.ShopDeleteDTO;
import org.skyer.channel.api.dto.WarehouseDTO;
import org.skyer.channel.api.dto.WarehouseListDTO;
import org.skyer.channel.domain.entity.SalesOrganization;
import org.skyer.channel.domain.entity.Warehouse;
import org.skyer.channel.domain.repository.WarehouseRepository;
import org.skyer.channel.domain.service.WarehouseService;
import org.skyer.channel.domain.vo.WarehouseListVO;
import org.skyer.channel.infra.mapper.WarehouseMapper;
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
public class WarehouseServiceImpl  implements WarehouseService {

	private static final Logger logger = LoggerFactory.getLogger(WarehouseServiceImpl.class);

	@Autowired
	private WarehouseRepository warehouseRepository;

	@Autowired
	private WarehouseMapper warehouseMapper;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean updateWarehouse(WarehouseDTO warehouseDTO) {
		Warehouse warehouse = warehouseRepository.selectByPrimaryKey(warehouseDTO.getId());
		BeanUtils.copyProperties(warehouseDTO,warehouse);
		return warehouseRepository.updateByPrimaryKey(warehouse)>0;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean createWarehouse(Warehouse warehouse) {
		if (ObjectUtils.isEmpty(warehouse.getWareCode())){
			warehouse.setWareCode(UUID.randomUUID().toString());
		}
		CustomUserDetails userDetails = DetailsHelper.getUserDetails();
		logger.info("获取当前用户==============》" + userDetails);
		logger.info("获取当前用户租户ID==============》" + userDetails.getTenantId());
		Long tenantId = userDetails.getTenantId();
		warehouse.setTenantId(tenantId);
		return warehouseRepository.insertSelective(warehouse)>0;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean deleteById(Long id) {
		Warehouse warehouse = warehouseRepository.selectByPrimaryKey(id);
		warehouse.setDeleteFlag(true);
		return warehouseRepository.updateByPrimaryKey(warehouse)>0;
	}

	@Override
	public Warehouse getWarehouseDetail(Long id) {
		return warehouseRepository.selectByPrimaryKey(id);
	}

	@Override
	@ProcessCacheValue
	public Page<WarehouseListVO> pageWarehouseList(PageRequest pageRequest, WarehouseListDTO warehouseListDTO) {
		return PageHelper.doPageAndSort(pageRequest,()->warehouseMapper.pageWarehouseList(warehouseListDTO));
	}

	@Override
	public boolean operation(Long id, Boolean flag) {
		Warehouse warehouse = warehouseMapper.selectByPrimaryKey(id);
		warehouse.setStatus(flag);
		return warehouseMapper.updateByPrimaryKey(warehouse) > 0;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean deletes(ShopDeleteDTO shopDeleteDTO) {
		List<Long> ids = shopDeleteDTO.getIds();
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < ids.size(); i++) {
			stringBuilder.append(ids.get(i));
			if (i != (ids.size() - 1)) {
				stringBuilder.append(",");
			}
		}
		List<Warehouse> list = warehouseRepository.selectByIds(stringBuilder.toString());
		for (Warehouse warehouse : list) {
			warehouse.setDeleteFlag(true);
		}
		// 执行软删
		warehouseRepository.batchUpdateByPrimaryKey(list);
		return true;
	}
}
