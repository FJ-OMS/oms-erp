package org.skyer.goods.infra.convertor;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.skyer.goods.api.dto.GoodsGroupReqDTO;
import org.skyer.goods.domain.entity.SkuGroup;

/**
 * @description
 * @Author wusc
 * @create 2021/8/13 2:52 下午
 */
@Mapper(componentModel="spring")
public interface SkuGroupConvertor {

    SkuGroup dtoTOSkuGroup(GoodsGroupReqDTO goodsGroupReqDTO);


}
