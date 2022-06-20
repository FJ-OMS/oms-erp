package org.skyer.stock.domain.repository;

import java.util.List;

import org.skyer.core.domain.Page;
import org.skyer.mybatis.base.BaseRepository;
import org.skyer.mybatis.pagehelper.domain.PageRequest;
import org.skyer.stock.api.dto.LogisticsResultDTO;
import org.skyer.stock.api.dto.ResultRecordPageDTO;
import org.skyer.stock.domain.entity.ResultRecord;
import org.skyer.stock.domain.vo.LogisticsResultVO;
import org.skyer.stock.domain.vo.ResultRecordListVO;

/**
 * 收发货结果单资源库
 *
 * @author lixiaoyang 2021-08-03 16:41:29
 */
public interface ResultRecordRepository extends BaseRepository<ResultRecord> {

    ResultRecord getResultByCode(String resultCode);

    /**
     * 分页查询
     * @param pageRequest 分页条件
     * @param dto 查询条件
     * @return 带分页的 ResultRecord
     */
    Page<ResultRecordListVO> page(PageRequest pageRequest, ResultRecordPageDTO dto);

    /**
     * 发货汇总
     * @param dto 统计条件
     * @return {@link LogisticsResultVO}集合
     */
    List<LogisticsResultVO> statsLogisticsResult(LogisticsResultDTO dto);
}
