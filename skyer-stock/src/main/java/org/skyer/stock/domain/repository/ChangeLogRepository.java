package org.skyer.stock.domain.repository;

import java.math.BigDecimal;

import org.skyer.core.domain.Page;
import org.skyer.mybatis.base.BaseRepository;
import org.skyer.mybatis.pagehelper.domain.PageRequest;
import org.skyer.stock.api.dto.StockChangeListDTO;
import org.skyer.stock.domain.entity.ChangeLog;
import org.skyer.stock.domain.vo.StockChangeListVO;

/**
 * 库存流水表资源库
 *
 * @author lixiaoyang 2021-08-03 16:51:06
 */
public interface ChangeLogRepository extends BaseRepository<ChangeLog> {

    Page<StockChangeListVO> getStockChangePage(StockChangeListDTO dto, Long tenantId, PageRequest pageRequest);

    BigDecimal getPutNum(StockChangeListDTO dto, Long tenantId);

    BigDecimal getOutNum(StockChangeListDTO dto, Long tenantId);
}
