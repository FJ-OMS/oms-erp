package org.skyer.stock.domain.service;


/**
 * 可用库存计算服务
 *
 * @author lixiaoyang
 * @date 2021/9/7 11:18
 */
public interface ComputeAvailableStockService {

    /**
     * 库存同步到es
     *
     * @author lixiaoyang
     * @date 2022/4/19 14:26
     */
    void asyncEs();
}
