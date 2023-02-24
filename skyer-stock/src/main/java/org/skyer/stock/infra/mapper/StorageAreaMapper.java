package org.skyer.stock.infra.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

import org.skyer.mybatis.common.BaseMapper;
import org.skyer.stock.api.dto.StorageAreaListReqDTO;
import org.skyer.stock.domain.entity.StorageArea;
import org.skyer.stock.domain.vo.StorageAreaListVO;

/**
 * 仓储管理表Mapper
 *
 * @author lixiaoyang 2021-08-02 16:31:19
 */
public interface StorageAreaMapper extends BaseMapper<StorageArea> {

    /**
     * 逻辑仓分页查询
     *
     * @param listReqDTO
     * @return
     */
    List<StorageAreaListVO> pageStorageAreaList(@Param("listReqDTO") StorageAreaListReqDTO listReqDTO);

    List<StorageArea> getStorageAreaListByIds(@Param("ids") List<Long> ids);

    StorageArea getByLogicWarehouseId(@Param("logicWarehouseId") Long logicWarehouseId);

    StorageArea getByLogicWarehouseCode(@Param("logicWarehouseCode") String logicWarehouseCode);
}
