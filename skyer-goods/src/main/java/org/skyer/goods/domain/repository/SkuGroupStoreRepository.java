package org.skyer.goods.domain.repository;

import org.skyer.mybatis.base.BaseRepository;
import org.skyer.goods.domain.entity.SkuGroupStore;

import java.util.List;

/**
 * Sku组合和店铺表资源库
 *
 * @author wushaochuan 2021-08-13 09:55:04
 */
public interface SkuGroupStoreRepository extends BaseRepository<SkuGroupStore> {
    /**
     * 更新组合商品门店
     * @param storeIdList
     * @param groupId
     * @param tenantId
     */
    void updateSkuGroupStore(List<Long> storeIdList,Long groupId,Long tenantId);

    /**
     * 查询店铺id列表
     * @param groupId
     * @return
     */
    List<SkuGroupStore> querySkuGroupStoreList(Long groupId);


}
