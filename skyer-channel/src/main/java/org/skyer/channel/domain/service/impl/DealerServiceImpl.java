package org.skyer.channel.domain.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.skyer.channel.api.dto.DealerDTO;
import org.skyer.channel.api.dto.DealerListDTO;
import org.skyer.channel.api.dto.ShopDeleteDTO;
import org.skyer.channel.domain.entity.Dealer;
import org.skyer.channel.domain.repository.DealerRepository;
import org.skyer.channel.domain.service.DealerService;
import org.skyer.channel.domain.vo.DealerVO;
import org.skyer.core.cache.ProcessCacheValue;
import org.skyer.core.domain.Page;
import org.skyer.core.oauth.CustomUserDetails;
import org.skyer.core.oauth.DetailsHelper;
import org.skyer.mybatis.pagehelper.domain.PageRequest;

/**
 * @author: lzh
 * @date: 2021-06-17
 * @description:
 */
@Service
public class DealerServiceImpl implements DealerService {

	private static final Logger logger = LoggerFactory.getLogger(DealerServiceImpl.class);
	@Autowired
	private DealerRepository dealerRepository;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean addDealer(Dealer dealer) {
		if (ObjectUtils.isEmpty(dealer.getDealerCode())){
			dealer.setDealerCode(UUID.randomUUID().toString().replace("-", ""));
		}
		CustomUserDetails userDetails = DetailsHelper.getUserDetails();
		logger.info("获取当前用户==============》" + userDetails);
		logger.info("获取当前用户租户ID==============》" + userDetails.getTenantId());
		Long tenantId = userDetails.getTenantId();
		dealer.setTenantId(tenantId);
		return dealerRepository.insert(dealer) > 0;
	}

	@Override
	public Boolean updateDealer(DealerDTO dealerDTO) {
		Dealer dealer = dealerRepository.selectByPrimaryKey(dealerDTO.getId());
		BeanUtils.copyProperties(dealerDTO,dealer);
		return dealerRepository.updateByPrimaryKey(dealer)>0;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean deleteById(Long id) {
		Dealer dealer = dealerRepository.selectByPrimaryKey(id);
		dealer.setDeleteFlag(true);
		return dealerRepository.updateByPrimaryKey(dealer) >0;
	}

	@Override
	@ProcessCacheValue
	public Dealer getDealerDetail(Long id) {
		return dealerRepository.selectByPrimaryKey(id);
	}

	@Override
	@ProcessCacheValue
	public Page<DealerVO> pageDealerList(PageRequest pageRequest, DealerListDTO dealerListDTO) {
		Page<Dealer> page = dealerRepository.pageDealerList(pageRequest, dealerListDTO);
		List<Dealer> content = page.getContent();
		List<DealerVO> list = new ArrayList<>();
		content.forEach(item->{
			DealerVO dealerVO = new DealerVO();
			BeanUtils.copyProperties(item,dealerVO);
			list.add(dealerVO);
		});
		Page<DealerVO> a = new Page<>();
		a.setContent(list);
		return a;
	}

	@Override
	public boolean operation(Long id, Boolean flag) {
		Dealer dealer = dealerRepository.selectByPrimaryKey(id);
		dealer.setStatus(flag);
		return dealerRepository.updateByPrimaryKey(dealer) > 0;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean deletes(ShopDeleteDTO shopDeleteDTO) {
		List<Long> ids = shopDeleteDTO.getIds();
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < ids.size(); i++) {
			stringBuilder.append(ids.get(i));
			if (i != (ids.size() - 1)) {
				stringBuilder.append(",");
			}
		}
		List<Dealer> dealarList = dealerRepository.selectByIds(stringBuilder.toString());
		for (Dealer dealar : dealarList) {
			dealar.setDeleteFlag(true);
		}
		// 执行软删
		dealerRepository.batchUpdateByPrimaryKey(dealarList);
		return true;
	}
}
