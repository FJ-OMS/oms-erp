package org.skyer.stock.domain.service;


import java.util.List;

import org.skyer.core.domain.Page;
import org.skyer.stock.api.dto.LogisticsResultDTO;
import org.skyer.stock.api.dto.OutboundDeliveryOrderDTO;
import org.skyer.stock.api.dto.ResultRecordDTO;
import org.skyer.stock.api.dto.ResultRecordPageDTO;
import org.skyer.stock.domain.vo.LogisticsResultVO;
import org.skyer.stock.domain.vo.ResultRecordInfoVO;
import org.skyer.stock.domain.vo.ResultRecordListVO;

/**
 * 收发货结果单应用服务
 *
 * @author lixiaoyang 2021-08-03 16:41:29
 */
public interface ResultRecordService {

    /**
     * 接收创建结果单
     *
     * @author lixiaoyang
     * @date 2022/2/22 15:13
     * @return
     */
    ResultRecordDTO createResultRecord(OutboundDeliveryOrderDTO dto);

    /**
     * 分页查询
     * @param dto 查询条件
     * @return 带分页的 ResultRecord
     */
    Page<ResultRecordListVO> page(ResultRecordPageDTO dto);

    /**
     * 根据id查详情
     * @param id 出库结果单id
     * @return ResultRecordInfoVO
     */
    ResultRecordInfoVO detail(Long id);

    /**
     * 发货汇总
     * @param dto 统计条件
     * @return {@link LogisticsResultVO}
     */
    List<LogisticsResultVO> statsLogisticsResult(LogisticsResultDTO dto);
}
