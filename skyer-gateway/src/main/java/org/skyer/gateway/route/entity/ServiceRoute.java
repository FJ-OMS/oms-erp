package org.skyer.gateway.route.entity;

import org.skyer.mybatis.domain.AuditDomain;
import org.skyer.core.base.BaseConstants;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 服务路由配置
 *
 * @author zhiying.dong@hand-china.com 2018-12-07 14:45:53
 */
public class ServiceRoute extends AuditDomain {

    public ServiceRoute() {}

    public ServiceRoute(@NotNull String serviceCode, @NotBlank String name, @NotBlank String path) {
        this.serviceCode = serviceCode;
        this.name = name;
        this.path = path;
    }

    private Long serviceRouteId;
    private Long serviceId;
    private String serviceCode;
    private String name;
    private String path;
    private String url;
    private Integer stripPrefix;
    private String sensitiveHeaders;
    private String extendConfigMap;

    public Long getServiceRouteId() {
        return serviceRouteId;
    }

    public void setServiceRouteId(Long serviceRouteId) {
        this.serviceRouteId = serviceRouteId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getStripPrefix() {
        return stripPrefix;
    }

    public void setStripPrefix(Integer stripPrefix) {
        this.stripPrefix = stripPrefix;
    }

    public String getSensitiveHeaders() {
        return sensitiveHeaders;
    }

    public void setSensitiveHeaders(String sensitiveHeaders) {
        this.sensitiveHeaders = sensitiveHeaders;
    }

    public String getExtendConfigMap() {
        return extendConfigMap;
    }

    public void setExtendConfigMap(String extendConfigMap) {
        this.extendConfigMap = extendConfigMap;
    }

    public boolean ifStripPrefix() {
        return BaseConstants.Flag.YES.equals(this.stripPrefix);
    }
}
