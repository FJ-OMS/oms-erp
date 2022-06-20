package org.skyer.goods.domain.repository;

import org.skyer.core.domain.Page;
import org.skyer.goods.api.dto.GoodsPageReqDTO;
import org.skyer.goods.domain.entity.Spu;
import org.skyer.goods.domain.vo.GoodsPageResVO;
import org.skyer.mybatis.base.BaseRepository;

import java.util.List;

/**
 * 商品数据库层
 * @description
 * @Author wusc
 * @create 2021/6/28 10:34 上午
 */

public interface SpuRepository extends BaseRepository<Spu> {

    /**
     * 通过名称查找商品
     * @param name
     * @return
     */
    Spu queryGoodsByName(String name, Long id, Long tenantId);

    /**
     * 分页查询商品
     * @param goodsPageReqDTO
     * @return
     */
    Page<GoodsPageResVO> pageGoods(GoodsPageReqDTO goodsPageReqDTO);

    /**
     * 查询商品spuList
     * @param spuCodeList
     * @return
     */
    List<GoodsPageResVO> queryGoodsList(List<String> spuCodeList);
    /**
     * 通过名称查找商品
     * @param name
     * @return
     */
    Spu queryGoodsByNameNew(String name, Long id);
}
