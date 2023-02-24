package org.skyer.stock.domain.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.skyer.stock.infra.constant.ObjectConstant.LovRecordTypeCode.ADJUST_RECORD;
import static org.skyer.stock.infra.constant.ObjectConstant.LovStockQty.STOCK_NUM;

import org.skyer.boot.platform.lov.annotation.ProcessLovValue;
import org.skyer.core.cache.ProcessCacheValue;
import org.skyer.core.domain.Page;
import org.skyer.core.oauth.CustomUserDetails;
import org.skyer.core.oauth.DetailsHelper;
import org.skyer.mybatis.pagehelper.domain.PageRequest;
import org.skyer.stock.api.dto.StockChangeListDTO;
import org.skyer.stock.domain.entity.ChangeLog;
import org.skyer.stock.domain.entity.GoodsStockDetail;
import org.skyer.stock.domain.repository.ChangeLogRepository;
import org.skyer.stock.domain.service.ChangeLogService;
import org.skyer.stock.domain.vo.StockChangeListVO;
import org.skyer.stock.domain.vo.StockChangeVO;
import org.skyer.stock.infra.constant.ObjectConstant;

/**
 * 库存流水表应用服务默认实现
 *
 * @author lixiaoyang 2021-08-03 16:51:06
 */
@Service
public class ChangeLogServiceImpl implements ChangeLogService {
    private static final Logger logger = LoggerFactory.getLogger(ChangeLogServiceImpl.class);
    @Autowired
    private ChangeLogRepository changeLogRepository;


    @Override
    public void createChangeLog(Long warehouseId, String skuCode, String recordCode, String recordTypeCode, BigDecimal preNum, BigDecimal num, BigDecimal afterNum, String qtyTypeCode, String remark) {
        ChangeLog changeLog = packChangeLog(warehouseId, skuCode, recordCode, recordTypeCode, preNum, num, afterNum, qtyTypeCode, remark);
        changeLog.setTurnoverCode("");
        changeLogRepository.insert(changeLog);
    }

    @Override
    public ChangeLog packChangeLog(Long warehouseId, String skuCode, String recordCode, String recordTypeCode, BigDecimal preNum, BigDecimal num, BigDecimal afterNum, String qtyTypeCode, String remark) {
        ChangeLog changeLog = new ChangeLog();
        changeLog.setWarehouseId(warehouseId);
        changeLog.setSkuCode(skuCode);
        changeLog.setRecordCode(recordCode);
        changeLog.setRecordTypeCode(recordTypeCode);
        //设置租户id
        Long tenantId = Optional.ofNullable(DetailsHelper.getUserDetails()).map(CustomUserDetails::getTenantId).orElse(0L);
        changeLog.setInOutType(ObjectConstant.InOutType.IN);
        if (BigDecimal.ZERO.compareTo(num) > 0) {
            changeLog.setInOutType(ObjectConstant.InOutType.OUT);
        }
        changeLog.setPreRealNum(preNum);
        changeLog.setAftRealNum(afterNum);
        changeLog.setQtyTypeCode(qtyTypeCode);
        changeLog.setChangeNum(num);
        changeLog.setTurnoverCode("");
        changeLog.setRemark(remark);
        changeLog.setTenantId(tenantId);
        return changeLog;
    }

    @Override
    @ProcessLovValue(targetField = "page")
    @ProcessCacheValue
    public StockChangeVO getStockChangePage(StockChangeListDTO dto) {
        CustomUserDetails userDetails = DetailsHelper.getUserDetails();
        logger.info("获取当前用户==============》" + userDetails);
        Long tenantId = userDetails.getTenantId();

        Page<StockChangeListVO> stockChangePage = this.getStockChangePage(dto, tenantId);
        BigDecimal putNum = changeLogRepository.getPutNum(dto, tenantId);
        if (ObjectUtils.isEmpty(putNum)) {
            putNum = BigDecimal.ZERO;
        }
        BigDecimal outNum = changeLogRepository.getOutNum(dto, tenantId);
        if (ObjectUtils.isEmpty(outNum)) {
            outNum = BigDecimal.ZERO;
        }
        outNum = outNum.abs();
        BigDecimal differenceNum = putNum.subtract(outNum);
        StockChangeVO vo = new StockChangeVO();
        vo.setPage(stockChangePage);
        vo.setPutNum(putNum);
        vo.setOutNum(outNum);
        vo.setDifferenceNum(differenceNum);
        return vo;
    }

    @Override
    @ProcessCacheValue
    public Page<StockChangeListVO> getStockChangePage(StockChangeListDTO dto, Long tenantId) {
        PageRequest pageRequest = new PageRequest();
        pageRequest.setSize(dto.getSize());
        pageRequest.setPage(dto.getPage());
        return changeLogRepository.getStockChangePage(dto, tenantId, pageRequest);
    }

    @Override
    public List<ChangeLog> getLogList(String adjustCode, List<GoodsStockDetail> insertDetails) {
        List<ChangeLog> logs = new ArrayList<>();
        for (GoodsStockDetail detail : insertDetails) {
            ChangeLog log = this.packChangeLog(detail.getWarehouseId(),
                    detail.getSkuCode(),
                    adjustCode, ADJUST_RECORD,
                    detail.getPreRealNum(),
                    detail.getChangeNum(),
                    detail.getStockNum(),
                    STOCK_NUM, "");
            logs.add(log);
        }
        return logs;
    }
}
