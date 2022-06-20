package org.skyer.order.app.service;

import java.util.List;

import org.skyer.order.api.dto.*;
import org.skyer.order.api.vo.CopyOrderVO;
import org.skyer.order.api.vo.OrderTotalVO;
import org.skyer.order.api.vo.RpcOrderInfoVO;
import org.skyer.order.app.dto.BaseListDTO;
import org.skyer.order.app.dto.OutboundDeliveryOrderDTO;
import org.skyer.order.app.dto.ResultRecordDTO;

/**
 * 内部订单头应用服务
 *
 * @author wushaochuan 2022-01-18 14:34:53
 */
public interface HeaderService {

    /**
     * 创建订单头
     *
     * @param orderTableDTO
     */
    BaseListDTO<String, OrderItemDTO> createHandOrder(OrderTableDTO orderTableDTO);

    /**
     * 根据订单id获取订单详情
     *
     * @param orderId
     * @return
     */
    OrderTotalVO queryOrderTotal(Long orderId,String code);


    /**
     * 编辑订单基础信息
     *
     * @param orderBaseDTO
     */
    void editOrderBase(OrderBaseDTO orderBaseDTO);

    /**
     * 复制订单
     *
     * @param innerOrderId
     * @return
     */
    CopyOrderVO copyOrder(String innerOrderId);

    /**
     * 确认收货
     *
     * @param orderId
     */
    void confirmReceipt(Long orderId);

    /**
     * 商品行确认收货
     *
     * @param itemConfirmDTO
     */
    void itemConfirmReceipt(ItemConfirmDTO itemConfirmDTO);

    /**
     * 出库单生成后反写
     *
     * @author lixiaoyang
     * @date 2022/2/25 16:57
     */
    Boolean outboundDeliveryOrderRetrography(OutboundDeliveryOrderDTO dto);

    /**
     * 结果单生成后反写
     *
     * @author lixiaoyang
     * @date 2022/2/25 17:22
     */
    Boolean resultRecordRetrography(ResultRecordDTO dto);

    /**
     * 根据第三方订单编号获取内部单集合（远程调用）
     *
     * @author lixiaoyang
     * @date 2022/3/23 11:34
     */
    List<AccountCheckingOrderDTO> getOrderList(AccountCheckingOrderQueryDTO dto);

    /**
     * 根据退款单号获取
     *
     * @author lixiaoyang
     * @date 2022/3/24 10:44
     */
    List<AccountCheckingOrderDTO> getAfterSaleList(AccountCheckingOrderQueryDTO dto);

    /**
     * 远程调用获取订单价格与收货人信息
     * @param batchStringListDTO
     * @return
     */
    List<RpcOrderInfoVO> batchGetOrderPrice(List<String> batchStringListDTO);

    /**
     * 生成出库单
     * @param innerOrderCode
     */
    void createSendNotice(String innerOrderCode);
}
