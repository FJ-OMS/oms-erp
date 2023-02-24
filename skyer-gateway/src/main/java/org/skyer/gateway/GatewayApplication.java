package org.skyer.gateway;

import org.skyer.autoconfigure.gateway.EnableSkyerGateway;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author XCXCXCXCX
 * @date 2019/9/9
 * @project skyer-gateway
 */
@EnableSkyerGateway
@EnableDiscoveryClient
public class GatewayApplication {

    public static void main(String[] args) {
        try {
            new SpringApplicationBuilder(GatewayApplication.class).web(WebApplicationType.REACTIVE).run(args);
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }
}
