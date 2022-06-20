package org.skyer.stock.domain.repository;

import java.util.List;

import org.skyer.core.domain.Page;
import org.skyer.mybatis.base.BaseRepository;
import org.skyer.mybatis.pagehelper.domain.PageRequest;
import org.skyer.stock.api.dto.StorageAreaListReqDTO;
import org.skyer.stock.domain.entity.StorageArea;
import org.skyer.stock.domain.vo.StorageAreaListVO;

/**
 * 仓储管理表资源库
 *
 * @author lixiaoyang 2021-08-02 16:18:08
 */
public interface StorageAreaRepository extends BaseRepository<StorageArea> {


	/**
	 * 分页查询
	 * @param pageRequest
	 * @param listReqDTO
	 * @return
	 */
	Page<StorageAreaListVO> pageStorageAreaList(PageRequest pageRequest, StorageAreaListReqDTO listReqDTO);

	/**
	 * 批量查询接口
	 * @param ids
	 * @return
	 */
	List<StorageArea> getStorageAreaListByIds(List<Long> ids);

	/**
	 * 通过逻辑仓ID获取实体仓信息
	 * @author lixiaoyang
	 * @date 2021/10/21 10:11
	 */
	StorageArea getByLogicWarehouseId(Long logicWarehouseId);

	/**
	 *  通过逻辑仓编码获取实体仓信息
	 * @author lixiaoyang
	 * @date 2022/3/2 15:46
	 */
	StorageArea getByLogicWarehouseCode(String logicWarehouseCode);
}
