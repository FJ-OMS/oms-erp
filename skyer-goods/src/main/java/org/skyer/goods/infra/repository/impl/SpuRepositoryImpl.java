package org.skyer.goods.infra.repository.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.skyer.core.domain.Page;
import org.skyer.goods.api.dto.GoodsPageReqDTO;
import org.skyer.goods.domain.entity.Spu;
import org.skyer.goods.domain.repository.SpuRepository;
import org.skyer.goods.domain.vo.GoodsPageResVO;
import org.skyer.goods.infra.mapper.SpuMapper;
import org.skyer.mybatis.base.impl.BaseRepositoryImpl;
import org.skyer.mybatis.domian.Condition;
import org.skyer.mybatis.pagehelper.PageHelper;
import org.skyer.mybatis.util.Sqls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description
 * @Author wusc
 * @create 2021/6/28 10:52 上午
 */
@Component
public class SpuRepositoryImpl extends BaseRepositoryImpl<Spu> implements SpuRepository {
    @Autowired
    private SpuMapper spuMapper;

    @Override
    public Spu queryGoodsByName(String name, Long id, Long tenantId) {
        List<Spu> spuList = spuMapper.selectByCondition(Condition.builder(Spu.class).andWhere(Sqls.custom()
                .andEqualTo(org.skyer.goods.domain.entity.Spu.NAME, name).andEqualTo(org.skyer.goods.domain.entity.Spu.TENANT_ID, tenantId).andEqualTo(Spu.DELETE_FLAG, 0)).build());
        if (CollectionUtils.isNotEmpty(spuList)){
            if (!spuList.get(0).getId().equals(id)) {
                return spuList.get(0);
            }
        }
        return null;
    }

    @Override
    public Page<GoodsPageResVO> pageGoods(GoodsPageReqDTO goodsPageReqDTO) {
        return PageHelper.doPage(goodsPageReqDTO.getPage(),goodsPageReqDTO.getSize(),
                ()-> spuMapper.getGoodsList(goodsPageReqDTO));
    }

    @Override
    public List<GoodsPageResVO> queryGoodsList(List<String> spuCodeList) {
        return spuMapper.queryGoodsListBySpu(spuCodeList);
    }

    @Override
    public Spu queryGoodsByNameNew(String name, Long id) {
        List<Spu> spuList = spuMapper.selectByCondition(Condition.builder(Spu.class).andWhere(Sqls.custom()
                .andEqualTo(org.skyer.goods.domain.entity.Spu.NAME, name)
                .andEqualTo(Spu.DELETE_FLAG, 0)).build());
        if (CollectionUtils.isNotEmpty(spuList)){
            if (!spuList.get(0).getId().equals(id)) {
                return spuList.get(0);
            }
        }
        return null;
    }
}
