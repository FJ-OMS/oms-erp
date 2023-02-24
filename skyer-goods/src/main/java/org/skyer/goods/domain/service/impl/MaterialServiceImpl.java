package org.skyer.goods.domain.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import org.skyer.core.cache.ProcessCacheValue;
import org.skyer.core.domain.Page;
import org.skyer.core.domain.PageInfo;
import org.skyer.core.exception.CommonException;
import org.skyer.core.oauth.CustomUserDetails;
import org.skyer.core.oauth.DetailsHelper;
import org.skyer.goods.api.dto.MaterialDTO;
import org.skyer.goods.api.dto.MaterialListDTO;
import org.skyer.goods.domain.entity.Material;
import org.skyer.goods.domain.entity.Spu;
import org.skyer.goods.domain.repository.MaterialRepository;
import org.skyer.goods.domain.repository.SpuRepository;
import org.skyer.goods.domain.service.MaterialService;
import org.skyer.goods.domain.vo.MaterialVO;
import org.skyer.mybatis.base.impl.BaseRepositoryImpl;
import org.skyer.mybatis.domian.Condition;
import org.skyer.mybatis.pagehelper.PageHelper;
import org.skyer.mybatis.pagehelper.domain.PageRequest;
import org.skyer.mybatis.util.Sqls;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 商品素材
 * </p>
 *
 * @author chenzz
 * @since 2021-06-23
 */
@Slf4j
@Service
public class MaterialServiceImpl extends BaseRepositoryImpl<Material> implements MaterialService {

    @Autowired
    private MaterialRepository materialRepository;
    @Autowired
    private SpuRepository spuRepository;

    @Override
    @ProcessCacheValue
    public Page<MaterialVO>  pageList(PageRequest pageRequest, MaterialListDTO materialListDTO) {
        Page<Material> pageList = PageHelper.doPage(pageRequest, () ->
                selectByCondition(Condition.builder(Material.class)
                        .andWhere(Sqls.custom()
                                .andLike(Material.FIELD_MATERIAL_NAME, materialListDTO.getMaterialName(), true)
                                .andEqualTo(Material.FIELD_CODE, materialListDTO.getCode(), true)
                                .andEqualTo(Material.FIELD_DELETE_FLAG,false)
                        )
                        .build()));

        List<Material> materialList = pageList.getContent();


        List<MaterialVO> materialVOList = new ArrayList<>();
        materialList.forEach(o -> {
            MaterialVO materialVO = new MaterialVO();
            BeanUtils.copyProperties(o, materialVO);

            if (ObjectUtil.isNotEmpty(o.getMainImage())) {
                String[] mainImages = o.getMainImage().split(",");
                materialVO.setMainImage(CollUtil.newArrayList(mainImages));
            }
            if (ObjectUtil.isNotEmpty(o.getDetailImage())) {
                String[] detailImages = o.getDetailImage().split(",");
                materialVO.setDetailImage(CollUtil.newArrayList(detailImages));
            }

            materialVOList.add(materialVO);
        });

        return new Page<>(materialVOList, new PageInfo(pageList.getTotalPages(), pageList.getSize()), pageList.getTotalElements());
    }

    @Override
    public Boolean add(MaterialDTO materialDTO) {

        // 素材名称校验
        if (materialRepository.getSizeByName(materialDTO.getMaterialName()) >0) {
            throw new CommonException("素材名称已存在");
        }
        // 素材编号校验
        if (StringUtils.isNotEmpty(materialDTO.getCode())) {
            if (materialRepository.getSizeByCode(materialDTO.getCode()) >0) {
                throw new CommonException("素材编码已存在");
            }
        }

        Material material = new Material();
        BeanUtils.copyProperties(materialDTO, material);

        // 商品主图
        if (CollUtil.isNotEmpty(materialDTO.getMainImage())) {
            material.setMainImage(String.join(",", materialDTO.getMainImage()));
        }

        // 商品详情图
        if (CollUtil.isNotEmpty(materialDTO.getDetailImage())) {
            material.setDetailImage(String.join(",", materialDTO.getDetailImage()));
        }

        material.setDeleteFlag(false);
        return materialRepository.insert(material) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean edit(MaterialDTO materialDTO) {
        // 素材名称校验
        if (materialRepository.getSizeByName(materialDTO.getMaterialName()) >1) {
            throw new CommonException("素材名称已存在");
        }
        // 素材编号校验
        if (StringUtils.isNotEmpty(materialDTO.getCode())) {
            if (materialRepository.getSizeByCode(materialDTO.getCode()) >1) {
                throw new CommonException("素材编码已存在");
            }
        }

        Material material = materialRepository.selectByPrimaryKey(materialDTO.getId());
        BeanUtils.copyProperties(materialDTO, material);
        if (CollUtil.isNotEmpty(materialDTO.getMainImage())) {
            material.setMainImage(String.join(",", materialDTO.getMainImage()));
        }
        if (CollUtil.isNotEmpty(materialDTO.getDetailImage())) {
            material.setDetailImage(String.join(",", materialDTO.getDetailImage()));
        }
        return materialRepository.updateByPrimaryKey(material) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteById(Long id) {
        if (CollUtil.isNotEmpty(getSpuByMaterial(id))){
            throw new CommonException("该素材有相应关联商品无法删除");
        }
        Material material = materialRepository.selectByPrimaryKey(id);
        material.setDeleteFlag(true);
        return materialRepository.updateByPrimaryKey(material) > 0;
    }

    @Override
    public MaterialVO getDetail(Long id) {
        Material material = this.selectByPrimaryKey(id);
        MaterialVO materialVO = new MaterialVO();
        BeanUtils.copyProperties(material, materialVO);
        if (ObjectUtil.isNotEmpty(material.getMainImage()) ) {
            String[] mainImages = material.getMainImage().split(",");
            materialVO.setMainImage(CollUtil.newArrayList(mainImages));
        }
        if (ObjectUtil.isNotEmpty(material.getDetailImage()) ) {
            String[] detailImages = material.getDetailImage().split(",");
            materialVO.setDetailImage(CollUtil.newArrayList(detailImages));
        }
        return materialVO;
    }

    @Override
    public Boolean deleteByIds(List<Long> ids) {
        List<Material> materials = super.selectByCondition(Condition.builder(Material.class)
                .andWhere(Sqls.custom()
                        .andIn(Material.FIELD_ID, ids, true)
                        .andEqualTo(Material.FIELD_DELETE_FLAG, false))
                .build());

        for (Material mm : materials) {
            if (CollUtil.isNotEmpty(getSpuByMaterial(mm.getId()))){
                throw new CommonException("该素材有相应关联商品无法删除");
            }
            mm.setDeleteFlag(true);
        }
        return !super.batchUpdateByPrimaryKeySelective(materials).isEmpty();
    }

    /**
     * 素材关联商品
     * @param id
     * @return
     */
    private List<Spu> getSpuByMaterial(Long id){
        return spuRepository.selectByCondition(Condition.builder(Spu.class)
                .andWhere(Sqls.custom()
                        .andEqualTo(Spu.MATERIAL_ID, id))
                .build());
    }
}
