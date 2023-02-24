package org.skyer.gateway.filter.metric;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.trace.http.HttpTrace;

import java.util.ArrayList;
import java.util.List;

/**
 * @author XCXCXCXCX
 * @version 1.2.0
 * @date 2019/12/6 11:18 上午
 */
public class CustomInMemoryHttpTraceRepository
                extends org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomInMemoryHttpTraceRepository.class);

    private List<TraceListener> listeners = new ArrayList<>();

    public CustomInMemoryHttpTraceRepository(List<TraceListener> listeners) {
        this.listeners.addAll(listeners);
    }

    @Override
    public void add(HttpTrace trace) {
        super.add(trace);
        listeners.forEach(listener -> {
            try {
                listener.onTrace(trace);
            } catch (Exception e) {
                LOGGER.error("listener onTrace() failed", e);
            }
        });
    }

}
