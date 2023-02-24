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
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.skyer.stock.infra.constant.ObjectConstant.OutType.OUT_TYPE_ORDER_SALES_OUT_STOCK;
import static org.skyer.stock.infra.constant.ObjectConstant.WarehouseTypeCode.LOGIC;

import org.skyer.boot.platform.code.builder.CodeRuleBuilder;
import org.skyer.boot.platform.code.constant.CodeConstants;
import org.skyer.boot.platform.lov.annotation.ProcessLovValue;
import org.skyer.core.cache.ProcessCacheValue;
import org.skyer.core.domain.Page;
import org.skyer.core.exception.CommonException;
import org.skyer.mybatis.pagehelper.domain.PageRequest;
import org.skyer.mybatis.pagehelper.domain.Sort;
import org.skyer.stock.api.dto.*;
import org.skyer.stock.domain.entity.OutboundDeliveryOrder;
import org.skyer.stock.domain.entity.OutboundDeliveryOrderDetail;
import org.skyer.stock.domain.entity.Warehouse;
import org.skyer.stock.domain.repository.OutboundDeliveryOrderDetailRepository;
import org.skyer.stock.domain.repository.OutboundDeliveryOrderRepository;
import org.skyer.stock.domain.repository.WarehouseRepository;
import org.skyer.stock.domain.service.OutboundDeliveryOrderService;
import org.skyer.stock.domain.service.saga.StockService;
import org.skyer.stock.domain.vo.OutboundDeliveryOrderInfoVO;
import org.skyer.stock.domain.vo.OutboundDeliveryOrderListVO;
import org.skyer.stock.infra.constant.ObjectConstant;

/**
 * 出库单应用服务默认实现
 *
 * @author lixiaoyang 2021-10-15 15:24:26
 */
@Service
public class OutboundDeliveryOrderServiceImpl implements OutboundDeliveryOrderService {
    private static final Logger logger = LoggerFactory.getLogger(OutboundDeliveryOrderServiceImpl.class);
    @Autowired
    private StockService stockService;
    @Autowired
    private CodeRuleBuilder codeRuleBuilder;
    @Autowired
    private WarehouseRepository warehouseRepository;
    @Autowired
    private OutboundDeliveryOrderRepository outboundDeliveryOrderRepository;
    @Autowired
    private OutboundDeliveryOrderDetailRepository outboundDeliveryOrderDetailRepository;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public OutboundDeliveryOrderDTO generateOutboundDeliveryOrder(OutboundDeliveryOrderInfoDTO dto) {
        logger.info("进入生成出库单方法===》" + dto);
        String orderCode = dto.getOrderCode();
        if (ObjectUtils.isEmpty(orderCode)) {
            throw new CommonException("内部订单号为空");
        }
        Long tenantId = dto.getTenantId();

        OutboundDeliveryOrder outboundDeliveryOrder = new OutboundDeliveryOrder();
        // 参数转换
        BeanUtils.copyProperties(dto, outboundDeliveryOrder);
        outboundDeliveryOrder.setCode(codeRuleBuilder.generateCode(CodeConstants.Level.TENANT, tenantId, ObjectConstant.CodeRule.QD_WAREHOUSE_CODE, CodeConstants.CodeRuleLevelCode.CUSTOM, ObjectConstant.LevelValue.QD_WAREHOUSE_CODE, null));
        outboundDeliveryOrder.setStatus(outboundDeliveryOrder.STATUS_TO_BE_AGREED_WMS);
        outboundDeliveryOrder.setOutType(OUT_TYPE_ORDER_SALES_OUT_STOCK);

        // 收货逻辑仓信息
        Long receivingLogicWarehouseId = outboundDeliveryOrder.getReceivingLogicWarehouseId();
        if (!ObjectUtils.isEmpty(receivingLogicWarehouseId)) {
            // 获取租户下所以仓库
            List<Warehouse> warehouseList = warehouseRepository.queryAllByTenantId(tenantId);
            // key = 仓库类型编码 value = ( key = 仓库ID value = 仓库信息)
            Map<String, Map<Long, Warehouse>> warehouseMap = warehouseList.stream().collect(
                    Collectors.groupingBy(
                            Warehouse::getTypeCode, Collectors.toMap(
                                    Warehouse::getId, Function.identity())));
            Map<Long, Warehouse> map = warehouseMap.get(LOGIC);
            Warehouse warehouse = map.get(receivingLogicWarehouseId);
            if (!ObjectUtils.isEmpty(warehouse)) {
                outboundDeliveryOrder.setReceivingLogicWarehouseName(warehouse.getName());
                outboundDeliveryOrder.setReceivingLogicWarehouseCode(warehouse.getCode());
            }
        }

        // 生成主表
        int flag = outboundDeliveryOrderRepository.insertSelective(outboundDeliveryOrder);
        OutboundDeliveryOrderDTO returnDto = new OutboundDeliveryOrderDTO();
        BeanUtils.copyProperties(outboundDeliveryOrder, returnDto);
        if (flag > 0) {
            logger.info("出库单主表插入成功");
            List<OutboundDeliveryOrderDetailLineDTO> detailList = dto.getDetailList();

            if (!ObjectUtils.isEmpty(detailList)) {
                Long odoId = outboundDeliveryOrder.getId();
                List<OutboundDeliveryOrderDetail> insertList = new ArrayList<>();
                for (OutboundDeliveryOrderDetailLineDTO detailDTO : detailList) {
                    List<Long> orderGoodsLineIds = detailDTO.getOrderGoodsLineIds();
                    int size = orderGoodsLineIds.size();
                    OutboundDeliveryOrderDetail detail = new OutboundDeliveryOrderDetail();
                    BeanUtils.copyProperties(detailDTO, detail);
                    detail.setOdoId(odoId);
                    detail.setPlannedQty(BigDecimal.valueOf(size));
                    // 参数初始化
                    detail.preset();
                    insertList.add(detail);
                }
                if (insertList.size() > 0) {
                    outboundDeliveryOrderDetailRepository.batchInsertSelective(insertList);
                    List<OutboundDeliveryOrderDetailDTO> returnDetailDTO = insertList.stream().map(model -> {
                        OutboundDeliveryOrderDetailDTO detailDTO = new OutboundDeliveryOrderDetailDTO();
                        BeanUtils.copyProperties(model, detailDTO);
                        return detailDTO;
                    }).collect(Collectors.toList());
                    returnDto.setDetailList(returnDetailDTO);
                }
                // saga生成
                stockService.acceptResultRecord(returnDto);
            } else {
                logger.error("包裹商品明细为空，回滚出库单主表");
                throw new CommonException("商品明细不能为空");
            }
        }
        logger.info("返回成功运行结果===>" + returnDto);
        return returnDto;
    }

    @Override
    @ProcessCacheValue
    @ProcessLovValue
    public Page<OutboundDeliveryOrderListVO> getPage(OutboundDeliveryOrderPageDTO dto) {
        Sort sort = dto.getSort();
        if (ObjectUtils.isEmpty(sort)) {
            sort = new Sort(Sort.Direction.DESC, OutboundDeliveryOrder.FIELD_CREATION_DATE);
        }

        PageRequest pageRequest = new PageRequest(dto.getPage(), dto.getSize(), sort);
        Page<OutboundDeliveryOrderListVO> page = outboundDeliveryOrderRepository.page(dto, pageRequest);
        List<OutboundDeliveryOrderListVO> content = page.getContent();
        content.forEach(x->{
            String resultCode = x.getResultCode();
            List<CodeAndIdDTO> resultCodeAndIdList = JSON.parseArray(resultCode, CodeAndIdDTO.class);
            if (resultCodeAndIdList == null){
                x.setResultCodeAndIdList(new ArrayList<>());
            }else{
                x.setResultCodeAndIdList(resultCodeAndIdList);
            }
            // 重置resultCode为空,避免id泄露
            x.setResultCode(null);
        });

        return page;
    }

    @Override
    @ProcessCacheValue
    @ProcessLovValue
    public OutboundDeliveryOrderInfoVO detail(Long id) {
        // 出库单详情
        OutboundDeliveryOrder outboundDeliveryOrder = outboundDeliveryOrderRepository.selectByPrimaryKey(id);
        OutboundDeliveryOrderInfoVO outboundDeliveryOrderInfoVO = new OutboundDeliveryOrderInfoVO();
        BeanUtils.copyProperties(outboundDeliveryOrder, outboundDeliveryOrderInfoVO);

        String resultCode = outboundDeliveryOrder.getResultCode();
        List<CodeAndIdDTO> resultCodeAndIdList = JSON.parseArray(resultCode, CodeAndIdDTO.class);
        if (resultCodeAndIdList == null) {
            outboundDeliveryOrderInfoVO.setResultCodeAndIdList(new ArrayList<>());
        } else {
            outboundDeliveryOrderInfoVO.setResultCodeAndIdList(resultCodeAndIdList);
        }

        return outboundDeliveryOrderInfoVO;
    }
}
