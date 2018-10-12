package com.lhiot.resource.api;

import com.leon.microx.util.Converter;
import com.lhiot.resource.config.FileUploadConfig;
import com.lhiot.resource.config.ResourceServerProperties;
import com.lhiot.resource.entity.ResourceFile;
import com.lhiot.resource.model.Base64Img;
import com.lhiot.resource.model.GroupType;
import com.lhiot.resource.repository.FileUploadRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Base64;

/**
 * 文件上传接口
 *
 * @author Leon (234239150@qq.com) created in 17:23 18.10.11
 */
@Slf4j
@RestController
@Api(description = "文件上传")
public class UploadApi {

    private FileUploadConfig config;

    private FileUploadRepository repository;

    public UploadApi(ResourceServerProperties properties, FileUploadRepository repository) {
        this.config = properties.getFileUpload();
        this.repository = repository;
        Assert.notNull(config.getRootDir(), "config: 'root-dir' is required; it must not be null");
        Assert.notNull(config.getBaseUrl(), "config: 'base-url' is required; it must not be null");
        Assert.notNull(config.getRecycleDir(), "config: 'recycle-dir' is required; it must not be null");
    }

    @CrossOrigin
    @Transactional
    @PostMapping("/upload/{groupType}")
    @ApiOperation(value = "文件上传", notes = "上传图片，视频等文件", response = ResourceFile.class,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity upload(
            @ApiParam(name = "groupType", value = "上传的文件所在的子文件夹", required = true)
            @PathVariable("groupType")
            @NotNull(message = "请输入上传文件的groupType") GroupType groupType,

            @ApiParam(name = "attach", value = "附加数据。（任意格式的字符串）")
            @RequestParam(name = "attach", required = false) String attach,

            @ApiParam(allowMultiple = true, name = "file", value = "上传的文件数据", required = true)
            @RequestPart("file")
            @NotNull(message = "请提供类型为MultipartFile的文件数据") MultipartFile file) throws IOException {
        ResourceFile sf = ResourceFile.of(this.config, groupType, attach, file);
        this.save(sf, file.getInputStream());
        return ResponseEntity.created(URI.create(sf.getAccessUrl())).body(sf);
    }

    @CrossOrigin
    @PostMapping("/base64/upload")
    @ApiOperation(value = "base64上传（图片）", notes = "base64上传文件（图片）", response = ResourceFile.class,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity base64(@RequestBody Base64Img img) throws IOException {
        ResourceFile sf = ResourceFile.of(this.config, img);
        byte[] bytes = Base64.getDecoder().decode(img.getContent());
        this.save(sf, new ByteArrayInputStream(bytes));
        return ResponseEntity.created(URI.create(sf.getAccessUrl())).body(sf);
    }

    private void save(ResourceFile sf, InputStream inputStream) throws IOException {
        Path newFile = sf.toPath();
        if (!Files.exists(newFile)) {
            Files.createDirectories(newFile.getParent());
            Files.createFile(newFile);
        }
        long size = Files.copy(inputStream, newFile, StandardCopyOption.REPLACE_EXISTING);
        if (size > 0){
            sf.setSize(size);
            repository.save(sf);
            log.debug("file uploaded and saved to database. size: {}, info: {}", Converter.fileSize(size), sf);
        }
    }
}
