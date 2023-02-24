package org.skyer.order.domain.repository;

import org.skyer.core.domain.Page;
import org.skyer.mybatis.base.BaseRepository;
import org.skyer.mybatis.pagehelper.domain.PageRequest;
import org.skyer.order.api.dto.ProcessDefineDTO;
import org.skyer.order.api.dto.ProcessDefinePageDTO;
import org.skyer.order.api.vo.ProcessDefineDetailsVO;
import org.skyer.order.api.vo.ProcessDefinePageVO;
import org.skyer.order.domain.entity.ProcessDefine;

/**
 * 资源库
 *
 * @author linzhuanghuang@ebc.com 2022-01-14 17:53:17
 */
public interface ProcessDefineRepository extends BaseRepository<ProcessDefine> {

	/**
	 * 获取流程详情
	 * @param id
	 * @return
	 */
	ProcessDefineDetailsVO queryProcessDetails(Long id);

	/**
	 * 删除
	 * @param processDefine
	 */
	void deleteProcess(ProcessDefine processDefine);

	/**
	 * 修改流程定义
	 * @param processDefine
	 */
	void updateProcessDefine(ProcessDefineDTO processDefine);

	/**
	 * 流程分页
	 * @param pageRequest
	 * @param processDefine
	 * @return
	 */
	Page<ProcessDefinePageVO> processPage(PageRequest pageRequest, ProcessDefinePageDTO processDefine);
}
