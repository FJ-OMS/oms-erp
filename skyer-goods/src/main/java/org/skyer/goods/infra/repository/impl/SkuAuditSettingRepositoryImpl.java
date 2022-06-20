package org.skyer.goods.infra.repository.impl;

import org.skyer.goods.infra.mapper.SkuAuditSettingMapper;
import org.skyer.mybatis.base.impl.BaseRepositoryImpl;
import org.skyer.goods.domain.entity.SkuAuditSetting;
import org.skyer.goods.domain.repository.SkuAuditSettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * 商品SKU审核 资源库实现
 *
 * @author zhanglongbing@ebc.com 2021-12-27 10:09:56
 */
@Component
public class SkuAuditSettingRepositoryImpl extends BaseRepositoryImpl<SkuAuditSetting> implements SkuAuditSettingRepository {

    @Autowired
    private SkuAuditSettingMapper skuAuditSettingMapper;

    @Override
    public SkuAuditSetting selectDefault() {
      List<SkuAuditSetting>  allList=  skuAuditSettingMapper.selectAll();
      if (ObjectUtils.isEmpty(allList)){
          SkuAuditSetting skuAuditSetting = new SkuAuditSetting();
          skuAuditSetting.setCreatedAuditStatus(0);
          skuAuditSetting.setUpdateAuditStatus(0);
          skuAuditSetting.setUpdatePriceStatus(0);
          skuAuditSetting.setUpdatePropertyStatus(0);
          return skuAuditSetting;
      }
      return allList.get(0);
    }

    @Override
    public int updateDefault(SkuAuditSetting skuAuditSetting) {
        List<SkuAuditSetting>  allList=  skuAuditSettingMapper.selectAll();
        if (ObjectUtils.isEmpty(allList)){
          return skuAuditSettingMapper.insertSelective(skuAuditSetting);
        }
        skuAuditSetting.setId(allList.get(0).getId());
        skuAuditSetting.setObjectVersionNumber(allList.get(0).getObjectVersionNumber());
        return skuAuditSettingMapper.updateByPrimaryKeySelective(skuAuditSetting);
    }
}
