package org.skyer.order.infra.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

import org.skyer.order.api.dto.ProcessDefinePageDTO;
import org.skyer.order.api.vo.ProcessDefinePageVO;
import org.skyer.order.domain.entity.ProcessDefine;
import org.skyer.mybatis.common.BaseMapper;

/**
 * Mapper
 *
 * @author linzhuanghuang@ebc.com 2022-01-14 17:53:17
 */
public interface ProcessDefineMapper extends BaseMapper<ProcessDefine> {

	/**
	 * 流程定义分页
	 * @param processDefinePageDTO
	 * @return
	 */
	List<ProcessDefinePageVO> getListPage(@Param("processDefinePageDTO") ProcessDefinePageDTO processDefinePageDTO);
}
