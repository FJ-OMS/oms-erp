package org.skyer.stock.domain.repository;

import org.skyer.core.domain.Page;
import org.skyer.mybatis.base.BaseRepository;
import org.skyer.mybatis.pagehelper.domain.PageRequest;
import org.skyer.stock.api.dto.WarehouseResultPageQueryDTO;
import org.skyer.stock.domain.entity.WarehouseResult;
import org.skyer.stock.domain.vo.WarehouseResultVO;

/**
 * 入库结果单资源库
 *
 * @author xurongfa 2022-04-26 14:10:52
 */
public interface WarehouseResultRepository extends BaseRepository<WarehouseResult> {

    /**
     * 分页
     * @param pageRequest 分页条件
     * @param dto 查询条件
     * @return {@link WarehouseResultVO} 分页
     */
    Page<WarehouseResultVO> page(PageRequest pageRequest, WarehouseResultPageQueryDTO dto);
}
