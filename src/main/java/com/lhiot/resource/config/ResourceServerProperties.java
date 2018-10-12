package com.lhiot.resource.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import static com.lhiot.resource.config.ResourceServerProperties.PREFIX;

/**
 * @author Leon (234239150@qq.com) created in 17:38 18.10.11
 */
@Data
@RefreshScope
@ConfigurationProperties(prefix = PREFIX)
public class ResourceServerProperties {
    static final String PREFIX = "lhiot.resource-server";

    private FileUploadConfig fileUpload;
}
