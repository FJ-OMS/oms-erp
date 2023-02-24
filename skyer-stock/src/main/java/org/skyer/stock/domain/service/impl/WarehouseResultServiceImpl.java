package org.skyer.stock.domain.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.skyer.stock.domain.entity.WarehouseResult.STATUS_ALL_SUCCESS;

import org.skyer.boot.platform.code.builder.CodeRuleBuilder;
import org.skyer.boot.platform.code.constant.CodeConstants;
import org.skyer.boot.platform.lov.annotation.ProcessLovValue;
import org.skyer.core.domain.Page;
import org.skyer.mybatis.pagehelper.domain.PageRequest;
import org.skyer.mybatis.pagehelper.domain.Sort;
import org.skyer.stock.api.dto.WarehouseResultPageQueryDTO;
import org.skyer.stock.api.dto.WarehouseWarrantDetailDTO;
import org.skyer.stock.api.dto.WarehouseWarrantResultDTO;
import org.skyer.stock.domain.entity.WarehouseResult;
import org.skyer.stock.domain.entity.WarehouseResultDetail;
import org.skyer.stock.domain.entity.WarehouseWarrant;
import org.skyer.stock.domain.entity.WarehouseWarrantDetail;
import org.skyer.stock.domain.repository.WarehouseResultDetailRepository;
import org.skyer.stock.domain.repository.WarehouseResultRepository;
import org.skyer.stock.domain.repository.WarehouseWarrantDetailRepository;
import org.skyer.stock.domain.repository.WarehouseWarrantRepository;
import org.skyer.stock.domain.service.WarehouseResultService;
import org.skyer.stock.domain.vo.WarehouseResultInfoVO;
import org.skyer.stock.domain.vo.WarehouseResultQtyVO;
import org.skyer.stock.domain.vo.WarehouseResultVO;
import org.skyer.stock.infra.constant.ObjectConstant;

/**
 * 入库结果单应用服务默认实现
 *
 * @author xurongfa 2022-04-26 14:10:52
 */
@Service
public class WarehouseResultServiceImpl implements WarehouseResultService {
    @Autowired
    private CodeRuleBuilder codeRuleBuilder;
    @Autowired
    private WarehouseWarrantRepository warehouseWarrantRepository;
    @Autowired
    private WarehouseResultRepository warehouseResultRepository;
    @Autowired
    private WarehouseResultDetailRepository warehouseResultDetailRepository;
    @Autowired
    private WarehouseWarrantDetailRepository warehouseWarrantDetailRepository;

    @Override
    @ProcessLovValue
    public Page<WarehouseResultVO> page(WarehouseResultPageQueryDTO dto) {
        Sort sort = dto.getSort();
        if (ObjectUtils.isEmpty(sort)) {
            sort = new Sort(Sort.Direction.DESC, WarehouseResult.FIELD_CREATION_DATE);
        }
        PageRequest pageRequest = new PageRequest(dto.getPage(), dto.getSize(), sort);

        return warehouseResultRepository.page(pageRequest, dto);

    }

    @Override
    @ProcessLovValue
    public WarehouseResultInfoVO detail(Long id, String code) {
        if (ObjectUtils.isEmpty(id) && ObjectUtils.isEmpty(code)) {
            return null;
        }
        WarehouseResult condition = new WarehouseResult();
        condition.setId(id);
        condition.setCode(code);

        WarehouseResult warehouseResult = warehouseResultRepository.selectOne(condition);
        if (ObjectUtils.isEmpty(warehouseResult)) {
            return null;
        }
        WarehouseResultInfoVO warehouseResultInfoVO = new WarehouseResultInfoVO();
        BeanUtils.copyProperties(warehouseResult, warehouseResultInfoVO);

        // 统计商品相关数量
        WarehouseResultQtyVO qtyVO = warehouseResultDetailRepository.statsQty(warehouseResult.getId());
        if (ObjectUtils.isEmpty(qtyVO)) {
            warehouseResultInfoVO.setAlreadyQty(BigDecimal.ZERO);
        } else {
            warehouseResultInfoVO.setAlreadyQty(qtyVO.getAlreadyQty());
        }

        return warehouseResultInfoVO;
    }

    @Override
    public Long generateWarehouseResult(WarehouseWarrantResultDTO dto) {
        List<WarehouseWarrantDetailDTO> detailList = dto.getDetailList();
        if (ObjectUtils.isEmpty(detailList)) {
            return null;
        }
        Long wwId = dto.getWwId();
        WarehouseWarrant warehouseWarrant = warehouseWarrantRepository.selectByPrimaryKey(wwId);
        Long tenantId = warehouseWarrant.getTenantId();
        WarehouseResult warehouseResult = new WarehouseResult();
        BeanUtils.copyProperties(dto, warehouseResult);
        warehouseResult.setWarehouseWarrantId(wwId);
        warehouseResult.setWarehouseWarrantCode(dto.getWwCode());
        warehouseResult.setStatus(STATUS_ALL_SUCCESS);
        warehouseResult.setCode(codeRuleBuilder.generateCode(CodeConstants.Level.TENANT, tenantId, ObjectConstant.CodeRule.QD_WAREHOUSE_CODE, CodeConstants.CodeRuleLevelCode.CUSTOM, ObjectConstant.LevelValue.QD_WAREHOUSE_CODE, null));
        int i = warehouseResultRepository.insertSelective(warehouseResult);
        if (i > 0) {
            warehouseWarrant.setRealNoticeCode(warehouseResult.getCode());
            warehouseWarrantRepository.updateByPrimaryKey(warehouseWarrant);
            List<WarehouseWarrantDetail> warehouseWarrantDetailList = warehouseWarrantDetailRepository.getAllByWarehouseWarrantId(wwId);
            Map<String, WarehouseWarrantDetail> skuMap = warehouseWarrantDetailList
                    .stream()
                    .collect(Collectors.toMap(WarehouseWarrantDetail::getSkuCode, Function.identity(), (m1, m2) -> m1));


            Long wrId = warehouseResult.getId();
            List<WarehouseResultDetail> insertList = new ArrayList<>();
            List<WarehouseWarrantDetail> updateList = new ArrayList<>();
            for (WarehouseWarrantDetailDTO warehouseWarrantDetailDTO : detailList) {
                WarehouseResultDetail resultDetail = new WarehouseResultDetail();
                BeanUtils.copyProperties(warehouseWarrantDetailDTO, resultDetail);
                resultDetail.setWarehouseResultId(wrId);
                BigDecimal plannedQty = warehouseWarrantDetailDTO.getPlannedQty();
                resultDetail.setAlreadyQty(plannedQty);
                insertList.add(resultDetail);

                String skuCode = warehouseWarrantDetailDTO.getSkuCode();
                WarehouseWarrantDetail warrantDetail = skuMap.get(skuCode);
                if (ObjectUtils.isEmpty(warrantDetail)) {
                    continue;
                }
                BigDecimal alreadyInputQty = Optional.ofNullable(warrantDetail.getAlreadyInputQty()).orElse(BigDecimal.ZERO);
                warrantDetail.setAlreadyInputQty(alreadyInputQty.add(plannedQty));
                updateList.add(warrantDetail);
            }
            if (insertList.size() > 0) {
                warehouseResultDetailRepository.batchInsertSelective(insertList);
            }
            if (updateList.size() > 0) {
                warehouseWarrantDetailRepository.batchUpdateByPrimaryKeySelective(updateList);
            }
        }
        return warehouseResult.getId();
    }


}
