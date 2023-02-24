package org.skyer.order.infra.convertor;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.skyer.order.domain.entity.Header;
import org.skyer.order.domain.entity.ItemSnapshot;
import org.skyer.order.infra.feign.vo.RpcGoodsResVO;

/**
 * @description
 * @Author wusc
 * @create 2022/1/19 2:34 下午
 */
@Mapper(componentModel="spring")
public interface ItemSnapshotConvertor {


    @Mappings({
            @Mapping(source = "rpcGoodsResVO.salesPrice",target = "salePrice")
    })
    ItemSnapshot skuToItemSnapshot(Header header, RpcGoodsResVO rpcGoodsResVO);
}
