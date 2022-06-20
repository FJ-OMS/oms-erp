package org.skyer.order.app.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import io.camunda.zeebe.spring.client.exception.ZeebeBpmnError;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.skyer.core.exception.CommonException;
import org.skyer.order.api.dto.*;
import org.skyer.order.app.dto.*;
import org.skyer.order.app.service.*;
import org.skyer.order.app.service.saga.OrderForwardSagaService;
import org.skyer.order.domain.entity.*;
import org.skyer.order.domain.repository.*;
import org.skyer.order.infra.common.CommonConstants;
import org.skyer.order.infra.enums.OrderItemEnum;
import org.skyer.order.infra.enums.OrderItemLineEnum;
import org.skyer.order.infra.enums.OrderStatusEnum;
import org.skyer.order.infra.feign.entity.generate.odo.in.OutboundDeliveryOrderDetailLineDTO;
import org.skyer.order.infra.feign.entity.generate.odo.in.OutboundDeliveryOrderInfoDTO;
import org.skyer.order.infra.util.CollectorsUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @description
 * @Author wusc
 * @create 2022/1/20 2:31 下午
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private HeaderRepository headerRepository;
    @Autowired
    private ItemLineRepository itemLineRepository;
    @Autowired
    private OrderEsService orderEsService;
    @Autowired
    private ReceiverInfoRepository receiverInfoRepository;

    @Autowired
    private OrderForwardSagaService orderForwardSagaService;
    @Autowired
    private ItemRepository itemRepository;




    @Override
    public void createInnerOrderBpmn(OrderTableDTO orderTableDTO) {
        // 判断第三方订单号是否存在
        String outerOrderNo = orderTableDTO.getOrderBaseInfoDTO().getOuterOrderNo();
        Header header = headerRepository.queryHeaderByOutNoAndType(outerOrderNo, orderTableDTO.getOrderBaseInfoDTO().getBusinessType());
        if (header != null) {
            throw new CommonException("创建订单失败:该第三方订单号已存在");
        }
        orderForwardSagaService.createInnerOrder(orderTableDTO);
    }
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void cancelOrder(OrderCancelDTO orderCancelDTO) {
        List<ItemLine> itemLineList = queryNeedCancelItemLine(orderCancelDTO);
        if (CollectionUtil.isEmpty(itemLineList)) {
            throw new CommonException("未查询到对应商品行");
        }
        List<ItemLine> suspendItemLineList = itemLineList.stream().filter(itemLine -> String.valueOf(OrderItemLineEnum.SUSPEND_SYSTEM_SUCCESS.getCode()).equals(itemLine.getSuspendStatus()) ||
                String.valueOf(OrderItemLineEnum.SUSPEND_ARTIFICIAL_SUCCESS.getCode()).equals(itemLine.getSuspendStatus())).collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(suspendItemLineList)) {
            log.info("订单取消失败，存在已挂起的商品行{}", JSON.toJSONString(suspendItemLineList));
            throw new CommonException("当前存在挂起状态的商品行");
        }
        List<ItemLine> noCancelItemLineList = itemLineList.stream().filter(x -> !String.valueOf(OrderItemLineEnum.CANCEL.getCode()).equals(x.getCancelStatus())).collect(Collectors.toList());
        if (CollectionUtil.isEmpty(noCancelItemLineList)) {
            throw new CommonException("该订单无可以取消的商品行");
        }
        //更改商品行取消
        itemLineList.forEach(itemLine ->{
            itemLine.setCancelStatus(String.valueOf(OrderItemLineEnum.CANCEL.getCode()));
            itemLine.setCurrentStatus(String.valueOf(OrderItemLineEnum.CANCEL.getCode()));
            itemLine.setPreemptionStatus(String.valueOf(OrderItemLineEnum.CANCEL.getCode()));
        });
        itemLineRepository.batchUpdateByPrimaryKeySelective(itemLineList);
        //更新订单明细状态
        changeItemStatus(itemLineList.stream().map(ItemLine::getInnerItemId).distinct().collect(Collectors.toList()));
        //修改订单状态
        List<String> innerOrderNoList = itemLineList.stream().map(ItemLine::getInnerOrderNo).distinct().collect(Collectors.toList());
        changeHeaderStatus(innerOrderNoList);
        //更新es状态
        // TODO添加订单修改日志
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @Override
            public void afterCommit() {
                try{
                    orderEsService.batchInsertOrderToEs(innerOrderNoList);
                }catch (Exception e) {
                    log.info("更新es报错",e);
                }
            }
        });
    }

    public void changeItemStatus(List<Long> itemIdList) {
        List<Item> items = itemRepository.queryItemListByItemIds(itemIdList);
        items.forEach(item -> {
            List<ItemLine> itemLineList = itemLineRepository.queryItemLineByItemId(item.getId());
            List<ItemLine> noCancelItemLine = itemLineList.stream().filter(itemLine -> !String.valueOf(OrderItemLineEnum.CANCEL.getCode()).equals(itemLine.getCurrentStatus())).collect(Collectors.toList());
            if (CollectionUtil.isEmpty(noCancelItemLine)) {
                item.setPreemptionStatus(String.valueOf(OrderItemEnum.CANCEL.getCode()));
            }
        });
        itemRepository.batchUpdateByPrimaryKeySelective(items);
    }

    public void changeHeaderStatus(List<String> innerOrderNoList) {
        //获取商品行
        List<Header> headers = headerRepository.queryHeaderByInnerOrderNo(innerOrderNoList);
        Map<String, List<ItemLine>> itemLineMap = itemLineRepository.queryItemLineByInner(innerOrderNoList);
        headers.forEach(header -> {
            List<ItemLine> itemLineList = itemLineMap.get(header.getInnerOrderNo());
            List<ItemLine> cancelItemLineList = Optional.of(itemLineList.stream().filter(x -> String.valueOf(OrderItemLineEnum.CANCEL.getCode()).equals(x.getCancelStatus())).collect(Collectors.toList())).orElse(Lists.newArrayList());
            if (cancelItemLineList.size() == itemLineList.size()) {
                header.setCancelStatus(OrderStatusEnum.CANCEL.getCode());
                header.setOrderStatus(CommonConstants.OrderAggrStatus.CLOSE);
            }
            List<ItemLine> completeItemLineList = Optional.of(itemLineList.stream().filter(x -> String.valueOf(OrderItemLineEnum.SEND_SUCCESS.getCode()).equals(x.getCurrentStatus())).collect(Collectors.toList())).orElse(Lists.newArrayList());
            if ( completeItemLineList.size() > 0  && (completeItemLineList.size() + cancelItemLineList.size() == itemLineList.size())) {
                header.setOrderStatus(CommonConstants.OrderAggrStatus.COMPLETE);
            }
        });
        headerRepository.batchUpdateByPrimaryKeySelective(headers);
    }

    @Override
    public void splitAmount(CalAmountDTO calAmountDTO,Boolean calGiftFlag,Integer type) {
        List<PackItemAmountDTO> packItemAmountDTOList = calAmountDTO.getPackItemAmountDTOList();
        List<PackItemAmountDTO> finalPackItemAmountDTOList = packItemAmountDTOList;
        //最后用于减法
        PackItemAmountDTO maxPackItemDTO = packItemAmountDTOList.stream().max((packItemAmountDTO1, packItemAmountDTO2) -> {
            SplitItemAmountDTO splitItemAmountDTO1 = packItemAmountDTO1.getSplitItemAmountDTOList().stream().max(Comparator.comparing(SplitItemAmountDTO::getIsGift)).get();
            SplitItemAmountDTO splitItemAmountDTO2 = packItemAmountDTO2.getSplitItemAmountDTOList().stream().max(Comparator.comparing(SplitItemAmountDTO::getIsGift)).get();
            if (splitItemAmountDTO1.getIsGift() < splitItemAmountDTO2.getIsGift()) {
                return 1;
            }
            return 0;
        }).get();
        //计算包裹内的内部或者外部销售价格
        if (type == 1) {
            calAmountDTO.setOutTotalPackPrice(packItemAmountDTOList.parallelStream().map(packItemAmountDTO->{
                BigDecimal totalSplitPrice = calPackTotalSplitPrice(calGiftFlag, packItemAmountDTO.getSplitItemAmountDTOList(),type);
                packItemAmountDTO.setOutTotalSplitPrice(totalSplitPrice);
                return totalSplitPrice;
            }).collect(CollectorsUtil.summingBigDecimal(x->x)));
        }else{
            calAmountDTO.setInnerTotalPackPrice(packItemAmountDTOList.parallelStream().map(packItemAmountDTO->{
                BigDecimal totalSplitPrice = calPackTotalSplitPrice(calGiftFlag, packItemAmountDTO.getSplitItemAmountDTOList(),type);
                packItemAmountDTO.setInnerTotalSplitPrice(totalSplitPrice);
                return totalSplitPrice;
            }).collect(CollectorsUtil.summingBigDecimal(x->x)));
        }
        //当只有一个包裹的时候不需要拆出最后一个做减法
        BigDecimal maxPackItemPrice = packItemAmountDTOList.stream().filter(x-> finalPackItemAmountDTOList.size() == 1 || !x.getCode().equals(maxPackItemDTO.getCode())).map(packItemAmountDTO -> {
            //  打包后的均摊总价 = 打包后的销售总价/外部总的销售总价 * 实付金额
            BigDecimal packBalancePrice = BigDecimal.ZERO;
            if (type == 1 && BigDecimal.ZERO.compareTo(Optional.ofNullable(calAmountDTO.getOutTotalPackPrice()).orElse(BigDecimal.ZERO)) != 0) {
                //赠品没有平台均摊价默认为0 保留两位小数并且向下取整
                packBalancePrice = packItemAmountDTO.getOutTotalSplitPrice().divide(calAmountDTO.getOutTotalPackPrice(),5, RoundingMode.DOWN).multiply(calAmountDTO.getTotalPrice()).setScale(2,RoundingMode.DOWN);
            }else if(BigDecimal.ZERO.compareTo(Optional.ofNullable(calAmountDTO.getInnerTotalPackPrice()).orElse(BigDecimal.ZERO)) != 0) {
                packBalancePrice = packItemAmountDTO.getInnerTotalSplitPrice().divide(calAmountDTO.getInnerTotalPackPrice(),5, RoundingMode.DOWN).multiply(calAmountDTO.getTotalPrice()).setScale(2,RoundingMode.DOWN);
            }
            packItemAmountDTO.setPackBalancePrice(packBalancePrice);
            return packBalancePrice;
        }).collect(CollectorsUtil.summingBigDecimal(x -> x));
        if (finalPackItemAmountDTOList.size() > 1) {
            maxPackItemDTO.setPackBalancePrice(calAmountDTO.getTotalPrice().subtract(maxPackItemPrice));
            packItemAmountDTOList = packItemAmountDTOList.parallelStream().filter(x-> !x.getCode().equals(maxPackItemDTO.getCode())).collect(Collectors.toList());
            packItemAmountDTOList.add(maxPackItemDTO);
        }
        calAmountDTO.setPackItemAmountDTOList(packItemAmountDTOList);
    }

    @Override
    public  List<SplitItemAmountDTO>  splitPackAmount(List<PackItemAmountDTO> packItemAmountDTOList,Boolean calGift,Integer type) {
         return packItemAmountDTOList.stream().flatMap(packItemAmountDTO -> {
            //为了保证代码健壮性，计算均摊价只用一套 splitAmount 这个方法,其他只带入该方法计算
            CalAmountDTO calAmountDTO = new CalAmountDTO();
            calAmountDTO.setTotalPrice(packItemAmountDTO.getPackBalancePrice());
            if (type == 1) {
                calAmountDTO.setOutTotalPackPrice(packItemAmountDTO.getOutTotalSplitPrice());
            }else {
                calAmountDTO.setInnerTotalPackPrice(packItemAmountDTO.getInnerTotalSplitPrice());
            }
            List<SplitItemAmountDTO> splitItemAmountDTOList = packItemAmountDTO.getSplitItemAmountDTOList();
            calAmountDTO.setPackItemAmountDTOList(splitItemAmountDTOList.parallelStream().map(x -> {
                PackItemAmountDTO tempPackItemAmountDTO = new PackItemAmountDTO();
                tempPackItemAmountDTO.setCode(x.getCodeOrId());
                if (type == 1) {
                    tempPackItemAmountDTO.setOutTotalSplitPrice(x.getOutPrice());
                }else {
                    tempPackItemAmountDTO.setInnerTotalSplitPrice(x.getInnerPrice());
                }
                tempPackItemAmountDTO.setSplitItemAmountDTOList(Collections.singletonList(x));
                return tempPackItemAmountDTO;
            }).collect(Collectors.toList()));
            //将打包后的均摊包裹在进行进一步均摊--套餐，赠品，随品的均摊
            splitAmount(calAmountDTO,calGift,type);
            List<PackItemAmountDTO> resultPackItemAmountDTOList = calAmountDTO.getPackItemAmountDTOList();
            return resultPackItemAmountDTOList.stream().flatMap(resultPackItemAmountDTO -> {
                List<SplitItemAmountDTO> resultSplitItemAmountDTOList = resultPackItemAmountDTO.getSplitItemAmountDTOList();
                resultSplitItemAmountDTOList.forEach(result -> {
                    if (type == 1) {
                        result.setOutBalancePrice(resultPackItemAmountDTO.getPackBalancePrice());
                    }else {
                        result.setInnerBalancePrice(resultPackItemAmountDTO.getPackBalancePrice());
                    }
                });
                return resultSplitItemAmountDTOList.stream();
            });
        }).collect(Collectors.toList());
    }

    /**
     * 计算需要均摊的
     * @param splitItemAmountDTOList
     */
    private BigDecimal calPackTotalSplitPrice(Boolean calGiftFlag,List<SplitItemAmountDTO> splitItemAmountDTOList,Integer type) {
        return splitItemAmountDTOList.stream().map(splitItemAmountDTO -> {
            if (!calGiftFlag && splitItemAmountDTO.getIsGift() == 1) {
                return BigDecimal.ZERO;
            }
            if (type ==1) {
               return splitItemAmountDTO.getOutPrice().multiply(BigDecimal.valueOf(splitItemAmountDTO.getNum()));
            }else {
                return splitItemAmountDTO.getInnerPrice().multiply(BigDecimal.valueOf(splitItemAmountDTO.getNum()));
            }
        }).collect(CollectorsUtil.summingBigDecimal(x -> x));
    }

    private List<ItemLine> queryNeedCancelItemLine(OrderCancelDTO orderCancelDTO) {
        List<ItemLine> itemLines = Lists.newArrayList();
        if (orderCancelDTO.getInnerOrderNo() != null) {
            itemLines.addAll(Optional.ofNullable(itemLineRepository.queryItemLineListByInner(orderCancelDTO.getInnerOrderNo())).orElse(new ArrayList<>()));
        }

        if (CollectionUtil.isNotEmpty(orderCancelDTO.getItemIdList())){
            itemLines.addAll(Optional.ofNullable(itemLineRepository.queryItemLineByItemId(orderCancelDTO.getItemIdList())).orElse(new ArrayList<>()));
        }

        if (CollectionUtil.isNotEmpty(orderCancelDTO.getItemLineIdList())) {
            itemLines.addAll(Optional.ofNullable(itemLineRepository.queryItemLineByIds(orderCancelDTO.getItemLineIdList())).orElse(new ArrayList<>()));
        }
        if (CollectionUtil.isNotEmpty(itemLines)) {
            return itemLines.stream().distinct().collect(Collectors.toList());
        }
        return null;
    }
}
