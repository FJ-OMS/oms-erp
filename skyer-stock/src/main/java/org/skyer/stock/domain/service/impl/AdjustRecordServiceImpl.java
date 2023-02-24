package org.skyer.stock.domain.service.impl;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.skyer.stock.infra.constant.ObjectConstant.DEFAULT_TENANT_ID;
import static org.skyer.stock.infra.constant.ObjectConstant.LovRecordTypeCode.ADJUST_RECORD;
import static org.skyer.stock.infra.constant.ObjectConstant.LovStockQty.STOCK_NUM;
import static org.skyer.stock.infra.constant.ObjectConstant.WarehouseTypeCode.LOGIC;

import org.skyer.boot.platform.code.builder.CodeRuleBuilder;
import org.skyer.boot.platform.code.constant.CodeConstants;
import org.skyer.core.cache.ProcessCacheValue;
import org.skyer.core.domain.Page;
import org.skyer.core.exception.CommonException;
import org.skyer.core.oauth.CustomUserDetails;
import org.skyer.core.oauth.DetailsHelper;
import org.skyer.stock.api.dto.AdjustDetailsReqDTO;
import org.skyer.stock.api.dto.AdjustPageReqDTO;
import org.skyer.stock.api.dto.AdjustRecordReqDTO;
import org.skyer.stock.domain.entity.*;
import org.skyer.stock.domain.repository.*;
import org.skyer.stock.domain.service.AdjustRecordService;
import org.skyer.stock.domain.service.ChangeLogService;
import org.skyer.stock.domain.service.saga.StockService;
import org.skyer.stock.domain.vo.AdjustDetailResVO;
import org.skyer.stock.domain.vo.AdjustRecordPageResVO;
import org.skyer.stock.domain.vo.AdjustRecordResVO;
import org.skyer.stock.domain.vo.RpcGoodsResVO;
import org.skyer.stock.infra.constant.ObjectConstant;
import org.skyer.stock.infra.convertor.AdjustRecordConvertor;
import org.skyer.stock.infra.feign.GoodsSkuRemoteService;

/**
 * 库存调整单应用服务默认实现
 *
 * @author lixiaoyang 2021-08-02 16:44:33
 */
@Service
public class AdjustRecordServiceImpl implements AdjustRecordService {
    private static final Logger logger = LoggerFactory.getLogger(AdjustRecordServiceImpl.class);
    @Autowired
    private StockService stockService;
    @Autowired
    private CodeRuleBuilder codeRuleBuilder;
    @Autowired
    private ChangeLogService changeLogService;
    @Autowired
    private ChangeLogRepository changeLogRepository;
    @Autowired
    private WarehouseRepository warehouseRepository;
    @Autowired
    private AdjustRecordConvertor adjustRecordConvertor;
    @Autowired
    private GoodsSkuRemoteService goodsSkuRemoteService;
    @Autowired
    private AdjustRecordRepository adjustRecordRepository;
    @Autowired
    private AdjustDetailRepository adjustDetailRepository;
    @Autowired
    private GoodsStockDetailRepository goodsStockDetailRepository;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void createOrUpdateAdjustRecord(AdjustRecordReqDTO adjustRecordReqDTO) {
        AdjustRecord adjustRecord = new AdjustRecord();
        if (adjustRecordReqDTO.getId() != null) {
            adjustRecord = Optional.ofNullable(adjustRecordRepository.getOne(adjustRecordReqDTO.getId(), Optional.ofNullable(DetailsHelper.getUserDetails()).map(CustomUserDetails::getTenantId).orElse(0L))).orElse(new AdjustRecord());
        }
        adjustRecord.setAdjustCode(codeRuleBuilder.generateCode(CodeConstants.Level.TENANT, 0L, ObjectConstant.CodeRule.ADJUST_RECORD_CODE, CodeConstants.CodeRuleLevelCode.CUSTOM, ObjectConstant.LevelValue.ADJUST_RECORD_CODE, null));
        adjustRecord.setWarehouseId(adjustRecordReqDTO.getWarehouseId());
        adjustRecord.setTenantId(Optional.ofNullable(DetailsHelper.getUserDetails()).map(CustomUserDetails::getTenantId).orElse(0L));
        adjustRecord.setAuditStatus(ObjectConstant.AuditStatus.WAIT_STATUS);
        adjustRecord.setRemarks(adjustRecordReqDTO.getRemark());
        adjustRecord.setLogicStatus(Boolean.FALSE);
        adjustRecord.setRealStatus(Boolean.FALSE);
        List<AdjustDetailsReqDTO> adjustDetailsReqDTOList = adjustRecordReqDTO.getAdjustDetailsReqDTOList();
        adjustDetailsReqDTOList.forEach(adjustDetailsReqDTO -> {
            adjustDetailsReqDTO.setTenantId(Optional.ofNullable(DetailsHelper.getUserDetails()).map(CustomUserDetails::getTenantId).orElse(0L));
        });
        if (adjustRecordReqDTO.getId() == null) {
            adjustRecordRepository.insertSelective(adjustRecord);
            adjustRecordReqDTO.setId(adjustRecord.getId());
        } else {
            adjustRecordRepository.updateByPrimaryKey(adjustRecord);
        }
        adjustDetailRepository.createOrUpdateAdjustDetail(adjustDetailsReqDTOList, adjustRecord.getId());
    }

    @Override
    @ProcessCacheValue
    public Page<AdjustRecordPageResVO> pageAdjustRecord(AdjustPageReqDTO adjustPageReqDTO) {
        Long tenantId = Optional.ofNullable(DetailsHelper.getUserDetails()).map(CustomUserDetails::getTenantId).orElse(0L);
        return adjustRecordRepository.pageAdjustRecord(adjustPageReqDTO, tenantId);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void auditAdjustRecord(Long adjustId, String status) {
        AdjustRecord adjustRecord = adjustRecordRepository.selectByPrimaryKey(adjustId);

        if (!ObjectConstant.AuditStatus.WAIT_STATUS.equals(adjustRecord.getAuditStatus())) {
            throw new CommonException("调整单已经审核");
        }
        if (ObjectConstant.AuditStatus.FAIL_STATUS.equals(status)) {
            auditFailAdjustRecord(adjustRecord);
        }
        if (ObjectConstant.AuditStatus.SUCCESS_STATUS.equals(status)) {
            Long structureId = 0L;
            adjustRecord.setStructureId(structureId);
            // 审核成功，对逻辑仓进行数量处理
            auditSuccessRecord(adjustRecord);
        }
    }

    @Override
    public AdjustRecordResVO adjustDetail(Long adjustId) {
        AdjustRecord adjustRecord = adjustRecordRepository.selectByPrimaryKey(adjustId);
        AdjustRecordResVO adjustRecordResVO = new AdjustRecordResVO();
        adjustRecordResVO.setAdjustCode(adjustRecord.getAdjustCode());
        Warehouse warehouse = Optional.ofNullable(warehouseRepository.selectByPrimaryKey(adjustRecord.getWarehouseId())).orElse(new Warehouse());
        adjustRecordResVO.setWarehouseCode(warehouse.getCode());
        adjustRecordResVO.setWarehouseName(warehouse.getName());
        List<AdjustDetail> adjustDetails = adjustDetailRepository.queryAdjustDetailList(adjustRecord.getId());
        List<String> skuCodeList = adjustDetails.stream().map(AdjustDetail::getSkuCode).distinct().collect(Collectors.toList());
        if (CollectionUtils.isEmpty(skuCodeList)) {
            return adjustRecordResVO;
        }
        ResponseEntity<List<RpcGoodsResVO>> listResponseEntity = goodsSkuRemoteService.queryRPCGoodsList(skuCodeList);
        List<RpcGoodsResVO> rpcGoodsResVOS = listResponseEntity.getBody();
        Map<String, RpcGoodsResVO> rpcGoodsMap = rpcGoodsResVOS.stream().collect(Collectors.toMap(RpcGoodsResVO::getSkuCode, Function.identity(), (o1, o2) -> o1));
        adjustRecordResVO.setAdjustDetailResVOList(adjustDetails.stream().map(adjustDetail -> {
            RpcGoodsResVO rpcGoodsResVO = Optional.ofNullable(rpcGoodsMap.get(adjustDetail.getSkuCode())).orElse(new RpcGoodsResVO());
            AdjustDetailResVO adjustDetailResVO = adjustRecordConvertor.adjustDetailToVo(adjustDetail);
            adjustDetailResVO.setSkuName(rpcGoodsResVO.getSkuName());
            adjustDetailResVO.setCategoryName(rpcGoodsResVO.getCategoryStr());
            adjustDetailResVO.setSpuName(rpcGoodsResVO.getSpuName());
            adjustDetailResVO.setSpuCode(rpcGoodsResVO.getSpuCode());
            return adjustDetailResVO;
        }).collect(Collectors.toList()));
        return adjustRecordResVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long exeSkuLogicQtyAdjust(Long adjustRecordId) {
        AdjustRecord adjustRecord = adjustRecordRepository.selectByPrimaryKey(adjustRecordId);
        Boolean logicStatus = adjustRecord.getLogicStatus();
        if (Boolean.TRUE.equals(logicStatus)) {
            // 已执行直接跳过
            return adjustRecordId;
        }
        Warehouse systemLogicWarehouse = warehouseRepository.getSystemLogicWarehouse();
        if (ObjectUtils.isEmpty(systemLogicWarehouse)) {
            systemLogicWarehouse = new Warehouse();
            systemLogicWarehouse.setStatus(Boolean.TRUE);
            systemLogicWarehouse.setDeleteFlag(ObjectConstant.DeleteFlag.NO);
            systemLogicWarehouse.setName("系统默认逻辑仓");
            systemLogicWarehouse.setSystemFlag(Boolean.TRUE);
            systemLogicWarehouse.setTypeCode(LOGIC);
            systemLogicWarehouse.setCode(codeRuleBuilder.generateCode(CodeConstants.Level.TENANT, 0L, ObjectConstant.CodeRule.QD_WAREHOUSE_CODE, CodeConstants.CodeRuleLevelCode.CUSTOM, ObjectConstant.LevelValue.QD_WAREHOUSE_CODE, null));
            systemLogicWarehouse.setTenantId(DEFAULT_TENANT_ID);
            warehouseRepository.insertSelective(systemLogicWarehouse);
        }
        String systemLogicWarehouseCode = systemLogicWarehouse.getCode();
        Long systemLogicWarehouseId = systemLogicWarehouse.getId();
        // 调整单编号
        String adjustCode = adjustRecord.getAdjustCode();
        List<AdjustDetail> adjustDetails = adjustDetailRepository.queryAdjustDetailList(adjustRecord.getId());

        List<GoodsStockDetail> insertDetails = new ArrayList<>();
        List<GoodsStockDetail> updateDetails = new ArrayList<>();
        // 审核成功调整库存
        for (AdjustDetail adjustDetail : adjustDetails) {
            String skuCode = adjustDetail.getSkuCode();
            if (ObjectUtils.isEmpty(skuCode)) {
                throw new CommonException("SKU CODE不能为空");
            }
            BigDecimal planNum;
            if (ObjectConstant.StockChange.REDUCE.equals(adjustDetail.getType())) {
                // 获取调整量
                planNum = adjustDetail.getPlanNum().negate();
            } else {
                planNum = adjustDetail.getPlanNum();
            }
            if (BigDecimal.ZERO.compareTo(planNum) != 0) {
                GoodsStockDetail stockDetail = goodsStockDetailRepository.getStockBySkuCodeAndWarehouseCode(systemLogicWarehouseCode, skuCode);
                boolean flag = false;
                if (ObjectUtils.isEmpty(stockDetail)) {
                    stockDetail = new GoodsStockDetail();
                    stockDetail.setTenantId(DEFAULT_TENANT_ID);
                    stockDetail.setWarehouseCode(systemLogicWarehouseCode);
                    stockDetail.setSkuCode(skuCode);
                    stockDetail.setStockNum(planNum);
                    stockDetail.setPreRealNum(BigDecimal.ZERO);
                    flag = true;
                } else {
                    BigDecimal stockNum = stockDetail.getStockNum();
                    stockDetail.setPreRealNum(stockNum);
                    stockNum = stockNum.add(planNum);
                    stockDetail.setStockNum(stockNum);
                }
                stockDetail.setWarehouseId(systemLogicWarehouseId);
                stockDetail.setChangeNum(planNum);
                if (flag) {
                    insertDetails.add(stockDetail);
                } else {
                    updateDetails.add(stockDetail);
                }
                logger.info("逻辑仓在库库存变动==============》" + stockDetail);
                adjustDetail.setRealityNum(planNum);
            }

        }
        List<ChangeLog> insertLogs = new ArrayList<>();
        // 写入库存
        if (insertDetails.size() > 0) {
            goodsStockDetailRepository.batchInsertSelective(insertDetails);
            insertLogs.addAll(changeLogService.getLogList(adjustCode, insertDetails));
        }
        // 更新库存
        if (updateDetails.size() > 0) {
            goodsStockDetailRepository.batchUpdateByPrimaryKeySelective(updateDetails);
            insertLogs.addAll(changeLogService.getLogList(adjustCode, insertDetails));
        }
        // 写入变动日志
        if (insertLogs.size() > 0) {
            changeLogRepository.batchInsertSelective(insertLogs);
        }
        if (adjustDetails.size() > 0) {
            adjustDetailRepository.batchUpdateByPrimaryKeySelective(adjustDetails);
        }
        // 设置为已经执行
        adjustRecord.setLogicStatus(Boolean.TRUE);
        adjustRecordRepository.updateByPrimaryKeySelective(adjustRecord);
        return adjustRecordId;
    }


    /**
     * 审核成功处理
     *
     * @author lixiaoyang
     * @date 2022/4/11 14:45
     */
    public void auditSuccessRecord(AdjustRecord adjustRecord) {
        Long adjustRecordId = adjustRecord.getId();
        adjustRecord.setAuditStatus(ObjectConstant.AuditStatus.SUCCESS_STATUS);
        adjustRecordRepository.updateByPrimaryKeySelective(adjustRecord);
        List<AdjustDetail> adjustDetails = adjustDetailRepository.queryAdjustDetailList(adjustRecordId);
        adjustDetails.forEach(adjustDetail -> {
            String skuCode = adjustDetail.getSkuCode();
            if (ObjectUtils.isEmpty(skuCode)) {
                throw new CommonException("SKU CODE不能为空");
            }
        });
        // 调用saga异步开启库存调整
        stockService.doSkuQtyAdjust(adjustRecordId);
    }


    /**
     * 审核拒绝，调整明细改成取消
     *
     * @author lixiaoyang
     * @date 2022/4/11 15:26
     */
    public void auditFailAdjustRecord(AdjustRecord adjustRecord) {
        List<AdjustDetail> adjustDetails = adjustDetailRepository.queryAdjustDetailList(adjustRecord.getId());
        adjustRecord.setAuditStatus(ObjectConstant.AuditStatus.FAIL_STATUS);
        adjustDetails.forEach(adjustDetail -> {
            adjustDetail.setCancelNum(adjustDetail.getPlanNum());
        });
        adjustRecordRepository.updateByPrimaryKeySelective(adjustRecord);
        adjustDetailRepository.batchUpdateByPrimaryKeySelective(adjustDetails);
    }

}
