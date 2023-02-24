package org.skyer.channel.domain.repository;

import org.skyer.channel.api.dto.DealerListDTO;
import org.skyer.channel.domain.entity.Dealer;
import org.skyer.channel.domain.vo.DealerVO;
import org.skyer.core.domain.Page;
import org.skyer.mybatis.base.BaseRepository;
import org.skyer.mybatis.pagehelper.domain.PageRequest;

/**
 * @author: lzh
 * @date: 2021-06-17
 * @description: 经销商
 */
public interface DealerRepository extends BaseRepository<Dealer> {

	/**
	 * 分页查询经销商
	 * @param pageRequest
	 * @param dealerListDTO
	 * @return
	 */
	Page<Dealer> pageDealerList(PageRequest pageRequest, DealerListDTO dealerListDTO);
}
