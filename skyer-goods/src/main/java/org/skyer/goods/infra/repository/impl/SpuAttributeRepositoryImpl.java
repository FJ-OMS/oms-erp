package org.skyer.goods.infra.repository.impl;

import org.skyer.core.exception.CommonException;
import org.skyer.goods.api.dto.AttrListRequestDTO;
import org.skyer.goods.domain.entity.SpuAttributeValue;
import org.skyer.goods.domain.repository.SpuAttributeValueRepository;
import org.skyer.goods.infra.constant.ObjectConstant;
import org.skyer.mybatis.base.impl.BaseRepositoryImpl;
import org.skyer.goods.domain.entity.SpuAttribute;
import org.skyer.goods.domain.repository.SpuAttributeRepository;
import org.skyer.mybatis.domian.Condition;
import org.skyer.mybatis.util.Sqls;

import cn.hutool.core.collection.CollUtil;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 属性表 资源库实现
 *
 * @author linzhuanghuang@ebc.com 2021-12-28 16:40:03
 */
@Component
public class SpuAttributeRepositoryImpl extends BaseRepositoryImpl<SpuAttribute> implements SpuAttributeRepository {


	@Autowired
	private SpuAttributeValueRepository spuAttributeValueRepository;

	@Override
	public void saveAttr(List<AttrListRequestDTO> attrs, Long spuId) {
		attrs.forEach(item->{
			if (!ObjectConstant.FORM_TYPE_TEXT.equals(item.getSubmissionsType())) {
				 if (CollUtil.isEmpty(item.getValues())){
					 throw new CommonException(item.getAttributeName()+"该文本类型至少需要一个属性值");
				 }
			}
		});
		if (CollUtil.isNotEmpty(attrs) && Boolean.TRUE.equals(checkRepeatCode(attrs))){
			throw new CommonException("属性编码重复");
		}

		List<SpuAttribute> attributeList = attrs.stream().map(item -> {
			SpuAttribute attribute = new SpuAttribute();
			BeanUtils.copyProperties(item, attribute);
			attribute.setName(item.getAttributeName());
			attribute.setSubmissionsType(item.getSubmissionsType());
			attribute.setDeleteFlag(ObjectConstant.DeleteFlag.NO);
			attribute.setSpuId(spuId);
			return attribute;
		}).collect(Collectors.toList());
		this.batchInsert(attributeList);
		attributeList.forEach(x->{
			List<SpuAttributeValue> values = x.getValues();
			if (CollUtil.isNotEmpty(values) && Boolean.TRUE.equals(checkRepeatValue(values))){
				throw new CommonException("属性值存在重复");
			}
			if (CollUtil.isNotEmpty(values)) {
				values.forEach(y -> {
					SpuAttributeValue value = new SpuAttributeValue();
					BeanUtils.copyProperties(y, value);
					value.setAttributeId(x.getId());
					value.setSpuId(spuId);
					value.setDeleteFlag(ObjectConstant.DeleteFlag.NO);
					spuAttributeValueRepository.insertSelective(value);
				});
			}
		});

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteAttribute(List<Long> ids) {
		String s1 = ids.stream().
				reduce(new StringBuilder(), (sb, s) -> sb.append(s).append(','), StringBuilder::append).toString();
		s1 = s1.substring(0,s1.length()-1);
		List<SpuAttribute> select = this.selectByIds(s1);
		if (CollUtil.isNotEmpty(select)){
			List<SpuAttribute> attributeList = select.stream().map(item -> {
				SpuAttribute spuAttribute = new SpuAttribute();
				spuAttribute.setId(item.getId());
				spuAttribute.setDeleteFlag(ObjectConstant.DeleteFlag.YES);
				return spuAttribute;
			}).collect(Collectors.toList());
			this.batchUpdateByPrimaryKeySelective(attributeList);
			List<Long> attrIds = select.stream().map(SpuAttribute::getId).collect(Collectors.toList());

			List<SpuAttributeValue> values = spuAttributeValueRepository.selectByCondition(Condition.builder(SpuAttributeValue.class)
					.andWhere(Sqls.custom()
							.andIn(SpuAttributeValue.FIELD_ATTRIBUTE_ID, attrIds)
							.andEqualTo(SpuAttributeValue.FIELD_DELETE_FLAG, ObjectConstant.DeleteFlag.NO)).build());

			List<SpuAttributeValue> valueList = values.stream().map(item -> {
				SpuAttributeValue spuAttributes = new SpuAttributeValue();
				spuAttributes.setId(item.getId());
				spuAttributes.setDeleteFlag(ObjectConstant.DeleteFlag.YES);
				return spuAttributes;
			}).collect(Collectors.toList());
			spuAttributeValueRepository.batchUpdateByPrimaryKeySelective(valueList);
		}


	}

	private Boolean checkRepeatCode(List<AttrListRequestDTO> attrs) {
		List<String> collectionOptionList;
		collectionOptionList=attrs.stream().filter(x->StringUtils.isNotBlank(x.getCode())).map(AttrListRequestDTO::getCode).collect(Collectors.toList());
		Set<String> set = new HashSet<>(collectionOptionList);
		return collectionOptionList.size() != set.size();
	}

	private Boolean checkRepeatValue(List<SpuAttributeValue> values) {
		List<String> collectionOptionList;
		collectionOptionList=values.stream().filter(x->StringUtils.isNotBlank(x.getValue())).map(SpuAttributeValue::getValue).collect(Collectors.toList());
		Set<String> set = new HashSet<>(collectionOptionList);
		return collectionOptionList.size() != set.size();
	}
}
