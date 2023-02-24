package org.skyer.order.app.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.skyer.boot.platform.code.builder.CodeRuleBuilder;
import org.skyer.boot.platform.code.constant.CodeConstants;
import org.skyer.core.exception.CommonException;
import org.skyer.order.api.dto.OrderItemDTO;
import org.skyer.order.app.dto.CalAmountDTO;
import org.skyer.order.app.dto.PackItemAmountDTO;
import org.skyer.order.app.dto.SplitItemAmountDTO;
import org.skyer.order.app.service.ItemPriceService;
import org.skyer.order.app.service.ItemService;
import org.skyer.order.app.service.ItemSnapshotService;
import org.skyer.order.app.service.OrderService;
import org.skyer.order.domain.entity.*;
import org.skyer.order.domain.repository.*;
import org.skyer.order.infra.common.CommonConstants;
import org.skyer.order.infra.convertor.OrderItemConvertor;
import org.skyer.order.infra.convertor.SplitIemAmountConvertor;
import org.skyer.order.infra.enums.PriceTypeEnum;
import org.skyer.order.infra.feign.dto.SearchGiftDTO;
import org.skyer.order.infra.feign.dto.SearchOrderDTO;
import org.skyer.order.infra.feign.vo.GiftSkuVO;
import org.skyer.order.infra.feign.vo.OrderSkuVO;
import org.skyer.order.infra.feign.vo.PromoSkuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 内部订单商品明细应用服务默认实现
 *
 * @author wushaochuan 2022-01-18 14:34:54
 */
@Service
@Slf4j
public class ItemServiceImpl implements ItemService {
    @Autowired
    private SplitIemAmountConvertor splitIemAmountConvertor;
    @Autowired
    private CodeRuleBuilder codeRuleBuilder;
    @Autowired
    private OrderService orderService;
    @Autowired
    private HeaderRepository headerRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ItemSnapshotService itemSnapshotService;
    @Autowired
    private OrderItemConvertor orderItemConvertor;
    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private ItemPriceService itemPriceService;
    @Autowired
    private ThirdHeaderRepository thirdHeaderRepository;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void detailPriceShare(String innerOrderNo) {
        Map<String, Price> orderPriceMap = priceRepository.queryOrderPrice(innerOrderNo);
        List<Item> itemList = itemRepository.queryItemByInner(innerOrderNo);
        itemPriceService.setItemPrice(itemList);
        // 价格均摊
        itemList = splitAmountByOrderItem(itemList,orderPriceMap.get(PriceTypeEnum.BUYER_REAL_PRICE.getType()).getAmount());
        itemPriceService.saveBalanceItemPrice(itemList);
    }

    @Override
    public void createItem(List<OrderItemDTO> orderItemDTOList, String innerOrderNo) {
        Header header = headerRepository.queryHeaderByInnerOrderNo(innerOrderNo);
        // 创建商品快照
        List<ItemSnapshot> itemSnapshots = itemSnapshotService.saveSnapshot(header, orderItemDTOList.parallelStream().map(OrderItemDTO::getSkuCode).collect(Collectors.toList()), new ArrayList<>());
        Map<String, ItemSnapshot> snapshotsMap = itemSnapshots.stream().collect(Collectors.toMap(ItemSnapshot::getSkuCode, Function.identity(), (o1, o2) -> o1));
        List<Item> items = orderItemDTOList.parallelStream().map(x -> {
            Item item = orderItemConvertor.dtoToOrderItem(x, snapshotsMap.get(x.getSkuCode()));
            item.setId(null);
            item.setStoreId(header.getStoreId());
            item.setOrderItemCode(codeRuleBuilder.generateCode(CodeConstants.Level.TENANT, 0L, CommonConstants.CodeRule.ORDER_ITEM_CODE, CodeConstants.CodeRuleLevelCode.CUSTOM, CommonConstants.LevelValue.ORDER_ITEM_NO, null));
            return item;
        }).collect(Collectors.toList());
        //todo 记录订单日志
        itemRepository.batchInsert(items);
        //创建订单价格项
        itemPriceService.saveItemPrice(items);
    }

    @Override
    public void thirdCreateItem(List<OrderItemDTO> orderItemDTOList, String innerOrderNo) {
        Header header = headerRepository.queryHeaderByInnerOrderNo(innerOrderNo);
        //TODO 商品信息、地址、收货人信息 校验
        createItem(orderItemDTOList,innerOrderNo);

        //转单正常 生成商品行 回写记录到三方单
        ThirdHeader searchThirdHeader = new ThirdHeader();
        searchThirdHeader.setOuterOrderNo(header.getOuterOrderNo());
        ThirdHeader thirdHeader = thirdHeaderRepository.selectOne(searchThirdHeader);
        thirdHeader.setInnerOrderNo(innerOrderNo);
        thirdHeaderRepository.updateByPrimaryKeySelective(thirdHeader);


    }


    @Override
    public List<Item> splitAmountByOrderItem(List<Item> itemList, BigDecimal totalPrice) {
        CalAmountDTO calAmountDTO = new CalAmountDTO();
        calAmountDTO.setTotalPrice(totalPrice);
        // 获取赠品和主商品打成一个均摊包
        Map<String, List<Item>> mainGiftItemMap = Optional.ofNullable(itemList.parallelStream().filter(item -> Objects.nonNull(item.getMainItemCode())).collect(Collectors.groupingBy(Item::getMainItemCode))).orElse(new HashMap<>());
        List<PackItemAmountDTO> packItemAmountDTOList = Lists.newArrayList();
        itemList.parallelStream().filter(orderItemDTO->Objects.isNull(orderItemDTO.getMainItemCode())).forEach(orderItemDTO -> {
            packPackItemAmountList(mainGiftItemMap, packItemAmountDTOList, orderItemDTO);
        });
        calAmountDTO.setPackItemAmountDTOList(packItemAmountDTOList);
        //计算平台均摊价
        orderService.splitAmount(calAmountDTO,false,1);
        List<SplitItemAmountDTO> outSplitItemAmountDTOList = orderService.splitPackAmount(calAmountDTO.getPackItemAmountDTOList(), false, 1);
        //计算内部均摊价
        orderService.splitAmount(calAmountDTO,false,2);
        List<SplitItemAmountDTO> innerSplitItemAmountDTOList = orderService.splitPackAmount(calAmountDTO.getPackItemAmountDTOList(), true, 2);
        Map<String, SplitItemAmountDTO> innerSplitItemAmountDTOMap = innerSplitItemAmountDTOList.stream().collect(Collectors.toMap(SplitItemAmountDTO::getCodeOrId, Function.identity(), (o1, o2) -> o1));
        Map<String, Item> orderItemDTOListMap = itemList.stream().collect(Collectors.toMap(Item::getOrderItemCode, Function.identity(), (o1, o2) -> o1));
        return outSplitItemAmountDTOList.parallelStream().map(outSplitItemAmountDTO -> {
            SplitItemAmountDTO splitItemAmountDTO = innerSplitItemAmountDTOMap.get(outSplitItemAmountDTO.getCodeOrId());
            outSplitItemAmountDTO.setInnerBalancePrice(splitItemAmountDTO.getInnerBalancePrice());
            String itemCode = splitItemAmountDTO.getCodeOrId().split("_")[0];
            Item item = orderItemDTOListMap.get(itemCode);
            return splitIemAmountConvertor.splitItemAmountDTOToOrderItemDTO(item, outSplitItemAmountDTO);
        }).collect(Collectors.toList());
    }


    private void packPackItemAmountList(Map<String, List<Item>> mainGiftItemMap, List<PackItemAmountDTO> packItemAmountDTOList, Item item) {
        PackItemAmountDTO packItemAmountDTO = new PackItemAmountDTO();
        List<SplitItemAmountDTO> splitItemAmountDTOList = Lists.newArrayList();
        AtomicReference<BigDecimal> totalOutAmount = new AtomicReference<>(BigDecimal.ZERO);
        AtomicReference<BigDecimal> totalInnerAmount = new AtomicReference<>(BigDecimal.ZERO);
        //如果为套餐需要对套餐进行平拆
        if (Optional.ofNullable(item.getSetFlag()).orElse(0) == 1) {
            splitItemAmountDTOList.addAll(Stream.iterate(0L, x -> x + 1).limit(item.getQuantity()).map(x->{
                SplitItemAmountDTO splitItemAmountDTO = splitIemAmountConvertor.itemToSplitItemAmount(item);
                //套餐code改为xxxx_1,xxx_2;
                splitItemAmountDTO.setCodeOrId(splitItemAmountDTO.getCodeOrId()+"_"+x);
                splitItemAmountDTO.setNum(1);
                splitItemAmountDTO.setOutPrice(Optional.ofNullable(item.getOuterSingleSaleAmount()).orElse(BigDecimal.ZERO));
                splitItemAmountDTO.setInnerPrice(Optional.ofNullable(item.getInnerSingleSaleAmount()).orElse(BigDecimal.ZERO));
                splitItemAmountDTO.setIsGift(0);
                totalOutAmount.set(totalOutAmount.get().add(Optional.ofNullable(item.getOuterSaleAmount()).orElse(BigDecimal.ZERO)));
                totalInnerAmount.set(totalInnerAmount.get().add(Optional.ofNullable(item.getInnerSaleAmount()).orElse(BigDecimal.ZERO)));
                return splitItemAmountDTO;
            }).collect(Collectors.toList()));
        }else {
            SplitItemAmountDTO splitItemAmountDTO = splitIemAmountConvertor.itemToSplitItemAmount(item);
            splitItemAmountDTO.setOutPrice(Optional.ofNullable(item.getOuterSingleSaleAmount()).orElse(BigDecimal.ZERO));
            splitItemAmountDTO.setInnerPrice(Optional.ofNullable(item.getInnerSingleSaleAmount()).orElse(BigDecimal.ZERO));
            splitItemAmountDTO.setIsGift(0);
            splitItemAmountDTOList.add(splitItemAmountDTO);
            totalOutAmount.set(totalOutAmount.get().add(Optional.ofNullable(item.getOuterSaleAmount()).orElse(BigDecimal.ZERO)));
            totalInnerAmount.set(totalInnerAmount.get().add(Optional.ofNullable(item.getInnerSaleAmount()).orElse(BigDecimal.ZERO)));
        }
        //匹配对应赠品
        List<Item> giftOrderItemList = mainGiftItemMap.get(item.getOrderItemCode());
        if (CollectionUtil.isNotEmpty(giftOrderItemList)) {
            splitItemAmountDTOList.addAll(giftOrderItemList.parallelStream().map(giftOrderItemDTO ->{
                SplitItemAmountDTO splitItemAmountDTO = splitIemAmountConvertor.itemToSplitItemAmount(giftOrderItemDTO);
                splitItemAmountDTO.setIsGift(1);
                splitItemAmountDTO.setOutPrice(Optional.ofNullable(giftOrderItemDTO.getOuterSingleSaleAmount()).orElse(BigDecimal.ZERO));
                splitItemAmountDTO.setInnerPrice(Optional.ofNullable(giftOrderItemDTO.getInnerSingleSaleAmount()).orElse(BigDecimal.ZERO));
                totalOutAmount.set(totalOutAmount.get().add(Optional.ofNullable(giftOrderItemDTO.getOuterSaleAmount()).orElse(BigDecimal.ZERO)));
                totalInnerAmount.set(totalInnerAmount.get().add(Optional.ofNullable(giftOrderItemDTO.getInnerSaleAmount()).orElse(BigDecimal.ZERO)));
                return splitItemAmountDTO;
            }).collect(Collectors.toList()));
        }
        packItemAmountDTO.setOutTotalSplitPrice(totalOutAmount.get());
        packItemAmountDTO.setInnerTotalSplitPrice(totalInnerAmount.get());
        packItemAmountDTO.setCode(codeRuleBuilder.generateCode(CodeConstants.Level.TENANT, 0L, CommonConstants.CodeRule.PACK_PRICE_PACK, CodeConstants.CodeRuleLevelCode.CUSTOM, CommonConstants.LevelValue.PACK_PRICE_NO, null));
        packItemAmountDTO.setSplitItemAmountDTOList(splitItemAmountDTOList);
        packItemAmountDTOList.add(packItemAmountDTO);
    }
}
