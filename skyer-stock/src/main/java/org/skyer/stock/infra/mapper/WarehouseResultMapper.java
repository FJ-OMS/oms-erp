package org.skyer.stock.infra.mapper;

import java.util.List;

import org.skyer.mybatis.common.BaseMapper;
import org.skyer.stock.api.dto.WarehouseResultPageQueryDTO;
import org.skyer.stock.domain.entity.WarehouseResult;
import org.skyer.stock.domain.vo.WarehouseResultVO;

/**
 * 入库结果单Mapper
 *
 * @author xurongfa 2022-04-26 14:10:52
 */
public interface WarehouseResultMapper extends BaseMapper<WarehouseResult> {

    /**
     * 查询列表
     * @param dto 查条件
     * @return {@link WarehouseResultVO}集合
     */
    List<WarehouseResultVO> selectList(WarehouseResultPageQueryDTO dto);

}
