package org.skyer.stock.domain.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.skyer.stock.domain.entity.WarehouseWarrant.INPUT_TYPE_SALES_RETURN_STORAGE;
import static org.skyer.stock.domain.entity.WarehouseWarrant.STATUS_INPUT_STOCK_PENDING;
import static org.skyer.stock.infra.constant.ObjectConstant.DEFAULT_TENANT_ID;
import static org.skyer.stock.infra.constant.ObjectConstant.FrontRecordType.FRONT_RECORD_TYPE_AFTER_SALE_2;

import org.skyer.boot.platform.code.builder.CodeRuleBuilder;
import org.skyer.boot.platform.code.constant.CodeConstants;
import org.skyer.boot.platform.lov.annotation.ProcessLovValue;
import org.skyer.core.domain.Page;
import org.skyer.mybatis.pagehelper.domain.PageRequest;
import org.skyer.mybatis.pagehelper.domain.Sort;
import org.skyer.stock.api.dto.*;
import org.skyer.stock.domain.entity.WarehouseWarrant;
import org.skyer.stock.domain.entity.WarehouseWarrantDetail;
import org.skyer.stock.domain.entity.WarehouseWarrantLogistics;
import org.skyer.stock.domain.repository.WarehouseWarrantDetailRepository;
import org.skyer.stock.domain.repository.WarehouseWarrantLogisticsRepository;
import org.skyer.stock.domain.repository.WarehouseWarrantRepository;
import org.skyer.stock.domain.service.WarehouseWarrantService;
import org.skyer.stock.domain.vo.WarehouseWarrantInfoLogisticsVO;
import org.skyer.stock.domain.vo.WarehouseWarrantInfoVO;
import org.skyer.stock.domain.vo.WarehouseWarrantQtyVO;
import org.skyer.stock.domain.vo.WarehouseWarrantVO;
import org.skyer.stock.infra.constant.ObjectConstant;
import org.skyer.stock.infra.convertor.CommonConvertor;

/**
 * 入库单应用服务默认实现
 *
 * @author lixiaoyang 2021-10-25 16:34:17
 */
@Service
public class WarehouseWarrantServiceImpl implements WarehouseWarrantService {
    @Autowired
    private CodeRuleBuilder codeRuleBuilder;
    @Autowired
    private WarehouseWarrantRepository warehouseWarrantRepository;
    @Autowired
    private WarehouseWarrantDetailRepository warehouseWarrantDetailRepository;
    @Autowired
    private WarehouseWarrantLogisticsRepository warehouseWarrantLogisticsRepository;

    @Override
    @ProcessLovValue
    public Page<WarehouseWarrantVO> page(WarehouseWarrantPageQueryDTO dto) {
        Sort sort = dto.getSort();
        if (ObjectUtils.isEmpty(sort)) {
            sort = new Sort(Sort.Direction.DESC, WarehouseWarrant.FIELD_CREATION_DATE);
        }
        PageRequest pageRequest = new PageRequest(dto.getPage(), dto.getSize(), sort);

        return warehouseWarrantRepository.page(pageRequest, dto);
    }

    @Override
    @ProcessLovValue
    public WarehouseWarrantInfoVO detail(Long id, String code) {
        if (Objects.isNull(id) && Objects.isNull(code)) {
            return null;
        }

        WarehouseWarrant warehouseWarrant = new WarehouseWarrant();
        warehouseWarrant.setId(id);
        warehouseWarrant.setCode(code);

        WarehouseWarrant result = warehouseWarrantRepository.selectOne(warehouseWarrant);
        if (ObjectUtils.isEmpty(result)) {
            return null;
        }
        // 参数转换
        WarehouseWarrantInfoVO vo = new WarehouseWarrantInfoVO();
        BeanUtils.copyProperties(result, vo);

        // 统计数量
        WarehouseWarrantQtyVO qtyVO = warehouseWarrantDetailRepository.statsQty(result.getId());
        if (ObjectUtils.isEmpty(qtyVO)) {
            vo.setAlreadyTotalQty(BigDecimal.ZERO);
            vo.setTotalQty(BigDecimal.ZERO);
        } else {
            vo.setAlreadyTotalQty(qtyVO.getAlreadyTotalQty());
            vo.setTotalQty(qtyVO.getTotalQty());
        }
        // 查询物流信息
        WarehouseWarrantLogistics warehouseWarrantLogistics = new WarehouseWarrantLogistics();
        warehouseWarrantLogistics.setWarehouseWarrantId(result.getId());
        List<WarehouseWarrantLogistics> logisticsList =
                warehouseWarrantLogisticsRepository.select(warehouseWarrantLogistics);
        if (ObjectUtils.isEmpty(logisticsList)) {
            vo.setLogisticsList(new ArrayList<>());
        } else {
            List<WarehouseWarrantInfoLogisticsVO> logisticsVOList =
                    CommonConvertor.listConvertor(WarehouseWarrantInfoLogisticsVO.class, logisticsList);
            vo.setLogisticsList(logisticsVOList);
        }

        return vo;
    }

    @Override
    public WarehouseWarrantResultDTO generateWarehouseWarrant(WarehouseWarrantSaveDTO dto) {
        WarehouseWarrantResultDTO resultDTO = new WarehouseWarrantResultDTO();
        List<WarehouseWarrantDetailDTO> detailList = dto.getDetailList();
        if (ObjectUtils.isEmpty(detailList)) {
            return resultDTO;
        }
        String frontRecordCode = dto.getFrontRecordCode();
        WarehouseWarrant pastRecordsWarehouseWarrant = warehouseWarrantRepository.getOneByFrontRecordTypeAndFrontRecordCode(FRONT_RECORD_TYPE_AFTER_SALE_2, frontRecordCode);
        if (!ObjectUtils.isEmpty(pastRecordsWarehouseWarrant)) {
            BeanUtils.copyProperties(pastRecordsWarehouseWarrant, resultDTO);
            resultDTO.setDetailList(detailList);
            return resultDTO;
        }
        BeanUtils.copyProperties(dto, resultDTO);
        Long tenantId = Optional.ofNullable(dto.getTenantId()).orElse(DEFAULT_TENANT_ID);
        WarehouseWarrant warrant = new WarehouseWarrant();
        // DTO转POJO
        BeanUtils.copyProperties(dto, warrant);
        warrant.setStatus(STATUS_INPUT_STOCK_PENDING);
        warrant.setInputType(INPUT_TYPE_SALES_RETURN_STORAGE);
        warrant.setFrontRecordType(FRONT_RECORD_TYPE_AFTER_SALE_2);
        warrant.setCode(codeRuleBuilder.generateCode(CodeConstants.Level.TENANT, tenantId, ObjectConstant.CodeRule.QD_WAREHOUSE_CODE, CodeConstants.CodeRuleLevelCode.CUSTOM, ObjectConstant.LevelValue.QD_WAREHOUSE_CODE, null));
        int i = warehouseWarrantRepository.insertSelective(warrant);
        if (i > 0) {
            Long wwId = warrant.getId();
            String wwCode = warrant.getCode();
            resultDTO.setWwId(wwId);
            resultDTO.setWwCode(wwCode);
            resultDTO.setInputType(warrant.getInputType());


            List<WarehouseWarrantDetail> insertList = new ArrayList<>();
            for (WarehouseWarrantDetailDTO warehouseWarrantDetailDTO : detailList) {
                WarehouseWarrantDetail detail = new WarehouseWarrantDetail();
                BeanUtils.copyProperties(warehouseWarrantDetailDTO, detail);
                detail.setWarehouseWarrantId(wwId);
                detail.initialize();
                insertList.add(detail);
            }

            List<WarehouseWarrantLogistics> insertLogisticsList = new ArrayList<>();
            List<WarehouseWarrantLogisticsDTO> logisticsList = dto.getLogisticsList();
            if (!ObjectUtils.isEmpty(logisticsList)) {
                for (WarehouseWarrantLogisticsDTO logisticsDTO : logisticsList) {
                    WarehouseWarrantLogistics logistics = new WarehouseWarrantLogistics();
                    BeanUtils.copyProperties(logisticsDTO, logistics);
                    logistics.setWarehouseWarrantId(wwId);
                    insertLogisticsList.add(logistics);
                }
            }

            if (insertLogisticsList.size() > 0) {
                warehouseWarrantLogisticsRepository.batchInsertSelective(insertLogisticsList);
            }

            if (insertList.size() > 0) {
                warehouseWarrantDetailRepository.batchInsertSelective(insertList);
            }
        }
        return resultDTO;
    }
}
