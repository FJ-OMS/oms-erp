package org.skyer.order.infra.feign;

import org.skyer.order.infra.feign.entity.self.tenants.TenantDTO;
import org.skyer.order.infra.feign.impl.TenantRemoteServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author chenzz
 */
@FeignClient(value = "skyer-iam", fallback = TenantRemoteServiceImpl.class, path = "/v1")
public interface TenantRemoteService {


    /**
     * /skyer/v1/users/self-tenants
     * 登录用户 - 查询可访问的租户列表
     *
     * @param dto
     * @return
     */
    @GetMapping("/users/self-tenants")
    ResponseEntity<List<TenantDTO>> selfTenants(TenantDTO dto);

}
