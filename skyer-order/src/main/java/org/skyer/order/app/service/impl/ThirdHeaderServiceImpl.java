package org.skyer.order.app.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import org.skyer.boot.customize.util.MD5Utils;
import org.skyer.boot.platform.code.builder.CodeRuleBuilder;
import org.skyer.boot.platform.code.constant.CodeConstants;
import org.skyer.boot.platform.lov.annotation.ProcessLovValue;
import org.skyer.core.domain.Page;
import org.skyer.core.exception.CommonException;
import org.skyer.mybatis.domian.Condition;
import org.skyer.mybatis.util.Sqls;
import org.skyer.order.api.dto.*;
import org.skyer.order.api.es.SearchThirdOrderEsParam;
import org.skyer.order.api.vo.*;
import org.skyer.order.app.dto.OrderLineWorkDTO;
import org.skyer.order.app.service.ThirdHeaderService;
import org.skyer.order.app.service.ThirdOrderEsService;
import org.skyer.order.app.service.saga.ThirdOrderForWardSagaService;
import org.skyer.order.domain.entity.*;
import org.skyer.order.domain.repository.HeaderRepository;
import org.skyer.order.domain.repository.ThirdHeaderRepository;
import org.skyer.order.domain.repository.ThirdItemRepository;
import org.skyer.order.domain.repository.ThirdReceiverInfoRepository;
import org.skyer.order.infra.common.CommonConstants;
import org.skyer.order.infra.convertor.ThirdHeaderConvertor;
import org.skyer.order.infra.feign.dto.SearchGiftDTO;
import org.skyer.order.infra.util.CollectorsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 第三方订单头应用服务默认实现
 *
 * @author zhanglongbing@ebc.com 2022-04-20 10:15:56
 */
@Service
public class ThirdHeaderServiceImpl implements ThirdHeaderService {
    @Autowired
    private ThirdHeaderRepository thirdHeaderRepository;
    @Autowired
    private CodeRuleBuilder codeRuleBuilder;
    @Autowired
    private ThirdHeaderConvertor thirdHeaderConvertor;
    @Autowired
    private ThirdReceiverInfoRepository thirdReceiverInfoRepository;
    @Autowired
    private ThirdItemRepository thirdItemRepository;
    @Autowired
    private ThirdOrderEsService thirdOrderEsService;
    @Autowired
    private ThirdOrderForWardSagaService thirdOrderForWardSagaService;
    @Autowired
    private HeaderRepository headerRepository;


    @Override
    public void createOuterOrderBpmn(ThirdOrderTableDTO thirdOrderTableDto) {
        //三方订单号+订单类型 唯一校验
        this.checkOnlyOrder(thirdOrderTableDto.getThirdOrderBaseInfoDTO().getOuterOrderNo(), thirdOrderTableDto.getThirdOrderBaseInfoDTO().getBusinessType());
        thirdOrderForWardSagaService.createOuterOrder(thirdOrderTableDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderTableDTO  createThirdOrder(ThirdOrderTableDTO thirdOrderTableDto) {
        // //商品数量校验
        // this.checkQuantity(thirdOrderTableDto.getThirdOrderItemDtoList());
        // //金额校验
        // this.checkOrderAmount(thirdOrderTableDto.getThirdOrderPaymentInfoDTO(), thirdOrderTableDto.getThirdOrderItemDtoList());
        //创建第三方头信息
        ThirdHeader thirdHeader = thirdHeaderConvertor.toThirdHeader(thirdOrderTableDto.getThirdOrderBaseInfoDTO(),thirdOrderTableDto.getThirdOrderBuyerInfoDTO(),thirdOrderTableDto.getThirdOrderPaymentInfoDTO(),thirdOrderTableDto.getThirdOrderDiscountInfoDTO());
        //应当是内部单创建后回写回来
        // thirdHeader.setInnerOrderNo(codeRuleBuilder.generateCode(CodeConstants.Level.TENANT, 0L, CommonConstants.CodeRule.INNER_ORDER_NO, CodeConstants.CodeRuleLevelCode.CUSTOM, CommonConstants.LevelValue.INNER_ORDER_NO, null));
        thirdHeader.setGoodsQuantity((long)thirdOrderTableDto.getThirdOrderItemDtoList().stream().mapToInt(ThirdOrderItemDTO::getQuantity).sum());
        BigDecimal totalAmount  = thirdOrderTableDto.getThirdOrderItemDtoList().stream()
                .map(goods-> goods.getOuterSaleAmount().multiply(BigDecimal.valueOf(goods.getQuantity()))).reduce(BigDecimal.ZERO, BigDecimal::add);
        thirdHeader.setTotalAmount(totalAmount);
        // String  thirdOrderJson = JSON.toJSONString(thirdOrderTableDto);
        // thirdHeader.setOutJsonInfo(thirdOrderJson);
        // String md5String = MD5Utils.encrypt(thirdOrderJson);
        // thirdHeader.setOutJsonInfoMd5(md5String);
        //第三方数据入库
        thirdHeaderRepository.insertSelective(thirdHeader);
        //第三方收货人信息入库
        ThirdReceiverInfo thirdReceiverInfo = thirdHeaderConvertor.toThirdReceiverInfo(thirdOrderTableDto.getThirdOrderBuyerInfoDTO());
        thirdReceiverInfo.setInnerOrderNo(thirdHeader.getInnerOrderNo());
        thirdReceiverInfo.setOuterOrderNo(thirdHeader.getOuterOrderNo());
        thirdReceiverInfoRepository.insertSelective(thirdReceiverInfo);
        //第三方商品信息入库
        List<ThirdOrderItemDTO> thirdOrderItemDtoList = thirdOrderTableDto.getThirdOrderItemDtoList();
        List<ThirdItem> thirdItemList = thirdOrderItemDtoList.stream().map(thirdOrderItemDto ->{
            ThirdItem thirdItem = thirdHeaderConvertor.toThirdItem(thirdOrderItemDto);
            thirdItem.setInnerOrderNo(thirdHeader.getInnerOrderNo());
            thirdItem.setOuterOrderNo(thirdHeader.getOuterOrderNo());
            return thirdItem;
        }).collect(Collectors.toList());
        thirdItemRepository.batchInsertSelective(thirdItemList);

        return this.thirdInfoToInner(thirdOrderTableDto);
    }

    private OrderTableDTO thirdInfoToInner(ThirdOrderTableDTO thirdOrderTableDto){
        OrderTableDTO orderTableDTO = new OrderTableDTO();
        //基础信息
        OrderBaseInfoDTO orderBaseInfoDTO = thirdHeaderConvertor.toOrderBaseInfoDTO(thirdOrderTableDto.getThirdOrderBaseInfoDTO());
        orderBaseInfoDTO.setActionType(CommonConstants.OrderActionType.COMMON_RETURN);
        //买家信息
        OrderBuyerInfoDTO orderBuyerInfoDTO = thirdHeaderConvertor.toOrderBuyerInfoDTO(thirdOrderTableDto.getThirdOrderBuyerInfoDTO());
        //支付信息
        OrderPaymentInfoDTO orderPaymentInfoDTO = thirdHeaderConvertor.toOrderPaymentInfoDTO(thirdOrderTableDto.getThirdOrderPaymentInfoDTO());
        //商品信息
        List<OrderItemDTO> orderItemDTOList = thirdOrderTableDto.getThirdOrderItemDtoList().stream()
                .map(thirdOrderItemDTO -> thirdHeaderConvertor.toOrderItemDTO(thirdOrderItemDTO))
                .collect(Collectors.toList());

        orderTableDTO.setOrderBaseInfoDTO(orderBaseInfoDTO);
        orderTableDTO.setOrderBuyerInfoDTO(orderBuyerInfoDTO);
        orderTableDTO.setOrderPaymentInfoDTO(orderPaymentInfoDTO);
        orderTableDTO.setOrderItemDTOList(orderItemDTOList);
        return orderTableDTO;
    }

    /**
     * 校验商品数量
     * @param orderPaymentInfoDTO orderPaymentInfoDTO
     * @param orderItemDTOList orderItemDTOList
     */
    private void checkOrderAmount(ThirdOrderPaymentInfoDTO orderPaymentInfoDTO, List<ThirdOrderItemDTO> orderItemDTOList) {
        BigDecimal sumOrderItemAmount = orderItemDTOList.stream().collect(CollectorsUtil.summingBigDecimal(ThirdOrderItemDTO::getOuterSaleAmount));
        if (sumOrderItemAmount.compareTo(orderPaymentInfoDTO.getPayAmount()) != 0) {
            throw new CommonException("创建订单失败:商品第三方销售总价不等于支付价格");
        }
    }

    /**
     * 校验商品数量
     * @param orderItemDTOList orderItemDTOList
     */
    private void checkQuantity(List<ThirdOrderItemDTO> orderItemDTOList) {
        for (ThirdOrderItemDTO orderItemDTO : orderItemDTOList) {
            if (ObjectUtil.isNull(orderItemDTO.getQuantity()) || orderItemDTO.getQuantity() <= 0) {
                throw new CommonException("商品数量应该是大于0的整数");
            }
        }
    }
    /**
     * 校验三方订单号+订单类型 唯一
     * @param outerOrderNo outerOrderNo
     * @param businessType businessType
     */
    public void checkOnlyOrder(String outerOrderNo, String businessType) {
        List<ThirdHeader> thirdHeader =  thirdHeaderRepository.selectByCondition(Condition.builder(ThirdHeader.class)
                .andWhere(Sqls.custom().andEqualTo(ThirdHeader.FIELD_OUTER_ORDER_NO,outerOrderNo)
                        .andEqualTo(ThirdHeader.FIELD_BUSINESS_TYPE,businessType)).build());
        if (ObjectUtil.isNotEmpty(thirdHeader)){
            throw new CommonException("已经有履约中的内部订单，不可重复创建");
        }
    }

    @Override
    @ProcessLovValue
    public ThirdOrderDetailVO getThirdOrderDetail(Long id,String outerOrderNo) {
        ThirdHeader thirdHeader;
        if (ObjectUtil.isNotEmpty(id)){
            thirdHeader  = thirdHeaderRepository.selectByPrimaryKey(id);
        }else {
            ThirdHeader selectHeader = new ThirdHeader();
            selectHeader.setOuterOrderNo(outerOrderNo);
            thirdHeader = thirdHeaderRepository.selectOne(selectHeader);
        }

        if (ObjectUtil.isEmpty(thirdHeader)){
            throw new CommonException("第三方订单不存在");
        }
        ThirdOrderDetailVO thirdOrderDetailVO = new ThirdOrderDetailVO();
        Header header = headerRepository.queryHeaderByInnerOrderNo(thirdHeader.getInnerOrderNo());
        //查询收货人信息
        List<ThirdReceiverInfo> receiverInfoList = thirdReceiverInfoRepository.selectByCondition(Condition.builder(ThirdReceiverInfo.class)
                .andWhere(Sqls.custom()
                        .andEqualTo(ThirdReceiverInfo.FIELD_OUTER_ORDER_NO,thirdHeader.getOuterOrderNo())).build());
        if (ObjectUtil.isNotEmpty(receiverInfoList)){
            ThirdReceiverInfo receiverInfo = receiverInfoList.get(0);
            ThirdReceiverInfoVO thirdReceiverInfoVO = thirdHeaderConvertor.toThirdReceiverInfoVO(receiverInfo);
            thirdOrderDetailVO.setReceiverInfoVO(thirdReceiverInfoVO);
        }
        //查询商品信息
        List<ThirdItem> thirdItemList = thirdItemRepository.selectByCondition(Condition.builder(ThirdItem.class)
                .andWhere(Sqls.custom().andEqualTo(ThirdItem.FIELD_OUTER_ORDER_NO,thirdHeader.getOuterOrderNo())).build());
        if (ObjectUtil.isNotEmpty(thirdItemList)){
            List<ThirdItemVO> thirdItemVOList = thirdItemList.parallelStream().map(
                    thirdItem -> thirdHeaderConvertor.toThirdItemVO(thirdItem)
            ).collect(Collectors.toList());
            thirdOrderDetailVO.setThirdItemVO(thirdItemVOList);
        }

        ThirdAmountInfoVO amountInfoVO = thirdHeaderConvertor.toThirdAmountInfoVO(thirdHeader);
        ThirdPaymentInfoVO paymentInfoVO = thirdHeaderConvertor.toThirdPaymentInfoVO(thirdHeader);
        ThirdOrderInfoVO thirdOrderInfoVO = thirdHeaderConvertor.toThirdOrderInfoVO(thirdHeader);
        thirdOrderInfoVO.setOrderStatus(header.getOrderStatus());
        thirdOrderDetailVO.setOrderInfoVO(thirdOrderInfoVO);
        thirdOrderDetailVO.setAmountInfoVO(amountInfoVO);
        thirdOrderDetailVO.setPaymentInfoVO(paymentInfoVO);

        return thirdOrderDetailVO;
    }




}
