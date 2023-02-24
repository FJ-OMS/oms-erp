package org.skyer.order.app.service;

import org.skyer.order.api.dto.OrderTableDTO;
import org.skyer.order.api.dto.ThirdOrderTableDTO;
import org.skyer.order.api.vo.ThirdOrderDetailVO;

/**
 * 第三方订单头应用服务
 *
 * @author zhanglongbing@ebc.com 2022-04-20 10:15:56
 */
public interface ThirdHeaderService {

    /**
     * 创建三方订单 saga
     * @param thirdOrderTableDto thirdOrderTableDto
     */
    void createOuterOrderBpmn(ThirdOrderTableDTO thirdOrderTableDto);
    /**
     * 创建第三方订单
     * @param thirdOrderTableDto thirdOrderTableDto
     */
    OrderTableDTO createThirdOrder(ThirdOrderTableDTO thirdOrderTableDto);

    /**
     * 第三方订单详情
     * @param id id
     * @param outerOrderNo outerOrderNo
     * @return ThirdOrderDetailVO
     */
    ThirdOrderDetailVO  getThirdOrderDetail(Long id,String outerOrderNo);


}
