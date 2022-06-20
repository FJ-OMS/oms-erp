package org.skyer.stock.domain.repository;

import java.util.List;

import org.skyer.mybatis.base.BaseRepository;
import org.skyer.stock.domain.entity.ResultDetail;
import org.skyer.stock.domain.vo.ResultRecordQtyVO;

/**
 * 收发货结果单详情资源库
 *
 * @author lixiaoyang 2021-08-03 16:41:29
 */
public interface ResultDetailRepository extends BaseRepository<ResultDetail> {

    /**
     * 查询通知单商品
     * @param resultId
     * @return
     */
    List<ResultDetail> queryResultDetail(Long resultId);

    /**
     * 统计出库结果单数量
     * @param resultId 结果单id
     * @return ResultRecordQtyVO
     */
    ResultRecordQtyVO statsResultRecordQty(Long resultId);
}
