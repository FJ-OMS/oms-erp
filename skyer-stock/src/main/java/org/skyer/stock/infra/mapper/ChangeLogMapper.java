package org.skyer.stock.infra.mapper;


import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

import org.skyer.mybatis.common.BaseMapper;
import org.skyer.stock.api.dto.StockChangeListDTO;
import org.skyer.stock.domain.entity.ChangeLog;
import org.skyer.stock.domain.vo.StockChangeListVO;

/**
 * 库存流水表Mapper
 *
 * @author lixiaoyang 2021-08-03 16:51:06
 */
public interface ChangeLogMapper extends BaseMapper<ChangeLog> {

    List<StockChangeListVO> getStockChangeList(@Param("dto") StockChangeListDTO dto, @Param("tenantId") Long tenantId);

    BigDecimal getNum(@Param("dto") StockChangeListDTO dto, @Param("tenantId") Long tenantId, @Param("flag") Boolean flag);
}
