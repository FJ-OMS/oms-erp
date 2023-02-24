package org.skyer.goods.domain.repository;

import java.util.List;

import org.skyer.mybatis.base.BaseRepository;
import org.skyer.goods.domain.entity.SkuAttributeValue;

/**
 * sku属性关联表资源库
 *
 * @author zhanglongbing@ebc.com 2021-12-28 09:17:10
 */
public interface SkuAttributeValueRepository extends BaseRepository<SkuAttributeValue> {
    /**
     * 根据SKU ID获取关联的属性
     *
     * @author lixiaoyang
     * @date 2021/12/30 11:20
     */
    List<SkuAttributeValue> queryAllBySkuId(Long skuId);

    /**
     * 根据SKU ID批量删除
     *
     * @author lixiaoyang
     * @date 2021/12/30 15:22
     */
    Integer deleteBySkuId(Long skuId);

}
