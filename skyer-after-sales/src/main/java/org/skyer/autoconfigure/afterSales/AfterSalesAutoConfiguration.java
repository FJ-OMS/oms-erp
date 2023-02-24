package org.skyer.autoconfigure.afterSales;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.skyer.afterSales.infra.properties.AfterSalesProperties;
import org.skyer.afterSales.infra.properties.DataHierarchyProperties;
import org.skyer.core.jackson.annotation.EnableObjectMapper;
import org.skyer.core.util.CommonExecutor;
import org.skyer.resource.annoation.EnableSkyerResourceServer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author bojiangzhou 2018/10/25
 */
@ComponentScan(value = {"org.hippius.wd", "org.skyer.afterSales.api", "org.skyer.afterSales.app",
        "org.skyer.afterSales.config", "org.skyer.afterSales.domain", "org.skyer.afterSales.infra"})
@EnableFeignClients({"org.skyer.afterSales", "org.skyer", "org.skyer.plugin"})
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@EnableSkyerResourceServer
@EnableObjectMapper
@EnableAsync
@EnableConfigurationProperties({AfterSalesProperties.class, DataHierarchyProperties.class})
public class AfterSalesAutoConfiguration {

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
