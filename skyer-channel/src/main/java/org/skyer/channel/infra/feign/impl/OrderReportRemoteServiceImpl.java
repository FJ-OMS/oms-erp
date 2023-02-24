package org.skyer.channel.infra.feign.impl;

import org.skyer.channel.domain.vo.ChannelSaleOrderVO;
import org.skyer.channel.infra.feign.OrderReportRemoteService;
import org.skyer.channel.infra.remote.api.dto.SaleOrderOrderApiDTO;
import org.skyer.core.exception.CommonException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderReportRemoteServiceImpl implements OrderReportRemoteService {

    @Override
    public ResponseEntity<List<ChannelSaleOrderVO>> queryChannelOrderSaleReport(SaleOrderOrderApiDTO saleOrderOrderApiDTO) {
        throw new CommonException("调用订单中心查询报表异常");
    }
}
