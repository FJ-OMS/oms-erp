package org.skyer.stock.domain.repository;

import java.util.List;

import org.skyer.core.domain.Page;
import org.skyer.mybatis.base.BaseRepository;
import org.skyer.stock.api.dto.WarehouseQueryReqDTO;
import org.skyer.stock.domain.entity.Warehouse;
import org.skyer.stock.domain.vo.WarehouseQueryResVO;

/**
 * 仓库表资源库
 *
 * @author lixiaoyang 2021-07-28 15:31:35
 */
public interface WarehouseRepository extends BaseRepository<Warehouse> {

    /**
     * 根据店铺获取仓库
     *
     * @author lixiaoyang
     * @date 2021/11/2 14:20
     */
    Warehouse getShopWarehouse(Long shopId);

    /**
     *  获取系统默认逻辑仓
     */
    Warehouse getSystemLogicWarehouse();

    /**
     * 查询仓库(分页）
     *
     * @param warehouseQueryReqDTO
     * @return
     */
    Page<WarehouseQueryResVO> queryWarehousePage(WarehouseQueryReqDTO warehouseQueryReqDTO, Long tenantId, Long sructureId);

    Warehouse getOneByNameOrType(String name, String typeCode);

    /**
     * 根据仓库ID批量查询仓库
     *
     * @param ids
     * @return
     */
    List<Warehouse> getWarehouseListByIds(List<Long> ids);

    /**
     * 根据仓库code查询仓库
     *
     * @param warehouseCode
     * @return
     */
    Warehouse getWarehouseByCondition(String warehouseCode, Long tenantId);

    /**
     * 根据租户获取各仓库信息
     *
     * @author lixiaoyang
     * @date 2021/8/31 11:02
     */
    List<Warehouse> queryAllByTenantId(Long tenantId);
}
