package org.skyer.stock.domain.service;

import org.skyer.core.domain.Page;
import org.skyer.stock.api.dto.WarehouseWarrantPageQueryDTO;
import org.skyer.stock.api.dto.WarehouseWarrantResultDTO;
import org.skyer.stock.api.dto.WarehouseWarrantSaveDTO;
import org.skyer.stock.domain.vo.WarehouseWarrantInfoVO;
import org.skyer.stock.domain.vo.WarehouseWarrantVO;

/**
 * 入库单应用服务
 *
 * @author lixiaoyang 2021-10-25 16:34:17
 */
public interface WarehouseWarrantService {
    /**
     * 生成入库通知单
     *
     * @author lixiaoyang
     * @date 2022/4/26 11:43
     */
    WarehouseWarrantResultDTO generateWarehouseWarrant(WarehouseWarrantSaveDTO dto);

    /**
     * 分页
     *
     * @param dto 查询条件
     * @return {@link WarehouseWarrantVO}分页
     */
    Page<WarehouseWarrantVO> page(WarehouseWarrantPageQueryDTO dto);

    /**
     * 根据id或编码查询详情
     *
     * @param id   id
     * @param code 编码
     * @return {@link WarehouseWarrantInfoVO}
     */
    WarehouseWarrantInfoVO detail(Long id, String code);
}
