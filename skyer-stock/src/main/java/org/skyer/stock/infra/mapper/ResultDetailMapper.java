package org.skyer.stock.infra.mapper;

import org.apache.ibatis.annotations.Param;

import org.skyer.mybatis.common.BaseMapper;
import org.skyer.stock.domain.entity.ResultDetail;
import org.skyer.stock.domain.vo.ResultRecordQtyVO;

/**
 * 收发货结果单详情Mapper
 *
 * @author lixiaoyang 2021-08-03 16:41:29
 */
public interface ResultDetailMapper extends BaseMapper<ResultDetail> {

    /**
     * 根据结果单id 统计数量
     * @param resultId 出库结果单id
     * @return ResultRecordQtyVO
     */
    ResultRecordQtyVO statsByResultId(@Param("resultId") Long resultId);
}
