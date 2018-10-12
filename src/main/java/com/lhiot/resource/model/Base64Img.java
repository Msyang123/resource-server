package com.lhiot.resource.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author Leon (234239150@qq.com) created in 12:23 18.10.8
 */
@Data
@ToString
@ApiModel("base64文件信息")
public class Base64Img {

    @ApiModelProperty(notes = "自定义文件名（默认：unknown.jpg，文件将按此处的后缀保存）", dataType = "String")
    private String fileName = "unknown.jpg";

    @ApiModelProperty(notes = "文件base64字符串", dataType = "String", required = true)
    private String content;

    @ApiModelProperty(notes = "附加参数", dataType = "String")
    private String attach;
}
