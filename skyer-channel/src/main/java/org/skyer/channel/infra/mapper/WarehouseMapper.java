package org.skyer.channel.infra.mapper;


import java.util.List;

import org.skyer.channel.api.dto.WarehouseListDTO;
import org.skyer.channel.domain.entity.Warehouse;
import org.skyer.channel.domain.vo.WarehouseListVO;
import org.skyer.mybatis.common.BaseMapper;

/**
 * <p>
 *  仓库 Mapper
 * </p>
 *
 * @author lzh
 * @since 2021-06-18
 */
public interface WarehouseMapper extends BaseMapper<Warehouse> {

	/**
	 * 获取渠道仓库列表
	 * @param warehouseListDTO
	 * @return
	 */
	List<WarehouseListVO> pageWarehouseList(WarehouseListDTO warehouseListDTO);

}
