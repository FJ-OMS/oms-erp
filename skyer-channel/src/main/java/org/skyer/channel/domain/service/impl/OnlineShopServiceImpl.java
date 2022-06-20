package org.skyer.channel.domain.service.impl;

import com.alibaba.fastjson.JSON;
import org.skyer.channel.domain.vo.ChannelSaleOrderVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

import static org.skyer.channel.infra.constant.Constants.APP_CODE;

import org.skyer.boot.platform.lov.annotation.ProcessLovValue;
import org.skyer.channel.api.dto.*;
import org.skyer.channel.domain.entity.Shop;
import org.skyer.channel.domain.repository.ShopRepository;
import org.skyer.channel.domain.service.OnlineShopService;
import org.skyer.channel.domain.service.saga.ShopService;
import org.skyer.channel.domain.vo.OnlineShopDetailVO;
import org.skyer.channel.domain.vo.OnlineShopSettingVO;
import org.skyer.channel.domain.vo.OnlineShopVO;
import org.skyer.core.cache.ProcessCacheValue;
import org.skyer.core.domain.Page;
import org.skyer.core.exception.CommonException;
import org.skyer.core.redis.DynamicRedisHelper;
import org.skyer.mybatis.pagehelper.domain.PageRequest;

@Service
public class OnlineShopServiceImpl implements OnlineShopService {
    private static final Logger logger = LoggerFactory.getLogger(OnlineShopServiceImpl.class);

    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private DynamicRedisHelper redisHelper;
    @Autowired
    private ShopService shopService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean save(OnlineShopDTO dto) {
        String shopName = dto.getName();
        String channelWarehouseName = dto.getChannelWarehouseName();
        Shop shop = null;
        if (!ObjectUtils.isEmpty(shopName)) {
            String typeCode = dto.getChannelTypeCode();
            shop = shopRepository.getShopByNameAndCategoryTypeAndChannelType(shopName, OnlineShopDetailVO.ONLINE_SHOP, typeCode, null);
        }
        Shop channelShop = null;
        if (!ObjectUtils.isEmpty(channelWarehouseName)) {
            channelShop = shopRepository.getShopByNameAndCategoryTypeAndChannelType(null, null, null, channelWarehouseName);
        }

        Long id = dto.getId();
        // id为空走新增逻辑
        if (ObjectUtils.isEmpty(id) || id == 0) {
            if (!ObjectUtils.isEmpty(shop)) {
                throw new CommonException("店铺名称禁止重复");
            }
            if (!ObjectUtils.isEmpty(channelShop)) {
                throw new CommonException("逻辑仓名称禁止重复");
            }
            Long tenantId = 0L;
            Shop onlineShop = new Shop();
            // 参数转换
            BeanUtils.copyProperties(dto, onlineShop);
            String code = onlineShop.getCode();
            if (ObjectUtils.isEmpty(code)) {
                onlineShop.setCode(shopService.creationCode(tenantId));
            }
            onlineShop.setTypeCode(dto.getOnlineStoreTypeCode());
            onlineShop.setTenantId(tenantId);
            onlineShop.setStatus(true);
            onlineShop.setDeleteFlag(false);
            onlineShop.setCategoryType(OnlineShopDetailVO.ONLINE_SHOP);
            SaveShopDTO saveShopDTO = new SaveShopDTO();
            saveShopDTO.setShop(onlineShop);
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
            Shop onlineShop = shopRepository.selectByPrimaryKey(id);
            // CODE 不能修改 获取出来
            String code = onlineShop.getCode();
            // 参数转换
            BeanUtils.copyProperties(dto, onlineShop);
            // 重新放入CODE
            onlineShop.setCode(code);
            onlineShop.setTypeCode(dto.getOnlineStoreTypeCode());
            SaveShopDTO saveShopDTO = new SaveShopDTO();
            saveShopDTO.setShop(onlineShop);
            saveShopDTO.setFlag(false);
            ShopDTO shopDTO = shopService.doSaveShop(saveShopDTO);
            return !ObjectUtils.isEmpty(shopDTO);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean setting(OnlineShopSettingDTO onlineShopSettingDTO) {
        Shop onlineShop = shopRepository.selectByPrimaryKey(onlineShopSettingDTO.getId());
        // 参数转换
        BeanUtils.copyProperties(onlineShopSettingDTO, onlineShop);
        int update = shopRepository.updateByPrimaryKey(onlineShop);
        if (update > 0) {
            redisHelper.hshDelete(APP_CODE + ":" + OnlineShopDetailVO.ONLINE_SHOP, onlineShop.getId().toString());
            return true;
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(Long id, Long objectVersionNumber) {
        Shop onlineShop = shopRepository.selectByPrimaryKey(id);
        onlineShop.setDeleteFlag(true);
        onlineShop.setObjectVersionNumber(objectVersionNumber);
        int update = shopRepository.updateByPrimaryKey(onlineShop);
        if (update > 0) {
            redisHelper.hshDelete(APP_CODE + ":" + OnlineShopDetailVO.ONLINE_SHOP, onlineShop.getId().toString());
            return true;
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean operation(Long id, Boolean flag, Long objectVersionNumber) {
        Shop onlineShop = shopRepository.selectByPrimaryKey(id);
        onlineShop.setStatus(flag);
        onlineShop.setChannelWarehouseStatus(flag);
        onlineShop.setObjectVersionNumber(objectVersionNumber);
        SaveShopDTO saveShopDTO = new SaveShopDTO();
        saveShopDTO.setShop(onlineShop);
        saveShopDTO.setFlag(false);
        ShopDTO shopDTO = shopService.doSaveShop(saveShopDTO);
        return !ObjectUtils.isEmpty(shopDTO);
    }

    @Override
    @ProcessCacheValue
    @ProcessLovValue
    public Page<OnlineShopVO> getPage(OnlineShopShowDTO onlineShopShowDTO, PageRequest pageRequest) {
        Long tenantId = 0L;
        return shopRepository.getPage(pageRequest, onlineShopShowDTO, tenantId);
    }

    @Override
    @ProcessLovValue
    public OnlineShopDetailVO getDetails(Long id) {
        String data = redisHelper.hshGet(APP_CODE + ":" + OnlineShopDetailVO.ONLINE_SHOP, id.toString());
        if (ObjectUtils.isEmpty(data)) {
            Shop onlineShop = shopRepository.selectByPrimaryKey(id);
            OnlineShopDetailVO vo = new OnlineShopDetailVO();
            // 参数转换
            BeanUtils.copyProperties(onlineShop, vo);
            vo.setOnlineStoreTypeCode(onlineShop.getTypeCode());

            if (!ObjectUtils.isEmpty(onlineShop.getRelationPhysicalShopId())) {
                Shop relationShop = shopRepository.selectByPrimaryKey(onlineShop.getRelationPhysicalShopId());
                if (!ObjectUtils.isEmpty(relationShop)){
                    vo.setRelationPhysicalShopName(relationShop.getName());
                }

            }

            redisHelper.hshPut(APP_CODE + ":" + OnlineShopDetailVO.ONLINE_SHOP, onlineShop.getId().toString(), JSON.toJSONString(vo));
            logger.info("==============mysql获取==============");
            return vo;
        }
        logger.info("==============缓存获取==============");
        return JSON.parseObject(data, OnlineShopDetailVO.class);
    }

    @Override
    public OnlineShopDetailVO getDetailsByCode(String code) {
        Shop query = new Shop();
        query.setCode(code);
        Shop onlineShop = shopRepository.selectOne(query);
        OnlineShopDetailVO vo = new OnlineShopDetailVO();
        // 参数转换
        BeanUtils.copyProperties(onlineShop, vo);
        return vo;
    }

    @Override
    public OnlineShopSettingVO getSetting(Long id) {
        return shopRepository.getSetting(id);
    }

    @Override
    public void deleteCache(Long id) {
        redisHelper.hshDelete(APP_CODE + ":" + OnlineShopDetailVO.ONLINE_SHOP, id.toString());
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
        List<Shop> onlineShopList = shopRepository.selectByIds(stringBuilder.toString());
        for (Shop onlineShop : onlineShopList) {
            onlineShop.setDeleteFlag(true);
            // 清除缓存
            redisHelper.hshDelete(APP_CODE + ":" + OnlineShopDetailVO.ONLINE_SHOP, onlineShop.getId().toString());
        }
        // 执行软删
        shopRepository.batchUpdateByPrimaryKey(onlineShopList);
        return true;
    }

}
