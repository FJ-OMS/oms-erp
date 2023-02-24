package org.skyer.stock.domain.repository;

import java.util.List;

import org.skyer.mybatis.base.BaseRepository;
import org.skyer.stock.api.dto.AdjustDetailsReqDTO;
import org.skyer.stock.domain.entity.AdjustDetail;

/**
 * 库存调整单详情资源库
 *
 * @author lixiaoyang 2021-08-02 16:44:33
 */
public interface AdjustDetailRepository extends BaseRepository<AdjustDetail> {

    /**
     * 创建或者更新调整单详情
     * @param adjustDetailsReqDTOList
     */
    void createOrUpdateAdjustDetail(List<AdjustDetailsReqDTO> adjustDetailsReqDTOList,Long adjustRecordId);

    /**
     * 查询调整单列表
     * @param adjustId
     * @return
     */
    List<AdjustDetail> queryAdjustDetailList(Long adjustId);

    /**
     * 删除调整单详情
     * @param adjustRecordId
     */
    void delAdjustDetail(Long adjustRecordId);


}
