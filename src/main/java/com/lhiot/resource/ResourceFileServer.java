package com.lhiot.resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Spring Boot 2.x 文件上传
 * spring.servlet.multipart.max-file-size
 * spring.servlet.multipart.max-request-size
 *
 * @author Leon (234239150@qq.com) created in 17:12 18.10.11
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableTransactionManagement
public class ResourceFileServer {

    public static void main(String[] args) {
        SpringApplication.run(ResourceFileServer.class, args);
    }
}
