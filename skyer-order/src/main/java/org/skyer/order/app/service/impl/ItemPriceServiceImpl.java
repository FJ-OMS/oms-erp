package org.skyer.order.app.service.impl;

import org.apache.commons.compress.utils.Lists;
import org.skyer.order.app.service.ItemPriceService;
import org.skyer.order.app.statemachine.BizBean;
import org.skyer.order.domain.entity.Item;
import org.skyer.order.domain.entity.ItemPrice;
import org.skyer.order.domain.repository.ItemPriceRepository;
import org.skyer.order.infra.enums.PriceTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 内部订单明细价格（聚合）应用服务默认实现
 *
 * @author wushaochuan 2022-01-18 14:34:54
 */
@Service
public class ItemPriceServiceImpl implements ItemPriceService {
    @Autowired
    private ItemPriceRepository itemPriceRepository;
    @Override
    public void saveItemPrice(List<Item> itemList) {
        List<ItemPrice> finalItemPriceList = itemList.stream().flatMap(item -> {
            List<ItemPrice> itemPriceList = Lists.newArrayList();
            ItemPrice thirdSalePrice = new ItemPrice(null, item.getOuterOrderNo(), item.getInnerOrderNo(), item.getId(), PriceTypeEnum.OUT_SALE_PRICE.getType(), PriceTypeEnum.OUT_SALE_PRICE.getTypeName(), item.getOuterSaleAmount(), 0);
            ItemPrice innerSalePrice = new ItemPrice(null, item.getOuterOrderNo(), item.getInnerOrderNo(), item.getId(), PriceTypeEnum.INNER_SALE_PRICE.getType(), PriceTypeEnum.INNER_SALE_PRICE.getTypeName(), item.getInnerSaleAmount(), 0);
            ItemPrice costPrice = new ItemPrice(null, item.getOuterOrderNo(), item.getInnerOrderNo(), item.getId(), PriceTypeEnum.COST_TOTAL_PRICE.getType(), PriceTypeEnum.COST_TOTAL_PRICE.getTypeName(), Optional.ofNullable(item.getCostPrice()).map(x->x.multiply(BigDecimal.valueOf(item.getQuantity()))).orElse(BigDecimal.ZERO), 0);
            ItemPrice transPrice = new ItemPrice(null,item.getOuterOrderNo(),item.getInnerOrderNo(),item.getId(),PriceTypeEnum.TRANS_PRICE.getType(), PriceTypeEnum.TRANS_PRICE.getTypeName(),item.getTransPrice(),0);
            itemPriceList.add(thirdSalePrice);
            itemPriceList.add(innerSalePrice);
            itemPriceList.add(costPrice);
            itemPriceList.add(transPrice);
            return itemPriceList.stream();
        }).collect(Collectors.toList());
        itemPriceRepository.batchInsertSelective(finalItemPriceList);
    }

    @Override
    public void saveBalanceItemPrice(List<Item> itemList) {
        List<ItemPrice> finalItemPriceList = itemList.stream().flatMap(item -> {
            List<ItemPrice> itemPriceList = Lists.newArrayList();
            ItemPrice outBalancePrice = new ItemPrice(null, item.getOuterOrderNo(), item.getInnerOrderNo(), item.getId(), PriceTypeEnum.OUT_BALANCE_PRICE.getType(), PriceTypeEnum.OUT_BALANCE_PRICE.getTypeName(), item.getOutBalancePrice(), 0);
            ItemPrice innerBalancePrice = new ItemPrice(null, item.getOuterOrderNo(), item.getInnerOrderNo(), item.getId(), PriceTypeEnum.INNER_BALANCE_PRICE.getType(), PriceTypeEnum.INNER_BALANCE_PRICE.getTypeName(), item.getInnerBalancePrice(), 0);
            ItemPrice buyerPrice = new ItemPrice(null,item.getOuterOrderNo(),item.getInnerOrderNo(),item.getId(),PriceTypeEnum.BUYER_NEED_PRICE.getType(), PriceTypeEnum.BUYER_NEED_PRICE.getTypeName(), item.getInnerBalancePrice(),0);
            itemPriceList.add(buyerPrice);
            itemPriceList.add(outBalancePrice);
            itemPriceList.add(innerBalancePrice);
            return itemPriceList.stream();
        }).collect(Collectors.toList());
        itemPriceRepository.batchInsertSelective(finalItemPriceList);
    }

    @Override
    public void setItemPrice(List<Item> itemList) {
        itemList.parallelStream().forEach(item -> {
            //获取商品价格

            Map<String, ItemPrice> itemPriceMap = itemPriceRepository.queryItemPrice(item.getId());
            if(itemPriceMap == null || itemPriceMap.size() == 0) {
                throw new RuntimeException("商品价格不存在,商品id:" + item.getId());
            }
            if (itemPriceMap.get(PriceTypeEnum.COST_TOTAL_PRICE.getType()) != null) {
                item.setCostPrice(itemPriceMap.get(PriceTypeEnum.COST_TOTAL_PRICE.getType()).getAmount());
            }
            if (itemPriceMap.get(PriceTypeEnum.TRANS_PRICE.getType())!= null) {
                item.setTransPrice(itemPriceMap.get(PriceTypeEnum.TRANS_PRICE.getType()).getAmount());
            }
            if (itemPriceMap.get(PriceTypeEnum.INNER_SALE_PRICE.getType()) != null) {
                item.setInnerSaleAmount(itemPriceMap.get(PriceTypeEnum.INNER_SALE_PRICE.getType()).getAmount());
                item.setInnerSingleSaleAmount(itemPriceMap.get(PriceTypeEnum.INNER_SALE_PRICE.getType()).getAmount());
            }
            if (itemPriceMap.get(PriceTypeEnum.OUT_SALE_PRICE.getType()) != null) {
                item.setOuterSaleAmount(itemPriceMap.get(PriceTypeEnum.OUT_SALE_PRICE.getType()).getAmount());
                item.setOuterSingleSaleAmount(itemPriceMap.get(PriceTypeEnum.OUT_SALE_PRICE.getType()).getAmount());
            }
        });
    }
}
