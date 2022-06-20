package org.skyer.order.domain.repository;

import org.skyer.mybatis.base.BaseRepository;
import org.skyer.order.domain.entity.ReceiverInfo;
import org.skyer.order.domain.entity.ThirdItem;
import org.skyer.order.domain.entity.ThirdReceiverInfo;

import java.util.List;
import java.util.Map;

/**
 * 内部订单收货人信息资源库
 *
 * @author zhanglongbing@ebc.com 2022-04-20 10:15:57
 */
public interface ThirdReceiverInfoRepository extends BaseRepository<ThirdReceiverInfo> {

    /**
     * 根据外部订单号查询收货人信息
     * @param outOrderList outOrderList
     * @return Map<String, ThirdReceiverInfo>
     */
    Map<String, ThirdReceiverInfo> queryThirdReceiverInfoByOuterNo(List<String> outOrderList);
}
