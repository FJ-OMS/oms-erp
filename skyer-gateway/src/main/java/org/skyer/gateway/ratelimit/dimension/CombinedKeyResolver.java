package org.skyer.gateway.ratelimit.dimension;

import org.skyer.gateway.util.KeyGenerator;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 仅允许动态构造（必须由多个KeyResolver构成），无需注入spring容器
 */
public class CombinedKeyResolver implements KeyResolver {

    private List<KeyResolver> keyResolvers = new ArrayList<>();

    public CombinedKeyResolver(KeyResolver... keyResolvers) {
        this.keyResolvers.addAll(Arrays.asList(keyResolvers));
    }

    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        return keyResolvers.stream().map(keyResolver -> keyResolver.resolve(exchange))
                        .reduce((key1, key2) -> key1.zipWith(key2, (k1, k2) -> k1 + k2))
                        .orElse(Mono.just(KeyGenerator.generate()));
    }
}
