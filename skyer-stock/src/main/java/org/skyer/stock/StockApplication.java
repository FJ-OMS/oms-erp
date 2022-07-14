package org.skyer.stock;

import org.skyer.autoconfigure.stock.EnableSkyerStock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Skyer平台服务启动类 主要包含平台通用功能
 * 
 * @author
 */
@EnableSkyerStock
@EnableDiscoveryClient
@SpringBootApplication
public class StockApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(StockApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
