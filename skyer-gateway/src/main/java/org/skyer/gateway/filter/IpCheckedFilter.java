package org.skyer.gateway.filter;

import org.skyer.gateway.filter.metric.RequestCountRules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author XCXCXCXCX
 * @version 1.2.0
 * @date 2019/12/5 7:38 下午
 */
@Order(Integer.MIN_VALUE)
public class IpCheckedFilter implements WebFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(IpCheckedFilter.class);

    public static final String X_REAL_IP = "X-Real-IP";
    public static final String X_FORWARDED_FOR = "X-Forwarded-For";
    private static final String ERROR_MESSAGE = "You are not allowed to access.";

    private final RequestCountRules requestCountRules;

    public IpCheckedFilter(RequestCountRules requestCountRules) {
        this.requestCountRules = requestCountRules;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String realIp = getRealIp(exchange);
        if (realIp == null) {
            LOGGER.warn("unknown ip request access gateway.");
            return chain.filter(exchange);
        }

        String uri = exchange.getRequest().getURI().getPath();

        /**
         * url匹配白名单
         */
        Set<String> urlWhitelist = requestCountRules.getWhitelist(uri);
        if (!CollectionUtils.isEmpty(urlWhitelist)) {
            if (!urlWhitelist.contains(realIp)) {
                return response(exchange.getResponse(), ERROR_MESSAGE);
            }
        }

        /**
         * url匹配黑名单
         */
        Set<String> urlBlacklist = requestCountRules.getBlacklist(uri);
        if (!CollectionUtils.isEmpty(urlBlacklist)) {
            if (urlBlacklist.contains(realIp)) {
                return response(exchange.getResponse(), ERROR_MESSAGE);
            }
        }

        return chain.filter(exchange);
    }

    private String getRealIp(ServerWebExchange exchange) {
        String realIp = null;
        InetSocketAddress remoteAddress = exchange.getRequest().getRemoteAddress();
        if (remoteAddress != null) {
            realIp = remoteAddress.getHostName();
        }

        HttpHeaders headers = exchange.getRequest().getHeaders();
        List<String> forwardedIps = headers.get(X_FORWARDED_FOR);
        if (!CollectionUtils.isEmpty(forwardedIps)) {
            realIp = forwardedIps.get(0);
        }

        List<String> realIps = headers.get(X_REAL_IP);
        if (!CollectionUtils.isEmpty(realIps)) {
            realIp = realIps.get(0);
        }
        return realIp;
    }

    private Mono<Void> response(ServerHttpResponse response, String message) {
        response.setStatusCode(HttpStatus.FORBIDDEN);
        response.getHeaders().put(HttpHeaders.CONTENT_TYPE, Collections.singletonList("text/html;charset=UTF-8"));
        response.getHeaders().put(HttpHeaders.ACCEPT_CHARSET, Collections.singletonList("UTF-8"));
        String responseMessage = "{" + "\"code\":403," + "\"message\":\"" + message + "\"" + "}";
        LOGGER.debug(responseMessage);
        DataBufferFactory factory = response.bufferFactory();
        DataBuffer dataBuffer = factory.wrap(responseMessage.getBytes(StandardCharsets.UTF_8));
        return response.writeAndFlushWith(Flux.just(dataBuffer).map(Flux::just));
    }

}
