package org.skyer.order.domain.repository;

import org.skyer.core.domain.Page;
import org.skyer.mybatis.base.BaseRepository;
import org.skyer.order.api.dto.ThirdPartyAfterSaleDTO;
import org.skyer.order.api.dto.ThirdPartyAfterSalePageDTO;
import org.skyer.order.api.dto.ThirdPartyRefundBaseInfo;
import org.skyer.order.api.vo.ThirdPartyAfterSaleHeaderDetailsVO;
import org.skyer.order.api.vo.ThirdPartyAfterSalePageVO;
import org.skyer.order.domain.entity.ThirdPartyAfterSaleHeader;

/**
 * 第三方售后单头资源库
 *
 * @author lzh 2022-04-20 16:30:30
 */
public interface ThirdPartyAfterSaleHeaderRepository extends BaseRepository<ThirdPartyAfterSaleHeader> {

	/**
	 * 创建第三方仅售后单头
	 * @param thirdPartyAfterSaleDTO 第三方售后单入参
	 * @return  ThirdPartyAfterSaleDTO 第三方售后单出参
	 */
	ThirdPartyAfterSaleHeader createThirdPartyRefund(ThirdPartyAfterSaleDTO thirdPartyAfterSaleDTO);

	/**
	 * 获取售后单明细
	 * @param id 售后单id
	 * @param code 售后单编码
	 * @return
	 */
	ThirdPartyAfterSaleHeaderDetailsVO detail(Long id,String code);

	/**
	 * 售后单分页查询
	 * @param thirdPartyAfterSalePageDTO 售后单分页入参
	 * @return
	 */
	Page<ThirdPartyAfterSalePageVO> orderPage(ThirdPartyAfterSalePageDTO thirdPartyAfterSalePageDTO);

	/**
	 * 根据三方退款单号查询
	 * @param outRefundNo 三方退款单号
	 * @return ThirdPartyAfterSaleHeader
	 */
	ThirdPartyAfterSaleHeader queryByOutRefundNo(String outRefundNo);

	/**
	 * 创建售后单发起saga
	 * @param thirdPartyAfterSaleDTO
	 */
	void createThirdPartyAfterSaleSaga(ThirdPartyAfterSaleDTO thirdPartyAfterSaleDTO);
}
