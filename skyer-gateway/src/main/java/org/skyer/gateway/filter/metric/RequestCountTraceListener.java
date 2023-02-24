package org.skyer.gateway.filter.metric;

import org.skyer.gateway.filter.IpCheckedFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @author XCXCXCXCX
 * @version 1.2.0
 * @date 2019/12/6 11:22 上午
 */
public class RequestCountTraceListener implements TraceListener {

    public static final int SELECT_DB = 1;

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestCountTraceListener.class);
    private static final int FAILED_HTTP_STATUS = 300;

    private RequestCountRules requestCountRules;

    public RequestCountTraceListener(RequestCountRules requestCountRules) {
        this.requestCountRules = requestCountRules;
    }

    @Override
    public void onTrace(HttpTrace trace) {
        String uri = trace.getRequest().getUri().getPath();
        int index = requestCountRules.match(uri);
        if (index != -1) {
            String ip = getIp(trace);
            if (ip == null) {
                LOGGER.warn("unresolved ip?");
                ip = "unresolved";
            }
            TimeWindow window = requestCountRules.getTimeWindow(index, uri, ip);
            if (window == null) {
                LOGGER.debug("time window is not found, request count rules might be abandoned.");
                return;
            }
            doCount(window, trace);
        }
    }

    private void doCount(TimeWindow timeWindow, HttpTrace trace) {
        timeWindow.incrRequestTimes();
        if (trace.getResponse().getStatus() > FAILED_HTTP_STATUS) {
            timeWindow.incrFailedRequestTimes();
        }
    }

    private String getIp(HttpTrace trace) {

        String ip = null;

        String remoteAddress = trace.getRequest().getRemoteAddress();
        if (!StringUtils.isEmpty(remoteAddress)) {
            ip = remoteAddress;
        }
        Map<String, List<String>> headers = trace.getRequest().getHeaders();
        List<String> forwardIp = headers.get(IpCheckedFilter.X_FORWARDED_FOR);
        if (!CollectionUtils.isEmpty(forwardIp)) {
            ip = forwardIp.get(0);
        }
        List<String> realIp = headers.get(IpCheckedFilter.X_REAL_IP);
        if (!CollectionUtils.isEmpty(realIp)) {
            ip = realIp.get(0);
        }

        return ip;
    }
}
