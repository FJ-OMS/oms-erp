package org.skyer.goods.infra.repository.impl;

import org.skyer.core.domain.Page;
import org.skyer.goods.api.dto.MaterialListDTO;
import org.skyer.goods.domain.entity.Material;
import org.skyer.goods.domain.repository.MaterialRepository;
import org.skyer.goods.domain.vo.MaterialVO;
import org.skyer.goods.infra.mapper.MaterialMapper;
import org.skyer.mybatis.base.impl.BaseRepositoryImpl;
import org.skyer.mybatis.domian.Condition;
import org.skyer.mybatis.pagehelper.PageHelper;
import org.skyer.mybatis.pagehelper.domain.PageRequest;
import org.skyer.mybatis.util.Sqls;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author chenzz
 */
@Component
public class MaterialRepositoryImpl extends BaseRepositoryImpl<Material> implements MaterialRepository {

    @Autowired
    private MaterialMapper materialMapper;

    @Override
    public Page<MaterialVO> pageList(PageRequest pageRequest, MaterialListDTO dealerListDTO) {
        return PageHelper.doPageAndSort(pageRequest, () -> materialMapper.getListPage(dealerListDTO));
        // return PageHelper.doPage(pageRequest, () -> {
        //     return selectByCondition(Condition.builder(Material.class)
        //             .andWhere(Sqls.custom()
        //                     .andLike(Material.MATERIAL_NAME, dealerListDTO.getMaterialName(), true)
        //                     .andEqualTo(Material.CODE, dealerListDTO.getCode(), true)
        //                     .andEqualTo(Material.DELETE_FLAG, false))
        //             .build());
        // });
    }

    @Override
    public Integer getSizeByName(String materialName) {
        List<Material> materials = this.selectByCondition(Condition.builder(Material.class)
                .andWhere(Sqls.custom()
                        .andEqualTo(Material.FIELD_MATERIAL_NAME, materialName))
                .build());
        if (CollectionUtils.isEmpty(materials)) {
            return 0;
        }
        return materials.size();
    }

    @Override
    public Integer getSizeByCode(String code) {
        List<Material> materials = this.selectByCondition(Condition.builder(Material.class)
                .andWhere(Sqls.custom()
                        .andEqualTo(Material.FIELD_CODE, code))
                .build());
        if (CollectionUtils.isEmpty(materials)) {
            return 0;
        }
        return materials.size();
    }
}