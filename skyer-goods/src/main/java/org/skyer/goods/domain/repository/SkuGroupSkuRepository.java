package org.skyer.goods.domain.repository;

import org.skyer.goods.api.dto.GoodsGroupSkuReqDTO;
import org.skyer.mybatis.base.BaseRepository;
import org.skyer.goods.domain.entity.SkuGroupSku;

import java.math.BigDecimal;
import java.util.List;

/**
 * Sku组合和sku关联表资源库
 *
 * @author wushaochuan 2021-08-13 09:55:04
 */
public interface SkuGroupSkuRepository extends BaseRepository<SkuGroupSku> {

    /**
     * 创建商品sku关系表
     * @param goodsGroupSkuReqDTOS
     * @param groupId
     * @param tenantId
     */
    void createGroupSku(List<GoodsGroupSkuReqDTO> goodsGroupSkuReqDTOS,Long spuId, Long groupId, Long tenantId);

    /**
     * 查询套餐商品
     * @param groupId
     * @return
     */
    List<SkuGroupSku> queryList(Long groupId);

    /**
     * 统计套餐数量
     * @param groupId
     * @return
     */
    BigDecimal totalSkuNum(Long groupId);


}
