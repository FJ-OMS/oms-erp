package org.skyer.stock.domain.repository;

import org.skyer.mybatis.base.BaseRepository;
import org.skyer.stock.domain.entity.WarehouseResultDetail;
import org.skyer.stock.domain.vo.WarehouseResultQtyVO;

/**
 * 入库商品详情资源库
 *
 * @author xurongfa 2022-04-26 14:10:53
 */
public interface WarehouseResultDetailRepository extends BaseRepository<WarehouseResultDetail> {

    /**
     * 统计入库结果的商品数量
     *
     * @param id 入库结果单id
     * @return {@link WarehouseResultQtyVO}
     */
    WarehouseResultQtyVO statsQty(Long id);

}
