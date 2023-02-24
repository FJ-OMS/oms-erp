package org.skyer.stock.domain.repository;

import java.util.List;

import org.skyer.core.domain.Page;
import org.skyer.mybatis.base.BaseRepository;
import org.skyer.stock.api.dto.AdjustPageReqDTO;
import org.skyer.stock.domain.entity.AdjustRecord;
import org.skyer.stock.domain.vo.AdjustRecordPageResVO;

/**
 * 库存调整单资源库
 *
 * @author lixiaoyang 2021-08-02 16:44:33
 */
public interface AdjustRecordRepository extends BaseRepository<AdjustRecord> {
    /**
     * 查询调整单分页
     *
     * @param adjustPageReqDTO
     * @return
     */
    Page<AdjustRecordPageResVO> pageAdjustRecord(AdjustPageReqDTO adjustPageReqDTO, Long tenantId);

    /**
     * 获取一个调整单
     *
     * @param id
     * @param tenantId
     * @return
     */
    AdjustRecord getOne(Long id, Long tenantId);

    /**
     * 根据逻辑仓ID回去调整单信息
     *
     * @author lixiaoyang
     * @date 2022/5/6 15:01
     */
    List<AdjustRecord> getAllByLogicWarehouseIds(List<Long> logicWarehouseIds);
}
