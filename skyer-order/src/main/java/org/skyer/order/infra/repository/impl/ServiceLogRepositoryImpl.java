package org.skyer.order.infra.repository.impl;

import org.skyer.mybatis.base.impl.BaseRepositoryImpl;
import org.skyer.order.domain.entity.ServiceLog;
import org.skyer.order.domain.repository.ServiceLogRepository;
import org.springframework.stereotype.Component;

/**
 * 订单中心与其他中心交互日志 资源库实现
 *
 * @author linzhuanghuang@ebc.com 2022-02-28 13:24:39
 */
@Component
public class ServiceLogRepositoryImpl extends BaseRepositoryImpl<ServiceLog> implements ServiceLogRepository {

  
}
