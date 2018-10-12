package com.lhiot.resource.config;

import lombok.Data;
import lombok.ToString;

/**
 * @author Leon (234239150@qq.com) created in 17:42 18.10.11
 */
@Data
@ToString
public class FileUploadConfig {
    private String baseUrl;
    private String rootDir;
    private String recycleDir;
}
