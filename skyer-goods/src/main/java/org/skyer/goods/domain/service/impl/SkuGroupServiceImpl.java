package org.skyer.goods.domain.service.impl;


import cn.hutool.core.util.ObjectUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.skyer.boot.platform.code.builder.CodeRuleBuilder;
import org.skyer.boot.platform.code.constant.CodeConstants;
import org.skyer.core.domain.Page;
import org.skyer.core.oauth.CustomUserDetails;
import org.skyer.core.oauth.DetailsHelper;
import org.skyer.goods.api.dto.GoodsGroupReqDTO;
import org.skyer.goods.api.dto.GroupPageReqDTO;
import org.skyer.goods.api.dto.IdsReqDTO;
import org.skyer.goods.domain.entity.*;
import org.skyer.goods.domain.repository.*;
import org.skyer.goods.domain.service.SkuGroupService;
import org.skyer.goods.domain.service.SpuService;
import org.skyer.goods.domain.vo.*;
import org.skyer.goods.infra.constant.ObjectConstant;
import org.skyer.goods.infra.convertor.SkuGroupConvertor;
import org.skyer.goods.infra.feign.OnlineShopRemoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Sku组合表应用服务默认实现
 *
 * @author wushaochuan 2021-08-13 09:55:04
 */
@Service
public class SkuGroupServiceImpl implements SkuGroupService {
    @Autowired
    private SkuGroupConvertor skuGroupConvertor;
    @Autowired
    private SkuRepository skuRepository;
    @Autowired
    private SkuGroupRepository skuGroupRepository;
    @Autowired
    private SkuGroupStoreRepository skuGroupStoreRepository;
    @Autowired
    private SkuGroupSkuRepository skuGroupSkuRepository;
    @Autowired
    private SpuService spuService;
    @Autowired
    private OnlineShopRemoteService onlineShopRemoteService;
    @Autowired
    private SpuRepository spuRepository;
    @Autowired
    private CodeRuleBuilder codeRuleBuilder;
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void createOrUpdateSkuGroup(GoodsGroupReqDTO goodsGroupReqDTO) {
        // 校验名称是否重复
        CustomUserDetails userDetails = DetailsHelper.getUserDetails();

        SkuGroup skuGroup = skuGroupConvertor.dtoTOSkuGroup(goodsGroupReqDTO);
        skuGroup.setTenantId(userDetails.getTenantId());
        skuGroup.setSkuGroupCode(Objects.isNull(skuGroup.getSkuGroupCode()) ? codeRuleBuilder.generateCode(CodeConstants.Level.TENANT, 0L, ObjectConstant.CodeRule.SKU_CODE, CodeConstants.CodeRuleLevelCode.CUSTOM, ObjectConstant.LevelValue.SKU_LEVEL, null) :skuGroup.getSkuGroupCode());
        //判读是更新还是创建
        if (Objects.isNull(goodsGroupReqDTO.getId())){
            skuGroupRepository.insert(skuGroup);
        }
        if (!Objects.isNull(goodsGroupReqDTO.getId())){
            skuGroupRepository.updateByPrimaryKeySelective(skuGroup);
        }
        if (!goodsGroupReqDTO.getIsAllStore()){
            skuGroupStoreRepository.updateSkuGroupStore(goodsGroupReqDTO.getStoreIdList(),skuGroup.getId(),userDetails.getTenantId());
        }
        if (ObjectUtil.isNotNull(goodsGroupReqDTO.getGoodsGroupSkuReqDTOS())){
            skuGroupSkuRepository.createGroupSku(goodsGroupReqDTO.getGoodsGroupSkuReqDTOS(),skuGroup.getSpuId(),skuGroup.getId(),userDetails.getTenantId());
        }
    }

    @Override
    public SkuGroupResVO skuGroupDetail(Long groupId) {
        SkuGroup skuGroup = skuGroupRepository.selectByPrimaryKey(groupId);
        SkuGroupResVO skuGroupResVO = new SkuGroupResVO();
        GoodsDetailResVO goodsDetailResVO = spuService.queryDetail(skuGroup.getSpuId());
        if (goodsDetailResVO != null) {
            skuGroupResVO.setId(groupId);
            skuGroupResVO.setSpuId(goodsDetailResVO.getId());
            skuGroupResVO.setCategoryName(goodsDetailResVO.getCategoryName());
            skuGroupResVO.setRemark(skuGroup.getRemark());
            skuGroupResVO.setIsAllStore(skuGroup.getIsAllStore());
            skuGroupResVO.setSkuGroupCode(skuGroup.getSkuGroupCode());
            skuGroupResVO.setSpuCode(goodsDetailResVO.getCode());
            skuGroupResVO.setSpuName(goodsDetailResVO.getName());
        }
        //查询门店
        List<SkuGroupStore> skuGroupStores = skuGroupStoreRepository.querySkuGroupStoreList(groupId);
        if (CollectionUtils.isNotEmpty(skuGroupStores)) {
            skuGroupResVO.setGroupStoreResVOList(packStore(skuGroupStores));
        }
        //查询商品
        List<SkuGroupSku> skuGroupSkus = skuGroupSkuRepository.queryList(groupId);
        if (CollectionUtils.isNotEmpty(skuGroupSkus)) {
            skuGroupResVO.setSkuGroupSkuResVOList(skuGroupSkus.stream().map(skuGroupSku -> {
                SkuGroupSkuResVO skuGroupSkuResVO = new SkuGroupSkuResVO();
                Long skuId = skuGroupSku.getSkuId();
                Sku sku = skuRepository.selectByPrimaryKey(skuId);
                GoodsDetailResVO skuSpuVO = spuService.queryDetail(sku.getSpuId());
                skuGroupSkuResVO.setSkuId(sku.getId());
                skuGroupSkuResVO.setSkuCode(sku.getSkuCode());
                skuGroupSkuResVO.setNum(skuGroupSku.getNum());
                skuGroupSkuResVO.setSpuCode(skuSpuVO.getCode());
                skuGroupSkuResVO.setSpuName(skuSpuVO.getName());
                skuGroupSkuResVO.setPrice(sku.getSalesPrice());
                skuGroupSkuResVO.setCategoryName(skuSpuVO.getCategoryName());
                return skuGroupSkuResVO;
            }).collect(Collectors.toList()));

        }
        return skuGroupResVO;
    }

    @Override
    public Page<GroupPageResVO> pageGroup(GroupPageReqDTO groupPageReqDTO) {
        CustomUserDetails userDetails = DetailsHelper.getUserDetails();
        Page<GroupPageResVO> groupPageResVOS = skuGroupRepository.pageGroup(groupPageReqDTO, userDetails.getTenantId());
        List<GroupPageResVO> groupPageResVOSContent = groupPageResVOS.getContent();
        if (CollectionUtils.isNotEmpty(groupPageResVOSContent)) {
            groupPageResVOSContent.forEach(x->{
                Spu spu = spuService.selectByPrimaryKey(x.getSpuId());
//                x.setCategoryName(categoryOldService.getCategoryNameByListId(spu.getCategoryId()));
                //统计数量
                x.setGoodsNum(skuGroupSkuRepository.totalSkuNum(x.getGroupId()));
                List<SkuGroupStore> skuGroupStores = skuGroupStoreRepository.querySkuGroupStoreList(x.getGroupId());
                //先这样写后续响应速度慢在优化
                x.setStoreList(packStore(skuGroupStores));
            });
        }
        groupPageResVOS.setContent(groupPageResVOSContent);
        return groupPageResVOS;
    }

    @Override
    public void delGroup(IdsReqDTO idsReqDTO) {
        skuGroupRepository.delGroup(idsReqDTO);
    }

    private  List<GroupStoreResVO> packStore(List<SkuGroupStore> skuGroupStores) {
        List<GroupStoreResVO> groupStoreResVOS = skuGroupStores.stream().map(skuGroupStore -> {
            GroupStoreResVO groupStoreResVO = new GroupStoreResVO();
            ResponseEntity<OnlineShopVO> listResponseEntity = onlineShopRemoteService.showDetails(skuGroupStore.getStoreId());
            OnlineShopVO body = listResponseEntity.getBody();
            if (body != null) {
                groupStoreResVO.setCode(body.getCode());
                groupStoreResVO.setName(body.getName());
                groupStoreResVO.setChannelTypeMeaning(body.getChannelTypeCode());
                groupStoreResVO.setOnlineStoreTypeMeaning(body.getOnlineStoreTypeCode());
            }
            return groupStoreResVO;
        }).collect(Collectors.toList());
        return groupStoreResVOS;
    }
}
