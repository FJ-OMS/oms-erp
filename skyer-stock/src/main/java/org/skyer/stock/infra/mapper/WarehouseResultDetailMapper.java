package org.skyer.stock.infra.mapper;

import org.apache.ibatis.annotations.Param;

import org.skyer.mybatis.common.BaseMapper;
import org.skyer.stock.domain.entity.WarehouseResultDetail;
import org.skyer.stock.domain.vo.WarehouseResultQtyVO;

/**
 * 入库商品详情Mapper
 *
 * @author xurongfa 2022-04-26 14:10:53
 */
public interface WarehouseResultDetailMapper extends BaseMapper<WarehouseResultDetail> {
    /**
     * 统计入库结果的商品数量
     * @param warehouseResultId 入库结果单id
     * @return {@link WarehouseResultQtyVO}
     */
    WarehouseResultQtyVO statsQty(@Param("warehouseResultId") Long warehouseResultId);
}
