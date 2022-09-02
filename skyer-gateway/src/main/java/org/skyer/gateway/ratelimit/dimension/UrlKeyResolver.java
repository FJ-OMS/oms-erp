package org.skyer.gateway.ratelimit.dimension;

import org.skyer.gateway.ratelimit.switcher.EmptyModeSwitcher;
import org.skyer.gateway.ratelimit.switcher.ModeSwitcher;
import org.skyer.gateway.ratelimit.switcher.SwitcherDelegate;
import org.skyer.gateway.util.KeyGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author XCXCXCXCX
 * @date 2019/10/17
 * @project skyer-gateway
 */
public class UrlKeyResolver implements KeyResolver, SwitcherDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserKeyResolver.class);

    private static final String PREFIX = "url";

    private String interestUrl;

    private String[] interestParamKeys;

    private Pattern interestUrlPattern;

    private ModeSwitcher modeSwitcher = EmptyModeSwitcher.EMPTY_INSTANCE;

    public UrlKeyResolver() {}

    /**
     * /v1/{1}/invoke?namespace={2}&serverCode={3}&interfaceCode={4}
     */
    public UrlKeyResolver(String interestUrlTemplate) {
        resolveUrlAndParamKeys(interestUrlTemplate);
        initInterestUrlPattern();
    }

    private void initInterestUrlPattern() {
        String urlPatternTemp = interestUrl;
        if (!StringUtils.isEmpty(urlPatternTemp)) {
            int count = Integer.parseInt(urlPatternTemp.substring(urlPatternTemp.lastIndexOf("{") + 1,
                            urlPatternTemp.lastIndexOf("}")));
            for (int i = 1; i <= count; i++) {
                urlPatternTemp = urlPatternTemp.replace("{" + i + "}", "\\S+");
            }
        }
        interestUrlPattern = Pattern.compile(urlPatternTemp);
    }

    private void resolveUrlAndParamKeys(String template) {
        if (template == null) {
            return;
        }
        String[] urlAndParams = template.split("\\?");
        if (urlAndParams.length == 0) {
            return;
        }
        if (urlAndParams.length == 1) {
            interestUrl = urlAndParams[0];
            interestParamKeys = new String[0];
        } else if (urlAndParams.length == 2) {
            String[] params = urlAndParams[1].split("&");
            interestUrl = urlAndParams[0];
            interestParamKeys = new String[params.length];
            if (params.length > 0) {
                for (int i = 0; i < params.length; i++) {
                    interestParamKeys[i] = params[i].substring(0, params[i].indexOf("="));
                }
            }
        } else {
            LOGGER.error("resolve template failed, apply NULL config");
        }

    }

    @Override
    public ModeSwitcher getModeSwitcher() {
        return modeSwitcher;
    }

    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        return Mono.just(Optional.ofNullable(getUrlAndRequestParams(exchange)).orElse(KeyGenerator.generate()));
    }

    private String getUrlAndRequestParams(ServerWebExchange exchange) {
        String url = getUrl(exchange);
        if (url == null) {
            return null;
        }

        String urlKey = null;

        if (interestUrlPattern != null) {
            Matcher matcher = interestUrlPattern.matcher(url);
            if (matcher.matches()) {
                String requestParamString = getInterestRequestParamString(exchange);
                urlKey = getModeSwitcher().execute(url + requestParamString);
            }
        }

        if (urlKey == null) {
            urlKey = url;
        }

        return PREFIX + urlKey.replaceAll("\\?", "._").replaceAll("/", ".").replaceAll("&", "_").replaceAll("=", "-");
    }

    private String getUrl(ServerWebExchange exchange) {
        return exchange.getRequest().getURI().getPath();
    }

    private String getInterestRequestParamString(ServerWebExchange exchange) {
        MultiValueMap<String, String> queryParams = exchange.getRequest().getQueryParams();
        StringBuilder builder = new StringBuilder();
        if (interestParamKeys.length > 0) {
            for (String interestQueryParam : interestParamKeys) {
                if (queryParams.containsKey(interestQueryParam)) {
                    String value = queryParams.get(interestQueryParam).toString().replaceAll(" ", "");
                    builder.append("&").append(interestQueryParam).append("=").append(value, 1, value.length() - 1);
                } else {
                    String value = "";
                    builder.append("&").append(interestQueryParam).append("=").append(value);
                }
            }
        }
        if (builder.toString().isEmpty()) {
            return "";
        }
        return builder.deleteCharAt(0).insert(0, "?").toString();
    }

}
