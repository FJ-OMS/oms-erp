package org.skyer.gateway.filter;

import org.skyer.gateway.helper.config.GatewayHelperProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.skyer.core.variable.RequestVariableHolder.HEADER_JWT;

/**
 * 添加token和label到请求header
 *
 * @author flyleft
 */
public class HeaderWrapperFilter implements GlobalFilter, Ordered {

    private static final Logger LOGGER = LoggerFactory.getLogger(HeaderWrapperFilter.class);

    private GatewayHelperProperties gatewayHelperProperties;

    public HeaderWrapperFilter(GatewayHelperProperties gatewayHelperProperties) {
        this.gatewayHelperProperties = gatewayHelperProperties;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        List<String> token = request.getHeaders().get(HEADER_JWT);
        if (CollectionUtils.isEmpty(token)) {
            LOGGER.info("Request get empty jwt , request uri: {} method: {}", request.getURI().getPath(),
                            request.getMethod());
            return chain.filter(exchange);
        } else {
            if (gatewayHelperProperties.isEnabledJwtLog()) {
                LOGGER.info("Request get jwt , request uri: {} method: {} JWT: {}", request.getURI().getPath(),
                                request.getMethod(), token);
            }
            return chain.filter(exchange.mutate().request(builder -> builder.header(HEADER_JWT, token.get(0))).build());
        }
    }

    @Override
    public int getOrder() {
        return -3;
    }

}
