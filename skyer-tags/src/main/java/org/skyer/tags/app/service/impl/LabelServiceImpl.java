package org.skyer.tags.app.service.impl;

import org.skyer.core.cache.ProcessCacheValue;
import org.skyer.core.domain.Page;
import org.skyer.core.domain.PageInfo;
import org.skyer.core.exception.CommonException;
import org.skyer.mybatis.domian.Condition;
import org.skyer.mybatis.pagehelper.PageHelper;
import org.skyer.mybatis.pagehelper.domain.PageRequest;
import org.skyer.mybatis.util.Sqls;
import org.skyer.tags.api.dto.LabelListQueryDTO;
import org.skyer.tags.api.dto.LabelQueryDTO;
import org.skyer.tags.api.dto.LabelSaveDTO;
import org.skyer.tags.app.service.LabelService;
import org.skyer.tags.domain.entity.Label;
import org.skyer.tags.domain.repository.LabelRepository;
import org.skyer.tags.domain.vo.LabelVO;
import org.skyer.tags.infra.convertor.CommonConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 应用服务默认实现
 *
 * @author xurongfa 2022-01-22 11:33:07
 */
@Service
public class LabelServiceImpl implements LabelService {

    @Autowired
    private LabelRepository labelRepository;

    @Override
    @ProcessCacheValue
    public Page<LabelVO> page(PageRequest pageRequest, LabelQueryDTO labelQuery) {
        return labelRepository.page(pageRequest, labelQuery);
    }

    @Override
    public List<LabelVO> labelList(LabelListQueryDTO queryDTO) {
        Sqls sqls = Sqls.custom()
                .andEqualTo(Label.FIELD_SYSTEM_LABEL_FLAG, queryDTO.getSystemLabelFlag(), true)
                .andEqualTo(Label.FIELD_STATUS,true);
        List<Label> labelList  =  labelRepository.selectByCondition(Condition.builder(Label.class).where(sqls).build());
        return CommonConvertor.listConvertor(LabelVO.class, labelList);
    }

    /**
     * 批量修改状态
     * @param ids 标签id集合
     * @param status 状态
     */
    @Override
    public void batchUpdateByStatus(List<Long> ids, Boolean status) {
        List<Label> labelList = ids.stream().map(id -> {
            Label label = new Label();
            label.setId(id);
            label.setStatus(status);
            return label;
        }).collect(Collectors.toList());

        labelRepository.batchUpdateByPrimaryKeySelective(labelList);
    }

    /**
     * 批量删除
     * @param ids 标签id集合
     */
    @Override
    public void batchRemoveByIds(List<Long> ids) {

        List<Label> labelList = ids.stream().map(id -> {
            Label label = new Label();
            label.setId(id);
            return label;
        }).collect(Collectors.toList());

        labelRepository.batchDeleteByPrimaryKey(labelList);
    }

    @Override
    public Label save(LabelSaveDTO labelSaveDTO) {

        // 校验系统标签
        if (ObjectUtils.nullSafeEquals(true, labelSaveDTO.getSystemLabelFlag())){
            throw new CommonException("系统标签不允许新增修改");
        }

        validLabelName(labelSaveDTO);

        validTextContent(labelSaveDTO);

        Label label = CommonConvertor.beanConvert(Label.class, labelSaveDTO);

        if (ObjectUtils.isEmpty(labelSaveDTO.getId())){
            String labelCode  = "LB"+System.currentTimeMillis()+label.getLabelName().hashCode();
            label.setLabelCode(labelCode);
            labelRepository.insertSelective(label);
        }else{
            // 校验修改，标签类型是否变动
            validLabelType(label.getLabelType(), label.getId());

            labelRepository.updateByPrimaryKeySelective(label);
        }

        return label;
    }

    /**
     * 校验标签名称
     * @param labelSaveDTO 标签保存对象
     */
    private void validLabelName(LabelSaveDTO labelSaveDTO){
        int nameCount = labelRepository.selectCountByCondition(
                Condition.builder(Label.class)
                        .where(Sqls.custom()
                                .andEqualTo(Label.FIELD_LABEL_NAME, labelSaveDTO.getLabelName())
                                .andNotEqualTo(Label.FIELD_ID, labelSaveDTO.getId(), true))
                        .build());
        if (nameCount > 0){
            throw new CommonException("标签名称重复");
        }
    }

    /**
     * 校验修改时标签类型是否发生改变
     * @param type 标签类型
     * @param id 标签id
     */
    private void validLabelType(String type, Long id){
        Label label = labelRepository.selectByPrimaryKey(id);
        if (!ObjectUtils.isEmpty(label) && !ObjectUtils.nullSafeEquals(type, label.getLabelType())){
            throw new CommonException("标签类型不可修改");
        }
    }

    /**
     * 校验文本内容
     * @param labelSaveDTO 标签保存对象
     */
    private void validTextContent(LabelSaveDTO labelSaveDTO){
        String textContent = labelSaveDTO.getTextContent();
        int maxLength = 6;
        if (textContent.length() > maxLength){
            throw new CommonException("标签文字不允许超过6个字");
        }

        int textSystemCount = labelRepository.selectCountByCondition(Condition.builder(Label.class)
                .where(Sqls.custom()
                        .andEqualTo(Label.FIELD_TEXT_CONTENT, textContent)
                        .andEqualTo(Label.FIELD_SYSTEM_LABEL_FLAG, true))
                .build());
        if (textSystemCount > 1){
            throw new CommonException("文本内容不允许和系统标签内容一样");
        }

    }
}
