package org.skyer.stock.domain.service.saga.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.skyer.saga.saga.SagaDefinition;
import org.skyer.saga.saga.annotation.SagaTask;
import org.skyer.stock.domain.service.AdjustRecordService;
import org.skyer.stock.domain.service.ComputeAvailableStockService;

/**
 * 库存调整saga服务实现类
 *
 * @author lixiaoyang
 * @date 2022/4/20 15:36
 */
@Component
public class SkuQtyAdjustServiceImpl{
    @Autowired
    private AdjustRecordService adjustRecordService;
    @Autowired
    private ComputeAvailableStockService computeAvailableStockService;


    /**
     * 执行调整单审核后商品逻辑仓库存变化处理
     *
     * @author lixiaoyang
     * @date 2022/4/11 14:51
     */
    @SagaTask(code = "sku-logic-adjust-2", sagaCode = "do-sku-qty-adjust-2", description = "逻辑仓商品数量调整",
            concurrentLimitNum = 3, concurrentLimitPolicy = SagaDefinition.ConcurrentLimitPolicy.TYPE_AND_ID, seq = 1)
    public Long exeSkuLogicQtyAdjust(String data) {
        return adjustRecordService.exeSkuLogicQtyAdjust(Long.valueOf(data));
    }

    @SagaTask(code = "adjust-async-es-2", sagaCode = "do-sku-qty-adjust-2", description = "同步es",
            concurrentLimitNum = 2, concurrentLimitPolicy = SagaDefinition.ConcurrentLimitPolicy.TYPE_AND_ID, seq = 2)
    public void adjustAsyncEs(String data) {
        computeAvailableStockService.asyncEs();
    }
}
