package org.skyer.goods.infra.convertor;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.skyer.goods.api.dto.GoodsReqDTO;
import org.skyer.goods.domain.entity.Spu;
import org.skyer.goods.domain.vo.GoodsDetailResVO;

/**
 * @description
 * @Author wusc
 * @create 2021/6/28 10:39 上午
 */
@Mapper(componentModel="spring")
public interface GoodsConvertor {

    /**
     * req转商品
     * @param goodsReqDTO
     * @return
     */
    @Mappings(
            @Mapping(source = "code",target = "spuCode")
    )
    Spu reqToGoods(GoodsReqDTO goodsReqDTO);

    /**
     * 商品转res
     * @param spu
     * @return
     */
    @Mappings(
            @Mapping(source = "spuCode",target = "code")
    )
    GoodsDetailResVO goodsToRes(Spu spu);
}
