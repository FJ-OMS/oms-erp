package org.skyer.tags;

import org.skyer.autoconfigure.tags.EnableSkyerLabel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Skyer平台服务启动类 主要包含平台通用功能
 * 
 * @author
 */
@EnableSkyerLabel
@EnableDiscoveryClient
@SpringBootApplication
public class TagsApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(TagsApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
