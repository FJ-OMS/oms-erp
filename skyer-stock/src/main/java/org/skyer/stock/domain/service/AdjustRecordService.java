package org.skyer.stock.domain.service;

import org.skyer.core.domain.Page;
import org.skyer.stock.api.dto.AdjustPageReqDTO;
import org.skyer.stock.api.dto.AdjustRecordReqDTO;
import org.skyer.stock.domain.vo.AdjustRecordPageResVO;
import org.skyer.stock.domain.vo.AdjustRecordResVO;

/**
 * 库存调整单应用服务
 *
 * @author lixiaoyang 2021-08-02 16:44:33
 */
public interface AdjustRecordService {


    /**
     * 创建或者更新调整单
     *
     * @param adjustRecordReqDTO
     */
    void createOrUpdateAdjustRecord(AdjustRecordReqDTO adjustRecordReqDTO);

    /**
     * 查询调整单(分页)
     *
     * @param adjustPageReqDTO
     * @return
     */
    Page<AdjustRecordPageResVO> pageAdjustRecord(AdjustPageReqDTO adjustPageReqDTO);

    /**
     * 审核调整单
     *
     * @param adjustId
     * @param status
     */
    void auditAdjustRecord(Long adjustId, String status);

    /**
     * 获取调整单详情
     *
     * @param adjustId
     * @return
     */
    AdjustRecordResVO adjustDetail(Long adjustId);

    /**
     * 执行调整单审核后商品逻辑仓变化处理
     *
     * @author lixiaoyang
     * @date 2022/4/11 14:59
     */
    Long exeSkuLogicQtyAdjust(Long adjustRecordId);
}
