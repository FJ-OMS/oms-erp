package org.skyer.stock.infra.repository.impl;

import org.skyer.core.domain.Page;
import org.skyer.mybatis.base.impl.BaseRepositoryImpl;
import org.skyer.mybatis.domian.Condition;
import org.skyer.mybatis.pagehelper.PageHelper;
import org.skyer.mybatis.util.Sqls;
import org.skyer.stock.api.dto.WarehouseQueryReqDTO;
import org.skyer.stock.domain.entity.Warehouse;
import org.skyer.stock.domain.repository.WarehouseRepository;
import org.skyer.stock.domain.vo.WarehouseQueryResVO;
import org.skyer.stock.infra.constant.ObjectConstant;
import org.skyer.stock.infra.mapper.WarehouseMapper;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 仓库表 资源库实现
 *
 * @author lixiaoyang 2021-07-28 15:31:35
 */
@Component
public class WarehouseRepositoryImpl extends BaseRepositoryImpl<Warehouse> implements WarehouseRepository {

    @Autowired
    private WarehouseMapper warehouseMapper;


    @Override
    public Warehouse getShopWarehouse(Long shopId) {
        return warehouseMapper.getShopWarehouse(shopId);
    }

    @Override
    public Warehouse getSystemLogicWarehouse() {
        return warehouseMapper.getSystemLogicWarehouse();
    }

    @Override
    public Page<WarehouseQueryResVO> queryWarehousePage(WarehouseQueryReqDTO warehouseQueryReqDTO, Long tenantId, Long sructureId) {
        return PageHelper.doPage(warehouseQueryReqDTO.getPage(), warehouseQueryReqDTO.getSize(),
                () -> warehouseMapper.queryWarehouseList(warehouseQueryReqDTO, tenantId, sructureId));
    }

    @Override
    public Warehouse getOneByNameOrType(String name, String typeCode) {
        return warehouseMapper.getOneByNameOrType(name, typeCode);
    }

    @Override
    public List<Warehouse> getWarehouseListByIds(List<Long> ids) {
        return warehouseMapper.getWarehouseListByIds(ids);
    }

    @Override
    public Warehouse getWarehouseByCondition(String warehouseCode, Long tenantId) {
        List<Warehouse> warehouses = warehouseMapper.selectByCondition(Condition.builder(Warehouse.class).andWhere(Sqls.custom()
                .andEqualTo(Warehouse.FIELD_CODE, warehouseCode).andEqualTo(Warehouse.FIELD_TENANT_ID, tenantId)
                .andEqualTo(Warehouse.FIELD_DELETE_FLAG, ObjectConstant.DeleteFlag.NO)).build());
        if (CollectionUtils.isEmpty(warehouses)) {
            return null;
        }
        return warehouses.get(0);
    }

    @Override
    public List<Warehouse> queryAllByTenantId(Long tenantId) {
        return warehouseMapper.queryAllByTenantId(tenantId);
    }
}
