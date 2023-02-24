package org.skyer.stock.domain.vo;

import lombok.Data;

import java.math.BigDecimal;

import org.skyer.core.cache.Cacheable;
import org.skyer.core.domain.Page;

@Data
public class StockChangeVO implements Cacheable {

    Page<StockChangeListVO> page;

    BigDecimal putNum;

    BigDecimal outNum;

    BigDecimal differenceNum;
}
