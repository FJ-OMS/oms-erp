package org.skyer.order.app.service;

import org.skyer.core.domain.Page;
import org.skyer.order.api.dto.ItemSearchDTO;
import org.skyer.order.api.dto.StatsStockoutDTO;
import org.skyer.order.api.vo.OrderItemLinePageVO;
import org.skyer.order.api.vo.StatsStockoutVO;
import org.skyer.order.app.vo.WorkExecuteVO;

import java.util.List;

/**
 * 内部订单商品行应用服务
 *
 * @author wushaochuan 2022-01-18 14:34:54
 */
public interface ItemLineService {
    /**
     *  平摊商品行添加分布式锁
     * @param innerOrderNo
     * @param innerOrderNo
     * @return
     */
    WorkExecuteVO splitItemLineByLock(String innerOrderNo);
    /**
     * 平摊商品行
     * @param innerOrderNo
     */
    WorkExecuteVO splitItemLine(String innerOrderNo);

    /**
     * 获取商品行
     * @param itemSearchDTO
     * @return
     */
    Page<OrderItemLinePageVO> queryItemLinePage(ItemSearchDTO itemSearchDTO);

    /**
     * 缺货统计
     * @param dto 统计条件
     * @return {@link StatsStockoutVO} 集合
     */
    List<StatsStockoutVO> statsStockout(StatsStockoutDTO dto);
}
