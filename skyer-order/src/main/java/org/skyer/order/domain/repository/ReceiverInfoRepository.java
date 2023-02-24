package org.skyer.order.domain.repository;

import org.skyer.mybatis.base.BaseRepository;
import org.skyer.order.domain.entity.ReceiverInfo;

import java.util.List;
import java.util.Map;

/**
 * 内部订单收货人信息资源库
 *
 * @author wushaochuan 2022-01-18 14:34:53
 */
public interface ReceiverInfoRepository extends BaseRepository<ReceiverInfo> {

    /**
     * 根据内部no查询receive
     * @param innerNoList
     * @return
     */
    Map<String,ReceiverInfo> queryReceiveInfoListByInner(List<String> innerNoList);

    /**
     * 查询收货信息
     * @param innerNo
     * @return
     */
    ReceiverInfo queryReceiveInfoByInnerNo(String innerNo);
}
