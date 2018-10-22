package com.lhiot.resource.api;

import com.leon.microx.web.result.Multiple;
import com.lhiot.resource.entity.ResourceFile;
import com.lhiot.resource.model.FileSearch;
import com.lhiot.resource.repository.FileUploadRepository;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;

/**
 * @author Leon (234239150@qq.com) created in 14:35 18.10.12
 */
@Slf4j
@RestController
@RequestMapping("/files")
public class ResourceFileApi {

    private FileUploadRepository repository;

    public ResourceFileApi(FileUploadRepository repository) {
        this.repository = repository;
    }

    @CrossOrigin
    @Transactional
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除上传的文件", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity deleteFile(@PathVariable("id") long id) throws IOException {
        ResourceFile resourceFile = repository.getOne(id);
        boolean deleted = Files.deleteIfExists(resourceFile.toPath());
        if (deleted) {
            repository.delete(resourceFile);
        }
        return ResponseEntity.noContent().build();
    }

    @CrossOrigin
    @Transactional
    @PostMapping("/search")
    @ApiOperation(value = "查询文件列表", response = ResourceFile.class, responseContainer = "Set",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity files(@RequestBody(required = false) FileSearch search) {
        List<ResourceFile> list;
        if (Objects.isNull(search)) {
            list = repository.findAll();
        } else {
            list = repository.findAll(search.specification());
        }
        return ResponseEntity.ok().body(Multiple.of(list));
    }
}
