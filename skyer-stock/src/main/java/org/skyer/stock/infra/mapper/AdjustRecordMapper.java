package org.skyer.stock.infra.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

import org.skyer.mybatis.common.BaseMapper;
import org.skyer.stock.api.dto.AdjustPageReqDTO;
import org.skyer.stock.domain.entity.AdjustRecord;
import org.skyer.stock.domain.vo.AdjustRecordPageResVO;

/**
 * 库存调整单Mapper
 *
 * @author lixiaoyang 2021-08-02 16:44:33
 */
public interface AdjustRecordMapper extends BaseMapper<AdjustRecord> {

    /**
     * 调整单列表
     * @param adjustPageReqDTO
     * @return
     */
    List<AdjustRecordPageResVO> listAdjustRecordVO(@Param("param") AdjustPageReqDTO adjustPageReqDTO,@Param("tenantId") Long tenantId);


    List<AdjustRecord> getAllByLogicWarehouseIds(@Param("logicWarehouseIds") List<Long> logicWarehouseIds);
}
