package org.skyer.stock.infra.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

import org.skyer.mybatis.common.BaseMapper;
import org.skyer.stock.domain.entity.WarehouseWarrantDetail;
import org.skyer.stock.domain.vo.WarehouseWarrantQtyVO;

/**
 * 入库商品详情Mapper
 *
 * @author lixiaoyang 2021-10-25 16:34:17
 */
public interface WarehouseWarrantDetailMapper extends BaseMapper<WarehouseWarrantDetail> {

    /**
     * 统计通知单id的数量
     *
     * @param id 通知单id
     * @return {@link WarehouseWarrantQtyVO}
     */
    WarehouseWarrantQtyVO statsQty(Long id);

    /**
     * 根据通知单ID获取明细
     *
     * @author lixiaoyang
     * @date 2022/4/27 15:07
     */
    List<WarehouseWarrantDetail> getAllByWarehouseWarrantId(@Param("warehouseWarrantId") Long warehouseWarrantId);

}
