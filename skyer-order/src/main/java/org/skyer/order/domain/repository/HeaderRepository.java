package org.skyer.order.domain.repository;

import org.skyer.core.domain.Page;
import org.skyer.mybatis.base.BaseRepository;
import org.skyer.mybatis.pagehelper.domain.PageRequest;
import org.skyer.order.api.dto.AccountCheckingOrderDTO;
import org.skyer.order.api.dto.AccountCheckingOrderQueryDTO;
import org.skyer.order.api.dto.OrderPageDTO;
import org.skyer.order.api.dto.OrderSaleDTO;
import org.skyer.order.api.vo.OrderPageVo;
import org.skyer.order.api.vo.OrderSaleDateVO;
import org.skyer.order.api.vo.OrderSaleReportVO;
import org.skyer.order.app.dto.SaleOrderOrderApiDTO;
import org.skyer.order.app.vo.ChannelSaleOrderVO;
import org.skyer.order.domain.entity.Header;

import java.util.List;

/**
 * 内部订单头资源库
 *
 * @author wushaochuan 2022-01-18 14:34:53
 */
public interface HeaderRepository extends BaseRepository<Header> {

    /**
     * 根据外部订单号和类型查询订单头
     *
     * @param outerOrderNo
     * @param businessType
     * @return
     */
    Header queryHeaderByOutNoAndType(String outerOrderNo, String businessType);

    /**
     * 根据内部单号查询订单
     *
     * @param innerOrderNo
     * @return
     */
    List<Header> queryHeaderByInnerOrderNo(List<String> innerOrderNo);

    /**
     * 根据内部单查询header
     *
     * @param innerOrderNo
     * @return
     */
    Header queryHeaderByInnerOrderNo(String innerOrderNo);

    /**
     * 订单分页列表
     *
     * @param pageRequest
     * @param orderPageDTO
     * @return
     */
    Page<OrderPageVo> orderPageList(PageRequest pageRequest, OrderPageDTO orderPageDTO);

    /**
     * 对账（远程调用）
     *
     * @author lixiaoyang
     * @date 2022/3/23 11:40
     */
    List<AccountCheckingOrderDTO> getOrderList(AccountCheckingOrderQueryDTO dto);

    /**
     * 对账（远程调用）
     *
     * @author lixiaoyang
     * @date 2022/3/23 11:40
     */
    List<AccountCheckingOrderDTO> getAfterSaleList(AccountCheckingOrderQueryDTO dto);

    /**
     * 统计正向订单数，正向销售件数，正向销售金额 到销售统计列表
     * @param orderSaleDTO
     * @return
     */
    List<OrderSaleReportVO> queryPartHeaderToSaleReport(OrderSaleDTO orderSaleDTO);

    /**
     * 获取部分店铺销售信息
     * @param saleOrderOrderApiDTO
     * @return
     */
    List<ChannelSaleOrderVO> queryPartChannelSaleReport(SaleOrderOrderApiDTO saleOrderOrderApiDTO);

    /**
     *
     * @param orderSaleDTO
     * @return
     */
    List<OrderSaleDateVO> queryOrderSaleDateReport(OrderSaleDTO orderSaleDTO);
}
