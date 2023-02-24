package org.skyer.gateway.filter.metric;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 在读写时要保证原子性
 *
 * @author XCXCXCXCX
 * @version 1.2.0
 * @date 2019/12/6 4:19 下午
 */
public class RequestCount {

    private static final long DEFAULT_EXPIRED_TIME = 24 * 60 * 60 * 1000;

    private volatile int maxRequests = 0;

    private volatile int minRequests = 0;

    private volatile int avgCount = 0;

    private volatile int avgFailedRequests = 0;

    private volatile int avgRequests = 0;

    private volatile int sumFailedRequests = 0;

    private volatile int sumRequests = 0;

    private volatile long startTime = System.currentTimeMillis();

    private volatile long rentTime = System.currentTimeMillis();

    /**
     * 24小时过期时间
     */
    private long expiredTime = DEFAULT_EXPIRED_TIME;

    private ReadWriteLock lock = new ReentrantReadWriteLock();

    private void renew() {
        rentTime = System.currentTimeMillis();
    }

    public boolean isExpired() {
        return System.currentTimeMillis() - rentTime > expiredTime;
    }

    public void setExpiredTime(long expiredTime) {
        this.expiredTime = expiredTime;
    }

    public void join(TimeWindow timeWindow) {
        // 保证数据一致性
        Lock writeLock = lock.writeLock();
        writeLock.lock();
        try {
            int requestTimes = timeWindow.getRequestTimes();
            int failedRequestTimes = timeWindow.getFailedRequestTimes();
            compareAndReplaceMax(requestTimes);
            compareAndReplaceMin(requestTimes);
            compareAndReplaceSum(requestTimes, failedRequestTimes);
            countAvg();
        } finally {
            writeLock.unlock();
        }
    }

    private void compareAndReplaceMax(int requestTimes) {
        if (requestTimes > maxRequests) {
            maxRequests = requestTimes;
        }
    }

    private void compareAndReplaceMin(int requestTimes) {
        if (requestTimes < minRequests) {
            minRequests = requestTimes;
        }
    }

    private void countAvg() {
        avgRequests = sumRequests / ++avgCount;
        avgFailedRequests = sumFailedRequests / ++avgCount;
    }

    private void compareAndReplaceSum(int requestTimes, int failedRequestsTimes) {
        sumRequests += requestTimes;
        sumFailedRequests += failedRequestsTimes;
    }

    public int getMaxRequests() {
        Lock readLock = lock.readLock();
        readLock.lock();
        try {
            return maxRequests;
        } finally {
            readLock.unlock();
        }
    }

    public int getMinRequests() {
        Lock readLock = lock.readLock();
        readLock.lock();
        try {
            return minRequests;
        } finally {
            readLock.unlock();
        }
    }

    public int getAvgCount() {
        Lock readLock = lock.readLock();
        readLock.lock();
        try {
            return avgCount;
        } finally {
            readLock.unlock();
        }
    }

    public int getAvgFailedRequests() {
        Lock readLock = lock.readLock();
        readLock.lock();
        try {
            return avgFailedRequests;
        } finally {
            readLock.unlock();
        }
    }

    public int getAvgRequests() {
        Lock readLock = lock.readLock();
        readLock.lock();
        try {
            return avgRequests;
        } finally {
            readLock.unlock();
        }
    }

    public int getSumFailedRequests() {
        Lock readLock = lock.readLock();
        readLock.lock();
        try {
            return sumFailedRequests;
        } finally {
            readLock.unlock();
        }
    }

    public int getSumRequests() {
        Lock readLock = lock.readLock();
        readLock.lock();
        try {
            return sumRequests;
        } finally {
            readLock.unlock();
        }
    }

    public long getStartTime() {
        Lock readLock = lock.readLock();
        readLock.lock();
        try {
            return startTime;
        } finally {
            readLock.unlock();
        }
    }

    public long getEndTime() {
        return System.currentTimeMillis();
    }
}
