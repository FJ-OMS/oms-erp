package org.skyer.channel.infra.repository.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import org.skyer.channel.api.dto.DealerListDTO;
import org.skyer.channel.domain.entity.Dealer;
import org.skyer.channel.domain.repository.DealerRepository;
import org.skyer.channel.domain.vo.DealerVO;
import org.skyer.core.domain.Page;
import org.skyer.mybatis.base.impl.BaseRepositoryImpl;
import org.skyer.mybatis.domian.Condition;
import org.skyer.mybatis.pagehelper.PageHelper;
import org.skyer.mybatis.pagehelper.Select;
import org.skyer.mybatis.pagehelper.domain.PageRequest;
import org.skyer.mybatis.util.Sqls;

/**
 * @author: lzh
 * @date: 2021-06-18
 * @description:
 */
@Component
public class DealerRepositoryImpl extends BaseRepositoryImpl<Dealer> implements DealerRepository {
	@Override
	public Page<Dealer> pageDealerList(PageRequest pageRequest, DealerListDTO dealerListDTO) {

		return PageHelper.doPage(pageRequest, ()->
			 selectByCondition(Condition.builder(Dealer.class)
				   .andWhere(Sqls.custom()
						   .andLike(Dealer.DEALER_NAME, dealerListDTO.getDealerName(),true)
						   .andEqualTo(Dealer.STATUS,dealerListDTO.getStatus(),true)
						   .andEqualTo(Dealer.DELETEFLAG,false))
				   .build()) );

	}
}
