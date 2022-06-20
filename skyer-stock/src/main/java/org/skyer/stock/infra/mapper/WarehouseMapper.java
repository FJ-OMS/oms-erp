package org.skyer.stock.infra.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

import org.skyer.stock.api.dto.WarehouseQueryReqDTO;
import org.skyer.stock.domain.entity.Warehouse;
import org.skyer.mybatis.common.BaseMapper;
import org.skyer.stock.domain.vo.WarehouseQueryResVO;

/**
 * 仓库表Mapper
 *
 * @author lixiaoyang 2021-07-28 15:31:35
 */
public interface WarehouseMapper extends BaseMapper<Warehouse> {

    /**
     * 根据店铺ID获取渠道仓
     *
     * @param shopId 店铺ID
     * @author lixiaoyang
     * @date 2021/7/29 17:59
     */
    Warehouse getShopWarehouse(@Param("shopId") Long shopId);

    /**
     * 获取系统默认逻辑仓
     */
    Warehouse getSystemLogicWarehouse();

    /**
     * 仓库列表
     *
     * @param warehouseQueryReqDTO
     * @param tenantId
     * @return
     */
    List<WarehouseQueryResVO> queryWarehouseList(@Param("param") WarehouseQueryReqDTO warehouseQueryReqDTO, @Param("tenantId") Long tenantId, @Param("sructureId") Long sructureId);

    /**
     * 根据名称和类型获取一个仓
     *
     * @author lixiaoyang
     * @date 2022/1/22 10:09
     */
    Warehouse getOneByNameOrType(@Param("name") String name, @Param("typeCode") String typeCode);

    List<Warehouse> getWarehouseListByIds(@Param("ids") List<Long> ids);

    /**
     * 根据租户获取各仓库信息
     *
     * @author lixiaoyang
     * @date 2021/8/31 11:02
     */
    List<Warehouse> queryAllByTenantId(@Param("tenantId") Long tenantId);
}
