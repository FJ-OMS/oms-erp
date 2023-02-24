package org.skyer.goods.app.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.skyer.core.domain.Page;
import org.skyer.core.domain.PageInfo;
import org.skyer.core.exception.CommonException;
import org.skyer.core.oauth.CustomUserDetails;
import org.skyer.core.oauth.DetailsHelper;
import org.skyer.goods.app.service.MyEncryptionService;
import org.skyer.goods.app.service.SpuInstallServiceService;
import org.skyer.goods.domain.dto.PageSpuInstallServiceDTO;
import org.skyer.goods.domain.dto.SpuInstallServiceDTO;
import org.skyer.goods.domain.entity.*;
import org.skyer.goods.domain.repository.InstallServiceRepository;
import org.skyer.goods.domain.repository.SpuInstallServiceRepository;
import org.skyer.goods.domain.repository.SpuInstallServiceSkuRepository;
import org.skyer.goods.domain.repository.SpuInstallServiceStoreRepository;
import org.skyer.goods.domain.vo.OnlineShopVO;
import org.skyer.goods.infra.feign.OnlineShopRemoteService;
import org.skyer.goods.infra.mapper.SkuMapper;
import org.skyer.goods.infra.mapper.SpuMapper;
import org.skyer.mybatis.domian.Condition;
import org.skyer.mybatis.pagehelper.PageHelper;
import org.skyer.mybatis.pagehelper.domain.PageRequest;
import org.skyer.mybatis.util.Sqls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品安装服务应用服务默认实现
 *
 * @author chenzz 2021-08-18 16:42:11
 */
@Slf4j
@Service
public class SpuInstallServiceServiceImpl implements SpuInstallServiceService {
    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private InstallServiceRepository installServiceRepository;

    @Autowired
    private SpuInstallServiceRepository spuInstallServiceRepository;

    @Autowired
    private SpuInstallServiceSkuRepository spuInstallServiceSkuRepository;

    @Autowired
    private SpuInstallServiceStoreRepository spuInstallServiceStoreRepository;

    @Autowired
    private OnlineShopRemoteService onlineShopRemoteService;

    @Override
    public Page<Dict> page(PageRequest pageRequest, PageSpuInstallServiceDTO spuInstallService) {

        Page<SpuInstallService> page = PageHelper.doPageAndSort(pageRequest, () -> spuInstallServiceRepository.selectByCondition(Condition.builder(SpuInstallService.class)
                .andWhere(Sqls.custom()
                        .andLike(SpuInstallService.FIELD_SPU_CODE, spuInstallService.getSpuCode(), true)
                        .andLike(SpuInstallService.FIELD_SPU_NAME, spuInstallService.getSpuName(), true)
                        .andLike(SpuInstallService.FIELD_INSTALL_SERVICE_CODE, spuInstallService.getInstallServiceCode(), true)
                        .andLike(SpuInstallService.FIELD_INSTALL_SERVICE_NAME, spuInstallService.getInstallServiceName(), true)
                        .andEqualTo(SpuInstallService.FIELD_DELETE_FLAG, false))
                .build()));

        List<SpuInstallService> pageListContent = page.getContent();
        if (CollUtil.isNotEmpty(pageListContent)) {
            // List<String> spuCodes = pageListContent.stream().map(xx -> xx.getSpuCode()).collect(Collectors.toList());

            List<Dict> dictList = pageListContent.stream().map(xx -> {
                Dict item = Dict.create();

                item.set("spuCode", xx.getSpuCode())
                        .set("spuId", myEncryptionService.encrypt(xx.getSpuId()))
                        .set("id", myEncryptionService.encrypt(xx.getId()))
                        .set("spuName", xx.getSpuName())
                        .set("installServiceId", myEncryptionService.encrypt(xx.getInstallServiceId()))
                        .set("installServiceCode", xx.getInstallServiceCode())
                        .set("installServiceName", xx.getInstallServiceName())
                        .set("isAllStore", xx.getIsAllStore())
                        .set("remark", xx.getRemark())
                        .set("categoryId", xx.getCategoryId())
                        .set("createdBy", xx.getCreatedBy())
                        .set("creationDate", DateUtil.formatDateTime(xx.getCreationDate()));
//                item.set("categoryText", categoryOldService.getCategoryNameByListId(xx.getCategoryId()));

                // 套餐商品
                List<SpuInstallServiceSku> spuInstallServiceSkuList = spuInstallServiceSkuRepository.selectByCondition(Condition.builder(SpuInstallServiceSku.class)
                        .andWhere(Sqls.custom()
                                .andEqualTo(SpuInstallServiceSku.FIELD_SPU_CODE, xx.getSpuCode()))
                        .build());
                item.set("skuCount", spuInstallServiceSkuList.size());

                // 售卖店铺
                List<SpuInstallServiceStore> spuInstallServiceStoreList = spuInstallServiceStoreRepository.selectByCondition(Condition.builder(SpuInstallServiceStore.class)
                        .andWhere(Sqls.custom()
                                .andEqualTo(SpuInstallServiceStore.FIELD_SPU_CODE, xx.getSpuCode()))
                        .build());
                item.set("storeCount", spuInstallServiceStoreList.size());


                return item;
            }).collect(Collectors.toList());
            return new Page<>(dictList, new PageInfo(page.getTotalPages(), page.getSize()), page.getTotalElements());
        }

        return new Page<>(new ArrayList<>(), new PageInfo(0, page.getSize()), 0);
    }

    @Autowired
    private MyEncryptionService myEncryptionService;

    @Override
    public Dict detail(Long id) {
        SpuInstallService spuInstallService = spuInstallServiceRepository.selectByPrimaryKey(id);
        if (ObjectUtil.isNotNull(spuInstallService)) {
            Dict dict = Dict.create();

            dict.set("objectVersionNumber", spuInstallService.getObjectVersionNumber());
            dict.set("id", myEncryptionService.encrypt(spuInstallService.getId()));
            dict.set("spuId", myEncryptionService.encrypt(spuInstallService.getSpuId()));
            dict.set("spuCode", spuInstallService.getSpuCode());
            dict.set("installServiceId", myEncryptionService.encrypt(spuInstallService.getInstallServiceId()));
            dict.set("installServiceCode", spuInstallService.getInstallServiceCode());
            dict.set("installServiceName", spuInstallService.getInstallServiceName());
            dict.set("spuName", spuInstallService.getSpuName());
            dict.set("isAllStore", spuInstallService.getIsAllStore());
            dict.set("remark", spuInstallService.getRemark());
            dict.set("categoryId", String.valueOf(spuInstallService.getCategoryId()));
            // String categoryText = categoryOldService.getCategoryNameByListId(spuInstallService.getCategoryId());
            //dict.set("categoryText", categoryText);

            dict.set("creationDate", DateUtil.formatDateTime(spuInstallService.getCreationDate()));

            //----------------------
            SpuInstallServiceSku InstallServiceSkuQuery = new SpuInstallServiceSku();
            InstallServiceSkuQuery.setSpuCode(spuInstallService.getSpuCode());
            List<SpuInstallServiceSku> spuInstallServiceSkuList = spuInstallServiceSkuRepository.select(InstallServiceSkuQuery);
            List<Dict> dictList = spuInstallServiceSkuList.stream().map(xx -> {
                Dict itemDict = Dict.create();
                // itemDict.set("id", String.valueOf(xx.getId()));
                itemDict.set("id", myEncryptionService.encrypt(xx.getId()));

                itemDict.set("skuId", myEncryptionService.encrypt(xx.getSonSkuId()));
                itemDict.set("skuCode", xx.getSonSkuCode());
                // sku详情
                itemDict.set("spuCode", spuInstallService.getSpuCode());
                itemDict.set("spuName", spuInstallService.getSpuName());
                // itemDict.set("categoryName", categoryText);
                Sku sku = skuMapper.selectByPrimaryKey(xx.getSonSkuId());
                itemDict.set("price", sku.getSalesPrice());
                return itemDict;
            }).collect(Collectors.toList());
            dict.set("spuInstallServiceSkus", dictList);

            // 售卖店铺
            SpuInstallServiceStore InstallServiceStoreQuery = new SpuInstallServiceStore();
            InstallServiceStoreQuery.setSpuCode(spuInstallService.getSpuCode());
            List<SpuInstallServiceStore> spuInstallServiceStoreList = spuInstallServiceStoreRepository.select(InstallServiceStoreQuery);
            List<Dict> dictStoreList = spuInstallServiceStoreList.stream().map(xx -> {
                Dict itemDict = Dict.create();

                itemDict.set("id", myEncryptionService.encrypt(xx.getStoreId()));
                // 店铺编码
                itemDict.set("code", xx.getStoreCode());
                // sku详情
                ResponseEntity<OnlineShopVO> listResponseEntity = onlineShopRemoteService.showDetails(xx.getStoreId());
                OnlineShopVO body = listResponseEntity.getBody();
                if (body != null) {
                    // 店铺名称
                    itemDict.set("name", body.getName());
                    itemDict.set("storeDescription", body.getDescription());

                    itemDict.set("channelTypeMeaning", body.getChannelTypeMeaning());
                    itemDict.set("onlineStoreTypeMeaning", body.getOnlineStoreTypeMeaning());

                    itemDict.set("typeCode", body.getChannelTypeCode());
                    itemDict.set("storeType", body.getOnlineStoreTypeCode());

                }
                return itemDict;
            }).collect(Collectors.toList());
            dict.set("spuInstallServiceStores", dictStoreList);
            return dict;
        }
        return Dict.create().parseBean(spuInstallService);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean create(SpuInstallServiceDTO spuInstallServiceDTO) {
        if (!spuInstallServiceDTO.getIsAllStore() && CollUtil.isEmpty(spuInstallServiceDTO.getSpuStoreIds())) {
            // 如果不是适用于所有店铺且店铺没有设置值，返回错误
            throw new CommonException("没有设置店铺!");
        }
        // -------------------------------
        InstallService queryEntity = new InstallService();
        queryEntity.setServiceCode(spuInstallServiceDTO.getInstallServiceCode());
        InstallService installServiceOne = installServiceRepository.selectOne(queryEntity);

        Spu spu = spuMapper.selectOne((new Spu()).setSpuCode(spuInstallServiceDTO.getSpuCode()));
        if (ObjectUtil.isNotNull(spu) && ObjectUtil.isNotNull(installServiceOne)) {
            // 获取租户的id
            CustomUserDetails userDetails = DetailsHelper.getUserDetails();
            Long tenantId = userDetails.getTenantId();
            // 1. 添加主表数据
            SpuInstallService spuInstallService = new SpuInstallService();
            spuInstallService.setSpuCode(spuInstallServiceDTO.getSpuCode());
            spuInstallService.setInstallServiceId(installServiceOne.getId());
            spuInstallService.setInstallServiceName(installServiceOne.getName());
            spuInstallService.setInstallServiceCode(spuInstallServiceDTO.getInstallServiceCode());
            spuInstallService.setSpuId(spu.getId());
            spuInstallService.setCategoryId(spu.getCategoryId());
            spuInstallService.setSpuName(spu.getName());
            spuInstallService.setRemark(spuInstallServiceDTO.getRemark());
            spuInstallService.setIsAllStore(spuInstallServiceDTO.getIsAllStore());
            spuInstallService.setTenantId(tenantId);
            spuInstallService.setDeleteFlag(false);
            int rowsResult1 = spuInstallServiceRepository.insert(spuInstallService);
            log.info("rows1={}", rowsResult1);
            // 添加对应的sku数据
            for (String ss : spuInstallServiceDTO.getSonSkuCodes()) {
                Sku skuQuery = new Sku();
                skuQuery.setSkuCode(ss);
                Sku sku = skuMapper.selectOne(skuQuery);
                if (ObjectUtil.isNull(sku)) {
                    throw new CommonException("sku编码不存在，请检查!");
                }
                SpuInstallServiceSku itemSku = new SpuInstallServiceSku();
                itemSku.setSpuCode(spu.getSpuCode());
                itemSku.setSpuId(spu.getId());
                itemSku.setSonSkuId(sku.getId());
                itemSku.setSonSkuCode(ss);
                itemSku.setTenantId(tenantId);
                itemSku.setDeleteFlag(false);

                int rowsResult2 = spuInstallServiceSkuRepository.insert(itemSku);
                log.info("rows2={}", rowsResult2);
            }
            // 添加适用店铺
            // 需要使用远程调用
            for (Long ll : spuInstallServiceDTO.getSpuStoreIds()) {
                ResponseEntity<OnlineShopVO> listResponseEntity = onlineShopRemoteService.showDetails(ll);
                OnlineShopVO body = listResponseEntity.getBody();
                if (body != null) {
                    SpuInstallServiceStore spuInstallServiceStore = new SpuInstallServiceStore();
                    spuInstallServiceStore.setSpuCode(spuInstallServiceDTO.getSpuCode());
                    spuInstallServiceStore.setStoreId(ll);
                    spuInstallServiceStore.setStoreCode(body.getCode());
                    spuInstallServiceStore.setTenantId(tenantId);
                    spuInstallServiceStore.setDeleteFlag(false);

                    int rowsResult3 = spuInstallServiceStoreRepository.insert(spuInstallServiceStore);
                    log.info("rows3={}", rowsResult3);
                }
            }

            return true;
        }
        return false;
    }

    @Override
    public Boolean update(SpuInstallServiceDTO spuInstallServiceDTO) {
        if (!spuInstallServiceDTO.getIsAllStore() && CollUtil.isEmpty(spuInstallServiceDTO.getSpuStoreIds())) {
            // 如果不是适用于所有店铺且店铺没有设置值，返回错误
            throw new CommonException("没有设置店铺!");
        }
        // -------------------------------

        Spu spu = spuMapper.selectOne((new Spu()).setSpuCode(spuInstallServiceDTO.getSpuCode()));
        if (ObjectUtil.isNotNull(spu)) {
            // 获取租户的id
            CustomUserDetails userDetails = DetailsHelper.getUserDetails();
            Long tenantId = userDetails.getTenantId();
            // 1. 修改主表数据
            SpuInstallService spuInstallService = new SpuInstallService();
            spuInstallService.setId(spuInstallServiceDTO.getId());
            if (StrUtil.isNotEmpty(spuInstallServiceDTO.getInstallServiceCode())) {
                InstallService queryEntity = new InstallService();
                queryEntity.setServiceCode(spuInstallServiceDTO.getInstallServiceCode());
                InstallService installServiceOne = installServiceRepository.selectOne(queryEntity);

                spuInstallService.setInstallServiceId(installServiceOne.getId());
                spuInstallService.setInstallServiceName(installServiceOne.getName());
                spuInstallService.setInstallServiceCode(spuInstallServiceDTO.getInstallServiceCode());
            }
            spuInstallService.setObjectVersionNumber(spuInstallServiceDTO.getObjectVersionNumber());
            spuInstallService.setRemark(spuInstallServiceDTO.getRemark());
            spuInstallService.setIsAllStore(spuInstallServiceDTO.getIsAllStore());
            int rowsResult1 = spuInstallServiceRepository.updateByPrimaryKeySelective(spuInstallService);
            log.info("rows1={}", rowsResult1);
            // 先删除原来数据
            SpuInstallServiceSku InstallServiceSkuQuery = new SpuInstallServiceSku();
            InstallServiceSkuQuery.setSpuCode(spuInstallServiceDTO.getSpuCode());
            List<SpuInstallServiceSku> spuInstallServiceSkuList = spuInstallServiceSkuRepository.select(InstallServiceSkuQuery);

            int rowsResult11 = spuInstallServiceSkuRepository.batchDelete(spuInstallServiceSkuList);
            log.info("rowsResult211={}", rowsResult11);

            // 添加对应的sku数据
            for (String ss : spuInstallServiceDTO.getSonSkuCodes()) {
                Sku skuQuery = new Sku();
                skuQuery.setSkuCode(ss);
                Sku sku = skuMapper.selectOne(skuQuery);
                if (ObjectUtil.isNull(sku)) {
                    throw new CommonException("sku编码不存在，请检查!");
                }
                SpuInstallServiceSku itemSku = new SpuInstallServiceSku();
                itemSku.setSpuCode(spu.getSpuCode());
                itemSku.setSpuId(spu.getId());
                itemSku.setSonSkuId(sku.getId());
                itemSku.setSonSkuCode(ss);
                itemSku.setTenantId(tenantId);
                itemSku.setDeleteFlag(false);

                int rowsResult2 = spuInstallServiceSkuRepository.insert(itemSku);
                log.info("rows2={}", rowsResult2);
            }

            // 先删除原来数据
            SpuInstallServiceStore InstallServiceStoreQuery = new SpuInstallServiceStore();
            InstallServiceStoreQuery.setSpuCode(spuInstallServiceDTO.getSpuCode());
            List<SpuInstallServiceStore> spuInstallServiceStoreList = spuInstallServiceStoreRepository.select(InstallServiceStoreQuery);

            int rowsResult22 = spuInstallServiceStoreRepository.batchDelete(spuInstallServiceStoreList);
            log.info("rowsResult22={}", rowsResult22);

            // 添加适用店铺
            // 需要使用远程调用
            for (Long ll : spuInstallServiceDTO.getSpuStoreIds()) {
                ResponseEntity<OnlineShopVO> listResponseEntity = onlineShopRemoteService.showDetails(ll);
                OnlineShopVO body = listResponseEntity.getBody();
                if (body != null) {
                    SpuInstallServiceStore spuInstallServiceStore = new SpuInstallServiceStore();
                    spuInstallServiceStore.setSpuCode(spuInstallServiceDTO.getSpuCode());
                    spuInstallServiceStore.setStoreId(ll);
                    spuInstallServiceStore.setStoreCode(body.getCode());
                    spuInstallServiceStore.setTenantId(tenantId);
                    spuInstallServiceStore.setDeleteFlag(false);

                    int rowsResult3 = spuInstallServiceStoreRepository.insert(spuInstallServiceStore);
                    log.info("rows3={}", rowsResult3);
                }
            }

            return true;
        }
        return false;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean batchRemove(List<Long> ids) {
        List<SpuInstallService> spuInstallServices = spuInstallServiceRepository.selectByCondition(Condition.builder(SpuInstallService.class)
                .andWhere(Sqls.custom()
                        .andIn(SpuInstallService.FIELD_ID, ids, true)
                        .andEqualTo(SpuInstallService.FIELD_DELETE_FLAG, false))
                .build());

        if (CollUtil.isNotEmpty(spuInstallServices)) {
            for (SpuInstallService mm : spuInstallServices) {
                // 删除对应的sku
                SpuInstallServiceSku InstallServiceSkuQuery = new SpuInstallServiceSku();
                InstallServiceSkuQuery.setSpuCode(mm.getSpuCode());
                List<SpuInstallServiceSku> spuInstallServiceSkuList = spuInstallServiceSkuRepository.select(InstallServiceSkuQuery);

                int resultRows1 = spuInstallServiceSkuRepository.batchDelete(spuInstallServiceSkuList);
                log.info("resultRows1={}", resultRows1);

                // 删除对应的店铺
                SpuInstallServiceStore InstallServiceStoreQuery = new SpuInstallServiceStore();
                InstallServiceStoreQuery.setSpuCode(mm.getSpuCode());
                List<SpuInstallServiceStore> spuInstallServiceStoreList = spuInstallServiceStoreRepository.select(InstallServiceStoreQuery);

                int resultRows2 = spuInstallServiceStoreRepository.batchDelete(spuInstallServiceStoreList);
                log.info("resultRows2={}", resultRows2);
            }
            int resultRows3 = spuInstallServiceRepository.batchDelete(spuInstallServices);
            return resultRows3 > 0;
        }

        return false;
    }
}
