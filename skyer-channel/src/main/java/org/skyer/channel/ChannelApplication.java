package org.skyer.channel;

import org.skyer.autoconfigure.channel.EnableSkyerChannel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Skyer平台服务启动类 主要包含平台通用功能
 * 
 * @author
 */
@EnableSkyerChannel
@EnableDiscoveryClient
@SpringBootApplication
public class ChannelApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(ChannelApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
