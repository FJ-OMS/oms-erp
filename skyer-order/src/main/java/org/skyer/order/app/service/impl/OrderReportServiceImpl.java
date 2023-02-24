package org.skyer.order.app.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.skyer.core.exception.CommonException;
import org.skyer.order.api.dto.OrderSaleDTO;
import org.skyer.order.api.dto.SaleItemReportDTO;
import org.skyer.order.api.vo.OrderSaleDateVO;
import org.skyer.order.api.vo.OrderSaleReportVO;
import org.skyer.order.api.vo.SaleItemReportVO;
import org.skyer.order.app.dto.SaleOrderOrderApiDTO;
import org.skyer.order.app.service.OrderReportService;
import org.skyer.order.app.vo.ChannelSaleOrderVO;
import org.skyer.order.domain.repository.AfterSaleHeaderRepository;
import org.skyer.order.domain.repository.HeaderRepository;
import org.skyer.order.domain.repository.ItemRepository;
import org.skyer.order.infra.feign.SpuRemoteService;
import org.skyer.order.infra.feign.vo.GoodsPageResVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderReportServiceImpl implements OrderReportService {
    @Autowired
    private HeaderRepository headerRepository;
    @Autowired
    private AfterSaleHeaderRepository afterSaleHeaderRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private SpuRemoteService spuRemoteService;

    @Override
    public List<OrderSaleReportVO> queryOrderSaleReport(OrderSaleDTO orderSaleDTO) {
        //统计正向单
        List<OrderSaleReportVO> orderSaleReportVOS = headerRepository.queryPartHeaderToSaleReport(orderSaleDTO);
        //统计逆向单
        List<OrderSaleReportVO> afterSaleReportVOs = afterSaleHeaderRepository.queryAfterHeaderToSaleReport(orderSaleDTO);
        Map<String, OrderSaleReportVO> typeAfterSaleMap = afterSaleReportVOs.stream().collect(Collectors.toMap(OrderSaleReportVO::getType, Function.identity(), (o1, o2) -> o1));
        orderSaleReportVOS.forEach(orderSaleReportVO -> {
            OrderSaleReportVO afterOrderSaleReportVo = typeAfterSaleMap.get(orderSaleReportVO.getType());
            if (afterOrderSaleReportVo == null) {
                afterOrderSaleReportVo = new OrderSaleReportVO();
                afterOrderSaleReportVo.setReverseOrderCount(0);
                afterOrderSaleReportVo.setReverseOrderAmount(BigDecimal.ZERO);
                afterOrderSaleReportVo.setReverseItemCount(0);
            }
            orderSaleReportVO.setReverseOrderAmount(Optional.ofNullable(afterOrderSaleReportVo.getReverseOrderAmount()).orElse(BigDecimal.ZERO));
            orderSaleReportVO.setReverseOrderCount(Optional.ofNullable(afterOrderSaleReportVo.getReverseOrderCount()).orElse(0));
            orderSaleReportVO.setReverseItemCount(Optional.ofNullable(afterOrderSaleReportVo.getReverseItemCount()).orElse(0));
            // 退款商品数量
            orderSaleReportVO.setNetSalesItemCount(orderSaleReportVO.getOrderCount() - orderSaleReportVO.getReverseOrderCount());
            orderSaleReportVO.setNetSalesAmount(orderSaleReportVO.getOrderAmount().subtract(orderSaleReportVO.getReverseOrderAmount()));
            orderSaleReportVO.setItemProfit(orderSaleReportVO.getNetSalesAmount().subtract(BigDecimal.valueOf(orderSaleReportVO.getItemCost())));
        });
        return orderSaleReportVOS;
    }

    @Override
    public List<ChannelSaleOrderVO> queryChannelOrderSaleReport(SaleOrderOrderApiDTO saleOrderOrderApiDTO) {
        List<ChannelSaleOrderVO> channelSaleOrderVOList = headerRepository.queryPartChannelSaleReport(saleOrderOrderApiDTO);
        List<ChannelSaleOrderVO> afterChannelSaleOrderVOList = afterSaleHeaderRepository.queryPartAfterChannelSaleReport(saleOrderOrderApiDTO);
        if (CollectionUtil.isNotEmpty(channelSaleOrderVOList) && CollectionUtil.isNotEmpty(afterChannelSaleOrderVOList)) {
            Map<String, ChannelSaleOrderVO> afterChannelSaleOrderVOMap = afterChannelSaleOrderVOList.stream().collect(Collectors.toMap(ChannelSaleOrderVO::getStoreCode, Function.identity(), (o1, o2) -> o1));
            channelSaleOrderVOList.forEach(channelSaleOrderVO -> {
                ChannelSaleOrderVO afterChannelSaleOrderVO = afterChannelSaleOrderVOMap.get(channelSaleOrderVO.getStoreCode());
                channelSaleOrderVO.setReverseOrderAmount(BigDecimal.ZERO);
                channelSaleOrderVO.setReverseOrderCount(0);
                if (afterChannelSaleOrderVO == null) {
                    channelSaleOrderVO.setReverseOrderAmount(afterChannelSaleOrderVO.getReverseOrderAmount());
                    channelSaleOrderVO.setReverseOrderCount(afterChannelSaleOrderVO.getReverseOrderCount());
                }
            });
        }
        return channelSaleOrderVOList;
    }

    @Override
    public List<OrderSaleDateVO> queryOrderSaleDateReport(OrderSaleDTO orderSaleDTO) {
        List<OrderSaleDateVO> orderSaleDateVOS = headerRepository.queryOrderSaleDateReport(orderSaleDTO);
        if (CollectionUtil.isEmpty(orderSaleDateVOS)){
            return orderSaleDateVOS;
        }
        List<OrderSaleDateVO> afterOrderSaleDateVOS = afterSaleHeaderRepository.queryOrderSaleDateReport(orderSaleDTO);
        if (CollectionUtil.isNotEmpty(afterOrderSaleDateVOS)) {
            Map<String, OrderSaleDateVO> afterOrderSaleDateVOMap = afterOrderSaleDateVOS.stream().collect(Collectors.toMap(OrderSaleDateVO::getOrderDate, Function.identity(), (o1, o2) -> o1));
            orderSaleDateVOS.forEach(orderSaleDateVO -> {
                OrderSaleDateVO afterOrderSaleDateVO = afterOrderSaleDateVOMap.get(orderSaleDateVO.getOrderDate());
                orderSaleDateVO.setReverseAmount(BigDecimal.ZERO);
                orderSaleDateVO.setReverseCount(0);
                orderSaleDateVO.setReverseItemCount(0);
                if (afterOrderSaleDateVO != null) {
                    orderSaleDateVO.setReverseAmount(afterOrderSaleDateVO.getReverseAmount());
                    orderSaleDateVO.setReverseCount(afterOrderSaleDateVO.getReverseCount());
                    orderSaleDateVO.setReverseItemCount(afterOrderSaleDateVO.getReverseItemCount());
                }
            });
        }
        return orderSaleDateVOS;
    }

    @Override
    public List<SaleItemReportVO> queryOrderItemReport(SaleItemReportDTO saleItemReportDTO) {
        if (saleItemReportDTO.getStartDate() == null ||saleItemReportDTO.getEndDate() == null) {
            throw new CommonException("日期不能为空");
        }
        List<SaleItemReportVO> saleItemReportVOS = itemRepository.totalOrderItemSaleReport(saleItemReportDTO);
        //计算两个时间间隔多少天
        Integer days = Integer.valueOf(String.valueOf(DateUtil.betweenDay(saleItemReportDTO.getStartDate(), saleItemReportDTO.getEndDate(),true)));
        if (CollectionUtil.isEmpty(saleItemReportVOS)) {
            return null;
        }
        List<String> spuCodeList = saleItemReportVOS.stream().map(SaleItemReportVO::getSpuCode).distinct().collect(Collectors.toList());
        log.info("调用商品中心入参：{}",JSONObject.toJSONString(spuCodeList));
        ResponseEntity<List<GoodsPageResVO>> listResponseEntity = spuRemoteService.queryGoodsBySpuList(spuCodeList);

        if (listResponseEntity == null || listResponseEntity.getBody() == null) {
            throw new CommonException("调用商品中心查询spu失败：入参为"+ JSONObject.toJSONString(listResponseEntity));
        }
        List<GoodsPageResVO> goodsResVOList = listResponseEntity.getBody();
        Map<String, GoodsPageResVO> spuCodeMap = goodsResVOList.stream().collect(Collectors.toMap(GoodsPageResVO::getSpuCode, Function.identity(), (o1, o2) -> o1));
        saleItemReportVOS.forEach(saleItemReportVO -> {
            saleItemReportVO.setBalanceOrderCount(saleItemReportVO.getOrderCount() / days);
            saleItemReportVO.setBalanceSaleCount(saleItemReportVO.getSaleCount() / days);
            saleItemReportVO.setBalanceSaleAmount(saleItemReportVO.getSaleAmount().divide(BigDecimal.valueOf(days),2, RoundingMode.UP));
            saleItemReportVO.setSpuName(spuCodeMap.get(saleItemReportVO.getSpuCode()).getName());
            saleItemReportVO.setClassify(spuCodeMap.get(saleItemReportVO.getSpuCode()).getCategoryName());
            saleItemReportVO.setBrand(spuCodeMap.get(saleItemReportVO.getSpuCode()).getBrandName());
            saleItemReportVO.setStatus(spuCodeMap.get(saleItemReportVO.getSpuCode()).getStatus());
        });
        return saleItemReportVOS;
    }
}
