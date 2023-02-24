package org.skyer.stock.domain.repository;

import java.util.List;

import org.skyer.mybatis.base.BaseRepository;
import org.skyer.stock.domain.entity.WarehouseWarrantDetail;
import org.skyer.stock.domain.vo.WarehouseWarrantQtyVO;

/**
 * 入库商品详情资源库
 *
 * @author lixiaoyang 2021-10-25 16:34:17
 */
public interface WarehouseWarrantDetailRepository extends BaseRepository<WarehouseWarrantDetail> {

    /**
     * 统计通知单id的数量
     *
     * @param id 通知单id
     * @return {@link WarehouseWarrantQtyVO}
     */
    WarehouseWarrantQtyVO statsQty(Long id);


    /**
     * 根据通知单获取详情
     *
     * @author lixiaoyang
     * @date 2022/4/27 15:04
     */
    List<WarehouseWarrantDetail> getAllByWarehouseWarrantId(Long warehouseWarrantId);
}
