package com.lhiot.resource.api;

import com.lhiot.resource.config.FileUploadConfig;
import com.lhiot.resource.config.ResourceServerProperties;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.time.format.DateTimeFormatter;

/**
 * 文件上传接口
 *
 * @author Leon (234239150@qq.com) created in 17:23 18.10.11
 */
@Slf4j
@CrossOrigin
@RestController
@Api(description = "文件上传")
public class UploadApi {

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private FileUploadConfig config;

    public UploadApi(ResourceServerProperties properties) {
        this.config = properties.getFileUpload();
        Assert.notNull(config.getRootDir(), "config: 'root-dir' is required; it must not be null");
        Assert.notNull(config.getBaseUrl(), "config: 'base-url' is required; it must not be null");
        Assert.notNull(config.getRecycleDir(), "config: 'recycle-dir' is required; it must not be null");
    }

    @ApiIgnore
    @RequestMapping({"", "/", "/index", "/default"})
    public ResponseEntity index() {
        return ResponseEntity.ok().build();
    }


}
