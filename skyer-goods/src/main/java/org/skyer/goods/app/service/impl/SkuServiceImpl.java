package org.skyer.goods.app.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;

import org.skyer.core.cache.ProcessCacheValue;
import org.skyer.core.domain.Page;
import org.skyer.core.domain.PageInfo;
import org.skyer.core.exception.CommonException;
import org.skyer.core.oauth.CustomUserDetails;
import org.skyer.core.oauth.DetailsHelper;
import org.skyer.goods.api.dto.*;
import org.skyer.goods.app.service.SkuEsService;
import org.skyer.goods.app.service.SkuService;
import org.skyer.goods.domain.dto.AuditDTO;
import org.skyer.goods.domain.dto.SkuAuditDTO;
import org.skyer.goods.domain.entity.*;
import org.skyer.goods.domain.repository.*;
import org.skyer.goods.domain.vo.*;
import org.skyer.goods.infra.util.Md5Utils;
import org.skyer.mybatis.domian.Condition;
import org.skyer.mybatis.util.Sqls;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.skyer.goods.domain.entity.Sku.*;

/**
 * 商品规格表应用服务默认实现
 *
 * @author chenzz 2021-07-06 10:35:43
 */
@Slf4j
@Service
public class SkuServiceImpl implements SkuService {

    @Autowired
    private SkuRepository skuRepository;
    @Autowired
    private SkuAttributeValueRepository skuAttributeValueRepository;
    @Autowired
    private SkuDraftRepository skuDraftRepository;
    @Autowired
    private SkuAttributeValueDraftRepository skuAttributeValueDraftRepository;
    @Autowired
    private SkuAuditSettingRepository skuAuditSettingRepository;
    @Autowired
    private SpuRepository spuRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private MaterialRepository materialRepository;
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private SkuEsService skuEsService;
    @Autowired
    private SkuPackageRepository skuPackageRepository;

    @Override
    public Page<GoodsSkuPageVO> listPage(GoodsSkuPageDTO goodsSkuPageDTO) {
        if (CollUtil.isNotEmpty(goodsSkuPageDTO.getCategoryIdList())) {
            goodsSkuPageDTO.setCategoryIdList(categoryRepository.getLaterCategoryId(goodsSkuPageDTO.getCategoryIdList()));
        }
        Page<Sku> pageList = skuRepository.pageSku(goodsSkuPageDTO);
        List<Sku> skuList = pageList.getContent();
        List<GoodsSkuPageVO> pageVOS = new ArrayList<>();
        if (CollUtil.isNotEmpty(skuList)) {
            // 找出所有的商品数据
            Set<Long> goodsIds = skuList.stream().map(Sku::getSpuId).collect(Collectors.toSet());
            List<Spu> spuList = spuRepository.selectByCondition(Condition.builder(Spu.class)
                    .andWhere(Sqls.custom()
                            .andIn(org.skyer.goods.domain.entity.Spu.ID, goodsIds, true)
                            .andEqualTo(org.skyer.goods.domain.entity.Spu.DELETE_FLAG, false))
                    .build());
            // 找出所有素材
            Set<Long> materialIds = spuList.stream().map(Spu::getMaterialId).collect(Collectors.toSet());
            List<Material> materialList = materialRepository.selectByCondition(Condition.builder(Material.class)
                    .andWhere(Sqls.custom()
                            .andIn("id", materialIds, true)
                            .andEqualTo(org.skyer.goods.domain.entity.Spu.DELETE_FLAG, false))
                    .build());
            //组装返回数据
            skuList.forEach(o -> {
                GoodsSkuPageVO skuPageVO = new GoodsSkuPageVO();
                //复制id 价格等信息
                BeanUtils.copyProperties(o, skuPageVO);
                //找到与sku匹配的spu
                Spu tempSpu = spuList.stream().filter(xx -> xx.getId().longValue() == o.getSpuId().longValue()).findFirst().orElse(null);
                if (ObjectUtil.isNotNull(tempSpu)) {
                    //设置sku属性字段
                    skuPageVO.setMaterialId(tempSpu.getMaterialId());
                    skuPageVO.setSpuName(tempSpu.getName());
                    skuPageVO.setSpuCode(tempSpu.getSpuCode());
                    if (ObjectUtil.isNotNull(tempSpu.getRemark())) {
                        skuPageVO.setGoodsRemark(tempSpu.getRemark());
                    }
                    String categoryName = categoryRepository.getCategoryNameById(tempSpu.getCategoryId());
                    skuPageVO.setCategoryName(categoryName);
                    //匹配素材
                    if (ObjectUtil.isNotNull(tempSpu.getMaterialId())) {
                        skuPageVO.setMaterialId(tempSpu.getMaterialId());
                        Material tempMaterial = materialList.stream().filter(xx -> xx.getId().longValue() == skuPageVO.getMaterialId().longValue()).findFirst().orElse(null);
                        if (ObjectUtil.isNotNull(tempMaterial)) {
                            skuPageVO.setMaterialMainImages(Arrays.asList(Optional.ofNullable(tempMaterial.getMainImage()).orElse("").split(",")));
                            skuPageVO.setMaterialName(tempMaterial.getMaterialName());
                        }
                    }
                    //查询属性值 是带入初始属性 否放入到额外属性
                    List<SkuAttributeValue> skuAttrValueRelationList = skuAttributeValueRepository.selectByCondition(Condition.builder(SkuAttributeValue.class)
                            .andWhere(Sqls.custom().andEqualTo(SkuAttributeValue.FIELD_SKU_ID, o.getId(), true))
                            .build());

                    if (CollUtil.isNotEmpty(skuAttrValueRelationList)) {
                        List<SkuAttributeValue> originalAttributeList = skuAttrValueRelationList.stream().filter(SkuAttributeValue::getIsSku).collect(Collectors.toList());
                        skuPageVO.setSkuAttributeList(originalAttributeList);

                        List<SkuAttributeValue> attachedAttributeList = skuAttrValueRelationList.stream().filter(f -> !f.getIsSku()).collect(Collectors.toList());
                        skuPageVO.setOtherAttributeList(attachedAttributeList);
                    }
                }
                pageVOS.add(skuPageVO);
            });
        }
        return new Page<>(pageVOS, new PageInfo(pageList.getTotalPages(), pageList.getSize()), pageList.getTotalElements());
    }


    @Override
    public GoodsSkuDetailVO getDetail(Long id, boolean isEdit) {
        Sku sku = skuRepository.selectByPrimaryKey(id);
        GoodsSkuDetailVO goodsSkuVO = new GoodsSkuDetailVO();
        if (ObjectUtil.isNotEmpty(sku)) {
            BeanUtils.copyProperties(sku, goodsSkuVO);
            //如果是审核被驳回 需要去副本里寻找数据
            if (isEdit && Sku.STATUS_TAKE_EFFECT_REJECTED.equals(sku.getStatus())) {
                SkuDraft skuDraft = skuDraftRepository.selectByPrimaryKey(sku.getId());
                if (ObjectUtil.isNotEmpty(skuDraft)) {
                    //把草稿里的属性覆盖给vo显示
                    BeanUtils.copyProperties(skuDraft, goodsSkuVO);
                }
            }

            Spu spu = spuRepository.selectByPrimaryKey(sku.getSpuId());

            if (ObjectUtil.isNotNull(spu)) {
                //设置sku属性字段
                goodsSkuVO.setMaterialId(spu.getMaterialId());
                goodsSkuVO.setSpuName(spu.getName());
                goodsSkuVO.setSpuCode(spu.getSpuCode());
                if (ObjectUtil.isNotNull(spu.getRemark())) {
                    goodsSkuVO.setGoodsRemark(spu.getRemark());
                }
                String categoryName = categoryRepository.getCategoryNameById(spu.getCategoryId());
                goodsSkuVO.setCategoryName(categoryName);

                //匹配素材
                if (ObjectUtil.isNotNull(spu.getMaterialId())) {
                    goodsSkuVO.setMaterialId(spu.getMaterialId());
                    Material material = materialRepository.selectByPrimaryKey(spu.getMaterialId());
                    if (ObjectUtil.isNotNull(material)) {
                        goodsSkuVO.setMaterialName(material.getMaterialName());
                        goodsSkuVO.setMaterialMainImages(Arrays.asList(Optional.ofNullable(material.getMainImage()).orElse("").split(",")));
                        goodsSkuVO.setImageList(Arrays.asList(Optional.ofNullable(material.getDetailImage()).orElse("").split(",")));
                    }
                }
                //查询属性值 是带入初始属性 否放入到额外属性
                List<SkuAttributeValue> skuAttrValueRelationList = skuAttributeValueRepository.selectByCondition(Condition.builder(SkuAttributeValue.class)
                        .andWhere(Sqls.custom().andEqualTo(SkuAttributeValue.FIELD_SKU_ID, sku.getId(), true))
                        .build());
                if (CollUtil.isNotEmpty(skuAttrValueRelationList)) {
                    List<SkuAttributeValue> originalAttributeList = skuAttrValueRelationList.stream().filter(SkuAttributeValue::getIsSku).collect(Collectors.toList());
                    List<SkuAttributeValue> attachedAttributeList = skuAttrValueRelationList.stream().filter(f -> !f.getIsSku()).collect(Collectors.toList());
                    //如果是审核被驳回 需要去副本里寻找数据
                    if (isEdit && Sku.STATUS_TAKE_EFFECT_REJECTED.equals(sku.getStatus())) {
                        List<SkuAttributeValueDraft> oldSkuAttributeList = skuAttributeValueDraftRepository.selectByCondition(Condition.builder(SkuAttributeValueDraft.class)
                                .andWhere(Sqls.custom().andEqualTo(SkuAttributeValueDraft.FIELD_SKU_ID, sku.getId())).build());
                        if (ObjectUtil.isNotEmpty(oldSkuAttributeList)) {
                            //处理原始属性
                            if (ObjectUtil.isNotEmpty(originalAttributeList)) {
                                originalAttributeList.forEach(item -> {
                                    SkuAttributeValueDraft skuAttributeValue = oldSkuAttributeList.stream().filter(f -> f.getId().equals(item.getId())).findAny().orElse(null);
                                    if (ObjectUtil.isNotNull(skuAttributeValue)) {
                                        BeanUtils.copyProperties(skuAttributeValue, item);
                                    }
                                });
                            }
                            //处理额外属性
                            if (ObjectUtil.isNotEmpty(attachedAttributeList)) {
                                attachedAttributeList.forEach(item -> {
                                    SkuAttributeValueDraft skuAttributeValue = oldSkuAttributeList.stream().filter(f -> f.getId().equals(item.getId())).findAny().orElse(null);
                                    if (ObjectUtil.isNotNull(skuAttributeValue)) {
                                        BeanUtils.copyProperties(skuAttributeValue, item);
                                    }
                                });
                            }
                        }
                    }
                    goodsSkuVO.setOtherAttributeList(attachedAttributeList);
                    goodsSkuVO.setSkuAttributeList(originalAttributeList);
                }
                //品牌
                Brand brand = brandRepository.selectByPrimaryKey(spu.getBrandId());
                if (ObjectUtil.isNotNull(brand)) {
                    goodsSkuVO.setBrandName(brand.getName());
                }
                //套餐sku明细
                if (SET_FLAG_TRUE.equals(sku.getSetFlag())){
                    List<SkuPackage> skuPackageList = skuPackageRepository.selectByCondition(Condition.builder(SkuPackage.class)
                            .andWhere(Sqls.custom().andEqualTo(SkuPackage.FIELD_SKU_ID,sku.getId())).build());
                    if(ObjectUtil.isNotEmpty(skuPackageList)){
                        List<Long> skuIds = skuPackageList.stream().map(SkuPackage::getPackageSkuId).collect(Collectors.toList());
                        List<Sku> skuList = skuRepository.selectByCondition(Condition.builder(Sku.class)
                                .andWhere(Sqls.custom().andIn(FIELD_ID,skuIds)).build());
                        List<Long> spuIds = skuList.stream().map(Sku::getSpuId).collect(Collectors.toList());
                        List<Spu> spuList = spuRepository.selectByCondition(Condition.builder(Sku.class)
                                .andWhere(Sqls.custom().andIn(FIELD_ID,spuIds)).build());
                        List<SkuPackageVO> skuDetailList = skuPackageList.stream().map(skuPackage->{
                            Sku skuDetail = skuList.stream().filter(f->f.getId().equals(skuPackage.getPackageSkuId())).findAny().orElse(null);
                            SkuPackageVO packageVO = new SkuPackageVO();
                            if (ObjectUtil.isNotNull(skuDetail)){
                                packageVO.setId(skuPackage.getId());
                                packageVO.setSkuCode(skuDetail.getSkuCode());
                                packageVO.setSkuName(skuDetail.getSkuName());
                                packageVO.setCostPrice(skuDetail.getCostPrice());
                                packageVO.setSalesPrice(skuDetail.getSalesPrice());
                                packageVO.setNum(skuPackage.getNum());
                                Spu spuDetail =spuList.stream().filter(f->f.getId().equals(skuDetail.getSpuId())).findAny().orElse(null);
                                if (ObjectUtil.isNotNull(spuDetail)){
                                    packageVO.setSpuName(spuDetail.getName());
                                    packageVO.setSpuCode(spuDetail.getSpuCode());
                                }
                            }
                            return packageVO;
                        }).collect(Collectors.toList());
                        goodsSkuVO.setSkuDetailList(skuDetailList);
                    }
                }
            }
        }
        return goodsSkuVO;
    }


    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Boolean saveSku(SkuBatchSaveDTO skuBatchSaveDTO) {
        CustomUserDetails userDetails = DetailsHelper.getUserDetails();
        //sku
        List<SkuListRequestDTO> skuReqDTOList = skuBatchSaveDTO.getSkuList();
        if (ObjectUtil.isEmpty(skuReqDTOList)){
            throw new CommonException("请先生成sku列表");
        }
        List<String> codeList = skuReqDTOList.stream().map(SkuListRequestDTO::getSkuCode).filter(StringUtils::isNotEmpty).collect(Collectors.toList());
        long count = codeList.stream().distinct().count();
        if (count<codeList.size()){
            throw new CommonException("列表存在相同的sku编码 ");
        }
        int status;
        //ture为草稿
        if (Boolean.TRUE.equals(skuBatchSaveDTO.getFlag())) {
            status = Sku.STATUS_DRAFT;
        } else {
            //获取审核流程配置
            SkuAuditSetting skuAuditSetting = skuAuditSettingRepository.selectDefault();
            if (SkuAuditSetting.STATUS_ON.equals(skuAuditSetting.getCreatedAuditStatus())) {
                status = STATUS_UNDER_REVIEW;
            } else {
                status = STATUS_TAKE_EFFECT;
            }
        }
        List<Sku> skuList = skuReqDTOList.stream().map(x -> {

            // 查看是否重复
            String md5ByLong="";
            if(!SET_FLAG_TRUE.equals(skuBatchSaveDTO.getSetFlag())){
                List<AttributeDTO> skuAttributeValueList = x.getSkuAttributeList();
                List<String> valueIdList;
                if (ObjectUtil.isNotEmpty(skuAttributeValueList)) {
                    valueIdList = skuAttributeValueList.stream().map(AttributeDTO::getAttributeValue).filter(ObjectUtil::isNotNull).collect(Collectors.toList());
                } else {
                    valueIdList = new ArrayList<>();
                }
                 md5ByLong = Md5Utils.createMd5ByLong(valueIdList);
                Sku oldSku = skuRepository.checkHaveSameSku(x.getSpuId(), md5ByLong);
                if (ObjectUtil.isNotEmpty(oldSku)) {
                    throw new CommonException("当前属性已经生成sku:" + oldSku.getSkuCode());
                }
            }

            if (ObjectUtil.isNotEmpty(x.getSkuCode())) {
                Sku oldSkuCode = skuRepository.checkHaveSameSkuCode(x.getSkuCode());
                if (ObjectUtil.isNotNull(oldSkuCode)) {
                    throw new CommonException("当前skuCode已经生成sku:" + oldSkuCode.getSkuCode());
                }
            }
            Sku sku = new Sku();
            BeanUtils.copyProperties(x, sku);
            sku.setMd5CheckValue(md5ByLong);
            sku.setStatus(status);
            sku.setSetFlag(skuBatchSaveDTO.getSetFlag());
            sku.setAuditCommitBy(userDetails.getUserId());
            return sku;
        }).collect(Collectors.toList());
        //sku数据存储 获取sku存储id
        skuRepository.batchInsertSelective(skuList);
        List<SkuAttributeValue> attributeValueList = new ArrayList<>();
        skuList.forEach(sku -> {
            //处理原始属性
            List<AttributeDTO> originalAttributeList = sku.getSkuAttributeList();
            if (ObjectUtil.isNotEmpty(originalAttributeList)) {
                originalAttributeList.forEach(item -> {
                    SkuAttributeValue skuAttributeValue = new SkuAttributeValue();
                    BeanUtils.copyProperties(item, skuAttributeValue);
                    skuAttributeValue.setSkuId(sku.getId());
                    skuAttributeValue.setIsSku(true);
                    attributeValueList.add(skuAttributeValue);
                });
            }
            //处理额外属性
            List<AttributeDTO> attachedAttributeList = sku.getOtherAttributeList();
            if (ObjectUtil.isNotEmpty(attachedAttributeList)) {
                attachedAttributeList.forEach(item -> {
                    SkuAttributeValue skuAttributeValue = new SkuAttributeValue();
                    BeanUtils.copyProperties(item, skuAttributeValue);
                    skuAttributeValue.setSkuId(sku.getId());
                    skuAttributeValue.setIsSku(false);
                    attributeValueList.add(skuAttributeValue);
                });
            }
        });
        //套餐商品数据处理
        if (Sku.SET_FLAG_TRUE.equals(skuBatchSaveDTO.getSetFlag())){
            List<SkuPackage> addSkuPackageList = new ArrayList<>();
            skuList.forEach(sku -> {
                List<SkuPackage> skuPackageList = sku.getSkuDetailList();
                if (ObjectUtil.isNotEmpty(skuPackageList)){
                    skuPackageList.forEach(skuPackage -> {
                        skuPackage.setSkuId(sku.getId());
                        skuPackage.setNum(skuPackage.getNum()==null?1:skuPackage.getNum());
                    });
                    addSkuPackageList.addAll(skuPackageList);
                }
            });
            skuPackageRepository.batchInsertSelective(addSkuPackageList);
        }
        //sku的属性存储
        skuAttributeValueRepository.batchInsertSelective(attributeValueList);
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @Override
            public void afterCommit() {
                //数据保存到es
                skuEsService.syncSkuListEsSage(skuList);
            }
        });
        return true;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Boolean deleteByIds(List<Long> ids) {
        //判断是否可以删除
        List<Sku> skuList = skuRepository.selectByCondition(Condition.builder(Sku.class)
                .andWhere(Sqls.custom().andIn(Sku.FIELD_ID, ids, true)).build());

        if (CollUtil.isEmpty(skuList)) {
            throw new CommonException("查询不到sku");
        }

        List<Sku> noDeleteList = skuList.stream().filter(f -> f.getStatus() > Sku.STATUS_REJECTED).collect(Collectors.toList());
        if (CollUtil.isNotEmpty(noDeleteList)) {
            StringJoiner stringJoiner = new StringJoiner(",");
            noDeleteList.forEach(sku -> stringJoiner.add(sku.getSkuCode()));
            throw new CommonException("SKU不可删除:" + stringJoiner);
        }
        //todo 判断是否有库存

        //todo 判断是否有订单

        List<Long> skuIdList = skuList.stream().map(Sku::getId).collect(Collectors.toList());
        List<SkuAttributeValue> skuAttributeValues = skuAttributeValueRepository.selectByCondition(Condition.builder(SkuAttributeValue.class)
                .andWhere(Sqls.custom().andIn(SkuAttributeValue.FIELD_SKU_ID, skuIdList, true)).build());
        List<SkuPackage> skuPackageList = skuPackageRepository.selectByCondition(Condition.builder(SkuAttributeValue.class)
                .andWhere(Sqls.custom().andIn(SkuPackage.FIELD_SKU_ID, skuIdList, true)).build());
        //删除属性和sku
        skuAttributeValueRepository.batchDeleteByPrimaryKey(skuAttributeValues);
        skuPackageRepository.batchDeleteByPrimaryKey(skuPackageList);
        skuRepository.batchDeleteByPrimaryKey(skuList);
        skuEsService.syncDelSkuListEsSage(skuList);
        return true;
    }


    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean updateSku(SkuUpdateDTO updateDTO) {
        CustomUserDetails userDetails = DetailsHelper.getUserDetails();
        SkuUpdateInfoDTO sku = updateDTO.getSku();
        if (StringUtils.isNotEmpty(sku.getSkuCode())) {
            //去重校验
            Sku oldSkuCode = skuRepository.checkHaveSameSkuCodeWithOutSkuId(sku.getSkuCode(), sku.getId());
            if (ObjectUtil.isNotNull(oldSkuCode)) {
                throw new CommonException("当前skuCode已经生成sku:" + sku.getSkuCode());
            }
        }
        //获取未更新之前的数据
        Sku oldSku = skuRepository.selectByPrimaryKey(sku.getId());
        //获取审核流程配置
        SkuAuditSetting skuAuditSetting = skuAuditSettingRepository.selectDefault();
        if (ObjectUtil.isNotNull(oldSku)) {
            //状态如果为草稿
            if (Sku.STATUS_DRAFT.equals(oldSku.getStatus()) || Sku.STATUS_REJECTED.equals(oldSku.getStatus())) {
                BeanUtils.copyProperties(sku, oldSku);
                //判断是否提交审核  false为提交
                if (Boolean.FALSE.equals(updateDTO.getFlag())) {
                    //变更审核状态
                    oldSku.setAuditCommitBy(userDetails.getUserId());
                    oldSku.setAuditCommitDate(new Date());
                    oldSku.setStatus(SkuAuditSetting.STATUS_ON.equals(skuAuditSetting.getUpdateAuditStatus()) ? STATUS_UNDER_REVIEW : STATUS_TAKE_EFFECT);
                }else {
                    oldSku.setStatus(STATUS_DRAFT);
                }
                skuRepository.updateByPrimaryKeySelective(oldSku);
                skuEsService.syncSkuListEsSage(Collections.singletonList(oldSku));
                // 变更属性表属性
                return this.updateSkuAttributeInfo(sku);
            }
            // 判断是否有审核流程
            if (SkuAuditSetting.STATUS_ON.equals(skuAuditSetting.getUpdateAuditStatus())) {
                //判断是否监测价格
                if (SkuAuditSetting.STATUS_ON.equals(skuAuditSetting.getUpdatePriceStatus()) && this.isPriceChange(oldSku, sku)) {
                    return this.handleAuditSku(sku, oldSku, userDetails);
                } else if (SkuAuditSetting.STATUS_ON.equals(skuAuditSetting.getUpdatePropertyStatus()) && this.isAttributeChange(oldSku, sku)) {
                    //属性改变 状态变更
                    return this.handleAuditSku(sku, oldSku, userDetails);
                } else {
                    //没有审核流程 直接更新sku数据 直接变成已生效
                    BeanUtils.copyProperties(sku, oldSku);
                    oldSku.setStatus(STATUS_TAKE_EFFECT);
                    skuRepository.updateByPrimaryKeySelective(oldSku);
                     skuEsService.syncSkuListEsSage(Collections.singletonList(oldSku));
                    // 变更属性表属性
                    return this.updateSkuAttributeInfo(sku);
                }
            } else {
                //没有审核流程 直接更新sku数据 直接变成已生效
                BeanUtils.copyProperties(sku, oldSku);
                oldSku.setStatus(STATUS_TAKE_EFFECT);
                skuRepository.updateByPrimaryKeySelective(oldSku);
                 skuEsService.syncSkuListEsSage(Collections.singletonList(oldSku));
                // 变更属性表属性
                return this.updateSkuAttributeInfo(sku);
            }
        } else {
            throw new CommonException("查询不到数据");
        }
    }

    /**
     * 处理需要审核的sku
     *
     * @param sku    更新的数据
     * @param oldSku 旧数据
     * @return
     */
    private boolean handleAuditSku(SkuUpdateInfoDTO sku, Sku oldSku, CustomUserDetails userDetails) {
        if (STATUS_TAKE_EFFECT.equals(oldSku.getStatus())) {
            // 保存新的数据到draft表
            SkuDraft skuDraft = new SkuDraft();
            BeanUtils.copyProperties(sku, skuDraft);
            skuDraft.setSkuId(oldSku.getId());
            skuDraft.setShowFlag(1);
            //数据存入到副本
            skuDraftRepository.insert(skuDraft);
            List<SkuAttributeValueDraft> attributeValueList = new ArrayList<>();
            //处理原始属性
            List<SkuAttributeValue> originalAttributeList = sku.getSkuAttributeList();
            if (ObjectUtil.isNotEmpty(originalAttributeList)) {
                originalAttributeList.forEach(item -> {
                    SkuAttributeValueDraft skuAttributeValue = new SkuAttributeValueDraft();
                    BeanUtils.copyProperties(item, skuAttributeValue);
                    skuAttributeValue.setSkuId(sku.getId());
                    skuAttributeValue.setIsSku(true);
                    attributeValueList.add(skuAttributeValue);
                });
            }
            //处理额外属性
            List<SkuAttributeValue> attachedAttributeList = sku.getOtherAttributeList();
            if (ObjectUtil.isNotEmpty(attachedAttributeList)) {
                attachedAttributeList.forEach(item -> {
                    SkuAttributeValueDraft skuAttributeValue = new SkuAttributeValueDraft();
                    BeanUtils.copyProperties(item, skuAttributeValue);
                    skuAttributeValue.setSkuId(sku.getId());
                    skuAttributeValue.setIsSku(false);
                    attributeValueList.add(skuAttributeValue);
                });
            }
            skuAttributeValueDraftRepository.batchInsertSelective(attributeValueList);
        } else if (STATUS_TAKE_EFFECT_REJECTED.equals(oldSku.getStatus())) {
            //更新副本里的旧数据
            SkuDraft oldSkuDraft = skuDraftRepository.selectByPrimaryKey(oldSku.getId());
            List<SkuAttributeValueDraft> oldSkuAttributeList = skuAttributeValueDraftRepository.selectByCondition(Condition.builder(SkuDraft.class)
                    .andWhere(Sqls.custom().andEqualTo(SkuAttributeValueDraft.FIELD_SKU_ID, oldSku.getId())).build());
            if (ObjectUtil.isNotEmpty(oldSkuDraft)) {
                BeanUtils.copyProperties(sku, oldSkuDraft);
                skuDraftRepository.updateByPrimaryKeySelective(oldSkuDraft);
            }
            if (ObjectUtil.isNotEmpty(oldSkuAttributeList)) {
                //处理原始属性
                List<SkuAttributeValue> originalAttributeList = sku.getSkuAttributeList();
                if (ObjectUtil.isNotEmpty(originalAttributeList)) {
                    originalAttributeList.forEach(item -> {
                        SkuAttributeValueDraft skuAttributeValue = oldSkuAttributeList.stream().filter(f -> f.getId().equals(item.getId())).findAny().orElse(null);
                        if (ObjectUtil.isNotNull(skuAttributeValue)) {
                            BeanUtils.copyProperties(item, skuAttributeValue);
                            skuAttributeValue.setSkuId(sku.getId());
                            skuAttributeValue.setIsSku(true);
                        }
                    });
                }
                //处理额外属性
                List<SkuAttributeValue> attachedAttributeList = sku.getOtherAttributeList();
                if (ObjectUtil.isNotEmpty(attachedAttributeList)) {
                    attachedAttributeList.forEach(item -> {
                        SkuAttributeValueDraft skuAttributeValue = oldSkuAttributeList.stream().filter(f -> f.getId().equals(item.getId())).findAny().orElse(null);
                        if (ObjectUtil.isNotNull(skuAttributeValue)) {
                            BeanUtils.copyProperties(item, skuAttributeValue);
                            skuAttributeValue.setSkuId(sku.getId());
                            skuAttributeValue.setIsSku(false);
                        }
                    });
                }
                skuAttributeValueDraftRepository.batchUpdateByPrimaryKeySelective(oldSkuAttributeList);
            }
        }
        //变更sku状态数据
        oldSku.setAuditCommitBy(userDetails.getUserId());
        oldSku.setAuditCommitDate(new Date());
        oldSku.setStatus(STATUS_REVISING);
        return skuRepository.updateOptional(oldSku, Sku.FIELD_STATUS, FIELD_AUDIT_COMMIT_DATE) > 0;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public int batchAudit(SkuBatchAuditDTO batchAuditDTO) {
        CustomUserDetails userDetails = DetailsHelper.getUserDetails();
        List<Long> skuIds = batchAuditDTO.getSkuIds();
        List<Sku> skuList = skuRepository.selectByCondition(Condition.builder(Sku.class)
                .andWhere(Sqls.custom().andIn(FIELD_ID, skuIds, true)).build());
        if (ObjectUtil.isNotEmpty(skuList)) {
            //判断状态是否能够更改
            //把状态变更为下一级
            skuList.forEach(m -> {
                if (STATUS_DRAFT.equals(m.getStatus()) || STATUS_REJECTED.equals(m.getStatus())) {
                    m.setStatus(STATUS_UNDER_REVIEW);
                } else if (STATUS_TAKE_EFFECT_REJECTED.equals(m.getStatus())) {
                    m.setStatus(STATUS_REVISING);
                } else {
                    throw new CommonException("skuCode：" + m.getSkuCode() + " 不可提交");
                }
                m.setAuditCommitBy(userDetails.getUserId());
                m.setAuditCommitDate(new Date());
            });
            skuRepository.batchUpdateByPrimaryKeySelective(skuList);
            skuEsService.syncSkuListEsSage(skuList);
        } else {
            throw new CommonException("查询不到数据");
        }
        return 0;
    }

    /**
     * 变更 sku的属性
     *
     * @param updateSku
     */
    private boolean updateSkuAttributeInfo(SkuUpdateInfoDTO updateSku) {
        List<SkuAttributeValue> attributeValueList = new ArrayList<>();
        //处理原始属性
        List<SkuAttributeValue> originalAttributeList = updateSku.getSkuAttributeList();
        if (ObjectUtil.isNotEmpty(originalAttributeList)) {
            originalAttributeList.forEach(item -> {
                SkuAttributeValue skuAttributeValue = new SkuAttributeValue();
                BeanUtils.copyProperties(item, skuAttributeValue);
                skuAttributeValue.setIsSku(true);
                attributeValueList.add(skuAttributeValue);
            });
        }

        //处理额外属性
        List<SkuAttributeValue> attachedAttributeList = updateSku.getOtherAttributeList();
        if (ObjectUtil.isNotEmpty(attachedAttributeList)) {
            attachedAttributeList.forEach(item -> {
                SkuAttributeValue skuAttributeValue = new SkuAttributeValue();
                BeanUtils.copyProperties(item, skuAttributeValue);
                skuAttributeValue.setIsSku(false);
                attributeValueList.add(skuAttributeValue);
            });
        }
        //sku的属性存储
        return !skuAttributeValueRepository.batchUpdateByPrimaryKeySelective(attributeValueList).isEmpty();
    }

    /**
     * 判断sku价格是否变动
     *
     * @param oldSku
     * @param updateSku
     * @return
     */
    private boolean isPriceChange(Sku oldSku, SkuUpdateInfoDTO updateSku) {

        if (ObjectUtil.isNull(oldSku) || ObjectUtil.isNull(updateSku)) {
            return true;
        }
        if (oldSku.getCostPrice().compareTo(updateSku.getCostPrice()) != 0) {
            return true;
        }
        if (oldSku.getSalesPrice().compareTo(updateSku.getSalesPrice()) != 0) {
            return true;
        }
        if (oldSku.getActivityPrice().compareTo(updateSku.getActivityPrice()) != 0) {
            return true;
        }
        if (oldSku.getMarketPrice().compareTo(updateSku.getMarketPrice()) != 0) {
            return true;
        }
        if (oldSku.getDistributionPrice().compareTo(updateSku.getDistributionPrice()) != 0) {
            return true;
        }
        return oldSku.getWholesalePrice().compareTo(updateSku.getWholesalePrice()) != 0;
    }

    /**
     * 判断属性是否有变动
     *
     * @param oldSku
     * @param updateSku
     * @return
     */
    private boolean isAttributeChange(Sku oldSku, SkuUpdateInfoDTO updateSku) {
        // 判断属性值是否变动
        if (ObjectUtil.isNull(oldSku) || ObjectUtil.isNull(updateSku)) {
            return true;
        }

        if (oldSku.getWeight().compareTo(updateSku.getWeight()) != 0) {
            return true;
        }
        if (oldSku.getLength().compareTo(updateSku.getLength()) != 0) {
            return true;
        }
        if (oldSku.getWidth().compareTo(updateSku.getWidth()) != 0) {
            return true;
        }
        if (oldSku.getHeight().compareTo(updateSku.getHeight()) != 0) {
            return true;
        }

        //根据oldSku 查询出旧的两个List
        List<SkuAttributeValue> oldSkuAttributeValueList = skuAttributeValueRepository.selectByCondition(Condition.builder(SkuAttributeValue.class)
                .andWhere(Sqls.custom().andEqualTo(SkuAttributeValue.FIELD_SKU_ID, oldSku.getId())).build());

        //循环对比值是否更改
        List<SkuAttributeValue> skuAttributeList = updateSku.getSkuAttributeList();
        List<SkuAttributeValue> otherAttributeList = updateSku.getOtherAttributeList();
        if (ObjectUtil.isEmpty(skuAttributeList) || ObjectUtil.isEmpty(otherAttributeList)) {
            return true;
        }
        //数据值不同的数组
        List<SkuAttributeValue> differentList = new ArrayList<>();
        oldSkuAttributeValueList.forEach(item -> {
            SkuAttributeValue skuAttributeValue = skuAttributeList.stream().filter(f -> f.getId().equals(item.getId()) && !f.getAttributeValue().equals(item.getAttributeValue())).findAny().orElse(null);
            if (ObjectUtil.isNotNull(skuAttributeValue)) {
                differentList.add(skuAttributeValue);
            }
            SkuAttributeValue skuAttributeValue1 = otherAttributeList.stream().filter(f -> f.getId().equals(item.getId()) && !f.getAttributeValue().equals(item.getAttributeValue())).findAny().orElse(null);
            if (ObjectUtil.isNotNull(skuAttributeValue1)) {
                differentList.add(skuAttributeValue1);
            }
        });
        return ObjectUtil.isNotEmpty(differentList);
    }


    @Override
    public List<GoodsSkuDetailVO> getSkuAndAttributeDataByGoodsId(Long spuId) {

        List<Sku> skuList = skuRepository.selectByCondition(Condition.builder(Sku.class)
                .andWhere(Sqls.custom().andEqualTo(Sku.FIELD_SPU_ID, spuId, true)).build());

        List<GoodsSkuDetailVO> goodsSkuPageVos = new ArrayList<>();
        if (CollUtil.isNotEmpty(skuList)) {
            Set<Long> skuIds = skuList.stream().map(Sku::getId).collect(Collectors.toSet());
            // 找出所有的商品数据
            Set<Long> goodsIds = skuList.stream().map(Sku::getSpuId).collect(Collectors.toSet());
            List<Spu> spuList = spuRepository.selectByCondition(Condition.builder(Spu.class)
                    .andWhere(Sqls.custom()
                            .andIn(Spu.ID, goodsIds, true)
                            .andEqualTo(Spu.DELETE_FLAG, false))
                    .build());
            // 找出所有素材
            Set<Long> materialIds = spuList.stream().map(Spu::getMaterialId).collect(Collectors.toSet());
            List<Material> materialList = materialRepository.selectByCondition(Condition.builder(Material.class)
                    .andWhere(Sqls.custom()
                            .andIn("id", materialIds, true)
                            .andEqualTo(org.skyer.goods.domain.entity.Spu.DELETE_FLAG, false)).build());
            //找出所有属性
            List<SkuAttributeValue> skuAttrValueRelationList = skuAttributeValueRepository.selectByCondition(Condition.builder(SkuAttributeValue.class)
                    .andWhere(Sqls.custom().andIn(SkuAttributeValue.FIELD_SKU_ID, skuIds, true)).build());
            //找出所有sku明细
            List<SkuPackage> skuPackageList = skuPackageRepository.selectByCondition(Condition.builder(SkuPackage.class)
                    .andWhere(Sqls.custom().andIn(SkuPackage.FIELD_SKU_ID, skuIds, true)).build());
            List<Sku> skuDetailLists = new ArrayList<>();
            List<Spu> spuDetailLists = new ArrayList<>();
            if (ObjectUtil.isNotEmpty(skuPackageList)){
                List<Long> detailSkuIds = skuPackageList.stream().map(SkuPackage::getPackageSkuId).collect(Collectors.toList());
                skuDetailLists = skuRepository.selectByCondition(Condition.builder(Sku.class)
                        .andWhere(Sqls.custom().andIn(Sku.FIELD_ID, detailSkuIds, true)).build());
                List<Long> spuIds = skuDetailLists.stream().map(Sku::getSpuId).collect(Collectors.toList());
                spuDetailLists = spuRepository.selectByCondition(Condition.builder(Spu.class)
                        .andWhere(Sqls.custom().andIn(Spu.ID, spuIds, true)).build());
            }
            //组装返回数据
            List<Sku> finalSkuDetailLists = skuDetailLists;
            List<Spu> finalSpuDetailLists = spuDetailLists;
            skuList.forEach(o -> {
                GoodsSkuDetailVO skuDetailVO = new GoodsSkuDetailVO();
                //复制id 价格等信息
                BeanUtils.copyProperties(o, skuDetailVO);
                //找到与sku匹配的spu
                Spu tempSpu = spuList.stream().filter(xx -> xx.getId().longValue() == o.getSpuId().longValue()).findFirst().orElse(null);
                if (ObjectUtil.isNotNull(tempSpu)) {
                    //设置sku属性字段
                    skuDetailVO.setMaterialId(tempSpu.getMaterialId());
                    skuDetailVO.setSpuName(tempSpu.getName());
                    skuDetailVO.setSpuCode(tempSpu.getSpuCode());
                    if (ObjectUtil.isNotNull(tempSpu.getRemark())) {
                        skuDetailVO.setGoodsRemark(tempSpu.getRemark());
                    }
                    String categoryName = categoryRepository.getCategoryNameById(tempSpu.getCategoryId());
                    skuDetailVO.setCategoryName(categoryName);
                    //匹配素材
                    if (ObjectUtil.isNotNull(tempSpu.getMaterialId())) {
                        skuDetailVO.setMaterialId(tempSpu.getMaterialId());
                        Material tempMaterial = materialList.stream().filter(xx -> xx.getId().longValue() == skuDetailVO.getMaterialId().longValue()).findFirst().orElse(null);
                        if (ObjectUtil.isNotNull(tempMaterial)) {
                            skuDetailVO.setMaterialMainImages(Arrays.asList(Optional.ofNullable(tempMaterial.getMainImage()).orElse("").split(",")));
                        }
                    }
                    //匹配属性
                    if (CollUtil.isNotEmpty(skuAttrValueRelationList)) {

                        List<SkuAttributeValue> skuAttributeValues = skuAttrValueRelationList.stream().filter(f->f.getSkuId().equals(o.getId())).collect(Collectors.toList());

                        List<SkuAttributeValue> originalAttributeList = skuAttributeValues.stream().filter(SkuAttributeValue::getIsSku).collect(Collectors.toList());
                        skuDetailVO.setSkuAttributeList(originalAttributeList);

                        List<SkuAttributeValue> attachedAttributeList = skuAttributeValues.stream().filter(f -> !f.getIsSku()).collect(Collectors.toList());
                        skuDetailVO.setOtherAttributeList(attachedAttributeList);
                    }

                    if (SET_FLAG_TRUE.equals(o.getSetFlag())){
                        //遍历组装套餐sku明细
                        List<SkuPackageVO> skuDetailList = skuPackageList.stream().map(skuPackage->{
                            Sku skuDetail = finalSkuDetailLists.stream().filter(f->f.getId().equals(skuPackage.getPackageSkuId())).findAny().orElse(null);
                            SkuPackageVO packageVO = new SkuPackageVO();
                            if (ObjectUtil.isNotNull(skuDetail)){
                                packageVO.setId(skuPackage.getId());
                                packageVO.setSkuCode(skuDetail.getSkuCode());
                                packageVO.setSkuName(skuDetail.getSkuName());
                                packageVO.setCostPrice(skuDetail.getCostPrice());
                                packageVO.setSalesPrice(skuDetail.getSalesPrice());
                                packageVO.setNum(skuPackage.getNum());
                                Spu spuDetail = finalSpuDetailLists.stream().filter(f->f.getId().equals(skuDetail.getSpuId())).findAny().orElse(null);
                                if (ObjectUtil.isNotNull(spuDetail)){
                                    packageVO.setSpuName(spuDetail.getName());
                                    packageVO.setSpuCode(spuDetail.getSpuCode());
                                }
                            }
                            return packageVO;
                        }).collect(Collectors.toList());
                        skuDetailVO.setSkuDetailList(skuDetailList);
                    }

                }
                goodsSkuPageVos.add(skuDetailVO);
            });
        }
        return goodsSkuPageVos;
    }


    @Override
    public List<RpcGoodsResVO> queryRPCGoodsList(List<String> skuCodeList) {
        return skuRepository.queryRPCGoodsList(skuCodeList);
    }

    @Override
    public List<RpcOrderResVO> getMealsGoodsList(List<String> skuCodeList) {
        skuCodeList = skuCodeList.stream().distinct().collect(Collectors.toList());
        List<RpcOrderResVO> rpcOrderResVOList = Lists.newArrayList();
        List<Sku> mainSkuList = skuRepository.getSkuListBySkuCode(skuCodeList);
        List<Long> mainIdList = mainSkuList.parallelStream().map(Sku::getId).collect(Collectors.toList());
        mainIdList.forEach(mainId->{
            List<SkuPackage> skuPackages = skuPackageRepository.querySkuPackageByMainId(mainId);
            List<Sku> sonSkuList = skuRepository.getSkuListBySkuId(skuPackages.parallelStream().map(SkuPackage::getPackageSkuId).collect(Collectors.toList()));
            List<String> sonSkuCodeList = sonSkuList.parallelStream().map(Sku::getSkuCode).collect(Collectors.toList());
            List<RpcOrderResVO> rpcORderResVOS = this.queryOrderRpcList(sonSkuCodeList);
            Map<Long, RpcOrderResVO> skuIdRpcGoodsResVoMap = rpcORderResVOS.stream().collect(Collectors.toMap(RpcOrderResVO::getSkuId, Function.identity(), (o1, o2) -> o1));
            rpcOrderResVOList.addAll(skuPackages.stream().map(skuPackage -> {
                RpcOrderResVO rpcOrderResVO = skuIdRpcGoodsResVoMap.get(skuPackage.getPackageSkuId());
                rpcOrderResVO.setNum(skuPackage.getNum());
                rpcOrderResVO.setMainSkuCode(skuRepository.selectByPrimaryKey(skuPackage.getSkuId()).getSkuCode());
                return rpcOrderResVO;
            }).collect(Collectors.toList()));
        });
        return rpcOrderResVOList;
    }

    @Override
    public List<RpcOrderResVO> queryOrderRpcList(List<String> skuCodeList) {
        if(ObjectUtil.isEmpty(skuCodeList)){
            return new ArrayList<>();
        }
        //查询sku 与spu的基础信息
        List<RpcOrderResVO>  skuGoodsList =  skuRepository.queryOrderRpcList(skuCodeList);
        skuGoodsList.forEach(skuGood->{
            //把品牌id转换为品牌数据
            if (ObjectUtil.isNotEmpty(skuGood.getBrandInfo())){
                Brand brand = brandRepository.selectByPrimaryKey(Long.valueOf(skuGood.getBrandInfo()));
                skuGood.setBrandInfo(ObjectUtil.isNull(brand)?"":JSON.toJSONString(brand));
            }else {
                skuGood.setBrandInfo("");
            }
            //把素材id 转换为素材数据
            if (ObjectUtil.isNotEmpty(skuGood.getMaterialInfo())){
                Material material = materialRepository.selectByPrimaryKey(Long.valueOf(skuGood.getMaterialInfo()));
                skuGood.setMaterialInfo(ObjectUtil.isNull(material)?"":JSON.toJSONString(material));
            }else {
                skuGood.setMaterialInfo("");
            }
            //根据分类id 转化为分类数据
            if (ObjectUtil.isNotEmpty(skuGood.getCategoryInfo())){
                Category category = categoryRepository.selectByPrimaryKey(Long.valueOf(skuGood.getCategoryInfo()));
                skuGood.setCategoryInfo(ObjectUtil.isNull(category)?"":JSON.toJSONString(category));
            }else {
                skuGood.setCategoryInfo("");
            }
            //根据skuId 查询属性列表
            List<SkuAttributeValue> skuAttributeValues = skuAttributeValueRepository.queryAllBySkuId(skuGood.getSkuId());
            if (ObjectUtil.isNotEmpty(skuAttributeValues)){
                skuGood.setAttributeInfo(JSON.toJSONString(skuAttributeValues));
            }else {
                skuGood.setAttributeInfo("");
            }

        });
        return skuGoodsList;
    }





    @Override
    @ProcessCacheValue
    public SkuAuditVO getAuditDetail(SkuAuditDTO dto) {
        SkuAuditVO vo = new SkuAuditVO();
        Long id = dto.getId();
        GoodsSkuAuditVO thisSkuVO = new GoodsSkuAuditVO();
        Sku sku = skuRepository.selectByPrimaryKey(id);
        if (ObjectUtils.isEmpty(sku)) {
            throw new CommonException("SKU未找到");
        }
        // 获取spu信息
        Long spuId = sku.getSpuId();
        Spu spu = spuRepository.selectByPrimaryKey(spuId);
        // 设置spu信息
        thisSkuVO.setSpuName(spu.getName());
        thisSkuVO.setSpuCode(spu.getSpuCode());

        //获取分类名
        Long categoryId = spu.getCategoryId();
        String categoryName = categoryRepository.getCategoryNameById(categoryId);
        thisSkuVO.setCategoryName(categoryName);

        // 获取品牌信息
        Long brandId = spu.getBrandId();
        if(ObjectUtil.isNotNull(brandId)){
            Brand brand = brandRepository.selectByPrimaryKey(brandId);
            thisSkuVO.setBrandName(brand.getName());
        }


        // 获取素材信息
        Long materialId = spu.getMaterialId();
        Material material = materialRepository.selectByPrimaryKey(materialId);
        thisSkuVO.setMaterialName(material.getMaterialName());
        thisSkuVO.setMainImage(material.getMainImage());
        thisSkuVO.setDetailHtml(material.getDetailHtml());
        thisSkuVO.setDetailImage(material.getDetailImage());

        //参数转换
        BeanUtils.copyProperties(sku, thisSkuVO);
        GoodsSkuAuditPriceVO priceVO = new GoodsSkuAuditPriceVO();
        BeanUtils.copyProperties(sku, priceVO);
        thisSkuVO.setPriceVO(priceVO);
        List<SkuAttributeValue> skuAttributeValues = skuAttributeValueRepository.queryAllBySkuId(id);
        if (!ObjectUtils.isEmpty(skuAttributeValues)) {
            List<SkuAttributeValue> skuAttributeValueTrueList = skuAttributeValues.stream()
                    .filter(SkuAttributeValue::getIsSku).collect(Collectors.toList());

            List<AttributeDTO> skuAttributeList = doCopyData(skuAttributeValueTrueList);

            List<SkuAttributeValue> skuAttributeValueFalseList = skuAttributeValues.stream()
                    .filter(model -> !model.getIsSku()).collect(Collectors.toList());

            List<AttributeDTO> otherAttributeList = doCopyData(skuAttributeValueFalseList);

            thisSkuVO.setSkuAttributeList(skuAttributeList);
            thisSkuVO.setOtherAttributeList(otherAttributeList);

        }
        vo.setThisSkuVO(thisSkuVO);
        Integer status = sku.getStatus();

        GoodsSkuAuditVO draftSkuVO = new GoodsSkuAuditVO();
        //套餐sku明细
        if (SET_FLAG_TRUE.equals(sku.getSetFlag())){
            List<SkuPackage> skuPackageList = skuPackageRepository.selectByCondition(Condition.builder(SkuPackage.class)
                    .andWhere(Sqls.custom().andEqualTo(SkuPackage.FIELD_SKU_ID,sku.getId())).build());
            if(ObjectUtil.isNotEmpty(skuPackageList)){
                List<Long> skuIds = skuPackageList.stream().map(SkuPackage::getPackageSkuId).collect(Collectors.toList());
                List<Sku> skuList = skuRepository.selectByCondition(Condition.builder(Sku.class)
                        .andWhere(Sqls.custom().andIn(FIELD_ID,skuIds)).build());
                List<Long> spuIds = skuList.stream().map(Sku::getSpuId).collect(Collectors.toList());
                List<Spu> spuList = spuRepository.selectByCondition(Condition.builder(Sku.class)
                        .andWhere(Sqls.custom().andIn(FIELD_ID,spuIds)).build());
                List<SkuPackageVO> skuDetailList = skuPackageList.stream().map(skuPackage->{
                    Sku skuDetail = skuList.stream().filter(f->f.getId().equals(skuPackage.getPackageSkuId())).findAny().orElse(null);
                    SkuPackageVO packageVO = new SkuPackageVO();
                    if (ObjectUtil.isNotNull(skuDetail)){
                        packageVO.setId(skuPackage.getId());
                        packageVO.setSkuCode(skuDetail.getSkuCode());
                        packageVO.setSkuName(skuDetail.getSkuName());
                        packageVO.setCostPrice(skuDetail.getCostPrice());
                        packageVO.setSalesPrice(skuDetail.getSalesPrice());
                        packageVO.setNum(skuPackage.getNum());
                        Spu spuDetail =spuList.stream().filter(f->f.getId().equals(skuDetail.getSpuId())).findAny().orElse(null);
                        if (ObjectUtil.isNotNull(spuDetail)){
                            packageVO.setSpuName(spuDetail.getName());
                            packageVO.setSpuCode(spuDetail.getSpuCode());
                        }
                    }
                    return packageVO;
                }).collect(Collectors.toList());
                thisSkuVO.setSkuDetailList(skuDetailList);
                draftSkuVO.setSkuDetailList(skuDetailList);
            }
        }


        if (status < STATUS_REVISING) {
            // 新增审核直接返回
            return vo;
        }


        //参数转换
        BeanUtils.copyProperties(thisSkuVO, draftSkuVO);
        SkuDraft skuDraft = skuDraftRepository.selectByPrimaryKey(id);
        // 副本不为空时对副本进行赋值
        if (!ObjectUtils.isEmpty(skuDraft)) {
            //参数转换
            BeanUtils.copyProperties(skuDraft, draftSkuVO);
            GoodsSkuAuditPriceVO priceTwoVO = new GoodsSkuAuditPriceVO();
            BeanUtils.copyProperties(skuDraft, priceTwoVO);
            draftSkuVO.setPriceVO(priceTwoVO);
            List<SkuAttributeValueDraft> skuAttributeValueDrafts = skuAttributeValueDraftRepository.queryAllBySkuId(id);
            if (!ObjectUtils.isEmpty(skuAttributeValueDrafts)) {
                List<SkuAttributeValueDraft> skuAttributeValueDraftTrueList = skuAttributeValueDrafts.stream()
                        .filter(SkuAttributeValueDraft::getIsSku).collect(Collectors.toList());

                List<AttributeDTO> skuAttributeListTwo = doCopyData(skuAttributeValueDraftTrueList);

                List<SkuAttributeValueDraft> skuAttributeValueDraftFalseList = skuAttributeValueDrafts.stream()
                        .filter(model -> !model.getIsSku()).collect(Collectors.toList());

                List<AttributeDTO> otherAttributeListTwo = doCopyData(skuAttributeValueDraftFalseList);

                draftSkuVO.setSkuAttributeList(skuAttributeListTwo);
                draftSkuVO.setOtherAttributeList(otherAttributeListTwo);
            }
        }

        vo.setDraftSkuVO(draftSkuVO);
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean skuAudit(AuditDTO dto) {
        Long id = dto.getId();
        Sku sku = skuRepository.selectByPrimaryKey(id);
        if (ObjectUtils.isEmpty(sku)) {
            throw new CommonException("未找到对应的SKU");
        }
        // 审核意见
        String auditIdea = dto.getAuditIdea();
        sku.setAuditIdea(auditIdea);
        sku.setAuditCommitDate(new Date());
        Integer status = sku.getStatus();
        if (STATUS_UNDER_REVIEW.equals(status) || STATUS_REVISING.equals(status)) {
            List<SkuAttributeValue> skuAttributeValues = new ArrayList<>();
            if (Boolean.TRUE.equals(dto.getFlag())) {
                // 审核通过
                if (STATUS_REVISING.equals(status)) {
                    SkuDraft skuDraft = skuDraftRepository.selectByPrimaryKey(id);
                    if (!ObjectUtils.isEmpty(skuDraft)) {
                        //参数转换
                        BeanUtils.copyProperties(skuDraft, sku);
                        // 删除副本数据
                        skuDraftRepository.deleteByPrimaryKey(skuDraft);
                    }
                    // 获取副本属性数据
                    List<SkuAttributeValueDraft> skuAttributeValueDrafts = skuAttributeValueDraftRepository.queryAllBySkuId(id);
                    skuAttributeValueDrafts.forEach(model -> {
                        SkuAttributeValue skuAttributeValue = new SkuAttributeValue();
                        //参数转换
                        BeanUtils.copyProperties(model, skuAttributeValue);
                        skuAttributeValues.add(skuAttributeValue);
                    });
                    if (!skuAttributeValues.isEmpty()) {
                        // 删除属性副本数据
                        skuAttributeValueDraftRepository.batchDeleteByPrimaryKey(skuAttributeValueDrafts);
                    }
                }
                // 设置已生效
                sku.setStatus(STATUS_TAKE_EFFECT);
            } else {
                if (STATUS_UNDER_REVIEW.equals(status)) {
                    // 设置已驳回
                    sku.setStatus(STATUS_REJECTED);
                } else {
                    // 设置已生效(已驳回)
                    sku.setStatus(STATUS_TAKE_EFFECT_REJECTED);
                }
            }
            skuRepository.updateByPrimaryKeySelective(sku);
            skuEsService.syncSkuListEsSage(Collections.singletonList(sku));
            if (!skuAttributeValues.isEmpty()) {
                // 删除当前属性跟值
                skuAttributeValueRepository.deleteBySkuId(id);
                // 将副本属性赋值给当前属性
                skuAttributeValueRepository.batchInsertSelective(skuAttributeValues);
            }
        } else {
            throw new CommonException("SKU状态不正常，无法操作");
        }
        return true;
    }

    /**
     * 执行参数转换
     *
     * @author lixiaoyang
     * @date 2021/12/30 14:23
     */
    private <T> List<AttributeDTO> doCopyData(List<T> dataList) {
        Stream<AttributeDTO> stream = dataList.stream().map(model -> {
            AttributeDTO attributeDTO = new AttributeDTO();
            //参数转换
            BeanUtils.copyProperties(model, attributeDTO);
            return attributeDTO;
        });
        return stream.collect(Collectors.toList());
    }
}
