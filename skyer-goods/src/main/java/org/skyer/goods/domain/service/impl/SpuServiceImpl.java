package org.skyer.goods.domain.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.skyer.core.domain.Page;
import org.skyer.core.exception.CommonException;
import org.skyer.core.oauth.CustomUserDetails;
import org.skyer.core.oauth.DetailsHelper;
import org.skyer.goods.api.dto.*;
import org.skyer.goods.app.service.SpuEsService;
import org.skyer.goods.domain.entity.*;
import org.skyer.goods.domain.repository.*;
import org.skyer.goods.domain.service.SpuService;
import org.skyer.goods.domain.vo.GoodsDetailResVO;
import org.skyer.goods.domain.vo.GoodsDetailsVO;
import org.skyer.goods.domain.vo.GoodsPageResVO;
import org.skyer.goods.infra.constant.ObjectConstant;
import org.skyer.goods.infra.convertor.GoodsConvertor;
import org.skyer.mybatis.base.impl.BaseRepositoryImpl;
import org.skyer.mybatis.domian.Condition;
import org.skyer.mybatis.util.Sqls;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 商品表服务实现类
 *
 * @author chenzz
 * @date 2021-06-25 13:36:41
 */
@Service
@Slf4j
public class SpuServiceImpl extends  BaseRepositoryImpl<Spu> implements SpuService {
    @Autowired
    private GoodsConvertor goodsConvertor;
    @Autowired
    private SpuRepository spuRepository;
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private MaterialRepository materialRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private SpuAttrValueRelationRepository spuAttrValueRelationRepository;
    @Autowired
    private SpuAttributeValueRepository spuAttributeValueRepository;
    @Autowired
    private SpuAttributeRepository spuAttributeRepository;
    @Autowired
    private SkuRepository skuRepository;
    @Autowired
    private SpuEsService spuEsService;

    @Override
    public Page<GoodsPageResVO> pageGoods(GoodsPageReqDTO goodsPageReqDTO) {
        log.info("Spu查询mysql");
        if (CollUtil.isNotEmpty(goodsPageReqDTO.getCategoryIdList())) {
            goodsPageReqDTO.setCategoryIdList(categoryRepository.getLaterCategoryId(goodsPageReqDTO.getCategoryIdList()));
        }

        Page<GoodsPageResVO> goodsPage = spuRepository.pageGoods(goodsPageReqDTO);
        List<GoodsPageResVO> goodsPageResVOList = goodsPage.getContent();
        setBrandCategory(goodsPageResVOList);
        return goodsPage;
    }

    private void setBrandCategory(List<GoodsPageResVO> goodsPageResVOList) {
        Map<Long,String> categoryMap = Maps.newHashMap();
        goodsPageResVOList.forEach(x->{
            String categoryName = categoryMap.get(x.getCategoryId());
            if (StringUtils.isBlank(categoryName)){
                categoryName = categoryRepository.getCategoryNameById(x.getCategoryId());
                categoryMap.put(x.getCategoryId(),categoryName);
            }
            x.setCategoryName(categoryName);
            Material material = materialRepository.selectByPrimaryKey(x.getMaterialId());
            if (material !=null) {
                x.setMainImage(Arrays.asList(Optional.ofNullable(material.getMainImage()).orElse("").split(",")));
                x.setMaterialName(material.getMaterialName());
            }
            Brand brand = brandRepository.selectByPrimaryKey(x.getBrandId());
            if (brand != null){
                x.setBrandName(brand.getName());
            }
        });
    }

    @Override
    public List<GoodsPageResVO> queryGoodsBySpuList(List<String> spuList) {
        List<GoodsPageResVO> goodsPageResVOS = spuRepository.queryGoodsList(spuList);
        setBrandCategory(goodsPageResVOS);
        return goodsPageResVOS;
    }


    private String getCategoryName(Long categoryId){
//        List<LevelCategoryDTO> categoryByLastId = categoryOldService.getCategoryByLastId(categoryId);
//        if (CollectionUtil.isNotEmpty(categoryByLastId)){
//            return categoryByLastId.stream().sorted(Comparator.comparing(LevelCategoryDTO::getLevel).reversed()).map(LevelCategoryDTO::getName).collect(Collectors.joining("/"));
//        }
        return "";
    }




    @Override
    public void saveOrUpdateGoods(GoodsReqDTO goodsReqDTO) {
        //设置租户id
        CustomUserDetails userDetails = DetailsHelper.getUserDetails();
        //查询名称是否重复
        Spu oldSpu = spuRepository.queryGoodsByName(goodsReqDTO.getName(),goodsReqDTO.getId(), userDetails.getTenantId());
        if (!Objects.isNull(oldSpu)) {
            throw new CommonException("存在相同名称的商品");
        }
        Spu spu = goodsConvertor.reqToGoods(goodsReqDTO);
//        spu.setTenantId(userDetails.getTenantId());
        if (Objects.isNull(goodsReqDTO.getId())){
//            spu.setSpuCode(Objects.isNull(spu.getSpuCode()) ? codeRuleBuilder.generateCode(CodeConstants.Level.TENANT, 0L, ObjectConstant.CodeRule.GOODS_CODE, CodeConstants.CodeRuleLevelCode.CUSTOM, ObjectConstant.LevelValue.GOODS_LEVEL, null): spu.getSpuCode());
            spuRepository.insert(spu);
        }else {
            spuRepository.updateByPrimaryKeySelective(spu);
        }

    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteByIds(List<Long> ids) {
        //删除前需要判断商品是否禁用
        List<Spu> spuList = ids.stream().map(x->{
            Spu oldSpu = spuRepository.selectByPrimaryKey(x);
            if (!Objects.isNull(oldSpu) && ObjectConstant.Status.OPEN.equals(oldSpu.getStatus())) {
                throw new CommonException("商品"+ oldSpu.getName()+"需要禁用才能删除");
            }
            Sku sku = new Sku();
            sku.setSpuId(x);
            if(skuRepository.selectCount(sku)>0){
                throw new CommonException("商品"+ oldSpu.getName()+"已生成sku无法删除");
            }
            Spu spu = new Spu();
            spu.setId(x);
            spu.setDeleteFlag(true);
            return spu;
        }).collect(Collectors.toList());
        //删除
        spuRepository.batchUpdateByPrimaryKeySelective(spuList);
        SpuListDTO spuListDTO = new SpuListDTO();
        spuListDTO.setSpuList(getSpuList(ids));
        spuListDTO.setDeleteFlag(true);
        //同步es
        spuEsService.syncSpuEsSage(spuListDTO);

//        spuRepository.batchUpdateByPrimaryKeySelective(spuList);
//        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
//            @Override
//            public void afterCommit() {
//                List<Spu> spuList = getSpuList(ids);
//                spuList.forEach(x->{
//                    spuEsService.createOrUpdateSpuEs(x);
//                });
//            }
//        });
    }

    @Override
    public GoodsDetailResVO queryDetail(Long id) {
        Spu spu = spuRepository.selectByPrimaryKey(id);
        GoodsDetailResVO goodsDetailResVO = goodsConvertor.goodsToRes(spu);
        // 查询分类 需要用/来拼接多级分类
        String categoryName = getCategoryName(spu.getCategoryId());
        goodsDetailResVO.setCategoryName(categoryName);
        //查询品牌名称
        Long brandId = spu.getBrandId();
        Brand brand = brandRepository.selectByPrimaryKey(brandId);
        if (brand !=null) {
            goodsDetailResVO.setBrandName(brand.getName());
        }
        //拼接素材信息
        Material material = materialRepository.selectByPrimaryKey(spu.getMaterialId());
        if (material !=null){
            goodsDetailResVO.setMaterialName(material.getMaterialName());
        }
        goodsDetailResVO.setMainImage(Arrays.asList(Optional.ofNullable(material.getMainImage()).orElse("").split(",")));
        String detailImage = material.getDetailImage();
        if (StringUtils.isNotBlank(detailImage)){
            goodsDetailResVO.setImageList(Arrays.asList(detailImage.split(",")));
        }
        goodsDetailResVO.setDetailHtml(goodsDetailResVO.getDetailHtml());
        return goodsDetailResVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateGoodsStatus(List<Long> ids, Integer status) {
        List<Spu> spuList = Lists.newArrayList();
        for (Long id : ids) {
            Spu spu = new Spu();
            spu.setId(id);
            spu.setStatus(status);
            spuList.add(spu);

        }
        //批量更新数据库状态
        spuRepository.batchUpdateByPrimaryKeySelective(spuList);
//        //同步到es
//        spuEsService.syncSpuEsSage(new SpuListDTO(getSpuList(ids)));

    }

    private List<Spu> getSpuList(List<Long> ids) {
        String s1 = ids.stream().
                reduce(new StringBuilder(), (sb, s) -> sb.append(s).append(','), StringBuilder::append).toString();
        s1 = s1.substring(0,s1.length()-1);
        return spuRepository.selectByIds(s1);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertOrUpdateGoods(GoodsRequestDTO goodsRequestDTO) {
        //查询名称是否重复
        Spu oldSpu = spuRepository.queryGoodsByNameNew(goodsRequestDTO.getName(),goodsRequestDTO.getId());
        if (!Objects.isNull(oldSpu)) {
            throw new CommonException("存在相同名称的商品");
        }
        Spu spuEntity = new Spu();
        BeanUtils.copyProperties(goodsRequestDTO,spuEntity);

        try {
            if (Objects.isNull(goodsRequestDTO.getId())) {
                int insert = spuRepository.insert(spuEntity);
                // 插入属性
                if (insert > 0) {
                    spuAttributeRepository.saveAttr(goodsRequestDTO.getAttrs(), spuEntity.getId());
                }
            } else {
                // 删除原属性
                deleteAttr(goodsRequestDTO);
                // 删除原属性值
                deleteAttrValues(goodsRequestDTO);
                spuAttributeRepository.saveAttr(goodsRequestDTO.getAttrs(), spuEntity.getId());
                spuRepository.updateByPrimaryKeySelective(spuEntity);
            }
        }catch (DuplicateKeyException e){
            // 捕获编码唯一性异常，修改提示语
            throw new CommonException("存在相同编码的商品", e);
        }

        // 同步到es
        SpuListDTO spuListDTO = new SpuListDTO(spuEntity);
        spuListDTO.setDeleteFlag(false);
        spuEsService.syncSpuEsSage(spuListDTO);
    }

    private void deleteAttrValues(GoodsRequestDTO goodsRequestDTO) {
        SpuAttributeValue spuAttributeValue = new SpuAttributeValue();
        spuAttributeValue.setSpuId(goodsRequestDTO.getId());
//        spuAttributeValue.setDeleteFlag(ObjectConstant.DeleteFlag.NO);
        List<SpuAttributeValue> attrsValues = spuAttributeValueRepository.select(spuAttributeValue);
//        if (CollUtil.isNotEmpty(attrsValues)){
//            List<SpuAttributeValue> vas = attrsValues.stream().peek(a -> a.setDeleteFlag(ObjectConstant.DeleteFlag.YES)).collect(Collectors.toList());
//            spuAttributeValueRepository.batchUpdateByPrimaryKey(vas);
//        }
        spuAttributeValueRepository.batchDelete(attrsValues);
    }

    private void deleteAttr(GoodsRequestDTO goodsRequestDTO) {
        SpuAttribute spuAttribute = new SpuAttribute();
        spuAttribute.setSpuId(goodsRequestDTO.getId());
//        spuAttribute.setDeleteFlag(ObjectConstant.DeleteFlag.NO);
        List<SpuAttribute> attrs = spuAttributeRepository.select(spuAttribute);
//        if (CollUtil.isNotEmpty(attrs)){
//            List<SpuAttribute> collect = attrs.stream().peek(a -> a.setDeleteFlag(ObjectConstant.DeleteFlag.YES)).collect(Collectors.toList());
//            spuAttributeRepository.batchUpdateByPrimaryKey(collect);
//        }
        spuAttributeRepository.batchDelete(attrs);
    }


    @Override
    public GoodsDetailsVO getDetailsById(Long id) {
        Spu spu = spuRepository.selectByPrimaryKey(id);
        GoodsDetailsVO goodsDetailsVO = new GoodsDetailsVO();
        BeanUtils.copyProperties(spu,goodsDetailsVO);
        // 查询分类 需要用/来拼接多级分类
        goodsDetailsVO.setCategoryName(categoryRepository.getCategoryNameById(spu.getCategoryId()));
        //查询品牌名称
        Long brandId = spu.getBrandId();
        Brand brand = brandRepository.selectByPrimaryKey(brandId);
        if (brand !=null) {
            goodsDetailsVO.setBrandName(brand.getName());
        }
        //拼接素材信息
        Material material = materialRepository.selectByPrimaryKey(spu.getMaterialId());
        if (material !=null){
            goodsDetailsVO.setMaterialName(material.getMaterialName());
        }
        goodsDetailsVO.setMainImage(Arrays.asList(Optional.ofNullable(material.getMainImage()).orElse("").split(",")));
        String detailImage = material.getDetailImage();
        if (StringUtils.isNotBlank(detailImage)){
            goodsDetailsVO.setImageList(Arrays.asList(detailImage.split(",")));
        }
        goodsDetailsVO.setDetailHtml(material.getDetailHtml());
        //获取商品属性值列表
        goodsDetailsVO.setAttrLists((getAttributeValueList(id)));
        return goodsDetailsVO;
    }

    private List<AttrListRequestDTO>  getAttributeValueList(Long goodsId) {
        List<SpuAttribute> spuAttributes = spuAttributeRepository.selectByCondition(Condition.builder(SpuAttribute.class)
                .andWhere(Sqls.custom()
                        .andEqualTo(SpuAttribute.FIELD_SPU_ID, goodsId)
                        .andEqualTo(SpuAttribute.FIELD_DELETE_FLAG, ObjectConstant.DeleteFlag.NO)).build());


        List<SpuAttributeValue> spuAttributeValues = spuAttributeValueRepository.selectByCondition(Condition.builder(SpuAttributeValue.class)
                .andWhere(Sqls.custom()
                        .andEqualTo(SpuAttributeValue.FIELD_SPU_ID, goodsId)
                        .andEqualTo(SpuAttributeValue.FIELD_DELETE_FLAG, ObjectConstant.DeleteFlag.NO)).build());

        Map<Long,List<SpuAttributeValue> > valueMap = spuAttributeValues.stream()
                .collect(Collectors.toMap(SpuAttributeValue::getAttributeId,
                        com.google.common.collect.Lists::newArrayList,
                        (List<SpuAttributeValue> newValueList, List<SpuAttributeValue> oldValueList) -> {
                            oldValueList.addAll(newValueList);
                            return oldValueList;
                        })
                );

        return spuAttributes.stream().map(item -> {
            AttrListRequestDTO attrListRequestDTO = new AttrListRequestDTO();
            BeanUtils.copyProperties(item, attrListRequestDTO);
            attrListRequestDTO.setAttributeName(item.getName());
            attrListRequestDTO.setValues(valueMap.get(item.getId()));
            return attrListRequestDTO;
        }).collect(Collectors.toList());

    }

}
