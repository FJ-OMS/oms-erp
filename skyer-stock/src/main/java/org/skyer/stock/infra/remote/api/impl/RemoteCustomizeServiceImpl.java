package org.skyer.stock.infra.remote.api.impl;

import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.skyer.stock.infra.properties.StockProperties;
import org.skyer.stock.infra.remote.api.RemoteCustomizeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import org.skyer.core.exception.CommonException;
import org.skyer.core.exception.ExceptionResponse;

import org.skyer.core.base.BaseConstants;
import org.skyer.core.net.RequestHeaderCopyInterceptor;
import org.skyer.boot.api.customize.commons.vo.MethodMetaData;

/**
 * 远程 RemoteCustomizeService 方法
 *
 * @author bojiangzhou 2019/07/04
 */
@Component
public class RemoteCustomizeServiceImpl implements RemoteCustomizeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RemoteCustomizeServiceImpl.class);

    @LoadBalanced
    private final RestTemplate balanceRestTemplate;
    private final StockProperties stockProperties;
    private final DiscoveryClient discoveryClient;

    private final RestTemplate normalRestTemplate;

    private static final String SCAN_URI = "/v1/customize/scan";
    private static final String APPLY_URI = "/v1/customize/apply";

    private ParameterizedTypeReference<List<MethodMetaData>> METHOD_TYPE =
                    new ParameterizedTypeReference<List<MethodMetaData>>() {};

    public RemoteCustomizeServiceImpl(RestTemplate restTemplate, StockProperties stockProperties,
                    DiscoveryClient discoveryClient) {
        this.balanceRestTemplate = restTemplate;
        this.stockProperties = stockProperties;
        this.discoveryClient = discoveryClient;

        normalRestTemplate = new RestTemplate();
        normalRestTemplate.setInterceptors(Collections.singletonList(new RequestHeaderCopyInterceptor()));
    }

    @Override
    public List<MethodMetaData> fetchServiceMethods(String serviceName, String packageNames) {
        String[] names = packageNames.split(BaseConstants.Symbol.COMMA);
        StringBuilder builder = new StringBuilder();
        for (String name : names) {
            builder.append("&packageNames=").append(name);
        }
        String params = builder.toString().replaceFirst("&", "?");
        String url = stockProperties.getFullHttpProtocol() + serviceName + SCAN_URI + params;
        LOGGER.debug("fetch service class methods, service = [{}], url = [{}]", serviceName, url);
        List<MethodMetaData> points;
        try {
            ResponseEntity<List<MethodMetaData>> responseEntity =
                            balanceRestTemplate.exchange(url, HttpMethod.GET, null, METHOD_TYPE);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                points = responseEntity.getBody();
            } else {
                throw new CommonException("hpfm.warn.fetchMethods.failure", responseEntity.getStatusCodeValue());
            }
        } catch (RestClientException e) {
            LOGGER.error("fetch service class methods exception, service = {}, package = {}, ex={}", serviceName,
                            packageNames, e.getMessage());
            throw new CommonException("hpfm.warn.fetchMethods.exception", e.getMessage());
        } catch (Exception e) {
            LOGGER.error("fetch service class methods error, service = {}, package = {}, ex = {}", serviceName,
                            packageNames, e.getMessage());
            throw new CommonException("hpfm.warn.fetchServiceException", serviceName);
        }

        if (LOGGER.isDebugEnabled() && CollectionUtils.isNotEmpty(points)) {
            for (MethodMetaData point : points) {
                LOGGER.debug("point = {}", point);
            }
        }

        return points;
    }

    @Override
    public void applyCustomizeRule(List<String> serviceNames, List<String> ruleCodes) {
        for (String serviceName : serviceNames) {
            applyCustomizeRule(serviceName, ruleCodes);
        }
    }

    @Override
    public void applyCustomizeRule(String serviceName, List<String> ruleCodes) {
        StringBuilder builder = new StringBuilder();
        for (String ruleCode : ruleCodes) {
            builder.append("&ruleCodes=").append(ruleCode);
        }
        String params = builder.toString().replaceFirst("&", "?");

        List<ServiceInstance> instances = discoveryClient.getInstances(serviceName);

        if (CollectionUtils.isEmpty(instances)) {
            throw new CommonException("hpfm.warn.customize.instanceNotRunning", serviceName);
        }

        for (ServiceInstance instance : instances) {
            String url = instance.getUri().toString() + APPLY_URI + params;
            LOGGER.debug("apply customize rule, service = [{}], url = [{}]", serviceName, url);

            try {
                ResponseEntity<ExceptionResponse> responseEntity =
                                normalRestTemplate.getForEntity(url, ExceptionResponse.class);
                if (!responseEntity.getStatusCode().is2xxSuccessful()) {
                    if (responseEntity.getBody() != null) {
                        throw new CommonException("hpfm.warn.customizeRuleFailure", serviceName,
                                        responseEntity.getBody().getMessage());
                    } else {
                        throw new CommonException("hpfm.warn.callServiceFailure", serviceName);
                    }
                }
            } catch (Exception e) {
                throw new CommonException("hpfm.warn.callServiceFailure", serviceName);
            }

        }
    }

}
