package org.skyer.order.infra.convertor;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.skyer.order.api.es.OrderEsModel;
import org.skyer.order.domain.entity.Header;
import org.skyer.order.domain.entity.ReceiverInfo;

import java.util.Date;
import java.util.List;

/**
 * @description
 * @Author wusc
 * @create 2022/1/21 3:03 下午
 */
@Mapper(componentModel="spring")
public interface OrderEsConvertor {
    @Mappings({
        @Mapping(source = "header.outerOrderNo",target = "outerOrderNo"),
        @Mapping(source = "header.innerOrderNo",target = "innerOrderNo"),
        @Mapping(source = "header.creationDate",target = "creationDate"),
        @Mapping(source = "header.createdBy",target = "createdBy"),
        @Mapping(source = "header.lastUpdateDate",target = "lastUpdateDate"),
        @Mapping(source = "header.lastUpdatedBy",target = "lastUpdatedBy"),
        @Mapping(source = "header.id",target = "id")
    })
    OrderEsModel orderToEs(Header header, ReceiverInfo receiverInfo);

}
