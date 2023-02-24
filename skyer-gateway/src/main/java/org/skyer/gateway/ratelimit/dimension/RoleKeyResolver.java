package org.skyer.gateway.ratelimit.dimension;

import org.skyer.core.oauth.CustomUserDetails;
import org.skyer.gateway.helper.entity.RequestContext;
import org.skyer.gateway.ratelimit.switcher.DoubleModeSwitcher;
import org.skyer.gateway.ratelimit.switcher.ModeSwitcher;
import org.skyer.gateway.ratelimit.switcher.SwitcherDelegate;
import org.skyer.gateway.util.KeyGenerator;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * @author XCXCXCXCX
 * @date 2019/11/01
 * @project skyer-gateway
 */
public class RoleKeyResolver implements KeyResolver, SwitcherDelegate {

    private static final String PREFIX = "role";

    private ModeSwitcher modeSwitcher = new DoubleModeSwitcher();

    public RoleKeyResolver() {
        this.modeSwitcher.switchMode(null, null);
    }

    public RoleKeyResolver(String mode, String listString) {
        this.modeSwitcher.switchMode(mode, listString);
    }

    @Override
    public ModeSwitcher getModeSwitcher() {
        return modeSwitcher;
    }

    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        return Mono.just(Optional.ofNullable(getRoleId()).orElse(KeyGenerator.generate()));
    }

    private String getRoleId() {
        CustomUserDetails details = RequestContext.currentRequestContext().getCustomUserDetails();
        if (details == null || details.getRoleId() == null) {
            return null;
        }
        String key = String.valueOf(details.getRoleId());
        String roleKey = getModeSwitcher().execute(key);
        return roleKey == null ? null : PREFIX + roleKey;
    }
}
