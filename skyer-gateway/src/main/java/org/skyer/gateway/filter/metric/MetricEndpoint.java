package org.skyer.gateway.filter.metric;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;

import java.util.Map;

/**
 * @author XCXCXCXCX
 * @version 1.2.0
 * @date 2019/12/9 9:59 上午
 */
@Endpoint(id = "request-count")
public class MetricEndpoint {

    private final RequestCounter requestCounter;

    public MetricEndpoint(RequestCounter requestCounter) {
        this.requestCounter = requestCounter;
    }

    @ReadOperation
    public Map<String, RequestCount> read() {
        return requestCounter.getCountMap();
    }
}
