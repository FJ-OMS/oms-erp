package org.skyer.gateway.filter.metric;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author XCXCXCXCX
 * @version 1.2.0
 * @date 2019/12/6 2:23 下午
 */
public final class RequestCounter {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestCounter.class);

    private final ThreadPoolExecutor single = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<Runnable>(), r -> new Thread(r, "Request-Counter"));

    private final Map<String, RequestCount> countMap = new ConcurrentHashMap<>();

    private final RequestCountRules requestCountRules;

    public RequestCounter(RequestCountRules requestCountRules) {
        this.requestCountRules = requestCountRules;
    }

    public void start() {
        single.execute(this::consume);
    }

    private void consume() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                // 检查timeWindow，统计并移除已过期的时间窗口数据
                // 自动拉黑机制
                List<String> expiredKeys = new ArrayList<>();
                List<CountTask> countTasks = new ArrayList<>();
                for (Map.Entry<String, TimeWindow> entry : requestCountRules.getTimeWindowsMap().entrySet()) {
                    TimeWindow window = entry.getValue();
                    if (window.isExpired()) {
                        requestCountRules.blacklistIfNeed(entry.getKey(), window.getRequestTimes(),
                                        window.getFailedRequestTimes());
                        expiredKeys.add(entry.getKey());
                        countTasks.add(new CountTask(entry.getKey(), window));
                    }
                }
                requestCountRules.removeTimeWindows(expiredKeys);

                // 定期清理历史数据
                Iterator<Map.Entry<String, RequestCount>> iterator = countMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, RequestCount> entry = iterator.next();
                    RequestCount requestCount = entry.getValue();
                    if (requestCount.isExpired() && !requestCountRules.containKey(entry.getKey())) {
                        iterator.remove();
                    }
                }

                if (countTasks.isEmpty()) {
                    // 空闲cpu
                    Thread.sleep(100);
                } else {
                    doConsume(countTasks);
                }
            }
        } catch (InterruptedException e) {
            LOGGER.error("current thread is interrupted.", e);
        }
    }

    private void doConsume(List<CountTask> countTasks) {
        for (CountTask task : countTasks) {
            doConsume(task);
        }
    }

    private void doConsume(CountTask task) {
        String key = task.key;
        TimeWindow timeWindow = task.timeWindow;
        RequestCount count = countMap.get(key);
        if (count == null) {
            countMap.putIfAbsent(key, new RequestCount());
        }
        count = countMap.get(key);
        count.join(timeWindow);
    }

    public Map<String, RequestCount> getCountMap() {
        return countMap;
    }

    protected static class CountTask {

        // {id}:{uri}:{ip}
        private String key;

        private TimeWindow timeWindow;

        public CountTask(String key, TimeWindow timeWindow) {
            this.key = key;
            this.timeWindow = timeWindow;
        }

    }


}
