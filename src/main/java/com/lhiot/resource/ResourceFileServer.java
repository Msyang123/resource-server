package com.lhiot.resource;

import com.lhiot.resource.config.ResourceServerProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Spring Boot 2.x 文件上传
 * spring.servlet.multipart.max-file-size
 * spring.servlet.multipart.max-request-size
 *
 * @author Leon (234239150@qq.com) created in 17:12 18.10.11
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigurationProperties(ResourceServerProperties.class)
public class ResourceFileServer {

    public static void main(String[] args) {
        SpringApplication.run(ResourceFileServer.class, args);
    }
}
