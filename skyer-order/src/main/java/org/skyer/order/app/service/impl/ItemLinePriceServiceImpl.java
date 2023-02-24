package org.skyer.order.app.service.impl;

import org.apache.commons.compress.utils.Lists;
import org.skyer.order.app.service.ItemLinePriceService;
import org.skyer.order.domain.entity.Item;
import org.skyer.order.domain.entity.ItemLine;
import org.skyer.order.domain.entity.ItemLinePrice;
import org.skyer.order.domain.entity.ItemPrice;
import org.skyer.order.domain.repository.ItemLinePriceRepository;
import org.skyer.order.domain.repository.ItemPriceRepository;
import org.skyer.order.infra.enums.PriceTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 内部订单商品行价格应用服务默认实现
 *
 * @author wushaochuan 2022-01-18 14:34:54
 */
@Service
public class ItemLinePriceServiceImpl implements ItemLinePriceService {
    @Autowired
    private ItemLinePriceRepository itemLinePriceRepository;
    @Override
    public void saveItemLinePrice(List<ItemLine> itemLineList) {
        List<ItemLinePrice> finalItemLinePriceList = itemLineList.stream().flatMap(itemLine -> {
            List<ItemLinePrice> itemLinePriceList = Lists.newArrayList();
            ItemLinePrice thirdSalePrice = new ItemLinePrice(null, itemLine.getOuterOrderNo(), itemLine.getInnerOrderNo(), itemLine.getId(), PriceTypeEnum.OUT_SALE_PRICE.getType(), PriceTypeEnum.OUT_SALE_PRICE.getTypeName(),itemLine.getGiftFlag() == 0 ? itemLine.getInnerSaleAmount() : BigDecimal.ZERO , 0);
            ItemLinePrice innerSalePrice = new ItemLinePrice(null, itemLine.getOuterOrderNo(), itemLine.getInnerOrderNo(), itemLine.getId(), PriceTypeEnum.INNER_SALE_PRICE.getType(), PriceTypeEnum.INNER_SALE_PRICE.getTypeName(), itemLine.getInnerSaleAmount(), 0);
            ItemLinePrice costPrice = new ItemLinePrice(null, itemLine.getOuterOrderNo(), itemLine.getInnerOrderNo(), itemLine.getId(), PriceTypeEnum.COST_TOTAL_PRICE.getType(), PriceTypeEnum.COST_TOTAL_PRICE.getTypeName(), itemLine.getCostPrice(), 0);
            ItemLinePrice innerBalancePrice = new ItemLinePrice(null,itemLine.getOuterOrderNo(),itemLine.getInnerOrderNo(),itemLine.getId(),PriceTypeEnum.INNER_BALANCE_PRICE.getType(), PriceTypeEnum.INNER_BALANCE_PRICE.getTypeName(), itemLine.getInnerBalanceAmount(),0);
            ItemLinePrice outerBalancePrice = new ItemLinePrice(null,itemLine.getOuterOrderNo(), itemLine.getInnerOrderNo(),itemLine.getId(),PriceTypeEnum.OUT_BALANCE_PRICE.getType(),PriceTypeEnum.OUT_BALANCE_PRICE.getTypeName(),itemLine.getOutBalanceAmount() ,0);
            ItemLinePrice transPrice = new ItemLinePrice(null,itemLine.getOuterOrderNo(), itemLine.getInnerOrderNo(),itemLine.getId(),PriceTypeEnum.TRANS_PRICE.getType(),PriceTypeEnum.TRANS_PRICE.getTypeName(),itemLine.getTransPrice() ,0);
            itemLinePriceList.add(transPrice);
            itemLinePriceList.add(thirdSalePrice);
            itemLinePriceList.add(innerSalePrice);
            itemLinePriceList.add(costPrice);
            itemLinePriceList.add(innerBalancePrice);
            itemLinePriceList.add(outerBalancePrice);
            return itemLinePriceList.stream();
        }).collect(Collectors.toList());
        itemLinePriceRepository.batchInsertSelective(finalItemLinePriceList);
    }
}
