package org.skyer.order.app.service;


import org.skyer.order.api.dto.OrderSaleDTO;
import org.skyer.order.api.dto.SaleItemReportDTO;
import org.skyer.order.api.vo.OrderSaleDateVO;
import org.skyer.order.api.vo.OrderSaleReportVO;
import org.skyer.order.api.vo.SaleItemReportVO;
import org.skyer.order.app.dto.SaleOrderOrderApiDTO;
import org.skyer.order.app.vo.ChannelSaleOrderVO;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface OrderReportService {

    /**
     * 销售统计标
     * @param orderSaleDTO
     * @return
     */
    List<OrderSaleReportVO> queryOrderSaleReport(OrderSaleDTO orderSaleDTO);

    /**
     * 查询渠道报表订单部分信息
     * @param saleOrderOrderApiDTO
     * @return
     */
    List<ChannelSaleOrderVO> queryChannelOrderSaleReport(SaleOrderOrderApiDTO saleOrderOrderApiDTO);

    /**
     * 查询每日报表销售情况
     * @param orderSaleDTO
     * @return
     */
    List<OrderSaleDateVO> queryOrderSaleDateReport(OrderSaleDTO orderSaleDTO);

    /**
     * 销售商品统计表
     * @param saleItemReportDTO
     * @return
     */
    List<SaleItemReportVO> queryOrderItemReport(SaleItemReportDTO saleItemReportDTO);
}
