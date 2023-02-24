package org.skyer.order.infra.feign.impl;

import org.skyer.order.infra.feign.LogisticsRemoteService;
import org.skyer.order.infra.feign.entity.select.logistics.postage.in.CalcPostageChargeDTO;
import org.skyer.order.infra.feign.entity.select.logistics.postage.out.PackageSkuLogistics;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author chenzz
 */
@Component
public class LogisticsRemoteServiceImpl implements LogisticsRemoteService {
    @Override
    public ResponseEntity<List<PackageSkuLogistics>> selectLogisticsAndCalcPostage(CalcPostageChargeDTO dto) {
        return null;
    }
}
