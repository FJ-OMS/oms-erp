package org.skyer.gateway.filter.metric;

import org.springframework.boot.actuate.trace.http.HttpTrace;

/**
 * @author XCXCXCXCX
 * @version 1.2.0
 * @date 2019/12/6 11:17 上午
 */
public interface TraceListener {

    void onTrace(HttpTrace trace) throws Exception;

}
