package org.skyer.goods;

import org.skyer.autoconfigure.goods.EnableSkyerGoods;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Skyer平台服务启动类 主要包含平台通用功能
 * 
 * @author
 */
@EnableSkyerGoods
@EnableDiscoveryClient
@SpringBootApplication
public class GoodsApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(GoodsApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
