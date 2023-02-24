package org.skyer.stock.domain.service.impl;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.skyer.stock.domain.entity.ResultRecord.STATUS_ALL_OUT;
import static org.skyer.stock.infra.constant.ObjectConstant.DeliveryType.SEND;
import static org.skyer.stock.infra.constant.ObjectConstant.OutType.OUT_TYPE_ORDER_SALES_OUT_STOCK;

import org.skyer.boot.platform.code.builder.CodeRuleBuilder;
import org.skyer.boot.platform.code.constant.CodeConstants;
import org.skyer.boot.platform.lov.annotation.ProcessLovValue;
import org.skyer.core.cache.ProcessCacheValue;
import org.skyer.core.domain.Page;
import org.skyer.core.exception.CommonException;
import org.skyer.mybatis.pagehelper.domain.PageRequest;
import org.skyer.mybatis.pagehelper.domain.Sort;
import org.skyer.stock.api.dto.*;
import org.skyer.stock.domain.entity.*;
import org.skyer.stock.domain.repository.*;
import org.skyer.stock.domain.service.ChangeLogService;
import org.skyer.stock.domain.service.ResultRecordService;
import org.skyer.stock.domain.vo.LogisticsResultVO;
import org.skyer.stock.domain.vo.ResultRecordInfoVO;
import org.skyer.stock.domain.vo.ResultRecordListVO;
import org.skyer.stock.infra.constant.ObjectConstant;

/**
 * 收发货结果单应用服务默认实现
 *
 * @author lixiaoyang 2021-08-03 16:41:29
 */
@Service
public class ResultRecordServiceImpl implements ResultRecordService {
    private static final Logger logger = LoggerFactory.getLogger(ResultRecordServiceImpl.class);
    @Autowired
    private CodeRuleBuilder codeRuleBuilder;
    @Autowired
    private WarehouseRepository warehouseRepository;
    @Autowired
    private ChangeLogService changeLogService;
    @Autowired
    private ChangeLogRepository changeLogRepository;
    @Autowired
    private ResultRecordRepository resultRecordRepository;
    @Autowired
    private ResultDetailRepository resultDetailRepository;
    @Autowired
    private GoodsStockDetailRepository goodsStockDetailRepository;
    @Autowired
    private OutboundDeliveryOrderRepository outboundDeliveryOrderRepository;
    @Autowired
    private OutboundDeliveryOrderDetailRepository outboundDeliveryOrderDetailRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultRecordDTO createResultRecord(OutboundDeliveryOrderDTO dto) {
        ResultRecordDTO resultRecordDTO = new ResultRecordDTO();
        // 获取出库单ID
        Long odoId = dto.getId();
        ResultRecord resultRecord = new ResultRecord();
        // 参数转换
        BeanUtils.copyProperties(dto, resultRecord);
        String code = codeRuleBuilder.generateCode(CodeConstants.Level.TENANT, dto.getTenantId(), ObjectConstant.CodeRule.QD_WAREHOUSE_CODE, CodeConstants.CodeRuleLevelCode.CUSTOM, ObjectConstant.LevelValue.QD_WAREHOUSE_CODE, null);
        resultRecord.setResultCode(code);
        resultRecord.setId(null);
        resultRecord.setOdoId(odoId);
        resultRecord.setDeliveryType(SEND);
        resultRecord.setOdoCode(dto.getCode());
        resultRecord.setStatus(STATUS_ALL_OUT);
        resultRecord.setOutType(OUT_TYPE_ORDER_SALES_OUT_STOCK);
        int flag = resultRecordRepository.insertSelective(resultRecord);
        if (flag > 0) {

            // 参数转换
            BeanUtils.copyProperties(resultRecord, resultRecordDTO);
            // 获取出库单信息
            OutboundDeliveryOrder outboundDeliveryOrder = outboundDeliveryOrderRepository.selectByPrimaryKey(odoId);
            Long resultRecordId = resultRecord.getId();
            String resultCode = outboundDeliveryOrder.getResultCode();
            resultCode = spliceCode(resultCode, code, resultRecordId);
            outboundDeliveryOrder.setResultCode(resultCode);
            outboundDeliveryOrder.setStatus(outboundDeliveryOrder.STATUS_ALL_STOCK_OUT);
            // 获取出库单详情
            List<OutboundDeliveryOrderDetail> thisDetailList = outboundDeliveryOrderDetailRepository.getByOdoId(odoId);
            Map<Long, OutboundDeliveryOrderDetail> thisDetailMap = thisDetailList.stream().collect(Collectors.toMap(OutboundDeliveryOrderDetail::getId, Function.identity()));
            List<OutboundDeliveryOrderDetail> updateList = new ArrayList<>();
            List<OutboundDeliveryOrderDetailDTO> detailList = dto.getDetailList();

            if (!ObjectUtils.isEmpty(detailList)) {
                List<ResultDetail> insertList = new ArrayList<>();
                for (OutboundDeliveryOrderDetailDTO detailDTO : detailList) {
                    Long detailId = detailDTO.getId();
                    OutboundDeliveryOrderDetail detail = thisDetailMap.get(detailId);
                    if (!ObjectUtils.isEmpty(detail)) {
                        // 出库单明细不为空
                        BigDecimal plannedQty = detail.getPlannedQty();
                        BigDecimal newPlannedQty = detailDTO.getPlannedQty();
                        detail.setAlreadyOutQty(plannedQty);
                        if (plannedQty.compareTo(newPlannedQty) != 0) {
                            BigDecimal surplusQty = newPlannedQty.multiply(plannedQty);
                            detail.setWaitOutQty(surplusQty);
                            outboundDeliveryOrder.setStatus(outboundDeliveryOrder.STATUS_PART_STOCK_OUT);
                        }
                        updateList.add(detail);
                    }
                    ResultDetail resultDetail = new ResultDetail();
                    // 参数转换
                    BeanUtils.copyProperties(detailDTO, resultDetail);
                    resultDetail.setRealNum(detailDTO.getPlannedQty());
                    resultDetail.setResultId(resultRecord.getId());
                    insertList.add(resultDetail);
                }
                if (insertList.size() > 0) {
                    resultDetailRepository.batchInsertSelective(insertList);
                    String packageCode = dto.getPackageCode();
                    Warehouse systemLogicWarehouse = warehouseRepository.getSystemLogicWarehouse();
                    List<GoodsStockDetail> insertDetailList = new ArrayList<>();
                    for (ResultDetail resultDetail : insertList) {
                        doDeductionStock(insertDetailList, systemLogicWarehouse, resultDetail.getSkuCode(), resultDetail.getRealNum(), packageCode);
                    }
                    if (insertDetailList.size() > 0) {
                        goodsStockDetailRepository.batchInsertSelective(insertDetailList);
                        List<ChangeLog> logList = changeLogService.getLogList(packageCode, insertDetailList);
                        changeLogRepository.batchInsertSelective(logList);
                    }
                    resultRecordDTO.setDetailList(insertList);
                }
                if (updateList.size() > 0) {
                    outboundDeliveryOrderDetailRepository.batchUpdateByPrimaryKeySelective(updateList);
                }
            } else {
                logger.info("明细为空不能生成出库单");
                throw new CommonException("明细为空不能生成出库单");
            }
            // 反写信息到出库单
            outboundDeliveryOrderRepository.updateByPrimaryKeySelective(outboundDeliveryOrder);
        }
        return resultRecordDTO;
    }

    /**
     * 拼接编码
     *
     * @author lixiaoyang
     * @date 2022/2/23 16:15
     */
    public String spliceCode(String thisCode, String newCode, Long newId) {
        CodeAndIdDTO codeAndIdDTO = new CodeAndIdDTO();
        codeAndIdDTO.setCode(newCode);
        codeAndIdDTO.setId(newId);
        if (ObjectUtils.isEmpty(thisCode)) {
            return JSON.toJSONString(Collections.singletonList(codeAndIdDTO));
        }
        List<CodeAndIdDTO> codeAndIdDTOS = JSON.parseArray(thisCode, CodeAndIdDTO.class);
        codeAndIdDTOS.add(codeAndIdDTO);
        return JSON.toJSONString(codeAndIdDTOS);
    }


    /**
     * 执行库存扣减
     *
     * @author lixiaoyang
     * @date 2022/3/9 13:39
     */
    public void doDeductionStock(List<GoodsStockDetail> detailList, Warehouse logicWarehouse, String skuCode, BigDecimal qty, String packageCode) {
        Long logicWarehouseId = logicWarehouse.getId();
        String logicWarehouseCode = logicWarehouse.getCode();
        GoodsStockDetail stockDetail = goodsStockDetailRepository.getStockBySkuCodeAndWarehouseCode(logicWarehouseCode, skuCode);
        BigDecimal stockNum = stockDetail.getStockNum();
        stockDetail.setPreRealNum(stockNum);
        stockDetail.setChangeNum(qty.abs().negate());
        stockNum = stockNum.subtract(qty.abs());
        stockDetail.setStockNum(stockNum);
        logger.info("skuCode:" + skuCode + " logicWarehouseId:" + logicWarehouseId + " stockDetail:" + stockDetail);
        detailList.add(stockDetail);
    }

    @Override
    @ProcessCacheValue
    @ProcessLovValue
    public Page<ResultRecordListVO> page(ResultRecordPageDTO dto) {
        Sort sort = dto.getSort();
        if (ObjectUtils.isEmpty(sort)) {
            sort = new Sort(Sort.Direction.DESC, ResultRecord.FIELD_CREATION_DATE);
        }

        PageRequest pageRequest = new PageRequest(dto.getPage(), dto.getSize(), sort);

        return resultRecordRepository.page(pageRequest, dto);
    }

    @Override
    @ProcessCacheValue
    @ProcessLovValue
    public ResultRecordInfoVO detail(Long id) {
        ResultRecord resultRecord = resultRecordRepository.selectByPrimaryKey(id);
        ResultRecordInfoVO resultRecordInfoVO = new ResultRecordInfoVO();
        BeanUtils.copyProperties(resultRecord, resultRecordInfoVO);

        return resultRecordInfoVO;
    }

    @Override
    public List<LogisticsResultVO> statsLogisticsResult(LogisticsResultDTO dto) {
        return resultRecordRepository.statsLogisticsResult(dto);
    }
}
