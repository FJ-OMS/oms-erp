package org.skyer.goods.infra.mapper;

import org.apache.ibatis.annotations.Param;
import org.skyer.goods.api.dto.GoodsPageReqDTO;
import org.skyer.goods.domain.entity.Spu;
import org.skyer.goods.domain.vo.GoodsPageResVO;
import org.skyer.mybatis.common.BaseMapper;

import java.util.List;

/**
 * 商品表Mapper
 *
 * @author chenzz 2021-06-25 13:36:41
 */
public interface SpuMapper extends BaseMapper<Spu> {

    /**
     * 查询商品列表
     * @param goodsPageReqDTO
     * @return
     */
    List<GoodsPageResVO> getGoodsList(@Param("param") GoodsPageReqDTO goodsPageReqDTO);

    /**
     * 查询spuList
     * @param spuList
     * @return
     */
    List<GoodsPageResVO> queryGoodsListBySpu(@Param("spuList") List<String> spuList);
}

