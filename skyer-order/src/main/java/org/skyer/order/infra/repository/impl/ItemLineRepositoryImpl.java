package org.skyer.order.infra.repository.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.skyer.core.domain.Page;
import org.skyer.mybatis.base.impl.BaseRepositoryImpl;
import org.skyer.mybatis.domian.Condition;
import org.skyer.mybatis.pagehelper.PageHelper;
import org.skyer.mybatis.pagehelper.domain.PageRequest;
import org.skyer.mybatis.util.Sqls;
import org.skyer.order.api.dto.ItemSearchDTO;
import org.skyer.order.api.dto.ShopIdsAndSkuCodesDTO;
import org.skyer.order.api.vo.*;
import org.skyer.order.domain.entity.*;
import org.skyer.order.domain.repository.HeaderRepository;
import org.skyer.order.domain.repository.ItemLinePriceRepository;
import org.skyer.order.domain.repository.ItemLineRepository;
import org.skyer.order.infra.convertor.OrderItemLineConvertor;
import org.skyer.order.infra.enums.OrderItemLineEnum;
import org.skyer.order.infra.enums.PriceTypeEnum;
import org.skyer.order.infra.mapper.ItemLineMapper;
import org.skyer.order.infra.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * 内部订单商品行 资源库实现
 *
 * @author wushaochuan 2022-01-18 14:34:54
 */
@Component
@Slf4j
public class ItemLineRepositoryImpl extends BaseRepositoryImpl<ItemLine> implements ItemLineRepository {
    @Autowired
    private ItemLineMapper itemLineMapper;
    @Autowired
    private OrderItemLineConvertor orderItemLineConvertor;
    @Autowired
    private ItemLinePriceRepository itemLinePriceRepository;
    @Autowired
    private HeaderRepository headerRepository;


    @Override
    public Map<String, List<ItemLine>> queryItemLineByInner(List<String> innerOrderList) {
        List<ItemLine> itemLines = itemLineMapper.selectByCondition(Condition.builder(ItemLine.class).andWhere(Sqls.custom().andIn(ItemLine.FIELD_INNER_ORDER_NO, innerOrderList)).build());
        if (CollectionUtil.isEmpty(itemLines)) {
            return null;
        }
        return itemLines.stream().collect(Collectors.groupingBy(ItemLine::getInnerOrderNo));
    }

    @Override
    public Map<Long, List<ItemLine>> queryNormalItemLineByAuditId(List<Long> auditIdList) {
        List<ItemLine> itemLines = itemLineMapper.selectByCondition(Condition.builder(ItemLine.class).andWhere(Sqls.custom().andIn(ItemLine.FIELD_AUDIT_ID, auditIdList)).build());
        if (CollectionUtil.isEmpty(itemLines)) {
            return null;
        }
        Map<Long, List<ItemLine>> itemLineMap = itemLines.stream().collect(Collectors.groupingBy(ItemLine::getAuditId));
        return itemLineMap;
    }

    @Override
    public List<ItemLine> filterAbnormal(List<ItemLine> itemLines) {
       return itemLines.stream().filter(itemLine -> {
            boolean cancelStatus = String.valueOf(OrderItemLineEnum.CANCEL.getCode()).equals(Optional.ofNullable(itemLine.getCancelStatus()).orElse("0"));
            boolean systemSuspendStatus = String.valueOf(OrderItemLineEnum.SUSPEND_SYSTEM_SUCCESS.getCode()).equals(Optional.ofNullable(itemLine.getSuspendStatus()).orElse("0"));
            boolean artificialSuspendStatus = String.valueOf(OrderItemLineEnum.SUSPEND_ARTIFICIAL_SUCCESS.getCode()).equals(Optional.ofNullable(itemLine.getSuspendStatus()).orElse("0"));
            return !(cancelStatus || systemSuspendStatus || artificialSuspendStatus);
        }).collect(Collectors.toList());
    }

    @Override
    public List<ItemLine> queryItemLineByItemLineId(String innerOrderNo, List<Long> itemLineIdList) {
        return itemLineMapper.selectByCondition(Condition.builder(ItemLine.class).andWhere(Sqls.custom().andEqualTo(ItemLine.FIELD_INNER_ORDER_NO, innerOrderNo)
                .andIn(ItemLine.FIELD_ID, itemLineIdList).andNotEqualTo(Item.FIELD_SOURCE_STATUS, OrderItemLineEnum.SOURCING_SUCCESS.getCode())
                .andEqualTo(Item.FIELD_PREEMPTION_STATUS,OrderItemLineEnum.PREEMPTION_SUCCESS.getCode())).build());

    }

    @Override
    public List<ItemLine> queryItemLineListByInner(String innerOrderNo) {
        List<ItemLine> itemLines = itemLineMapper.selectByCondition(Condition.builder(ItemLine.class).andWhere(Sqls.custom().andEqualTo(ItemLine.FIELD_INNER_ORDER_NO, innerOrderNo)).build());
        if (CollectionUtil.isEmpty(itemLines)) {
            return null;
        }
        return itemLines;
    }

    @Override
    public List<ItemLine> batchQueryItemLineByIds(List<Long> itemLineIds) {
        return itemLineMapper.selectByCondition(Condition.builder(ItemLine.class).andWhere(Sqls.custom().andIn(ItemLine.FIELD_ID, itemLineIds)).build());
    }

    @Override
    public Page<OrderItemLinePageVO> queryItemLinePage(ItemSearchDTO itemSearchDTO) {
        Header header = headerRepository.selectByPrimaryKey(itemSearchDTO.getOrderId());
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPage(itemSearchDTO.getPage());
        pageRequest.setSize(itemSearchDTO.getSize());
        Page<ItemLine> itemLinePage = PageHelper.doPageAndSort(pageRequest, () -> itemLineMapper.selectByCondition(Condition.builder(ItemLine.class).andWhere(Sqls.custom()
                .andEqualTo(ItemLine.FIELD_INNER_ORDER_NO,header.getInnerOrderNo())
                .andEqualTo(ItemLine.FIELD_DELIVERY_STATUS, itemSearchDTO.getDeliveryStatus(), true)
                .andEqualTo(ItemLine.FIELD_PREEMPTION_STATUS, itemSearchDTO.getPreemptionStatus(), true)
                .andEqualTo(ItemLine.FIELD_SUSPEND_STATUS, itemSearchDTO.getSuspendStatus(), true)
                .andEqualTo(ItemLine.FIELD_SOURCE_STATUS, itemSearchDTO.getSourceStatus(), true)
                .andEqualTo(ItemLine.FIELD_SYNC_DELIVERY_STATUS, itemSearchDTO.getSyncDeliveryStatus(), true)
                .andEqualTo(ItemLine.FIELD_SYNC_PREEMP_STATUS, itemSearchDTO.getSyncPreempStatus(), true)
                .andEqualTo(ItemLine.FIELD_REFUND_FLAG, itemSearchDTO.getRefundFlag(), true)
                .andEqualTo(ItemLine.FIELD_RECEIVING, itemSearchDTO.getReceiveStatus(), true)
        ).build()));

        List<OrderItemLinePageVO> orderItemLinePageVOS = orderItemLineConvertor.itemLineTOPageVOList(itemLinePage.getContent());
        if (CollectionUtil.isNotEmpty(orderItemLinePageVOS)) {
            setItemLinePrice(orderItemLinePageVOS);
        }
        Page<OrderItemLinePageVO> page = new Page<>();
        page.setTotalElements(itemLinePage.getTotalElements());
        page.setNumberOfElements(itemLinePage.getNumberOfElements());
        page.setTotalPages(itemLinePage.getTotalPages());
        page.setNumber(itemLinePage.getNumber());
        page.setSize(itemLinePage.getSize());
        page.setContent(orderItemLinePageVOS);
        return page;
    }


    @Override
    public List<OrderItemLinePageVO> queryItemLineList(Long itemId) {
        List<ItemLine> itemLines = itemLineMapper.selectByCondition(Condition.builder(ItemLine.class).andWhere(Sqls.custom().andEqualTo(ItemLine.FIELD_INNER_ITEM_ID,itemId)).build());
        if (CollUtil.isEmpty(itemLines)) {
            return new ArrayList<>();
        }
        List<OrderItemLinePageVO> orderItemLinePageVOS = orderItemLineConvertor.itemLineTOPageVOList(itemLines);
        setItemLinePrice(orderItemLinePageVOS);
        return orderItemLinePageVOS;
    }

    @Override
    public List<ItemLine> queryItemLineByItemId(Long itemId) {
        List<ItemLine> itemLines = itemLineMapper.selectByCondition(Condition.builder(ItemLine.class).andWhere(Sqls.custom().andEqualTo(ItemLine.FIELD_INNER_ITEM_ID,itemId)).build());
        if (CollUtil.isEmpty(itemLines)) {
            return new ArrayList<>();
        }
        return itemLines;
    }

    @Override
    public List<ItemLine> queryItemLineByItemIdPreemStatus(Long itemId, String preemptionStatus) {
        return itemLineMapper.selectByCondition(Condition.builder(ItemLine.class).andWhere(Sqls.custom().andEqualTo(ItemLine.FIELD_INNER_ITEM_ID, itemId).andEqualTo(ItemLine.FIELD_PREEMPTION_STATUS, preemptionStatus)).build());
    }

    @Override
    public List<ItemLine> queryItemLineByOrderNoPreemStatus(String innerNo, String preemptionStatus) {
        return itemLineMapper.selectByCondition(Condition.builder(ItemLine.class).andWhere(Sqls.custom().andEqualTo(ItemLine.FIELD_INNER_ORDER_NO, innerNo).andEqualTo(ItemLine.FIELD_PREEMPTION_STATUS, preemptionStatus)).build());
    }

    @Override
    public List<ItemLine> queryItemLineByItemId(List<Long> itemId) {
        List<ItemLine> itemLines = itemLineMapper.selectByCondition(Condition.builder(ItemLine.class).andWhere(Sqls.custom().andIn(ItemLine.FIELD_INNER_ITEM_ID,itemId)).build());
        if (CollUtil.isEmpty(itemLines)) {
            return new ArrayList<>();
        }
        return itemLines;
    }

    @Override
    public List<ItemLine> queryItemLineByIds(List<Long> itemLineId) {
        List<ItemLine> itemLines = itemLineMapper.selectByCondition(Condition.builder(ItemLine.class).andWhere(Sqls.custom().andIn(ItemLine.FIELD_ID,itemLineId)).build());
        if (CollUtil.isEmpty(itemLines)) {
            return new ArrayList<>();
        }
        return itemLines;
    }

    @Override
    public List<ItemLine> queryItemLineByPackNo(String packNo) {
        List<ItemLine> itemLineList = itemLineMapper.selectByCondition(Condition.builder(ItemLine.class).andWhere(Sqls.custom().andEqualTo(ItemLine.FIELD_PACKAGE_NO, packNo)).build());
        return itemLineList;
    }


    @Override
    public List<ItemLine> queryItemLineByInnerList(List<String> innerOrderList) {
        List<ItemLine> itemLines = itemLineMapper.selectByCondition(Condition.builder(ItemLine.class).andWhere(Sqls.custom().andIn(ItemLine.FIELD_INNER_ORDER_NO, innerOrderList)).build());
        if (CollectionUtil.isEmpty(itemLines)) {
            return null;
        }
        return itemLines;
    }



    private void setItemLinePrice(List<OrderItemLinePageVO> orderItemLinePageVOS) {
        List<Long> idList = orderItemLinePageVOS.stream().map(OrderItemLinePageVO::getId).collect(Collectors.toList());
        if (CollUtil.isNotEmpty(idList)) {
            Map<Long, Map<String, ItemLinePrice>> itemLinePriceMap = itemLinePriceRepository.queryItemLinePriceMap(idList);
            //拼接价格
            orderItemLinePageVOS.forEach(orderItemLinePageVO -> {
                if (itemLinePriceMap != null && itemLinePriceMap.get(orderItemLinePageVO.getId()) != null) {
                    Map<String, ItemLinePrice> itemLinePriceMapById = itemLinePriceMap.get(orderItemLinePageVO.getId());
                    orderItemLinePageVO.setThirdSaleAmount(Optional.ofNullable(itemLinePriceMapById.get(PriceTypeEnum.OUT_SALE_PRICE.getType())).orElse(new ItemLinePrice()).getAmount());
                    orderItemLinePageVO.setOutBalancePrice(Optional.ofNullable(itemLinePriceMapById.get(PriceTypeEnum.OUT_BALANCE_PRICE.getType())).orElse(new ItemLinePrice()).getAmount());
                    orderItemLinePageVO.setInnerSalePrice(Optional.ofNullable(itemLinePriceMapById.get(PriceTypeEnum.INNER_SALE_PRICE.getType())).orElse(new ItemLinePrice()).getAmount());
                    orderItemLinePageVO.setInnerBalancePrice(Optional.ofNullable(itemLinePriceMapById.get(PriceTypeEnum.INNER_BALANCE_PRICE.getType())).orElse(new ItemLinePrice()).getAmount());
                    orderItemLinePageVO.setTotalCostAmount(Optional.ofNullable(itemLinePriceMapById.get(PriceTypeEnum.COST_TOTAL_PRICE.getType())).orElse(new ItemLinePrice()).getAmount());
                    orderItemLinePageVO.setCostAmount(Optional.ofNullable(itemLinePriceMapById.get(PriceTypeEnum.COST_TOTAL_PRICE.getType())).orElse(new ItemLinePrice()).getAmount());
                    orderItemLinePageVO.setTransPrice(Optional.ofNullable(itemLinePriceMapById.get(PriceTypeEnum.TRANS_PRICE.getType())).orElse(new ItemLinePrice()).getAmount());
                }
            });
        }
    }




    @Override
    public List<ItemLine> queryItemLineBySourceId(Long sourceId) {
        return itemLineMapper.selectByCondition(Condition.builder(ItemLine.class).andWhere(Sqls.custom().andEqualTo(ItemLine.FIELD_SOURCE_NOTICE_ID, sourceId)).build());
    }



    @Override
    public List<ShopSkuStockoutVO> storeStockout(ShopIdsAndSkuCodesDTO shopIdsAndSkuCodesDTO) {
        List<ShopSkuStockoutVO> result =Lists.newArrayList();
        List<Long> shopIdList = shopIdsAndSkuCodesDTO.getShopIdList();
        List<String> skuCodeList = shopIdsAndSkuCodesDTO.getSkuCodeList();
        if (CollUtil.isEmpty(shopIdList) || CollUtil.isEmpty(skuCodeList)){
            return Collections.emptyList();
        }
        List<ItemLine> lines = this.selectByCondition(Condition.builder(ItemLine.class)
                .andWhere(Sqls.custom().andIn(ItemLine.FIELD_STORE_ID, shopIdList)).build());
        if (CollUtil.isNotEmpty(lines)){
            // 过滤skuCode
            List<ItemLine> itemLines = lines.stream().filter(x -> skuCodeList.contains(x.getSkuCode())).collect(Collectors.toList());

            // 统计店铺Sku
            itemLines.forEach(x->{
                ShopSkuStockoutVO vo = new ShopSkuStockoutVO();
                vo.setShopId(x.getStoreId());
                vo.setSkuCode(x.getSkuCode());
                vo.setStockoutQty(new BigDecimal(itemLineMapper.countStockout(x.getStoreId(),x.getSkuCode())));
                result.add(vo);
            });

        }
        return result.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(()-> new TreeSet<>(Comparator.comparing(u->u.getSkuCode()+u.getShopId()))),ArrayList::new));
    }
}
