package org.skyer.goods.infra.repository.impl;

import org.apache.commons.compress.utils.Lists;
import org.skyer.goods.domain.entity.Sku;
import org.skyer.goods.domain.repository.SkuRepository;
import org.skyer.goods.domain.vo.RpcGoodsResVO;
import org.skyer.goods.infra.mapper.SkuPackageMapper;
import org.skyer.mybatis.base.impl.BaseRepositoryImpl;
import org.skyer.goods.domain.entity.SkuPackage;
import org.skyer.goods.domain.repository.SkuPackageRepository;
import org.skyer.mybatis.domian.Condition;
import org.skyer.mybatis.util.Sqls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *  资源库实现
 *
 * @author zhanglongbing@ebc.com 2022-03-03 10:45:30
 */
@Component
public class SkuPackageRepositoryImpl extends BaseRepositoryImpl<SkuPackage> implements SkuPackageRepository {
    @Autowired
    private SkuPackageMapper skuPackageMapper;
    @Autowired
    private SkuRepository skuRepository;

    @Override
    public List<SkuPackage> querySkuPackageByMainId(Long packageId) {
        List<SkuPackage> skuPackages = skuPackageMapper.selectByCondition(Condition.builder(SkuPackage.class).andWhere(Sqls.custom().andEqualTo(SkuPackage.FIELD_SKU_ID, packageId)).build());
        return skuPackages;
    }
}
