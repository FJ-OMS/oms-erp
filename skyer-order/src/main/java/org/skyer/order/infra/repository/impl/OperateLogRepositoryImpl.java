package org.skyer.order.infra.repository.impl;

import org.springframework.stereotype.Component;

import org.skyer.mybatis.base.impl.BaseRepositoryImpl;
import org.skyer.order.domain.entity.OperateLog;
import org.skyer.order.domain.repository.OperateLogRepository;

/**
 * 订单操作日志 资源库实现
 *
 * @author wushaochuan 2022-01-18 14:34:53
 */
@Component
public class OperateLogRepositoryImpl extends BaseRepositoryImpl<OperateLog> implements OperateLogRepository {

}
