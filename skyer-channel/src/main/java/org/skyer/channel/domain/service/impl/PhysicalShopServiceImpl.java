package org.skyer.channel.domain.service.impl;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.UUID;

import static org.skyer.channel.infra.constant.Constants.APP_CODE;

import org.skyer.boot.platform.lov.annotation.ProcessLovValue;
import org.skyer.channel.api.dto.*;
import org.skyer.channel.domain.entity.Shop;
import org.skyer.channel.domain.repository.ShopRepository;
import org.skyer.channel.domain.service.PhysicalShopService;
import org.skyer.channel.domain.service.saga.ShopService;
import org.skyer.channel.domain.vo.OnlineShopDetailVO;
import org.skyer.channel.domain.vo.PhysicalShopDetailVO;
import org.skyer.channel.domain.vo.PhysicalShopVO;
import org.skyer.core.cache.ProcessCacheValue;
import org.skyer.core.domain.Page;
import org.skyer.core.exception.CommonException;
import org.skyer.core.oauth.CustomUserDetails;
import org.skyer.core.oauth.DetailsHelper;
import org.skyer.core.redis.DynamicRedisHelper;
import org.skyer.mybatis.pagehelper.domain.PageRequest;

@Service
public class PhysicalShopServiceImpl implements PhysicalShopService {
    private static final Logger logger = LoggerFactory.getLogger(PhysicalShopServiceImpl.class);
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private DynamicRedisHelper redisHelper;
    @Autowired
    private ShopService shopService;

    @Override
    public Boolean save(PhysicalShopDTO dto) {
        Shop shop = shopRepository.getShopByNameAndCategoryTypeAndChannelType(dto.getName(), PhysicalShopDetailVO.PHYSICAL_SHOP, null, null);
        Shop channelShop = shopRepository.getShopByNameAndCategoryTypeAndChannelType(null, null, null, dto.getChannelWarehouseName());
        Long id = dto.getId();
        // id为空走新增逻辑
        if (ObjectUtils.isEmpty(id) || id == 0) {
            if (!ObjectUtils.isEmpty(shop)) {
                throw new CommonException("店铺名称禁止重复");
            }
            if (!ObjectUtils.isEmpty(channelShop)) {
                throw new CommonException("逻辑仓名称禁止重复");
            }
            Shop physicalShop = new Shop();
            // 参数转换
            BeanUtils.copyProperties(dto, physicalShop);
            String code = physicalShop.getCode();
            // 请求头中获取当前用户信息
            CustomUserDetails userDetails = DetailsHelper.getUserDetails();
            logger.info("获取当前用户==============》" + userDetails);
            Long tenantId = userDetails.getTenantId();
            if (ObjectUtils.isEmpty(code)) {
                physicalShop.setCode(shopService.creationCode(tenantId));
            }
            physicalShop.setTenantId(tenantId);
            physicalShop.setStatus(true);
            physicalShop.setDeleteFlag(false);
            physicalShop.setCategoryType(PhysicalShopDetailVO.PHYSICAL_SHOP);
            SaveShopDTO saveShopDTO = new SaveShopDTO();
            saveShopDTO.setShop(physicalShop);
            saveShopDTO.setFlag(true);
            ShopDTO shopDTO = shopService.doSaveShop(saveShopDTO);
            return !ObjectUtils.isEmpty(shopDTO);
        } else {
            if (!ObjectUtils.isEmpty(shop) && shop.getId().compareTo(id) != 0) {
                throw new CommonException("店铺名称禁止重复");
            }
            if (!ObjectUtils.isEmpty(channelShop) && channelShop.getId().compareTo(id) != 0) {
                throw new CommonException("逻辑仓名称禁止重复");
            }
            Shop updatePhysicalShop = shopRepository.selectByPrimaryKey(id);
            // CODE 不能修改 获取出来
            String code = updatePhysicalShop.getCode();
            // tenantId 不能修改 获取出来
            Long tenantId = updatePhysicalShop.getTenantId();
            // 参数转换
            BeanUtils.copyProperties(dto, updatePhysicalShop);
            // 重新放入CODE
            updatePhysicalShop.setCode(code);
            updatePhysicalShop.setTenantId(tenantId);
            SaveShopDTO saveShopDTO = new SaveShopDTO();
            saveShopDTO.setShop(updatePhysicalShop);
            saveShopDTO.setFlag(false);
            ShopDTO shopDTO = shopService.doSaveShop(saveShopDTO);
            return !ObjectUtils.isEmpty(shopDTO);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(Long id, Long objectVersionNumber) {
        Shop physicalShop = shopRepository.selectByPrimaryKey(id);
        physicalShop.setDeleteFlag(true);
        physicalShop.setObjectVersionNumber(objectVersionNumber);
        int update = shopRepository.updateByPrimaryKey(physicalShop);
        if (update > 0) {
            redisHelper.hshDelete(APP_CODE + ":" + PhysicalShopDetailVO.PHYSICAL_SHOP, physicalShop.getId().toString());
            return true;
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deletes(ShopDeleteDTO shopDeleteDTO) {
        List<Long> ids = shopDeleteDTO.getIds();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < ids.size(); i++) {
            stringBuilder.append(ids.get(i));
            if (i != (ids.size() - 1)) {
                stringBuilder.append(",");
            }
        }
        List<Shop> physicalShopList = shopRepository.selectByIds(stringBuilder.toString());
        for (Shop physicalShop : physicalShopList) {
            physicalShop.setDeleteFlag(true);
            // 清除缓存
            redisHelper.hshDelete(APP_CODE + ":" + PhysicalShopDetailVO.PHYSICAL_SHOP, physicalShop.getId().toString());
        }
        // 执行软删
        shopRepository.batchUpdateByPrimaryKey(physicalShopList);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean operation(Long id, Boolean flag, Long objectVersionNumber) {
        Shop physicalShop = shopRepository.selectByPrimaryKey(id);
        physicalShop.setStatus(flag);
        physicalShop.setChannelWarehouseStatus(flag);
        physicalShop.setObjectVersionNumber(objectVersionNumber);
        SaveShopDTO saveShopDTO = new SaveShopDTO();
        saveShopDTO.setShop(physicalShop);
        saveShopDTO.setFlag(false);
        ShopDTO shopDTO = shopService.doSaveShop(saveShopDTO);
        return !ObjectUtils.isEmpty(shopDTO);
    }

    @Override
    @ProcessCacheValue
    @ProcessLovValue
    public Page<PhysicalShopVO> getPage(PhysicalShopShowDTO physicalShopShowDTO, PageRequest pageRequest) {
        Long tenantId = 0L;
        return shopRepository.getPage(pageRequest, physicalShopShowDTO, tenantId);
    }

    @Override
    @ProcessLovValue
    public PhysicalShopDetailVO getDetails(Long id) {
        String data = redisHelper.hshGet(APP_CODE + ":" + PhysicalShopDetailVO.PHYSICAL_SHOP, id.toString());
        if (ObjectUtils.isEmpty(data)) {
            Shop physicalShop = shopRepository.selectByPrimaryKey(id);
            PhysicalShopDetailVO vo = new PhysicalShopDetailVO();
            // 参数转换
            BeanUtils.copyProperties(physicalShop, vo);
            redisHelper.hshPut(APP_CODE + ":" + PhysicalShopDetailVO.PHYSICAL_SHOP, id.toString(), JSON.toJSONString(vo));
            logger.info("==============mysql获取==============");
            return vo;
        }
        logger.info("==============缓存获取==============");
        return JSON.parseObject(data, PhysicalShopDetailVO.class);
    }

    @Override
    public void deleteCache(Long id) {
        redisHelper.hshDelete(APP_CODE + ":" + PhysicalShopDetailVO.PHYSICAL_SHOP, id.toString());
    }
}
