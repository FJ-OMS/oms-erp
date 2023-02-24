package org.skyer.gateway.route;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.skyer.common.SkyerService;
import org.skyer.gateway.route.entity.ServiceRoute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 1.1.0 采用监听spring事件拉全部路由的方式(监听到路由刷新事件时重新拉一次路由) RoadMap 无需拉全部路由，通过管理端口暴露接口，允许动态增删路由
 *
 * @author XCXCXCXCX
 */
public class DatabaseRouteDefinitionRepository implements RouteDefinitionRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseRouteDefinitionRepository.class);

    private static final String ENDPOINT = "/routes";
    private static final String CONTEXT = "CONTEXT";

    private volatile RouteDefinition[] cache;

    private RestTemplate restTemplate = new RestTemplate();

    private ObjectMapper objectMapper = new ObjectMapper();

    private Environment env;

    private DiscoveryClient discoveryClient;

    public DatabaseRouteDefinitionRepository(Environment environment, DiscoveryClient discoveryClient) {
        this.env = environment;
        this.discoveryClient = discoveryClient;
        this.cache = fetchRoutesFromAdmin();
    }

    private RouteDefinition[] fetchRoutesFromAdmin() {
        List<RouteDefinition> routeDefinitions = requestAdmin();
        if (routeDefinitions == null) {
            return new RouteDefinition[0];
        }
        Object[] objects = routeDefinitions.toArray();
        return Arrays.copyOf(objects, objects.length, RouteDefinition[].class);
    }

    private List<RouteDefinition> requestAdmin() {
        LOGGER.info("fetch routes from {}", env.resolvePlaceholders(SkyerService.Admin.NAME));
        try {
            return doRequest();
        } catch (Throwable e) {
            LOGGER.error("fetch routes failed, applying application.yml routes, throwable cause: ", e);
            return null;
        }
    }

    private List<RouteDefinition> doRequest() {

        List<ServiceInstance> instances =
                        discoveryClient.getInstances(env.resolvePlaceholders(SkyerService.Admin.NAME));

        ServiceRoute[] routes = null;

        for (ServiceInstance instance : instances) {
            String uri = "http://" + instance.getHost() + ":" + instance.getPort();
            String contextPath = null;
            if ((contextPath = getContextPath(instance)) != null) {
                uri = uri + contextPath;
            }
            uri = uri + ENDPOINT;
            try {
                routes = restTemplate.getForObject(uri, ServiceRoute[].class);
            } catch (Throwable e) {
                LOGGER.debug("restTemplate request failed [uri = " + uri + "], retry to a new instance..");
            }
            if (routes != null) {
                LOGGER.debug("restTemplate request success.");
                return transferRouteDefinition(routes);
            }
        }
        throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
    }

    private String getContextPath(ServiceInstance instance) {
        return instance.getMetadata() == null ? null : instance.getMetadata().get(CONTEXT);
    }

    private List<RouteDefinition> transferRouteDefinition(ServiceRoute[] routes) {
        List<RouteDefinition> routeDefinitions = new ArrayList<>();
        for (ServiceRoute route : routes) {
            try {
                RouteDefinition routeDefinition = new RouteDefinition();
                String id = route.getName();
                String uri = "lb://" + route.getServiceCode();
                if (route.getUrl() != null) {
                    uri = route.getUrl();
                }
                JsonNode predicates = null;
                JsonNode filters = null;
                if (route.getExtendConfigMap() != null) {
                    JsonNode extend = null;
                    try {
                        extend = objectMapper.readTree(route.getExtendConfigMap());
                    } catch (IOException e) {
                        LOGGER.error("json convert failed", e);
                    }
                    if (extend != null) {
                        predicates = extend.get("predicates");
                        filters = extend.get("filters");
                    }
                }

                List<PredicateDefinition> predicateDefinitions = buildPredicateDefinitions(route, predicates);
                List<FilterDefinition> filterDefinitions = buildFilterDefinitions(route, filters);
                routeDefinition.setId(id);
                routeDefinition.setUri(URI.create(uri));
                routeDefinition.setPredicates(predicateDefinitions);
                routeDefinition.setFilters(filterDefinitions);
                routeDefinitions.add(routeDefinition);
            } catch (Throwable e) {
                LOGGER.error("unknown error", e);
            }
        }
        return routeDefinitions;
    }

    private List<PredicateDefinition> buildPredicateDefinitions(ServiceRoute route, JsonNode predicates) {
        List<PredicateDefinition> predicateDefinitions = new ArrayList<>();
        PredicateDefinition definition = new PredicateDefinition("Path=" + route.getPath());
        predicateDefinitions.add(definition);
        if (predicates != null) {
            predicates.forEach(predicate -> {
                PredicateDefinition def = new PredicateDefinition();
                def.setName(predicate.get("name").asText());
                try {
                    def.setArgs(objectMapper.readValue(predicate.get("args").toString(), Map.class));
                } catch (IOException e) {
                    LOGGER.error("json string convert to map failed", e);
                    return;
                }
                predicateDefinitions.add(def);
            });
        }
        return predicateDefinitions;
    }

    private List<FilterDefinition> buildFilterDefinitions(ServiceRoute route, JsonNode filters) {
        List<FilterDefinition> filterDefinitions = new ArrayList<>();
        if (route.ifStripPrefix()) {
            FilterDefinition stripPrefixDefinition = new FilterDefinition("StripPrefix=" + route.getStripPrefix());
            filterDefinitions.add(stripPrefixDefinition);
        }
        if (!StringUtils.isEmpty(route.getSensitiveHeaders())) {
            FilterDefinition removeRequestHeaderFilter =
                            new FilterDefinition("RemoveResponseHeader=" + route.getSensitiveHeaders());
            filterDefinitions.add(removeRequestHeaderFilter);
        }
        FilterDefinition preserveHostHeaderDefinition = new FilterDefinition("PreserveHostHeader");
        filterDefinitions.add(preserveHostHeaderDefinition);
        if (filters != null) {
            filters.forEach(filter -> {
                FilterDefinition def = new FilterDefinition();
                Map<String, Object> filterMap = null;
                try {
                    filterMap = objectMapper.readValue(filter.asText(), Map.class);
                    def.setName((String) filterMap.get("name"));
                    def.setArgs((Map<String, String>) filterMap.get("args"));
                } catch (IOException e) {
                    LOGGER.error("json string convert to map failed", e);
                    return;
                }
                filterDefinitions.add(def);
            });
        }
        return filterDefinitions;
    }

    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        return Flux.just(this.cache);
    }

    /**
     * 现有的刷新机制无需使用
     *
     * @param route
     * @return
     */
    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return null;
    }

    /**
     * 现有的刷新机制无需使用
     *
     * @param routeId
     * @return
     */
    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        return null;
    }

    @EventListener(RefreshRoutesEvent.class)
    public void onApplicationEvent(RefreshRoutesEvent event) {
        RouteDefinition[] tmp = fetchRoutesFromAdmin();
        if (tmp.length > 0) {
            cache = tmp;
        }
    }
}
