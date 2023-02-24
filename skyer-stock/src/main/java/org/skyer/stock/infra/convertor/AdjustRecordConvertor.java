package org.skyer.stock.infra.convertor;

import org.mapstruct.Mapper;

import org.skyer.stock.domain.entity.AdjustDetail;
import org.skyer.stock.domain.vo.AdjustDetailResVO;

/**
 * @description
 * @Author wusc
 * @create 2021/8/10 1:52 下午
 */
@Mapper(componentModel="spring")
public interface AdjustRecordConvertor {

    AdjustDetailResVO adjustDetailToVo(AdjustDetail adjustDetail);
}
