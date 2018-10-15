package com.lhiot.resource.entity;

import com.leon.microx.util.Converter;
import com.leon.microx.util.StringUtils;
import com.leon.microx.util.auditing.Random;
import com.lhiot.resource.config.ResourceServerProperties;
import com.lhiot.resource.model.Base64Img;
import com.lhiot.resource.model.GroupType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 资源文件实体
 *
 * @author Leon (234239150@qq.com) created in 10:26 18.10.12
 */
@Data
@Entity
@ToString
@NoArgsConstructor
public class ResourceFile implements Serializable {

    @Transient
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private FileGroup fileGroup;

    private String originalName;

    private String root;

    private String subdirectory;

    private String newName;

    private Long size;

    private String accessUrl;

    private String attach;

    private Date createAt;

    @Version
    private Long version;

    public Path toPath(){
        return Paths.get(StringUtils.format("{}/{}/{}", this.root, this.subdirectory, this.newName));
    }

    public static ResourceFile of(ResourceServerProperties.FileUploadConfig config, Base64Img img){
        LocalDateTime now = LocalDateTime.now();
        ResourceFile sf = new ResourceFile();
        sf.setFileGroup(FileGroup.BASE64_IMAGE);
        sf.setOriginalName(img.getFileName());
        sf.setRoot(config.getRootDir());
        sf.setSubdirectory(StringUtils.format("{}/{}", FileGroup.BASE64_IMAGE.name().toLowerCase(), now.format(dateTimeFormatter)));
        sf.setNewName(Random.length(20) + "." + StringUtils.getFilenameExtension(img.getFileName()));
        sf.setAttach(img.getAttach());
        sf.setCreateAt(Converter.date(now));
        sf.setAccessUrl(StringUtils.format("{}/{}/{}", config.getBaseUrl(), sf.getSubdirectory(), sf.getNewName()));
        return sf;
    }

    public static  ResourceFile of(ResourceServerProperties.FileUploadConfig config, GroupType groupType, String attach, MultipartFile file) {
        LocalDateTime now = LocalDateTime.now();
        ResourceFile sf = new ResourceFile();
        sf.setFileGroup(groupType.toFileGroup());
        sf.setOriginalName(file.getOriginalFilename());
        sf.setRoot(config.getRootDir());
        sf.setSubdirectory(StringUtils.format("{}/{}", groupType.name(), now.format(dateTimeFormatter)));
        sf.setNewName(Random.length(20) + "." + StringUtils.getFilenameExtension(file.getOriginalFilename()));
        sf.setAttach(attach);
        sf.setCreateAt(Converter.date(now));
        sf.setAccessUrl(StringUtils.format("{}/{}/{}", config.getBaseUrl(), sf.getSubdirectory(), sf.getNewName()));
        return sf;
    }
}
