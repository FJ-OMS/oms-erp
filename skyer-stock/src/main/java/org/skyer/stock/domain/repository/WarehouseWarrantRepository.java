package org.skyer.stock.domain.repository;

import org.skyer.core.domain.Page;
import org.skyer.mybatis.base.BaseRepository;
import org.skyer.mybatis.pagehelper.domain.PageRequest;
import org.skyer.stock.api.dto.WarehouseWarrantPageQueryDTO;
import org.skyer.stock.domain.entity.WarehouseWarrant;
import org.skyer.stock.domain.vo.WarehouseWarrantVO;

/**
 * 入库单资源库
 *
 * @author lixiaoyang 2021-10-25 16:34:17
 */
public interface WarehouseWarrantRepository extends BaseRepository<WarehouseWarrant> {

    /**
     * 分页
     *
     * @param pageRequest 分页条件
     * @param dto         查询条件
     * @return {@link  WarehouseWarrantVO} 分页
     */
    Page<WarehouseWarrantVO> page(PageRequest pageRequest, WarehouseWarrantPageQueryDTO dto);

    /**
     * 根据前置单据查询是否存在入库通知单
     *
     * @author lixiaoyang
     * @date 2022/4/27 10:46
     */
    WarehouseWarrant getOneByFrontRecordTypeAndFrontRecordCode(String frontRecordType, String frontRecordCode);
}
