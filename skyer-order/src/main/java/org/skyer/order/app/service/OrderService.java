package org.skyer.order.app.service;

import org.skyer.order.api.dto.OrderCancelDTO;
import org.skyer.order.api.dto.OrderItemDTO;
import org.skyer.order.api.dto.OrderTableDTO;
import org.skyer.order.app.dto.CalAmountDTO;
import org.skyer.order.app.dto.NeedOrderDTO;
import org.skyer.order.app.dto.PackItemAmountDTO;
import org.skyer.order.app.dto.SplitItemAmountDTO;
import org.skyer.order.app.vo.WorkExecuteVO;
import org.skyer.order.domain.entity.Header;
import org.skyer.order.domain.entity.Item;
import org.skyer.order.domain.entity.ItemLine;
import org.skyer.order.domain.entity.ItemSnapshot;

import java.math.BigDecimal;
import java.util.List;

/**
 * @description
 * @Author wusc
 * @create 2022/1/20 2:30 下午
 */
public interface OrderService {


    void createInnerOrderBpmn(OrderTableDTO orderTableDTO);

    /**
     * 订单取消
     * @param orderCancelDTO
     */
    void cancelOrder(OrderCancelDTO orderCancelDTO);



    /**
     * 价格均摊
     * @param calAmountDTO type 1 计算平台均摊价 2 计算内部均摊价
     */
    void splitAmount(CalAmountDTO calAmountDTO,Boolean calGiftFlag,Integer type);

    /**
     * 计算打包均摊包(赠品打包)里面的商品均摊价格
     * @param packItemAmountDTOList
     */
    List<SplitItemAmountDTO>  splitPackAmount(List<PackItemAmountDTO> packItemAmountDTOList,Boolean calGift,Integer type);

}
