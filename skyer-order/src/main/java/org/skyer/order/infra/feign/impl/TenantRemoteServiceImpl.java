package org.skyer.order.infra.feign.impl;

import org.skyer.order.infra.feign.LogisticsRemoteService;
import org.skyer.order.infra.feign.TenantRemoteService;
import org.skyer.order.infra.feign.entity.select.logistics.postage.in.CalcPostageChargeDTO;
import org.skyer.order.infra.feign.entity.select.logistics.postage.out.PackageSkuLogistics;
import org.skyer.order.infra.feign.entity.self.tenants.TenantDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author chenzz
 */
@Component
public class TenantRemoteServiceImpl implements TenantRemoteService {
    @Override
    public ResponseEntity<List<TenantDTO>> selfTenants(TenantDTO dto) {
        return null;
    }
}
