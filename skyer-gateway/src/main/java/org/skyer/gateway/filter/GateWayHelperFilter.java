package org.skyer.gateway.filter;

import org.apache.commons.lang3.StringUtils;
import org.skyer.gateway.helper.api.reactive.ReactiveAuthenticationHelper;
import org.skyer.gateway.helper.entity.CheckState;
import org.skyer.gateway.helper.entity.ResponseContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

import static org.skyer.core.variable.RequestVariableHolder.HEADER_JWT;

/**
 * 自定义的servlet filter 负责将HTTP请求去除消息体后转发到gateway helper去权限校验，限流 org.skyer.gateway
 * helper返回后将授权码和label加到消息头部 再交给zuul(1.0.0以后是sc-gateway)去路由到真实服务
 * <p>
 * Mark: 修改了返回状态码支持401
 *
 * @author bojiangzhou
 * @author flyleft
 */
@Order(-1)
public class GateWayHelperFilter implements WebFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(GateWayHelperFilter.class);

    private ReactiveAuthenticationHelper authenticationHelper;

    private static final String CONFIG_ENDPOINT = "/skyer/config";

    /**
     * 构造器
     *
     * @param authenticationHelper spring内部requestCustomizers
     */
    public GateWayHelperFilter(ReactiveAuthenticationHelper authenticationHelper) {
        this.authenticationHelper = authenticationHelper;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String uri = exchange.getRequest().getURI().getPath();

        if (CONFIG_ENDPOINT.equals(uri)) {
            return chain.filter(exchange);
        }

        ResponseContext responseContext = null;
        try {
            responseContext = authenticationHelper.authentication(exchange);
            if (StringUtils.equals(responseContext.getRequestCode(), CheckState.SUCCESS_SKIP_PATH.getCode())) {
                return chain.filter(exchange);
            }
            if (responseContext.getHttpStatus().is2xxSuccessful()) {
                String jwtToken = responseContext.getJwtToken();
                return chain.filter(exchange.mutate().request(builder -> builder.header(HEADER_JWT, jwtToken)).build());
            } else {
                return handleError(exchange.getResponse(), responseContext);
            }
        } catch (IOException e) {
            LOGGER.error("org.skyer.gateway call helper authentication error, ex={}", e.getMessage());

            ServerHttpResponse res = exchange.getResponse();
            StringBuilder responseMessage = new StringBuilder().append("<error>").append("<status>500</status>")
                            .append("<code>GATEWAY_ERROR</code>").append("<message>").append(e.getMessage())
                            .append("</message>").append("</error>");

            res.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            setHeaders(res);

            DataBufferFactory factory = res.bufferFactory();
            DataBuffer dataBuffer = factory.wrap(responseMessage.toString().getBytes(StandardCharsets.UTF_8));
            return res.writeAndFlushWith(Flux.just(dataBuffer).map(Flux::just));
        }

    }

    private void setHeaders(ServerHttpResponse response) {
        HttpHeaders headers = response.getHeaders();
        headers.put(HttpHeaders.ACCEPT_CHARSET, Collections.singletonList("utf-8"));
        headers.put(HttpHeaders.CONTENT_TYPE, Collections.singletonList("text/html;charset=UTF-8"));
    }

    /**
     * 鉴权失败逻辑处理
     *
     * @param response ServerHttpResponse
     * @param responseContext ResponseContext
     */
    private Mono<Void> handleError(ServerHttpResponse response, ResponseContext responseContext) throws IOException {
        setHeaders(response);

        int statusCode = responseContext.getHttpStatus().value();
        String requestStatus = responseContext.getRequestStatus();
        String requestCode = responseContext.getRequestCode();
        String requestMessage = responseContext.getRequestMessage();

        StringBuilder responseMessage = new StringBuilder();
        if (statusCode == HttpStatus.FORBIDDEN.value()) {
            response.setStatusCode(HttpStatus.FORBIDDEN);
            responseMessage.append("<oauth>").append("<status>").append(requestStatus).append("</status>")
                            .append("<code>").append(requestCode).append("</code>").append("<message>")
                            .append(requestMessage).append("</message>").append("</oauth>");
        } else if (statusCode == HttpStatus.UNAUTHORIZED.value()) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            responseMessage.append("<oauth>").append("<status>").append(requestStatus).append("</status>")
                            .append("<code>").append(requestCode).append("</code>").append("<message>")
                            .append(requestMessage).append("</message>").append("</oauth>");
        } else {
            if (CheckState.PERMISSION_SERVICE_ROUTE.getCode().equals(requestCode)) {
                response.setStatusCode(HttpStatus.SERVICE_UNAVAILABLE);
            }
            responseMessage.append("<oauth>").append("<status>").append(requestStatus).append("</status>")
                            .append("<code>").append(requestCode).append("</code>").append("<message>")
                            .append(requestMessage).append("</message>").append("</oauth>");
        }
        LOGGER.warn("org.skyer.gateway-helper response message, {}", responseMessage.toString());
        DataBufferFactory factory = response.bufferFactory();
        DataBuffer dataBuffer = factory.wrap(responseMessage.toString().getBytes(StandardCharsets.UTF_8));
        return response.writeAndFlushWith(Flux.just(dataBuffer).map(Flux::just));
    }

}
