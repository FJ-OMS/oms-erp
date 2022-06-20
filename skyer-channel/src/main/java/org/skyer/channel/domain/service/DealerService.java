package org.skyer.channel.domain.service;

import org.skyer.channel.api.dto.DealerDTO;
import org.skyer.channel.api.dto.DealerListDTO;
import org.skyer.channel.api.dto.ShopDeleteDTO;
import org.skyer.channel.domain.entity.Dealer;
import org.skyer.channel.domain.vo.DealerVO;
import org.skyer.core.domain.Page;
import org.skyer.mybatis.pagehelper.domain.PageRequest;

public interface DealerService {

	/**
	 * 添加经销商
	 dealer	 * @return
	 */
	Boolean addDealer(Dealer dealer);

	/**
	 * 修改经销商信息
	 * @param dealerDTO
	 * @return
	 */
	Boolean updateDealer(DealerDTO dealerDTO);

	/**
	 * 删除经销商信息
	 * @param id
	 * @return
	 */
	Boolean deleteById(Long id);

	/**
	 * 获取经销商详情
	 * @param id
	 * @return
	 */
	Dealer getDealerDetail(Long id);

	/**
	 * 分页查询经销商列表
	 * @param pageRequest
	 * @param dealerListDTO
	 * @return
	 */
	Page<DealerVO> pageDealerList(PageRequest pageRequest, DealerListDTO dealerListDTO);

	/**
	 * 禁用
	 * @param id
	 * @param flag
	 * @return
	 */
	boolean operation(Long id, Boolean flag);

	/**
	 * 批量删除
	 * @param shopDeleteDTO
	 * @return
	 */
	Boolean deletes(ShopDeleteDTO shopDeleteDTO);
}
