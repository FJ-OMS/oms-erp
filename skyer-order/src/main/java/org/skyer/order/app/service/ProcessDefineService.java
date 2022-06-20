package org.skyer.order.app.service;

import java.io.UnsupportedEncodingException;

import org.skyer.order.api.dto.ProcessDefineDTO;
import org.skyer.order.domain.entity.ProcessDefine;

/**
 * 应用服务
 *
 * @author linzhuanghuang@ebc.com 2022-01-14 17:53:17
 */
public interface ProcessDefineService {

	/**
	 * 保存流程定义
	 * @param processDefine
	 */
	void saveProcessDefine(ProcessDefineDTO processDefine) throws Exception;

	void updateDeploy(Long id);
}
