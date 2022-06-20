package org.skyer.stock.domain.service.saga;

import org.skyer.stock.api.dto.OutboundDeliveryOrderDTO;

public interface StockService {

    /**
     * 调整审核后处理
     *
     * @param adjustRecordId
     * @return
     * @author lixiaoyang
     * @date 2022/4/11 14:43
     */
    void doSkuQtyAdjust(Long adjustRecordId);

    /**
     * 接收结果单
     *
     * @author lixiaoyang
     * @date 2022/2/22 11:11
     */
    void acceptResultRecord(OutboundDeliveryOrderDTO dto);
}
