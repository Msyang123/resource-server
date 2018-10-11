package com.lhiot.resource.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * @author Leon (234239150@qq.com) created in 17:38 18.10.11
 */
@Data
@RefreshScope
@ConfigurationProperties(prefix = "resource.server")
public class ResourceServerProperties {

    private FileUploadConfig fileUpload;
}
