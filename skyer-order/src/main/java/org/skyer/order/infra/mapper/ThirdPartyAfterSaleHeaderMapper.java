package org.skyer.order.infra.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

import org.skyer.order.api.dto.ThirdPartyAfterSalePageDTO;
import org.skyer.order.api.vo.ThirdPartyAfterSalePageVO;
import org.skyer.order.domain.entity.ThirdPartyAfterSaleHeader;
import org.skyer.mybatis.common.BaseMapper;

/**
 * 第三方售后单头Mapper
 *
 * @author lzh 2022-04-20 16:30:30
 */
public interface ThirdPartyAfterSaleHeaderMapper extends BaseMapper<ThirdPartyAfterSaleHeader> {

	/**
	 * 售后列表分页
	 * @param thirdPartyAfterSalePageDTO
	 * @return
	 */
	List<ThirdPartyAfterSalePageVO> orderPage(@Param("thirdPartyAfterSalePageDTO") ThirdPartyAfterSalePageDTO thirdPartyAfterSalePageDTO);
}
