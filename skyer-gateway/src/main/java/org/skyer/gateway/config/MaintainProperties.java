package org.skyer.gateway.config;


import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * @author XCXCXCXCX
 * @since 1.0
 */
@ConfigurationProperties(prefix = MaintainProperties.PREFIX)
public class MaintainProperties {

    public static final String PREFIX = "skyer.maintain";

    /**
     * 运维请求 secretKey
     */
    private String secretKey = "skyer";

    private MaintainInfo globalInfo = new MaintainInfo(MaintainState.NORMAL);

    /**
     * key=serviceName value={@link MaintainInfo}
     */
    private Map<String, MaintainInfo> serviceMaintainInfo = new HashMap<>();

    public MaintainInfo getGlobalInfo() {
        return globalInfo;
    }

    public void setGlobalInfo(MaintainInfo globalInfo) {
        this.globalInfo = globalInfo;
    }

    public Map<String, MaintainInfo> getServiceMaintainInfo() {
        return serviceMaintainInfo;
    }

    public void setServiceMaintainInfo(Map<String, MaintainInfo> serviceMaintainInfo) {
        this.serviceMaintainInfo = serviceMaintainInfo;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public static class MaintainInfo {
        private MaintainState state;

        public MaintainInfo() {}

        public MaintainInfo(MaintainState state) {
            this.state = state;
        }

        public MaintainState getState() {
            return state;
        }

        public void setState(MaintainState state) {
            this.state = state;
        }
    }

    public enum MaintainState {

        /**
         * 正常运行
         */
        NORMAL,
        /**
         * 暂停提供服务
         */
        PAUSED,
        /**
         * 服务已停止，不再提供服务
         */
        STOPPED;

    }

}
