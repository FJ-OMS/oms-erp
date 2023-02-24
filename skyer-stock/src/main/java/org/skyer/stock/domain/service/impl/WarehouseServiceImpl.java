package org.skyer.stock.domain.service.impl;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

import static org.skyer.stock.infra.constant.ObjectConstant.WarehouseTypeCode.CHANNEL;

import org.skyer.boot.platform.code.builder.CodeRuleBuilder;
import org.skyer.boot.platform.code.constant.CodeConstants;
import org.skyer.core.domain.Page;
import org.skyer.core.oauth.CustomUserDetails;
import org.skyer.core.oauth.DetailsHelper;
import org.skyer.saga.saga.SagaDefinition;
import org.skyer.saga.saga.annotation.SagaTask;
import org.skyer.stock.api.dto.ShopDTO;
import org.skyer.stock.api.dto.WarehouseQueryReqDTO;
import org.skyer.stock.domain.entity.ShopWarehouse;
import org.skyer.stock.domain.entity.Warehouse;
import org.skyer.stock.domain.repository.ShopWarehouseRepository;
import org.skyer.stock.domain.repository.WarehouseRepository;
import org.skyer.stock.domain.service.WarehouseService;
import org.skyer.stock.domain.vo.WarehouseQueryResVO;
import org.skyer.stock.infra.constant.ObjectConstant;


/**
 * 仓库表应用服务默认实现
 *
 * @author lixiaoyang 2021-07-28 15:31:35
 */
@Service
public class WarehouseServiceImpl implements WarehouseService {
    private static final Logger logger = LoggerFactory.getLogger(WarehouseServiceImpl.class);
    // 门店
    private final String PHYSICAL_SHOP = "PhysicalShop";

    @Autowired
    private CodeRuleBuilder codeRuleBuilder;
    @Autowired
    private WarehouseRepository warehouseRepository;
    @Autowired
    private ShopWarehouseRepository shopWarehouseRepository;

    @Override
    @SagaTask(code = "init-channel-warehouse", sagaCode = "do-save-shop", description = "初始渠道仓",
            concurrentLimitNum = 2, concurrentLimitPolicy = SagaDefinition.ConcurrentLimitPolicy.TYPE, seq = 1)
    public Boolean doSaveChannelWarehouse(String data) {
        logger.info("进入创建渠道仓=====>" + data);
        ShopDTO shopDTO = JSON.parseObject(data, ShopDTO.class);
        String categoryType = shopDTO.getCategoryType();
        if (PHYSICAL_SHOP.equals(categoryType) || ObjectUtils.isEmpty(categoryType)) {
            logger.info("店铺类型不存在获取为门店时跳过");
            return true;
        }
        Warehouse warehouse = warehouseRepository.getShopWarehouse(shopDTO.getId());
        if (ObjectUtils.isEmpty(warehouse)) {
            logger.info("执行新增");
            // 创建渠道仓
            warehouse = new Warehouse();
            warehouse.setName(shopDTO.getChannelWarehouseName());
            warehouse.setTenantId(shopDTO.getTenantId());
            warehouse.setTypeCode(CHANNEL);
            warehouse.setDeleteFlag(ObjectConstant.DeleteFlag.NO);
            warehouse.setCode(codeRuleBuilder.generateCode(CodeConstants.Level.TENANT, 0L, ObjectConstant.CodeRule.QD_WAREHOUSE_CODE, CodeConstants.CodeRuleLevelCode.CUSTOM, ObjectConstant.LevelValue.QD_WAREHOUSE_CODE, null));
            warehouse.setStatus(shopDTO.getChannelWarehouseStatus());
            warehouseRepository.insertSelective(warehouse);
            // 设置店铺与渠道的关联关系
            settingRelation(shopDTO, warehouse);

            logger.info("执行新增结束=====>" + warehouse);
        } else {
            logger.info("执行修改");
            // 设置店铺与渠道的关联关系
            settingRelation(shopDTO, warehouse);
            warehouse.setName(shopDTO.getChannelWarehouseName());
            warehouse.setStatus(shopDTO.getChannelWarehouseStatus());
            warehouseRepository.updateByPrimaryKeySelective(warehouse);
            logger.info("执行修改结束=====>" + warehouse);
        }
        return true;
    }

    @Override
    public Page<WarehouseQueryResVO> queryWarehousePage(WarehouseQueryReqDTO warehouseQueryReqDTO) {
        Long tenantId = Optional.ofNullable(DetailsHelper.getUserDetails()).map(CustomUserDetails::getTenantId).orElse(0L);

        return warehouseRepository.queryWarehousePage(warehouseQueryReqDTO, tenantId, 0L);
    }

    /**
     * 设置店铺关系
     * @author lixiaoyang
     * @date 2022/6/8 14:15
     */
    public void settingRelation(ShopDTO shopDTO, Warehouse warehouse) {
        // 店铺与仓库关系
        ShopWarehouse shopWarehouse = new ShopWarehouse();
        shopWarehouse.setShopId(shopDTO.getId());
        shopWarehouse.setShopCode(shopDTO.getCode());
        // 删除脏数据 维持 一店一个渠道库
        shopWarehouseRepository.delete(shopWarehouse);
        shopWarehouse.setChannelTypeCode(shopDTO.getChannelTypeCode());
        shopWarehouse.setWarehouseId(warehouse.getId());
        shopWarehouse.setWarehouseCode(warehouse.getCode());
        shopWarehouseRepository.insertSelective(shopWarehouse);
    }
}
