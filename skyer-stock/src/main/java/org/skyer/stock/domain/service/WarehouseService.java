package org.skyer.stock.domain.service;

import org.skyer.core.domain.Page;
import org.skyer.stock.api.dto.WarehouseQueryReqDTO;
import org.skyer.stock.domain.vo.WarehouseQueryResVO;

/**
 * 仓库表应用服务
 *
 * @author lixiaoyang 2021-07-28 15:31:35
 */
public interface WarehouseService {

    /**
     * 保存渠道仓
     *
     * @author lixiaoyang
     * @date 2021/7/29 15:42
     */
    Boolean doSaveChannelWarehouse(String data);

    /**
     * 根据条件查询逻辑仓列表(分页）
     *
     * @param warehouseQueryReqDTO
     * @return
     */
    Page<WarehouseQueryResVO> queryWarehousePage(WarehouseQueryReqDTO warehouseQueryReqDTO);
}
