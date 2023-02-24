package org.skyer.order.domain.repository;

import org.skyer.mybatis.base.BaseRepository;
import org.skyer.order.domain.entity.ItemSnapshot;

import java.util.List;
import java.util.Map;

/**
 * 内部订单商品明细快照资源库
 *
 * @author wushaochuan 2022-01-18 14:34:54
 */
public interface ItemSnapshotRepository extends BaseRepository<ItemSnapshot> {

    /**
     * 查询商品快照
     * @param innerOrderNo
     * @param skuCode
     * @return
     */
    Map<String,ItemSnapshot> querySnapshot(String innerOrderNo, List<String> skuCode);

    /**
     *
     * @param innerOrderNo
     * @param skuCode
     * @return
     */
    List<ItemSnapshot> querySnapShotList(String innerOrderNo, List<String> skuCode);
}
