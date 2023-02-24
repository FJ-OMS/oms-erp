package org.skyer.order.domain.repository;

import org.skyer.mybatis.base.BaseRepository;
import org.skyer.order.domain.entity.ItemLine;
import org.skyer.order.domain.entity.ThirdItem;

import java.util.List;
import java.util.Map;

/**
 * 内部订单商品明细资源库
 *
 * @author zhanglongbing@ebc.com 2022-04-20 10:15:56
 */
public interface ThirdItemRepository extends BaseRepository<ThirdItem> {

    /**
     * 根据外部单号查询thirdItemList(Map)
     * @param outerOrderList outerOrderList
     * @return Map<String, List<ThirdItem>>
     */
    Map<String, List<ThirdItem>> queryThirdItemByOuterNo(List<String> outerOrderList);
}
