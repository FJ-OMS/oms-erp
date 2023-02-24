package org.skyer.goods.infra.mapper;

import org.apache.ibatis.annotations.Param;
import org.skyer.goods.domain.entity.SkuGroupSku;
import org.skyer.mybatis.common.BaseMapper;

import java.math.BigDecimal;

/**
 * Sku组合和sku关联表Mapper
 *
 * @author wushaochuan 2021-08-13 09:55:04
 */
public interface SkuGroupSkuMapper extends BaseMapper<SkuGroupSku> {

    BigDecimal totalSkuNum(@Param("groupId")Long groupId);
}
