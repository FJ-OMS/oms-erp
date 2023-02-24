package org.skyer.stock.infra.mapper;

import java.util.List;

import org.skyer.mybatis.common.BaseMapper;
import org.skyer.stock.api.dto.LogisticsResultDTO;
import org.skyer.stock.api.dto.ResultRecordPageDTO;
import org.skyer.stock.domain.entity.ResultRecord;
import org.skyer.stock.domain.vo.LogisticsResultVO;

/**
 * 收发货结果单Mapper
 *
 * @author lixiaoyang 2021-08-03 16:41:29
 */
public interface ResultRecordMapper extends BaseMapper<ResultRecord> {

    /**
     * 查询列表
     * @param dto 查询条件
     * @return ResultRecord集合
     */
    List<ResultRecord> selectList(ResultRecordPageDTO dto);

    /**
     * 发货汇总
     * @param dto 统计条件
     * @return {@link LogisticsResultVO}集合
     */
    List<LogisticsResultVO> statsLogisticsResult(LogisticsResultDTO dto);
}
