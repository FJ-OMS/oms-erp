package org.skyer.stock.infra.repository.impl;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.skyer.mybatis.base.impl.BaseRepositoryImpl;
import org.skyer.mybatis.domian.Condition;
import org.skyer.mybatis.util.Sqls;
import org.skyer.stock.api.dto.AdjustDetailsReqDTO;
import org.skyer.stock.domain.entity.AdjustDetail;
import org.skyer.stock.domain.repository.AdjustDetailRepository;
import org.skyer.stock.infra.constant.ObjectConstant;
import org.skyer.stock.infra.mapper.AdjustDetailMapper;
import org.skyer.stock.infra.mapper.AdjustRecordMapper;

/**
 * 库存调整单详情 资源库实现
 *
 * @author lixiaoyang 2021-08-02 16:44:33
 */
@Component
public class AdjustDetailRepositoryImpl extends BaseRepositoryImpl<AdjustDetail> implements AdjustDetailRepository {
    @Autowired
    private AdjustDetailMapper adjustDetailMapper;
    @Override
    public void createOrUpdateAdjustDetail(List<AdjustDetailsReqDTO> adjustDetailsReqDTOList, Long adjustRecordId) {
        //先删除旧的
        delAdjustDetail(adjustRecordId);
        List<AdjustDetail> adjustDetailList = adjustDetailsReqDTOList.stream().map(adjustDetailsReqDTO -> {
            AdjustDetail adjustDetail = new AdjustDetail();
            adjustDetail.setAdjustId(adjustRecordId);
            adjustDetail.setPlanNum(adjustDetailsReqDTO.getNum());
            adjustDetail.setRealityNum(BigDecimal.ZERO);
            adjustDetail.setCancelNum(BigDecimal.ZERO);
            adjustDetail.setSkuCode(adjustDetailsReqDTO.getSkuCode());
            adjustDetail.setType(adjustDetailsReqDTO.getType());
            return adjustDetail;
        }).collect(Collectors.toList());
        this.batchInsertSelective(adjustDetailList);
    }

    @Override
    public List<AdjustDetail> queryAdjustDetailList(Long adjustId) {
        List<AdjustDetail> adjustDetails = adjustDetailMapper.selectByCondition(Condition.builder(AdjustDetail.class).andWhere(Sqls.custom()
                .andEqualTo(AdjustDetail.FIELD_ADJUST_ID, adjustId).andEqualTo(AdjustDetail.FIELD_DELETE_FLAG,ObjectConstant.DeleteFlag.NO)).build());
        return adjustDetails;
    }

    @Override
    public void delAdjustDetail(Long adjustRecordId) {
        List<AdjustDetail> adjustDetails = adjustDetailMapper.selectByCondition(Condition.builder(AdjustDetail.class).andWhere(Sqls.custom()
                .andEqualTo(AdjustDetail.FIELD_ADJUST_ID, adjustRecordId).andEqualTo(AdjustDetail.FIELD_DELETE_FLAG,ObjectConstant.DeleteFlag.NO)).build());
        if (CollectionUtils.isEmpty(adjustDetails)) {
            return;
        }
        this.batchDeleteByPrimaryKey(adjustDetails);
    }


}
