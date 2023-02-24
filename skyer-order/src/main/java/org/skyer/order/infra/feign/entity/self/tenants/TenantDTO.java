package org.skyer.order.infra.feign.entity.self.tenants;

import lombok.Data;

/**
 * TenantDTO {
 * defaultTenantId (integer, optional),
 * tenantId (integer, optional),
 * tenantName (string, optional),
 * tenantNum (string, optional),
 * userId (integer, optional)
 * }
 */
@Data
public class TenantDTO {

    private Integer defaultTenantId;

    private Integer tenantId;

    private String tenantName;

    private String tenantNum;

    private String userId;

}
