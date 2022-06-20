package org.skyer.stock.infra.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import org.skyer.mybatis.base.impl.BaseRepositoryImpl;
import org.skyer.stock.domain.entity.WarehouseWarrantDetail;
import org.skyer.stock.domain.repository.WarehouseWarrantDetailRepository;
import org.skyer.stock.domain.vo.WarehouseWarrantQtyVO;
import org.skyer.stock.infra.mapper.WarehouseWarrantDetailMapper;

/**
 * 入库商品详情 资源库实现
 *
 * @author lixiaoyang 2021-10-25 16:34:17
 */
@Component
public class WarehouseWarrantDetailRepositoryImpl extends BaseRepositoryImpl<WarehouseWarrantDetail> implements WarehouseWarrantDetailRepository {
    @Autowired
    private WarehouseWarrantDetailMapper warehouseWarrantDetailMapper;

    @Override
    public WarehouseWarrantQtyVO statsQty(Long id) {
        return warehouseWarrantDetailMapper.statsQty(id);
    }

    @Override
    public List<WarehouseWarrantDetail> getAllByWarehouseWarrantId(Long warehouseWarrantId) {
        return warehouseWarrantDetailMapper.getAllByWarehouseWarrantId(warehouseWarrantId);
    }
}
