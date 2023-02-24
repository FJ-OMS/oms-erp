package org.skyer.autoconfigure.goods;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.skyer.goods.infra.properties.DataHierarchyProperties;
import org.skyer.goods.infra.properties.GoodsProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

import org.skyer.resource.annoation.EnableSkyerResourceServer;

import org.skyer.core.jackson.annotation.EnableObjectMapper;
import org.skyer.core.util.CommonExecutor;

/**
 * @author bojiangzhou 2018/10/25
 */
@ComponentScan(value = {"org.hippius.wd", "org.skyer.goods.api", "org.skyer.goods.app",
        "org.skyer.goods.config", "org.skyer.goods.domain", "org.skyer.goods.infra"})
@EnableFeignClients({"org.skyer.goods", "org.skyer", "org.skyer.plugin"})
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@EnableSkyerResourceServer
@EnableObjectMapper
@EnableAsync
@EnableConfigurationProperties({GoodsProperties.class, DataHierarchyProperties.class})
public class GoodsAutoConfiguration {

    /**
     * 通用线程池
     */
    @Bean
    @Qualifier("commonAsyncTaskExecutor")
    public ThreadPoolExecutor commonAsyncTaskExecutor() {
        int coreSize = CommonExecutor.getCpuProcessors();
        int maxSize = coreSize * 8;
        ThreadPoolExecutor executor =
                        new ThreadPoolExecutor(coreSize, maxSize, 30, TimeUnit.MINUTES, new LinkedBlockingQueue<>(16),
                                        new ThreadFactoryBuilder().setNameFormat("CommonExecutor-%d").build(),
                                        new ThreadPoolExecutor.CallerRunsPolicy());

        CommonExecutor.displayThreadPoolStatus(executor, "HpfmCommonExecutor");
        CommonExecutor.hookShutdownThreadPool(executor, "HpfmCommonExecutor");

        return executor;
    }

}
