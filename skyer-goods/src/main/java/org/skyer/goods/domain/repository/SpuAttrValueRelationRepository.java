package org.skyer.goods.domain.repository;

import org.skyer.goods.domain.entity.SpuAttrValueRelation;
import org.skyer.mybatis.base.BaseRepository;

import java.util.List;

/**
 * 商品和属性值关联关系资源库
 *
 * @author wushaochuan 2021-07-19 09:18:33
 */
public interface SpuAttrValueRelationRepository extends BaseRepository<SpuAttrValueRelation> {


    /**
     * 保存该商品选择的对应属性值
     * @param spuAttrValueRelationList
     */
    void createOrUpdateGoodsAttrRelation(Long goodsId,List<SpuAttrValueRelation> spuAttrValueRelationList);

    /**
     * 查询商品id对应商品关联列表
     * @param goodsId
     * @return
     */
    List<SpuAttrValueRelation> queryGoodsAttribute(Long goodsId);

    /**
     * 删除spu值的关联关系
     * @param spuId
     * @param valueId
     */
    void deleteSpuAttrValueRelation(Long spuId,Long valueId);
}
