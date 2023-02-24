package org.skyer.order.app.service;

import org.skyer.core.domain.Page;
import org.skyer.order.api.es.SearchThirdOrderEsParam;
import org.skyer.order.api.vo.ThirdOrderPageVo;

import java.util.List;

/**
 * @author zlb
 */
public interface ThirdOrderEsService {

    /**
     * 第三方订单列表
     * @param searchThirdOrderEsParam searchThirdOrderEsParam
     * @return Page<ThirdOrderPageVo>
     */
    Page<ThirdOrderPageVo> getThirdOrderPage(SearchThirdOrderEsParam searchThirdOrderEsParam) throws Exception;

    /**
     * 批量更新第三方订单es
     * @param outOrderNoList outOrderNoList
     */
    void batchInsertOrderToEs(List<String> outOrderNoList,List<String> innerOrderNoList);
}
