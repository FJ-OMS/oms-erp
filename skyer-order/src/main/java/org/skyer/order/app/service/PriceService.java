package org.skyer.order.app.service;

import org.skyer.order.api.dto.OrderPaymentInfoDTO;
import org.skyer.order.domain.entity.Header;

/**
 * 内部订单价格（聚合）应用服务
 *
 * @author wushaochuan 2022-01-18 14:34:54
 */
public interface PriceService {

    /**
     * 创建订单价格
     * @param orderPaymentInfoDTO
     */
    void saveOrderPrice(Header header, OrderPaymentInfoDTO orderPaymentInfoDTO);
}
