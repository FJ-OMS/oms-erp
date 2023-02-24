package org.skyer.goods.infra.mapper;


import org.apache.ibatis.annotations.Param;
import org.skyer.goods.api.dto.GoodsSkuPageDTO;
import org.skyer.goods.domain.entity.Sku;
import org.skyer.goods.domain.vo.RpcGoodsResVO;
import org.skyer.goods.domain.vo.RpcOrderResVO;
import org.skyer.mybatis.common.BaseMapper;

import java.util.List;

/**
 * 商品规格表Mapper
 *
 * @author chenzz 2021-07-06 10:35:43
 */
public interface SkuMapper extends BaseMapper<Sku> {

    /**
     * 分页查询
     * @param goodsSkuPageDTO
     * @return
     */
    List<Sku> getSkuList(@Param("param") GoodsSkuPageDTO goodsSkuPageDTO);

    /**
     * 查询skuCode(rpc调用)
     * @param skuCodeList
     * @return
     */
    List<RpcGoodsResVO> queryRPCGoodsList(@Param("skuCodeList") List<String> skuCodeList);

    /**
     * 查询skuCode(rpc调用)
     * @param skuCodeList
     * @return
     */
    List<RpcOrderResVO> queryOrderRpcList(@Param("skuCodeList") List<String> skuCodeList);
}
