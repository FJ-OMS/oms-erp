package org.skyer.stock.infra.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import org.skyer.core.cache.ProcessCacheValue;
import org.skyer.core.domain.Page;
import org.skyer.mybatis.base.impl.BaseRepositoryImpl;
import org.skyer.mybatis.pagehelper.PageHelper;
import org.skyer.mybatis.pagehelper.domain.PageRequest;
import org.skyer.stock.api.dto.StorageAreaListReqDTO;
import org.skyer.stock.domain.entity.StorageArea;
import org.skyer.stock.domain.repository.StorageAreaRepository;
import org.skyer.stock.domain.vo.StorageAreaListVO;
import org.skyer.stock.infra.mapper.StorageAreaMapper;

/**
 * 仓储管理表 资源库实现
 *
 * @author lixiaoyang 2021-08-02 16:31:19
 */
@Component
public class StorageAreaRepositoryImpl extends BaseRepositoryImpl<StorageArea> implements StorageAreaRepository {


    @Autowired
    private StorageAreaMapper storageAreaMapper;


    @Override
    @ProcessCacheValue
    public Page<StorageAreaListVO> pageStorageAreaList(PageRequest pageRequest, StorageAreaListReqDTO listReqDTO) {
        return PageHelper.doPageAndSort(pageRequest, () -> storageAreaMapper.pageStorageAreaList(listReqDTO));
    }

    /**
     * 批量查询接口
     *
     * @param ids
     * @return
     */
    @Override
    public List<StorageArea> getStorageAreaListByIds(List<Long> ids) {
        return storageAreaMapper.getStorageAreaListByIds(ids);
    }

    @Override
    public StorageArea getByLogicWarehouseId(Long logicWarehouseId) {
        return storageAreaMapper.getByLogicWarehouseId(logicWarehouseId);
    }

    @Override
    public StorageArea getByLogicWarehouseCode(String logicWarehouseCode) {
        return storageAreaMapper.getByLogicWarehouseCode(logicWarehouseCode);
    }
}
