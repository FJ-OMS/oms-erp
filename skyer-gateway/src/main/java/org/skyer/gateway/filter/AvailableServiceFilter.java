package org.skyer.gateway.filter;

import org.skyer.gateway.config.MaintainProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

/**
 * 整体运维，判断服务是否可用
 *
 * @author XCXCXCXCX
 * @since 1.0
 */
public class AvailableServiceFilter implements GlobalFilter, Ordered {

    private static final Logger LOGGER = LoggerFactory.getLogger(AvailableServiceFilter.class);

    @Value("${spring.application.name}")
    private String appName;

    private final MaintainProperties maintainProperties;

    public AvailableServiceFilter(MaintainProperties maintainProperties) {
        this.maintainProperties = maintainProperties;
    }

    @Override
    public int getOrder() {
        return -4;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String requestURI = exchange.getRequest().getURI().getPath();
        String serviceName = parseServiceName(requestURI);

        // 如果没有解析到服务名，则表示请求目标是网关
        if (serviceName == null) {
            serviceName = appName;
        }

        MaintainProperties.MaintainState state = identifyState(serviceName);
        String msg = null;
        if (state == MaintainProperties.MaintainState.PAUSED) {
            msg = "This service is temporarily unavailable.";
        } else if (state == MaintainProperties.MaintainState.STOPPED) {
            msg = "The service is no longer available.";
        } else {
            msg = "The service is in an unknown state.";
        }

        Mono<Void> response = responseIfMatch(exchange.getResponse(), msg, state);
        if (response == null) {
            return chain.filter(exchange);
        } else {
            return response;
        }
    }

    private Mono<Void> responseIfMatch(ServerHttpResponse response, String message,
                    MaintainProperties.MaintainState state) {
        if (state == MaintainProperties.MaintainState.NORMAL) {
            return null;
        }
        LOGGER.error(message);
        response.setStatusCode(HttpStatus.NOT_IMPLEMENTED);
        response.getHeaders().put(HttpHeaders.CONTENT_TYPE, Collections.singletonList("text/html;charset=UTF-8"));
        response.getHeaders().put(HttpHeaders.ACCEPT_CHARSET, Collections.singletonList("UTF-8"));
        String responseMessage = new StringBuilder().append("{").append("\"code\":501,").append("\"status\":\"")
                        .append(state.name()).append("\",").append("\"message\":\"").append(message).append("\"")
                        .append("}").toString();
        DataBufferFactory factory = response.bufferFactory();
        DataBuffer dataBuffer = factory.wrap(responseMessage.getBytes(StandardCharsets.UTF_8));
        return response.writeAndFlushWith(Flux.just(dataBuffer).map(Flux::just));
    }

    private MaintainProperties.MaintainState identifyState(String serviceName) {
        MaintainProperties.MaintainInfo serviceInfo = maintainProperties.getServiceMaintainInfo().get(serviceName);
        MaintainProperties.MaintainInfo globalInfo = maintainProperties.getGlobalInfo();
        MaintainProperties.MaintainState serviceState =
                        serviceInfo == null ? MaintainProperties.MaintainState.NORMAL : serviceInfo.getState();
        MaintainProperties.MaintainState globalState =
                        globalInfo == null ? MaintainProperties.MaintainState.NORMAL : globalInfo.getState();
        if (globalState != MaintainProperties.MaintainState.NORMAL) {
            return globalState;
        } else {
            return serviceState;
        }
    }

    private String parseServiceName(String requestURI) {
        if (requestURI == null || requestURI.isEmpty()) {
            return null;
        }
        String[] parts = requestURI.trim().split("/");
        if (parts.length > 0) {
            return firstWord(parts);
        }
        return null;
    }

    private String firstWord(String[] parts) {
        for (String part : parts) {
            if (part == null || part.isEmpty()) {
                continue;
            }
            return part;
        }
        return null;
    }
}
