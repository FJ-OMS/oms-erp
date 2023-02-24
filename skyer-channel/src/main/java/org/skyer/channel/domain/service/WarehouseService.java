package org.skyer.channel.domain.service;


import org.skyer.channel.api.dto.ShopDeleteDTO;
import org.skyer.channel.api.dto.WarehouseDTO;
import org.skyer.channel.api.dto.WarehouseListDTO;
import org.skyer.channel.domain.entity.Warehouse;
import org.skyer.channel.domain.vo.WarehouseListVO;
import org.skyer.core.domain.Page;
import org.skyer.mybatis.pagehelper.domain.PageRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lzh
 * @since 2021-06-18
 */
public interface WarehouseService {
	/**
	 * 修改仓库
	 * @param warehouse
	 * @return
	 */
	Boolean updateWarehouse(WarehouseDTO warehouse);

	/**
	 * 新增仓库
	 * @param warehouse
	 * @return
	 */
	Boolean createWarehouse(Warehouse warehouse);

	/**
	 * 删除仓库
	 * @param id
	 * @return
	 */
	Boolean deleteById(Long id);

	/**
	 * 获取仓库详情
	 * @param id
	 * @return
	 */
	Warehouse getWarehouseDetail(Long id);

	/**
	 * 分页查询仓库信息
	 * @param pageRequest
	 * @param warehouseListDTO
	 * @return
	 */
	Page<WarehouseListVO> pageWarehouseList(PageRequest pageRequest, WarehouseListDTO warehouseListDTO);

	/**
	 * 禁用 OR 启用
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
