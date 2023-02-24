package org.skyer.order.domain.repository;

import org.skyer.mybatis.base.BaseRepository;
import org.skyer.order.domain.entity.Price;

import java.util.List;
import java.util.Map;

/**
 * 内部订单价格（聚合）资源库
 *
 * @author wushaochuan 2022-01-18 14:34:54
 */
public interface PriceRepository extends BaseRepository<Price> {

    /**
     * 获取金额
     * @param innerOrderNo
     * @return
     */
    Map<String,Price> queryOrderPrice(String innerOrderNo);

    /**
     * 获取金额
     * @param innerOrderNoList
     * @return
     */
    List<Price> queryOrderPriceByList(List<String> innerOrderNoList);
}
