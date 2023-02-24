package org.skyer.order.app.service;

import org.skyer.order.domain.entity.AfterSalesReason;

/**
 * 售后原因应用服务
 *
 * @author zhanglongbing@ebc.com 2022-03-14 10:41:33
 */
public interface AfterSalesReasonService {

    /**
     * 创建原因
     * @param afterSalesReason afterSalesReason
     * @return AfterSalesReason
     */
    AfterSalesReason createAfterSalesReason(AfterSalesReason afterSalesReason);


    /**
     * 编辑原因
     * @param afterSalesReason afterSalesReason
     * @return AfterSalesReason
     */
    AfterSalesReason updateAfterSalesReason(AfterSalesReason afterSalesReason);

}
