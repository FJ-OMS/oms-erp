package org.skyer.order.app.service;

import org.skyer.order.domain.entity.Header;
import org.skyer.order.domain.entity.ItemSnapshot;

import java.util.List;
import java.util.Map;

/**
 * 内部订单商品明细快照应用服务
 *
 * @author wushaochuan 2022-01-18 14:34:54
 */
public interface ItemSnapshotService {

    /**
     * 校验 商品价格和价格项是否维护
     * @param skuCodeList
     * @param skuSnapshotMap
     * @return
     */
    List<String> checkItemSnapshot(Header header,List<String> skuCodeList, Map<String, ItemSnapshot> skuSnapshotMap);

    /**
     * 根据skuCode生成快照
     * @param skuCodeList
     * @return
     */
    List<ItemSnapshot> saveSnapshot(Header header, List<String> skuCodeList,List<String> errorSkuCodeList);

    /**
     * 更新快照
     * @param skuCodeList
     * @return
     */
    List<String> updateSnapshot(Header header,List<String> skuCodeList,List<ItemSnapshot> notPriceSnapShot);
}
