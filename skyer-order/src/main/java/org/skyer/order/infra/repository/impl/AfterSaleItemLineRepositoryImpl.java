package org.skyer.order.infra.repository.impl;

import cn.hutool.core.util.ObjectUtil;
import org.skyer.core.exception.CommonException;
import org.skyer.mybatis.base.impl.BaseRepositoryImpl;
import org.skyer.mybatis.domian.Condition;
import org.skyer.mybatis.util.Sqls;
import org.skyer.order.api.vo.AfterSaleItemLineVO;
import org.skyer.order.domain.entity.AfterSaleItemLine;
import org.skyer.order.domain.repository.AfterSaleItemLineRepository;
import org.skyer.order.infra.mapper.AfterSaleItemLineMapper;

import cn.hutool.core.collection.CollUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 售后单商品行 资源库实现
 *
 * @author lzh 2022-03-16 10:55:40
 */
@Component
public class AfterSaleItemLineRepositoryImpl extends BaseRepositoryImpl<AfterSaleItemLine> implements AfterSaleItemLineRepository {
    @Autowired
    private AfterSaleItemLineMapper afterSaleItemLineMapper;

    @Override
    public List<AfterSaleItemLineVO> returnLisByItemId(Long itemId) {
        List<AfterSaleItemLineVO> afterSaleItemLineVos =  afterSaleItemLineMapper.getReturnListByItemId(itemId);
        if (ObjectUtil.isEmpty(afterSaleItemLineVos)){
            return Collections.emptyList();
        }
        return afterSaleItemLineVos;
    }

    @Override
    public List<AfterSaleItemLine> returnListByItemIds(List<Long> itemIds) {
        return afterSaleItemLineMapper.getReturnListByItemIds(itemIds);
    }

    @Override
    public List<AfterSaleItemLine> returnGoodsListByItemIds(List<Long> itemIds) {
        return afterSaleItemLineMapper.getReturnGoodsListByItemIds(itemIds);
    }

    @Override
    public List<AfterSaleItemLineVO> returnGoodsListByItemId(Long itemId) {
        List<AfterSaleItemLineVO> afterSaleItemLineVos =  afterSaleItemLineMapper.getReturnGoodsListByItemId(itemId);
        if (ObjectUtil.isEmpty(afterSaleItemLineVos)){
            return Collections.emptyList();
        }
        return afterSaleItemLineVos;
    }

    @Override
    public List<AfterSaleItemLine> queryByInnerRefundOrderNo(String innerRefundOrderNo) {
        List<AfterSaleItemLine> items = afterSaleItemLineMapper.selectByCondition(Condition.builder(AfterSaleItemLine.class).andWhere(Sqls.custom().andEqualTo(AfterSaleItemLine.FIELD_INNER_REFUND_NO, innerRefundOrderNo)).build());
        if (CollUtil.isEmpty(items)) {
            return Collections.emptyList();
        }
        return items;
    }

    @Override
    public List<AfterSaleItemLine> itemLineDetails(AfterSaleItemLine afterSaleItemLine) {
        if (ObjectUtil.isNull(afterSaleItemLine.getInnerRefundNo())){
            throw new CommonException("内部退款单号不能为空");
        }

        if (ObjectUtil.isNull(afterSaleItemLine.getOriginItemId())){
            throw new CommonException("原itemId不能为空");
        }

        List<AfterSaleItemLine> itemLines = afterSaleItemLineMapper.selectByCondition(Condition.builder(AfterSaleItemLine.class)
                .andWhere(Sqls.custom().andEqualTo(AfterSaleItemLine.FIELD_INNER_REFUND_NO, afterSaleItemLine.getInnerRefundNo())
                        .andEqualTo(AfterSaleItemLine.ORIGIN_ITEM_ID, afterSaleItemLine.getOriginItemId())).build());
        if (CollUtil.isEmpty(itemLines)) {
            return Collections.emptyList();
        }
        return itemLines;
    }
}
