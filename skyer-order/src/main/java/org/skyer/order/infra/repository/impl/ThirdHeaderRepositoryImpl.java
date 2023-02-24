package org.skyer.order.infra.repository.impl;

import org.skyer.core.exception.CommonException;
import org.skyer.mybatis.base.impl.BaseRepositoryImpl;
import org.skyer.mybatis.domian.Condition;
import org.skyer.mybatis.util.Sqls;
import org.skyer.order.domain.entity.Header;
import org.skyer.order.domain.entity.ThirdHeader;
import org.skyer.order.domain.repository.ThirdHeaderRepository;
import org.skyer.order.infra.mapper.ThirdHeaderMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 第三方订单头 资源库实现
 *
 * @author zhanglongbing@ebc.com 2022-04-20 10:15:56
 */
@Component
public class ThirdHeaderRepositoryImpl extends BaseRepositoryImpl<ThirdHeader> implements ThirdHeaderRepository {


	@Autowired
	private ThirdHeaderMapper thirdHeaderMapper;

	@Override
	public ThirdHeader queryByOutOrderNo(String outOrderNo) {
		List<ThirdHeader> headers = thirdHeaderMapper.selectByCondition(Condition.builder(ThirdHeader.class)
				.andWhere(Sqls.custom().andEqualTo(ThirdHeader.FIELD_OUTER_ORDER_NO, outOrderNo)).build());
		if (CollectionUtils.isEmpty(headers)){
			return null;
		}
		if (headers.size()>1){
			throw new CommonException(outOrderNo+  "该外部订单数据不唯一");
		}
		return headers.get(0);
	}
}
