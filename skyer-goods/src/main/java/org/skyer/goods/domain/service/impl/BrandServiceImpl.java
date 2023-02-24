package org.skyer.goods.domain.service.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.skyer.boot.platform.code.builder.CodeRuleBuilder;
import org.skyer.boot.platform.code.constant.CodeConstants;
import org.skyer.core.cache.ProcessCacheValue;
import org.skyer.core.exception.CommonException;
import org.skyer.core.oauth.CustomUserDetails;
import org.skyer.core.oauth.DetailsHelper;
import org.skyer.goods.api.dto.BrandPageReqDTO;
import org.skyer.goods.api.dto.BrandReqDTO;
import org.skyer.goods.domain.entity.Brand;
import org.skyer.goods.domain.entity.Spu;
import org.skyer.goods.domain.repository.BrandRepository;
import org.skyer.goods.domain.repository.SpuRepository;
import org.skyer.goods.domain.service.BrandService;
import org.skyer.goods.domain.vo.BrandPageResVO;
import org.skyer.goods.infra.convertor.BrandConvertor;
import org.skyer.goods.infra.constant.ObjectConstant;
import org.skyer.mybatis.domian.Condition;
import org.skyer.mybatis.util.Sqls;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.skyer.core.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* <p>
*  商品品牌表服务实现类
* </p>
* @author wusc
* @since 2021-06-24
*/
@Service
public class BrandServiceImpl implements BrandService {
    private static final Logger logger = LoggerFactory.getLogger(BrandServiceImpl.class);

    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private BrandConvertor brandConvertor;
    @Autowired
    private SpuRepository spuRepository;
    @Autowired
    private CodeRuleBuilder codeRuleBuilder;

    @Override
    @ProcessCacheValue
    public Page<BrandPageResVO> pageBrand(BrandPageReqDTO brandPageReqDTO) {
        // 请求头中获取当前用户信息
        CustomUserDetails userDetails = DetailsHelper.getUserDetails();
        brandPageReqDTO.setTenantId(userDetails.getTenantId());
        return brandRepository.pageBrand(brandPageReqDTO);
    }

    @Override
    public List<BrandPageResVO> listBrand(BrandPageReqDTO brandPageReqDTO) {
        // 请求头中获取当前用户信息
        CustomUserDetails userDetails = DetailsHelper.getUserDetails();
        brandPageReqDTO.setTenantId(userDetails.getTenantId());
        return brandRepository.listBrand(brandPageReqDTO);
    }

    @Override
    public void saveOrUpdateBrand(BrandReqDTO brandReqDTO) {
        //不能创建名称相同的品牌
        CustomUserDetails userDetails = DetailsHelper.getUserDetails();
        Brand oldNameBrand = brandRepository.getBrandByName(brandReqDTO.getBrandName(), brandReqDTO.getId(),userDetails.getTenantId());
        if (!Objects.isNull(oldNameBrand)){
            throw new CommonException("不能创建名称相同的品牌");
        }
        Brand oldCodeBrand = brandRepository.getBrandByCode(brandReqDTO.getCode(), brandReqDTO.getId());
        if (!Objects.isNull(oldCodeBrand)){
            throw new CommonException("不能创建编码相同的品牌");
        }
        Brand brand = brandConvertor.reqDtoToBrand(brandReqDTO);
        brand.setTenantId(userDetails.getTenantId());
        if (Objects.isNull(brandReqDTO.getId())){
            brand.setCode(Objects.isNull(brand.getCode()) ? codeRuleBuilder.generateCode(CodeConstants.Level.TENANT, 0L, ObjectConstant.CodeRule.BRAND_CODE, CodeConstants.CodeRuleLevelCode.CUSTOM, ObjectConstant.LevelValue.BRAND_LEVEL, null): brand.getCode());
            brandRepository.insert(brand);
        }else {
            brandRepository.updateByPrimaryKeySelective(brand);
        }
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteBrand(List<Long> ids) {
        List<Brand> brands = ids.stream().map(x -> {
            Brand oldBrand = brandRepository.selectByPrimaryKey(x);
            if (Boolean.TRUE.equals(this.checkHaveGoods(x))){
                throw new CommonException("品牌"+oldBrand.getName()+"存在关联商品不能删除");
            }
            Brand brand = new Brand();
            brand.setId(x);
            brand.setDeleteFlag(true);
            brand.setObjectVersionNumber(oldBrand.getObjectVersionNumber());
            return brand;
        }).collect(Collectors.toList());
        brandRepository.batchDeleteByPrimaryKey(brands);
    }


    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void updateBrandStatus(List<Long> ids, Integer status) {
        List<Brand> brands = ids.stream().map(x -> {
            Brand brand = new Brand();
            brand.setId(x);
            brand.setStatus(status);
            return brand;
        }).collect(Collectors.toList());
        brandRepository.batchUpdateByPrimaryKeySelective(brands);
    }

    /**
     * 根据品牌id 检查是否有关联商品
     * @param brandId 品牌id
     * @return Boolean
     */
    private Boolean checkHaveGoods(Long brandId){
        List<Spu> spuList = spuRepository.selectByCondition(Condition.builder(Spu.class)
                .andWhere(Sqls.custom().andEqualTo(Spu.BRAND_ID, brandId).andEqualTo(Spu.DELETE_FLAG, ObjectConstant.DeleteFlag.NO)).build());
            return CollectionUtils.isNotEmpty(spuList);
    }
}