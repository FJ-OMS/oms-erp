package org.skyer.stock.domain.service;

import java.math.BigDecimal;
import java.util.List;

import org.skyer.core.domain.Page;
import org.skyer.stock.api.dto.StockChangeListDTO;
import org.skyer.stock.domain.entity.ChangeLog;
import org.skyer.stock.domain.entity.GoodsStockDetail;
import org.skyer.stock.domain.vo.StockChangeListVO;
import org.skyer.stock.domain.vo.StockChangeVO;

/**
 * 库存流水表应用服务
 *
 * @author lixiaoyang 2021-08-03 16:51:06
 */
public interface ChangeLogService {

    /**
     * 创建日志 -- 单条
     * @param warehouseId
     * @param skuCode
     * @param recordCode
     * @param recordTypeCode
     * @param preNum
     * @param num
     * @param afterNum
     * @param qtyTypeCode
     */
    void createChangeLog(Long warehouseId,String skuCode, String recordCode,String recordTypeCode,BigDecimal preNum,BigDecimal num,BigDecimal afterNum,String qtyTypeCode,String remark);

    /**
     * 组装日志
     * @param warehouseId
     * @param skuCode
     * @param recordCode
     * @param recordTypeCode
     * @param preNum
     * @param num
     * @param afterNum
     * @param qtyTypeCode
     * @return
     */
    ChangeLog packChangeLog(Long warehouseId, String skuCode, String recordCode, String recordTypeCode, BigDecimal preNum, BigDecimal num, BigDecimal afterNum, String qtyTypeCode,String remark);

    /**
     * 库存变动查询
     * @author lixiaoyang
     * @date 2021/9/15 10:33
     */
    StockChangeVO getStockChangePage(StockChangeListDTO dto);

    Page<StockChangeListVO> getStockChangePage(StockChangeListDTO dto, Long tenantId);

    /**
     * 转换日志集合
     */
    List<ChangeLog> getLogList(String adjustCode, List<GoodsStockDetail> insertDetails);
}
