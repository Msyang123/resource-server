package com.lhiot.resource.model;

import com.leon.microx.util.Converter;
import com.leon.microx.util.StringUtils;
import com.lhiot.resource.entity.FileGroup;
import com.lhiot.resource.entity.ResourceFile;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author Leon (234239150@qq.com) created in 14:38 18.10.12
 */
@Data
@ApiModel
@ToString
public class FileSearch {

    private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    @ApiModelProperty(notes = "关键字（按原文件名搜索）", dataType = "String")
    private String keywords;

    @ApiModelProperty(notes = "文件分组", dataType = "FileGroup")
    private FileGroup fileGroup;

    @ApiModelProperty(notes = "开始时间", dataType = "String")
    private String startTime;

    @ApiModelProperty(notes = "结束时间", dataType = "String")
    private String endTime;

    @ApiModelProperty(value = "最小值", notes = "查询大于此大小的文件", dataType = "String", example = "1.5MB or 30KB")
    private String minSize;

    @ApiModelProperty(value = "最大值", notes = "查询小于此大小的文件", dataType = "String", example = "1.5MB or 30KB")
    private String maxSize;

    private String keywords() {
        return StringUtils.format("%{}%", this.keywords);
    }

    private Date formatDate(String timeString) {
        return Converter.date(timeString, DATE_FORMAT_PATTERN);
    }

    private Long formatSize(String sizeString) {
        return Converter.byteSize(sizeString);
    }

    public Specification<ResourceFile> specification() {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (Objects.nonNull(this.fileGroup)) { // 文件分组
                predicates.add(builder.equal(root.get("fileGroup").as(FileGroup.class), this.fileGroup));
            }
            if (StringUtils.hasLength(this.keywords)) {  //模糊查询
                predicates.add(builder.like(root.get("originalName").as(String.class), this.keywords()));
            }
            if (StringUtils.hasLength(this.startTime)) { //大于或等于传入时间
                predicates.add(builder.greaterThanOrEqualTo(root.get("createAt").as(Date.class), this.formatDate(this.startTime)));
            }
            if (StringUtils.hasLength(this.endTime)) {  //小于或等于传入时间
                predicates.add(builder.lessThanOrEqualTo(root.get("createAt").as(Date.class), this.formatDate(this.endTime)));
            }
            if (StringUtils.hasLength(this.minSize)) { //大于或等于文件大小
                predicates.add(builder.greaterThanOrEqualTo(root.get("size").as(Long.class), this.formatSize(this.minSize)));
            }
            if (StringUtils.hasLength(this.maxSize)) {  //小于或等于文件大小
                predicates.add(builder.lessThanOrEqualTo(root.get("size").as(Long.class), this.formatSize(this.maxSize)));
            }
            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
