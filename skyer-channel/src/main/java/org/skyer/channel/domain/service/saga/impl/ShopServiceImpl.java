package org.skyer.channel.domain.service.saga.impl;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import org.skyer.channel.api.dto.ChannelSaleOrderDTO;
import org.skyer.channel.domain.vo.ChannelSaleOrderVO;
import org.skyer.channel.infra.feign.OrderReportRemoteService;
import org.skyer.channel.infra.remote.api.dto.SaleOrderOrderApiDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static org.skyer.channel.infra.constant.Constants.APP_CODE;

import org.skyer.boot.platform.code.builder.CodeRuleBuilder;
import org.skyer.boot.platform.code.constant.CodeConstants;
import org.skyer.boot.platform.lov.annotation.ProcessLovValue;
import org.skyer.channel.api.dto.QueryShopListDTO;
import org.skyer.channel.api.dto.SaveShopDTO;
import org.skyer.channel.api.dto.ShopDTO;
import org.skyer.channel.domain.entity.Shop;
import org.skyer.channel.domain.repository.ShopRepository;
import org.skyer.channel.domain.service.saga.ShopService;
import org.skyer.channel.domain.vo.OnlineShopDetailVO;
import org.skyer.channel.domain.vo.PhysicalShopDetailVO;
import org.skyer.channel.domain.vo.ShopListVO;
import org.skyer.channel.infra.constant.HrConstants;
import org.skyer.core.domain.Page;
import org.skyer.core.iam.ResourceLevel;
import org.skyer.core.oauth.CustomUserDetails;
import org.skyer.core.oauth.DetailsHelper;
import org.skyer.core.redis.DynamicRedisHelper;
import org.skyer.saga.saga.annotation.Saga;
import org.skyer.saga.saga.producer.StartSagaBuilder;
import org.skyer.saga.saga.producer.TransactionalProducer;

import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ShopServiceImpl implements ShopService {
    private static final Logger logger = LoggerFactory.getLogger(ShopServiceImpl.class);

    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private CodeRuleBuilder codeRuleBuilder;
    @Autowired
    private DynamicRedisHelper redisHelper;
    @Autowired
    private TransactionalProducer transactionalProducer;
    @Autowired
    private OrderReportRemoteService orderReportRemoteService;

    @Override
    @Saga(code = "do-save-shop", description = "商户创建", inputSchemaClass = Shop.class)
    public ShopDTO doSaveShop(SaveShopDTO saveShopDTO) {
        return transactionalProducer.applyAndReturn(StartSagaBuilder
                        .newBuilder()
                        .withLevel(ResourceLevel.SITE)
                        .withSourceId(1L)
                        .withRefType("saveShopDTO")
                        .withPayloadAndSerialize(saveShopDTO)
                        .withSagaCode("do-save-shop"),
                build -> {
                    Boolean flag = saveShopDTO.getFlag();
                    Shop shop = saveShopDTO.getShop();
                    ShopDTO shopDTO = new ShopDTO();
                    int size;
                    if (flag) {
                        size = shopRepository.insertSelective(shop);
                    } else {
                        size = shopRepository.updateByPrimaryKeySelective(shop);
                    }
                    if (size > 0) {
                        redisHelper.hshDelete(APP_CODE + ":" + OnlineShopDetailVO.ONLINE_SHOP, shop.getId().toString());
                        redisHelper.hshDelete(APP_CODE + ":" + PhysicalShopDetailVO.PHYSICAL_SHOP, shop.getId().toString());
                        BeanUtils.copyProperties(shop, shopDTO);
                    }
                    build.withPayloadAndSerialize(shopDTO).withRefId(shopDTO.getId() + " time:" + System.currentTimeMillis());
                    return shopDTO;
                });
    }

    @Override
    @ProcessLovValue
    public Page<ShopListVO> queryShop(QueryShopListDTO dto) {
        // 999 其他渠道特殊处理
        String channelTypeCode = dto.getChannelTypeCode();
        if (!ObjectUtils.isEmpty(channelTypeCode) && Shop.PHYSICAL_SHOP_CHANNEL_TYPE_CODE.equals(channelTypeCode)) {
            dto.setChannelTypeCode(null);
            dto.setCategoryType(Shop.PHYSICAL_SHOP_CATEGORY_TYPE);
        }

        // 提货店铺特殊处理
        Long o2oSalesStoreId = dto.getO2oSalesStoreId();
        if (Objects.nonNull(o2oSalesStoreId)) {
            Shop shop = shopRepository.selectByPrimaryKey(o2oSalesStoreId);
            if (Objects.nonNull(shop) && !Shop.PHYSICAL_SHOP_CATEGORY_TYPE.equals(shop.getCategoryType())) {
                // 如果销售店铺是网店，则取网店关联的门店列表。
                if (ObjectUtils.isEmpty(shop.getRelationPhysicalShopId())){
                    // 如果网店没有关联门店，则返回空值。
                    return new Page<>();
                }
                dto.setId(shop.getRelationPhysicalShopId());
            } else {
                // 如果销售店铺是门店，则取已启用的且是O2O订单自提门店列表。
                dto.setCategoryType(Shop.PHYSICAL_SHOP_CATEGORY_TYPE);
                dto.setSelfPickupFlag(true);
            }
        }

        return shopRepository.queryShop(dto, 0L, dto);
    }

    @Override
    public String creationCode(Long tenantId) {
        return codeRuleBuilder.generateCode(CodeConstants.Level.TENANT, tenantId, HrConstants.CodeRule.QD_WAREHOUSE_CODE, CodeConstants.CodeRuleLevelCode.CUSTOM, HrConstants.LevelValue.QD_WAREHOUSE_CODE, null);
    }

    @Override
    @ProcessLovValue
    public List<ChannelSaleOrderVO> queryChannelOrderSaleReport(ChannelSaleOrderDTO dto) {
        List<ChannelSaleOrderVO> channelSaleOrderVOS = shopRepository.queryChannelOrderSaleReport(dto);
        //调用订单中心获取订单金额等数据
        if (CollectionUtils.isEmpty(channelSaleOrderVOS)) {
            return channelSaleOrderVOS;
        }
        List<String> storeCodeList = channelSaleOrderVOS.stream().map(ChannelSaleOrderVO::getStoreCode).distinct().collect(Collectors.toList());
        SaleOrderOrderApiDTO saleOrderOrderApiDTO = new SaleOrderOrderApiDTO();
        saleOrderOrderApiDTO.setStartDate(dto.getStartDate());
        saleOrderOrderApiDTO.setEndDate(dto.getEndDate());
        saleOrderOrderApiDTO.setStoreCode(storeCodeList);
        ResponseEntity<List<ChannelSaleOrderVO>> listResponseEntity = orderReportRemoteService.queryChannelOrderSaleReport(saleOrderOrderApiDTO);
        log.info("调用订单中心获取订单数据 入参为:{},出参为:{}", JSONObject.toJSONString(saleOrderOrderApiDTO), JSONObject.toJSONString(listResponseEntity));
        //调用订单中心获取金额等参数
        List<ChannelSaleOrderVO> channelSaleOrderPartInfoVOList = listResponseEntity.getBody();
        if (CollectionUtils.isEmpty(channelSaleOrderPartInfoVOList)) {
            return channelSaleOrderVOS;
        }
        Map<String, ChannelSaleOrderVO> channelSaleOrderPartInfoVOMap = channelSaleOrderPartInfoVOList.stream().collect(Collectors.toMap(ChannelSaleOrderVO::getStoreCode, Function.identity(), (o1, o2) -> o1));
        channelSaleOrderVOS.forEach(channelSaleOrderVO -> {
            ChannelSaleOrderVO channelSaleOrderPartInfoVO = channelSaleOrderPartInfoVOMap.get(channelSaleOrderVO.getStoreCode());
            channelSaleOrderVO.setOrderCount(0);
            channelSaleOrderVO.setOrderAmount(BigDecimal.ZERO);
            channelSaleOrderVO.setReverseOrderCount(0);
            channelSaleOrderVO.setReverseOrderAmount(BigDecimal.ZERO);
            if (channelSaleOrderPartInfoVO != null) {
                channelSaleOrderVO.setOrderCount(channelSaleOrderPartInfoVO.getOrderCount());
                channelSaleOrderVO.setOrderAmount(channelSaleOrderPartInfoVO.getOrderAmount());
                channelSaleOrderVO.setReverseOrderCount(channelSaleOrderPartInfoVO.getReverseOrderCount());
                channelSaleOrderVO.setReverseOrderAmount(channelSaleOrderPartInfoVO.getReverseOrderAmount());
            }
        });
        return channelSaleOrderVOS;
    }
}
