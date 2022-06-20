package org.skyer.order.infra.repository.impl;

import org.skyer.core.domain.Page;
import org.skyer.core.exception.CommonException;
import org.skyer.mybatis.base.impl.BaseRepositoryImpl;
import org.skyer.mybatis.domian.Condition;
import org.skyer.mybatis.pagehelper.PageHelper;
import org.skyer.mybatis.pagehelper.domain.PageRequest;
import org.skyer.mybatis.util.Sqls;
import org.skyer.order.api.dto.AccountCheckingOrderDTO;
import org.skyer.order.api.dto.AccountCheckingOrderQueryDTO;
import org.skyer.order.api.dto.OrderPageDTO;
import org.skyer.order.api.dto.OrderSaleDTO;
import org.skyer.order.api.vo.OrderPageVo;
import org.skyer.order.api.vo.OrderSaleDateVO;
import org.skyer.order.api.vo.OrderSaleReportVO;
import org.skyer.order.app.dto.SaleOrderOrderApiDTO;
import org.skyer.order.app.vo.ChannelSaleOrderVO;
import org.skyer.order.domain.entity.Header;
import org.skyer.order.domain.repository.HeaderRepository;
import org.skyer.order.infra.mapper.HeaderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 内部订单头 资源库实现
 *
 * @author wushaochuan 2022-01-18 14:34:53
 */
@Component
    public class HeaderRepositoryImpl extends BaseRepositoryImpl<Header> implements HeaderRepository {
    @Autowired
    private HeaderMapper headerMapper;

    @Override
    public List<Header> queryHeaderByInnerOrderNo(List<String> innerOrderNo) {
        List<Header> headers = headerMapper.selectByCondition(Condition.builder(Header.class)
                .andWhere(Sqls.custom().andIn(Header.FIELD_INNER_ORDER_NO, innerOrderNo)).build());
        if (CollectionUtils.isEmpty(headers)){
            return null;
        }
        return headers;
    }

    @Override
    public Header queryHeaderByOutNoAndType(String outerOrderNo, String businessType) {
        List<Header> headers = headerMapper.selectByCondition(Condition.builder(Header.class)
                .andWhere(Sqls.custom().andEqualTo(Header.FIELD_OUTER_ORDER_NO, outerOrderNo).andEqualTo(Header.FIELD_BUSINESS_TYPE, businessType)).build());
        if (CollectionUtils.isEmpty(headers)){
            return null;
        }
        if (headers.size()>1){
            throw new CommonException(outerOrderNo+" "+businessType+"该外部订单数据库不唯一");
        }
        return headers.get(0);
    }

    @Override
    public Header queryHeaderByInnerOrderNo(String innerOrderNo) {
        List<Header> headers = headerMapper.selectByCondition(Condition.builder(Header.class)
                .andWhere(Sqls.custom().andEqualTo(Header.FIELD_INNER_ORDER_NO, innerOrderNo)).build());
        if (CollectionUtils.isEmpty(headers)){
            return null;
        }
        return headers.get(0);
    }

    @Override
    public Page<OrderPageVo> orderPageList(PageRequest pageRequest, OrderPageDTO orderPageDTO) {
        return PageHelper.doPageAndSort(pageRequest, () -> headerMapper.getListPage(orderPageDTO));
    }

    @Override
    public List<AccountCheckingOrderDTO> getOrderList(AccountCheckingOrderQueryDTO dto) {
        return headerMapper.getOrderList(dto);
    }

    @Override
    public List<AccountCheckingOrderDTO> getAfterSaleList(AccountCheckingOrderQueryDTO dto) {
        return headerMapper.getAfterSaleList(dto);
    }

    @Override
    public List<OrderSaleReportVO> queryPartHeaderToSaleReport(OrderSaleDTO orderSaleDTO) {
        return headerMapper.queryPartHeaderToSaleReport(orderSaleDTO);
    }

    @Override
    public List<ChannelSaleOrderVO> queryPartChannelSaleReport(SaleOrderOrderApiDTO saleOrderOrderApiDTO) {
        return headerMapper.queryPartChannelSaleReport(saleOrderOrderApiDTO.getStartDate(), saleOrderOrderApiDTO.getEndDate(), saleOrderOrderApiDTO.getStoreCode());
    }

    @Override
    public List<OrderSaleDateVO> queryOrderSaleDateReport(OrderSaleDTO orderSaleDTO) {
        return headerMapper.queryOrderSaleDateReport(orderSaleDTO);
    }
}
