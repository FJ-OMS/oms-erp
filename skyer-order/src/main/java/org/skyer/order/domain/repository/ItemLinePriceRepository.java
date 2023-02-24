package org.skyer.order.domain.repository;

import org.skyer.mybatis.base.BaseRepository;
import org.skyer.order.domain.entity.ItemLinePrice;

import java.util.List;
import java.util.Map;

/**
 * 内部订单商品行价格资源库
 *
 * @author wushaochuan 2022-01-18 14:34:54
 */
public interface ItemLinePriceRepository extends BaseRepository<ItemLinePrice> {
    /**
     * 获取平摊商品行价格价格
     * @param itemLineId
     * @return
     */
    Map<Long,Map<String,ItemLinePrice>> queryItemLinePriceMap(List<Long> itemLineId);

    /**
     * 获取商品行id集合获取商品行价格
     * @param itemLineId
     * @param type  结果类型 (选填)
     * @return
     */
    List<ItemLinePrice> queryItemLineIdList(List<Long> itemLineId,String type);
}
