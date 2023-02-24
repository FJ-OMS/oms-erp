package org.skyer.goods.infra.convertor;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.skyer.goods.api.dto.BrandReqDTO;
import org.skyer.goods.domain.entity.Brand;

/**
 * @description
 * @Author wusc
 * @create 2021/6/25 2:03 下午
 */
@Mapper(componentModel="spring")
public interface BrandConvertor {

    @Mappings({
            @Mapping(source = "brandName",target = "name"),

    })
    Brand reqDtoToBrand(BrandReqDTO brandReqDTO);
}
