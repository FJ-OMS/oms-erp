package org.skyer.goods.infra.repository.impl;

import org.skyer.core.domain.Page;
import org.skyer.goods.api.dto.GroupPageReqDTO;
import org.skyer.goods.api.dto.IdsReqDTO;
import org.skyer.goods.domain.vo.GroupPageResVO;
import org.skyer.goods.infra.constant.ObjectConstant;
import org.skyer.goods.infra.mapper.SkuGroupMapper;
import org.skyer.mybatis.base.impl.BaseRepositoryImpl;
import org.skyer.goods.domain.entity.SkuGroup;
import org.skyer.goods.domain.repository.SkuGroupRepository;
import org.skyer.mybatis.domian.Condition;
import org.skyer.mybatis.pagehelper.PageHelper;
import org.skyer.mybatis.util.Sqls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Sku组合表 资源库实现
 *
 * @author wushaochuan 2021-08-13 09:55:04
 */
@Component
public class SkuGroupRepositoryImpl extends BaseRepositoryImpl<SkuGroup> implements SkuGroupRepository {
    @Autowired
    private SkuGroupMapper skuGroupMapper;

    @Override
    public List<SkuGroup> getSkuGroupByName(String name, Long tenantId) {
        List<SkuGroup> skuGroups = skuGroupMapper.selectByCondition(Condition.builder(SkuGroup.class).andWhere(Sqls.custom().andEqualTo(SkuGroup.FIELD_GROUP_NAME, name)
                .andEqualTo(SkuGroup.FIELD_DELETE_FLAG, ObjectConstant.DeleteFlag.NO).andEqualTo(SkuGroup.FIELD_TENANT_ID, tenantId)).build());
        return skuGroups;
    }

    @Override
    public Page<GroupPageResVO> pageGroup(GroupPageReqDTO groupPageReqDTO,Long tenantId) {
        return PageHelper.doPage(groupPageReqDTO.getPage(),groupPageReqDTO.getSize(),()->skuGroupMapper.queryGroupPageList(groupPageReqDTO,tenantId));

    }

    @Override
    public void delGroup(IdsReqDTO idsReqDTO) {
        List<Long> ids = idsReqDTO.getIds();
        List<SkuGroup> skuGroupList = ids.stream().map(id -> {
            SkuGroup skuGroup = this.selectByPrimaryKey(id);
            skuGroup.setDeleteFlag(ObjectConstant.DeleteFlag.YES);
            return skuGroup;
        }).collect(Collectors.toList());
        this.batchUpdateByPrimaryKeySelective(skuGroupList);
    }
}
