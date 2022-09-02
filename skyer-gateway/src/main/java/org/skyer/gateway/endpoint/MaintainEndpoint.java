package org.skyer.gateway.endpoint;

import org.apache.commons.lang3.StringUtils;
import org.skyer.gateway.config.MaintainProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * @author XCXCXCXCX
 * @date 2019/7/26
 * @project skyer-org.skyer.gateway
 */
@RestController
public class MaintainEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(MaintainEndpoint.class);

    private String configKey;

    private final MaintainProperties maintainProperties;

    public MaintainEndpoint(MaintainProperties maintainProperties) {
        this.maintainProperties = maintainProperties;

        String randomKey = randomSecretKey();

        this.configKey = StringUtils.defaultIfBlank(maintainProperties.getSecretKey(), randomKey);

    }

    private String randomSecretKey() {
        String generateKey = UUID.randomUUID().toString();
        LOGGER.info("generate special request secretKey : " + generateKey
                        + " [Due to the lack of [key=skyer.maintain.special.request.secret-key] configuration]");
        return generateKey;
    }

    @PostMapping("/maintain")
    public ResponseEntity<?> maintain(@RequestParam("secretKey") String secretKey,
                    @RequestParam("openAll") Boolean openAll,
                    @RequestParam(value = "openList", required = false) List<String> openList,
                    @RequestParam(value = "closeList", required = false) List<String> closeList) {

        // 区分大小写
        if (!configKey.equals(secretKey)) {
            throw new RuntimeException("认证失败，[secretKey=" + secretKey + "]不通过");
        }
        if (openAll) {
            maintainProperties.setGlobalInfo(
                            new MaintainProperties.MaintainInfo(MaintainProperties.MaintainState.NORMAL));
        } else {
            maintainProperties.setGlobalInfo(
                            new MaintainProperties.MaintainInfo(MaintainProperties.MaintainState.PAUSED));
        }
        if (openList != null) {
            for (String serviceName : openList) {
                if (serviceName != null && !serviceName.isEmpty()) {
                    maintainProperties.getServiceMaintainInfo().put(serviceName,
                                    new MaintainProperties.MaintainInfo(MaintainProperties.MaintainState.NORMAL));
                }
            }
        }
        if (closeList != null) {
            for (String serviceName : closeList) {
                if (serviceName != null && !serviceName.isEmpty()) {
                    maintainProperties.getServiceMaintainInfo().put(serviceName,
                                    new MaintainProperties.MaintainInfo(MaintainProperties.MaintainState.PAUSED));
                }
            }
        }
        return ResponseEntity.ok().build();
    }
}
