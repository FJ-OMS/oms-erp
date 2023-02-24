package org.skyer.goods.infra.repository.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.skyer.core.domain.Page;
import org.skyer.goods.api.dto.BrandPageReqDTO;
import org.skyer.goods.domain.entity.Brand;
import org.skyer.goods.domain.repository.BrandRepository;
import org.skyer.goods.domain.vo.BrandPageResVO;
import org.skyer.goods.infra.mapper.BrandMapper;
import org.skyer.mybatis.base.impl.BaseRepositoryImpl;
import org.skyer.mybatis.domian.Condition;
import org.skyer.mybatis.pagehelper.PageHelper;
import org.skyer.mybatis.util.Sqls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 品牌dao层
 * @description
 * @Author wusc
 * @create 2021/6/25 1:39 下午
 */
@Component
public class BrandRepositoryImpl extends BaseRepositoryImpl<Brand> implements BrandRepository {
    @Autowired
    private BrandMapper brandMapper;

    @Override
    public Page<BrandPageResVO> pageBrand(BrandPageReqDTO brandPageReqDTO) {
        return PageHelper.doPage(brandPageReqDTO.getPage(),brandPageReqDTO.getSize(),() ->brandMapper.getBrandVOList(brandPageReqDTO));
    }

    @Override
    public List<BrandPageResVO> listBrand(BrandPageReqDTO brandPageReqDTO) {
        return brandMapper.getBrandVOList(brandPageReqDTO);
    }

    @Override
    public Brand getBrandByName(String name, Long id,Long tenantId) {
        List<Brand> brands = brandMapper.selectByCondition(Condition.builder(Brand.class)
                .andWhere(Sqls.custom().andEqualTo(Brand.NAME, name).andEqualTo(Brand.DELETE_FLAG,0)
                .andEqualTo(Brand.TENANT_ID,tenantId)).build());
        if (CollectionUtils.isNotEmpty(brands)){
            if (!brands.get(0).getId().equals(id)) {
                return brands.get(0);
            }
        }
        return null;
    }

    @Override
    public Brand getBrandByCode(String code, Long id) {
        List<Brand> brands = brandMapper.selectByCondition(Condition.builder(Brand.class)
                .andWhere(Sqls.custom().andEqualTo(Brand.CODE, code).andEqualTo(Brand.DELETE_FLAG,0)).build());
        if (CollectionUtils.isNotEmpty(brands)){
            if (!brands.get(0).getId().equals(id)) {
                return brands.get(0);
            }
        }
        return null;
    }
}
