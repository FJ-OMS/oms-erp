package org.skyer.stock.infra.convertor;

import org.mapstruct.Mapper;

import org.skyer.stock.api.dto.GoodsStockDTO;
import org.skyer.stock.api.dto.StockEsDTO;


/**
 * @description
 * @Author wusc
 * @create 2021/8/5 5:19 下午
 */
@Mapper(componentModel="spring")
public interface StockEsConvertor {

    StockEsDTO stockToEsDTO(GoodsStockDTO goodsStock);


}
