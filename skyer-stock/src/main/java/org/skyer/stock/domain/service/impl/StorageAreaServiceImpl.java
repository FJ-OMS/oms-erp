package org.skyer.stock.domain.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

import static org.skyer.stock.infra.constant.ObjectConstant.DEFAULT_TENANT_ID;
import static org.skyer.stock.infra.constant.ObjectConstant.WarehouseTypeCode.LOGIC;

import org.skyer.boot.platform.code.builder.CodeRuleBuilder;
import org.skyer.boot.platform.code.constant.CodeConstants;
import org.skyer.core.exception.CommonException;
import org.skyer.stock.api.dto.BatchRemoveDTO;
import org.skyer.stock.api.dto.StorageAreaDTO;
import org.skyer.stock.domain.entity.StorageArea;
import org.skyer.stock.domain.entity.Warehouse;
import org.skyer.stock.domain.repository.StorageAreaRepository;
import org.skyer.stock.domain.repository.WarehouseRepository;
import org.skyer.stock.domain.service.StorageAreaService;
import org.skyer.stock.domain.vo.AreaInfoDetailsVO;
import org.skyer.stock.infra.constant.ObjectConstant;
import org.skyer.stock.infra.feign.SkyerPlatformRemoteService;

/**
 * 仓储管理表应用服务默认实现
 *
 * @author linzhuanghuang@ebc.com 2021-08-13 17:53:17
 */
@Service
public class StorageAreaServiceImpl implements StorageAreaService {
    @Autowired
    private WarehouseRepository warehouseRepository;
    @Autowired
    private StorageAreaRepository storageAreaRepository;
    @Autowired
    private SkyerPlatformRemoteService skyerPlatformRemoteService;
    @Autowired
    private CodeRuleBuilder codeRuleBuilder;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean createStorageArea(StorageAreaDTO storageAreaDTO) {
        Long tenantId = DEFAULT_TENANT_ID;

        // 添加仓储区域
        StorageArea storageArea = new StorageArea();
        BeanUtils.copyProperties(storageAreaDTO, storageArea);
        storageArea.setTenantId(tenantId);
        storageArea.setDeleteFlag(ObjectConstant.DeleteFlag.NO);
        storageArea.setEnableFlag(ObjectConstant.EnableFlag.ENABLE);

        String logicalWarehouseName = storageAreaDTO.getLogicalWarehouseName();
        Warehouse oneWarehouse = warehouseRepository.getOneByNameOrType(logicalWarehouseName, LOGIC);
        if (!ObjectUtils.isEmpty(oneWarehouse)){
            throw new CommonException("逻辑仓名称不可重复");
        }
        // 添加逻辑仓库
        String generateCode = codeRuleBuilder.generateCode(
                CodeConstants.Level.TENANT, tenantId, ObjectConstant.CodeRule.QD_WAREHOUSE_CODE, CodeConstants.CodeRuleLevelCode.CUSTOM,
                ObjectConstant.LevelValue.QD_WAREHOUSE_CODE, null);
        Warehouse logicalWarehouse = new Warehouse();
        logicalWarehouse.setDeleteFlag(ObjectConstant.DeleteFlag.NO);
        logicalWarehouse.setCode(generateCode);
        logicalWarehouse.setTenantId(tenantId);
        logicalWarehouse.setStatus(storageAreaDTO.getLogicalFlag());
        logicalWarehouse.setTypeCode(ObjectConstant.WarehouseTypeCode.LOGIC);
        logicalWarehouse.setName(logicalWarehouseName);

        int countWare = warehouseRepository.insertSelective(logicalWarehouse);
        if (countWare < 0) {
            throw new CommonException("逻辑仓添加异常");
        }

        storageArea.setWarehouseId(logicalWarehouse.getId());
        String code = storageAreaDTO.getCode();
        if (ObjectUtils.isEmpty(code)){
            storageArea.setCode(generateCode);
        }

        // Feign远程获取地址信息
        try {
            ResponseEntity<AreaInfoDetailsVO> areaInfoDetails = skyerPlatformRemoteService.getCnareaAdministrativeDivisionDetail(storageAreaDTO.getDistrictCode());

            AreaInfoDetailsVO detailsVO = areaInfoDetails.getBody();
            storageArea.setProvinceCode(detailsVO.getLevelOneAreaCode());
            storageArea.setProvinceName(detailsVO.getLevelOneAreaName());
            storageArea.setCityCode(detailsVO.getLevelTwoAreaCode());
            storageArea.setCityName(detailsVO.getLevelTwoAreaName());
            storageArea.setDistrictCode(detailsVO.getLevelThreeAreaCode());
            storageArea.setDistrictName(detailsVO.getLevelThreeAreaName());
            storageArea.setLng(detailsVO.getLng());
            storageArea.setLat(detailsVO.getLat());
            int countAddr = storageAreaRepository.insertSelective(storageArea);
            if (countAddr < 0) {
                throw new CommonException("仓储区域添加异常");
            }
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateStorageArea(StorageAreaDTO storageAreaDTO) {
        StorageArea storageArea = storageAreaRepository.selectByPrimaryKey(storageAreaDTO.getId());
        if (ObjectUtils.isEmpty(storageArea)) {
            throw new CommonException("仓储管理修改异常");
        }
        BeanUtils.copyProperties(storageAreaDTO, storageArea);
        storageAreaRepository.updateByPrimaryKeySelective(storageArea);
        String logicalWarehouseName = storageAreaDTO.getLogicalWarehouseName();
        Long logicalWarehouseId = storageArea.getWarehouseId();
        Warehouse warehouse = warehouseRepository.getOneByNameOrType(logicalWarehouseName, LOGIC);
        if (!ObjectUtils.isEmpty(warehouse)){
            Long id = warehouse.getId();
            if (id.compareTo(logicalWarehouseId) != 0) {
                throw new CommonException("逻辑仓名称不可重复");
            }
        }

        // 只切换关联逻辑仓的启禁用
        Warehouse logicalWarehouse = warehouseRepository.selectByPrimaryKey(logicalWarehouseId);
        logicalWarehouse.setName(logicalWarehouseName);
        logicalWarehouse.setStatus(storageAreaDTO.getLogicalFlag());
        warehouseRepository.updateByPrimaryKeySelective(logicalWarehouse);

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean operation(Long id, Boolean enableFlag) {
        StorageArea storageArea = storageAreaRepository.selectByPrimaryKey(id);
        storageArea.setEnableFlag(enableFlag);
        Long warehouseId = storageArea.getWarehouseId();
        Warehouse warehouse = warehouseRepository.selectByPrimaryKey(warehouseId);
        warehouse.setStatus(enableFlag);
        warehouseRepository.updateByPrimaryKeySelective(warehouse);
        return storageAreaRepository.updateByPrimaryKey(storageArea) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(BatchRemoveDTO batchRemoveDTO) {
        List<Long> ids = batchRemoveDTO.getIds();

        List<StorageArea> storageAreaList = storageAreaRepository.getStorageAreaListByIds(ids);
        if (ObjectUtils.isEmpty(storageAreaList)) {
            throw new CommonException("未找到相应仓库，操作失败");
        }
        List<Long> logicalWarehouseIds = storageAreaList.stream().map(StorageArea::getWarehouseId).collect(Collectors.toList());
        List<Warehouse> logicalWarehouseList = warehouseRepository.getWarehouseListByIds(logicalWarehouseIds);

        // 先删除逻辑仓
        logicalWarehouseList.forEach(warehouse -> warehouse.setDeleteFlag(ObjectConstant.DeleteFlag.YES));
        if (CollectionUtils.isEmpty(warehouseRepository.batchUpdateByPrimaryKeySelective(logicalWarehouseList))) {
            throw new CommonException("逻辑仓删除异常");
        }

        // 删除仓储区域
        storageAreaList.forEach(storageArea -> storageArea.setDeleteFlag(ObjectConstant.DeleteFlag.YES));
        if (CollectionUtils.isEmpty(storageAreaRepository.batchUpdateByPrimaryKeySelective(storageAreaList))) {
            throw new CommonException("仓储删除异常");
        }

    }

    @Override
    public StorageAreaDTO getDetails(Long id) {
        StorageArea storageArea = storageAreaRepository.selectByPrimaryKey(id);
        Warehouse logicalWarehouse = warehouseRepository.selectByPrimaryKey(storageArea.getWarehouseId());

        StorageAreaDTO storageAreaDTO = new StorageAreaDTO();
        BeanUtils.copyProperties(storageArea, storageAreaDTO);
        storageAreaDTO.setLogicalWarehouseName(logicalWarehouse.getName());
        storageAreaDTO.setLogicalFlag(logicalWarehouse.getStatus());

        return storageAreaDTO;
    }

}
