package org.skyer.goods.app.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.skyer.core.domain.Page;
import org.skyer.core.domain.PageInfo;
import org.skyer.core.exception.CommonException;
import org.skyer.core.oauth.CustomUserDetails;
import org.skyer.core.oauth.DetailsHelper;
import org.skyer.goods.app.service.MyEncryptionService;
import org.skyer.goods.app.service.SpuSonMumService;
import org.skyer.goods.domain.dto.AddSonMumSkuDTO;
import org.skyer.goods.domain.dto.PageSonMumDTO;
import org.skyer.goods.domain.dto.SpuSonMumDTO;
import org.skyer.goods.domain.entity.*;
import org.skyer.goods.domain.repository.SpuSonMumRepository;
import org.skyer.goods.domain.repository.SpuSonMumSkuRepository;
import org.skyer.goods.domain.repository.SpuSonMumStoreRepository;
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
 * 子母商品应用服务默认实现
 *
 * @author chenzz 2021-08-16 15:04:38
 */
@Slf4j
@Service
public class SpuSonMumServiceImpl implements SpuSonMumService {

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private SpuSonMumRepository spuSonMumRepository;

    @Autowired
    private SpuSonMumSkuRepository spuSonMumSkuRepository;

    @Autowired
    private SpuSonMumStoreRepository spuSonMumStoreRepository;

    @Autowired
    private OnlineShopRemoteService onlineShopRemoteService;

    @Override
    public Page<Dict> page(PageRequest pageRequest, PageSonMumDTO spuSonMum) {

        Page<SpuSonMum> page = PageHelper.doPageAndSort(pageRequest, () -> spuSonMumRepository.selectByCondition(Condition.builder(SpuSonMum.class)
                .andWhere(Sqls.custom()
                        .andLike(SpuSonMum.FIELD_SPU_CODE, spuSonMum.getSpuCode(), true)
                        .andLike(SpuSonMum.FIELD_SPU_NAME, spuSonMum.getSpuName(), true)
                        .andEqualTo(SpuSonMum.FIELD_DELETE_FLAG, false))
                .build()));

        List<SpuSonMum> pageListContent = page.getContent();
        if (CollUtil.isNotEmpty(pageListContent)) {
            // List<String> spuCodes = pageListContent.stream().map(xx -> xx.getSpuCode()).collect(Collectors.toList());

            List<Dict> dictList = pageListContent.stream().map(xx -> {
                Dict item = Dict.create();

                item.set("spuCode", xx.getSpuCode())
                        .set("spuId", myEncryptionService.encrypt(xx.getSpuId()))
                        .set("id", myEncryptionService.encrypt(xx.getId()))
                        .set("spuName", xx.getSpuName())
                        .set("isAllStore", xx.getIsAllStore())
                        .set("remark", xx.getRemark())
                        .set("categoryId", xx.getCategoryId())
                        .set("createdBy", xx.getCreatedBy())
                        .set("creationDate", DateUtil.formatDateTime(xx.getCreationDate()));
//                item.set("categoryText", categoryOldService.getCategoryNameByListId(xx.getCategoryId()));

                // 套餐商品
                List<SpuSonMumSku> spuSonMumSkuList = spuSonMumSkuRepository.selectByCondition(Condition.builder(SpuSonMumSku.class)
                        .andWhere(Sqls.custom()
                                .andEqualTo(SpuSonMumSku.FIELD_SPU_CODE, xx.getSpuCode()))
                        .build());
                item.set("skuCount", spuSonMumSkuList.size());

                // 售卖店铺
                List<SpuSonMumStore> spuSonMumStoreList = spuSonMumStoreRepository.selectByCondition(Condition.builder(SpuSonMumStore.class)
                        .andWhere(Sqls.custom()
                                .andEqualTo(SpuSonMumStore.FIELD_SPU_CODE, xx.getSpuCode()))
                        .build());
                item.set("storeCount", spuSonMumStoreList.size());


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
        SpuSonMum spuSonMum = spuSonMumRepository.selectByPrimaryKey(id);
        if (ObjectUtil.isNotNull(spuSonMum)) {
            Dict dict = Dict.create();
            // dict.set("id", String.valueOf(spuSonMum.getId()));
            dict.set("objectVersionNumber", spuSonMum.getObjectVersionNumber());
            dict.set("id", myEncryptionService.encrypt(spuSonMum.getId()));
            dict.set("spuId", myEncryptionService.encrypt(spuSonMum.getSpuId()));
            dict.set("spuCode", spuSonMum.getSpuCode());
            dict.set("spuName", spuSonMum.getSpuName());
            dict.set("isAllStore", spuSonMum.getIsAllStore());
            dict.set("remark", spuSonMum.getRemark());
            dict.set("categoryId", myEncryptionService.encrypt(spuSonMum.getCategoryId()));
//            String categoryText = categoryOldService.getCategoryNameByListId(spuSonMum.getCategoryId());
//            dict.set("categoryText", categoryText);

            dict.set("creationDate", DateUtil.formatDateTime(spuSonMum.getCreationDate()));

            //----------------------
            SpuSonMumSku sonMumSkuQuery = new SpuSonMumSku();
            sonMumSkuQuery.setSpuCode(spuSonMum.getSpuCode());
            List<SpuSonMumSku> spuSonMumSkuList = spuSonMumSkuRepository.select(sonMumSkuQuery);
            List<Dict> dictList = spuSonMumSkuList.stream().map(xx -> {
                Dict itemDict = Dict.create();
                // itemDict.set("id", String.valueOf(xx.getId()));
                itemDict.set("id", myEncryptionService.encrypt(xx.getId()));

                itemDict.set("skuId", myEncryptionService.encrypt(xx.getSonSkuId()));
                itemDict.set("skuCode", xx.getSonSkuCode());
                itemDict.set("amount", xx.getAmount());
                // spu详情
                itemDict.set("spuCode", spuSonMum.getSpuCode());
                itemDict.set("spuName", spuSonMum.getSpuName());
//                itemDict.set("categoryName", categoryText);
                Sku sku = skuMapper.selectByPrimaryKey(xx.getSonSkuId());
                itemDict.set("price", sku.getSalesPrice());
                return itemDict;
            }).collect(Collectors.toList());
            dict.set("spuSonMumSkus", dictList);

            // 售卖店铺
            SpuSonMumStore sonMumStoreQuery = new SpuSonMumStore();
            sonMumStoreQuery.setSpuCode(spuSonMum.getSpuCode());
            List<SpuSonMumStore> spuSonMumStoreList = spuSonMumStoreRepository.select(sonMumStoreQuery);
            List<Dict> dictStoreList = spuSonMumStoreList.stream().map(xx -> {
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
            dict.set("spuSonMumStores", dictStoreList);
            return dict;
        }
        return Dict.create().parseBean(spuSonMum);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean create(SpuSonMumDTO spuSonMumDTO) {
        if (!spuSonMumDTO.getIsAllStore() && CollUtil.isEmpty(spuSonMumDTO.getSpuStoreIds())) {
            // 如果不是适用于所有店铺且店铺没有设置值，返回错误
            throw new CommonException("没有设置店铺!");
        }
        // -------------------------------
        Spu spu = spuMapper.selectOne((new Spu()).setSpuCode(spuSonMumDTO.getSpuCode()));
        if (ObjectUtil.isNotNull(spu)) {
            // 获取租户的id
            CustomUserDetails userDetails = DetailsHelper.getUserDetails();
            Long tenantId = userDetails.getTenantId();
            // 1. 添加主表数据
            SpuSonMum spuSonMum = new SpuSonMum();
            spuSonMum.setSpuCode(spuSonMumDTO.getSpuCode());
            spuSonMum.setSpuId(spu.getId());
            spuSonMum.setCategoryId(spu.getCategoryId());
            spuSonMum.setSpuName(spu.getName());
            spuSonMum.setRemark(spuSonMumDTO.getRemark());
            spuSonMum.setIsAllStore(spuSonMumDTO.getIsAllStore());
            spuSonMum.setTenantId(tenantId);
            spuSonMum.setDeleteFlag(false);
            int rowsResult1 = spuSonMumRepository.insert(spuSonMum);
            log.info("rows1={}", rowsResult1);
            // 添加对应的sku数据
            for (AddSonMumSkuDTO mm : spuSonMumDTO.getSonMumSkuDTOS()) {
                Sku skuQuery = new Sku();
                skuQuery.setSkuCode(mm.getSonSkuCode());
                Sku sku = skuMapper.selectOne(skuQuery);
                if (ObjectUtil.isNull(sku)) {
                    throw new CommonException("sku编码不存在，请检查!");
                }
                SpuSonMumSku itemSku = new SpuSonMumSku();
                itemSku.setSpuCode(spu.getSpuCode());
                itemSku.setSpuId(spu.getId());
                itemSku.setAmount(mm.getAmount());
                itemSku.setSonSkuId(sku.getId());
                itemSku.setSonSkuCode(mm.getSonSkuCode());
                itemSku.setTenantId(tenantId);
                itemSku.setDeleteFlag(false);

                int rowsResult2 = spuSonMumSkuRepository.insert(itemSku);
                log.info("rows2={}", rowsResult2);
            }
            // 添加适用店铺
            // 需要使用远程调用
            for (Long ll : spuSonMumDTO.getSpuStoreIds()) {
                ResponseEntity<OnlineShopVO> listResponseEntity = onlineShopRemoteService.showDetails(ll);
                OnlineShopVO body = listResponseEntity.getBody();
                if (body != null) {
                    SpuSonMumStore spuSonMumStore = new SpuSonMumStore();
                    spuSonMumStore.setSpuCode(spuSonMumDTO.getSpuCode());
                    spuSonMumStore.setStoreId(ll);
                    spuSonMumStore.setStoreCode(body.getCode());
                    spuSonMumStore.setTenantId(tenantId);
                    spuSonMumStore.setDeleteFlag(false);

                    int rowsResult3 = spuSonMumStoreRepository.insert(spuSonMumStore);
                    log.info("rows3={}", rowsResult3);
                }
            }

            return true;
        }
        return false;
    }

    @Override
    public Boolean update(SpuSonMumDTO spuSonMumDTO) {
        if (!spuSonMumDTO.getIsAllStore() && CollUtil.isEmpty(spuSonMumDTO.getSpuStoreIds())) {
            // 如果不是适用于所有店铺且店铺没有设置值，返回错误
            throw new CommonException("没有设置店铺!");
        }
        // -------------------------------
        Spu spu = spuMapper.selectOne((new Spu()).setSpuCode(spuSonMumDTO.getSpuCode()));
        if (ObjectUtil.isNotNull(spu)) {
            // 获取租户的id
            CustomUserDetails userDetails = DetailsHelper.getUserDetails();
            Long tenantId = userDetails.getTenantId();
            // 1. 修改主表数据
            SpuSonMum spuSonMum = new SpuSonMum();
            spuSonMum.setId(spuSonMumDTO.getId());
            spuSonMum.setObjectVersionNumber(spuSonMumDTO.getObjectVersionNumber());
            spuSonMum.setRemark(spuSonMumDTO.getRemark());
            spuSonMum.setIsAllStore(spuSonMumDTO.getIsAllStore());
            int rowsResult1 = spuSonMumRepository.updateByPrimaryKeySelective(spuSonMum);
            log.info("rows1={}", rowsResult1);
            // 先删除原来数据
            SpuSonMumSku sonMumSkuQuery = new SpuSonMumSku();
            sonMumSkuQuery.setSpuCode(spuSonMumDTO.getSpuCode());
            List<SpuSonMumSku> spuSonMumSkuList = spuSonMumSkuRepository.select(sonMumSkuQuery);

            int rowsResult11 = spuSonMumSkuRepository.batchDelete(spuSonMumSkuList);
            log.info("rowsResult211={}", rowsResult11);

            // 添加对应的sku数据
            for (AddSonMumSkuDTO mm : spuSonMumDTO.getSonMumSkuDTOS()) {
                Sku skuQuery = new Sku();
                skuQuery.setSkuCode(mm.getSonSkuCode());
                Sku sku = skuMapper.selectOne(skuQuery);
                if (ObjectUtil.isNull(sku)) {
                    throw new CommonException("sku编码不存在，请检查!");
                }
                SpuSonMumSku itemSku = new SpuSonMumSku();
                itemSku.setSpuCode(spu.getSpuCode());
                itemSku.setSpuId(spu.getId());
                itemSku.setAmount(mm.getAmount());
                itemSku.setSonSkuId(sku.getId());
                itemSku.setSonSkuCode(mm.getSonSkuCode());
                itemSku.setTenantId(tenantId);
                itemSku.setDeleteFlag(false);

                int rowsResult2 = spuSonMumSkuRepository.insert(itemSku);
                log.info("rows2={}", rowsResult2);
            }

            // 先删除原来数据
            SpuSonMumStore sonMumStoreQuery = new SpuSonMumStore();
            sonMumStoreQuery.setSpuCode(spuSonMumDTO.getSpuCode());
            List<SpuSonMumStore> spuSonMumStoreList = spuSonMumStoreRepository.select(sonMumStoreQuery);

            int rowsResult22 = spuSonMumStoreRepository.batchDelete(spuSonMumStoreList);
            log.info("rowsResult22={}", rowsResult22);

            // 添加适用店铺
            // 需要使用远程调用
            for (Long ll : spuSonMumDTO.getSpuStoreIds()) {
                ResponseEntity<OnlineShopVO> listResponseEntity = onlineShopRemoteService.showDetails(ll);
                OnlineShopVO body = listResponseEntity.getBody();
                if (body != null) {
                    SpuSonMumStore spuSonMumStore = new SpuSonMumStore();
                    spuSonMumStore.setSpuCode(spuSonMumDTO.getSpuCode());
                    spuSonMumStore.setStoreId(ll);
                    spuSonMumStore.setStoreCode(body.getCode());
                    spuSonMumStore.setTenantId(tenantId);
                    spuSonMumStore.setDeleteFlag(false);

                    int rowsResult3 = spuSonMumStoreRepository.insert(spuSonMumStore);
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
        List<SpuSonMum> spuSonMums = spuSonMumRepository.selectByCondition(Condition.builder(SpuSonMum.class)
                .andWhere(Sqls.custom()
                        .andIn(SpuSonMum.FIELD_ID, ids, true)
                        .andEqualTo(SpuSonMum.FIELD_DELETE_FLAG, false))
                .build());

        if (CollUtil.isNotEmpty(spuSonMums)) {
            for (SpuSonMum mm : spuSonMums) {
                // 删除对应的sku
                SpuSonMumSku sonMumSkuQuery = new SpuSonMumSku();
                sonMumSkuQuery.setSpuCode(mm.getSpuCode());
                List<SpuSonMumSku> spuSonMumSkuList = spuSonMumSkuRepository.select(sonMumSkuQuery);

                int resultRows1 = spuSonMumSkuRepository.batchDelete(spuSonMumSkuList);
                log.info("resultRows1={}", resultRows1);

                // 删除对应的店铺
                SpuSonMumStore sonMumStoreQuery = new SpuSonMumStore();
                sonMumStoreQuery.setSpuCode(mm.getSpuCode());
                List<SpuSonMumStore> spuSonMumStoreList = spuSonMumStoreRepository.select(sonMumStoreQuery);

                int resultRows2 = spuSonMumStoreRepository.batchDelete(spuSonMumStoreList);
                log.info("resultRows2={}", resultRows2);
            }
            int resultRows3 = spuSonMumRepository.batchDelete(spuSonMums);
            return resultRows3 > 0;
        }

        return false;
    }
}
