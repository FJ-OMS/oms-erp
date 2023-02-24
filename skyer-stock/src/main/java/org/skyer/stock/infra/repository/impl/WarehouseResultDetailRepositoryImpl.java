package org.skyer.stock.infra.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.skyer.mybatis.base.impl.BaseRepositoryImpl;
import org.skyer.stock.domain.entity.WarehouseResultDetail;
import org.skyer.stock.domain.repository.WarehouseResultDetailRepository;
import org.skyer.stock.domain.vo.WarehouseResultQtyVO;
import org.skyer.stock.infra.mapper.WarehouseResultDetailMapper;

/**
 * 入库商品详情 资源库实现
 *
 * @author xurongfa 2022-04-26 14:10:53
 */
@Component
public class WarehouseResultDetailRepositoryImpl extends BaseRepositoryImpl<WarehouseResultDetail> implements WarehouseResultDetailRepository {

    @Autowired
    private WarehouseResultDetailMapper warehouseResultDetailMapper;

    @Override
    public WarehouseResultQtyVO statsQty(Long id) {
        return warehouseResultDetailMapper.statsQty(id);
    }
}
