package org.skyer.stock.domain.service;

import java.util.List;

import org.skyer.stock.api.dto.BatchRemoveDTO;
import org.skyer.stock.api.dto.StorageAreaDTO;

/**
 * 仓储管理表应用服务
 *
 * @author linzhuanghuang@ebc.com 2021-08-13 17:53:17
 */
public interface StorageAreaService {

	/**
	 * 新增仓库
	 * @param storageAreaDTO
	 * @return
	 */
	Boolean createStorageArea(StorageAreaDTO storageAreaDTO);

	/**
	 *
	 * @param storageAreaDTO
	 */
	Boolean updateStorageArea(StorageAreaDTO storageAreaDTO);

	/**
	 * 禁用启用
	 * @param id
	 * @param activeflag
	 * @return
	 */
	boolean operation(Long id, Boolean activeflag);

	/**
	 * 批量删除
	 * @param batchRemoveDTO
	 * @return
	 */
	void batchDelete(BatchRemoveDTO batchRemoveDTO);

	/**
	 * 获取详情
	 * @param id
	 * @return
	 */
	StorageAreaDTO getDetails(Long id);

}
