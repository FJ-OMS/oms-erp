package org.skyer.stock.domain.service;

import org.skyer.core.domain.Page;
import org.skyer.stock.api.dto.WarehouseResultPageQueryDTO;
import org.skyer.stock.api.dto.WarehouseWarrantResultDTO;
import org.skyer.stock.domain.vo.WarehouseResultInfoVO;
import org.skyer.stock.domain.vo.WarehouseResultVO;

/**
 * 入库结果单应用服务
 *
 * @author xurongfa 2022-04-26 14:10:52
 */
public interface WarehouseResultService {

    /**
     * 分页
     *
     * @param dto 条件
     * @return {@link WarehouseResultVO}
     */
    Page<WarehouseResultVO> page(WarehouseResultPageQueryDTO dto);

    /**
     * 根据id或编码查询详情
     *
     * @param id   id
     * @param code 入库结果单编码
     * @return {@link WarehouseResultInfoVO}
     */
    WarehouseResultInfoVO detail(Long id, String code);

    /**
     * 创建入库结果单
     *
     * @author lixiaoyang
     * @date 2022/4/27 13:35
     */
    Long generateWarehouseResult(WarehouseWarrantResultDTO dto);

}
