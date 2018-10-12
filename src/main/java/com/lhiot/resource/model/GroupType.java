package com.lhiot.resource.model;

import com.lhiot.resource.entity.FileGroup;
import io.swagger.annotations.ApiModel;
import lombok.Getter;

@ApiModel
public enum GroupType {
    product_image("商品图片"),
    product_video("商品视频"),
    user_image("用户头像"),
    user_moment("用户动态"),
    knowledge_image("鲜果知识图片"),
    knowledge_video("鲜果知识视频"),
    store_image("门店图片"),
    store_video("门店视频");
    @Getter
    private final String showTag;

    GroupType(String showTag) {
        this.showTag = showTag;
    }

    public FileGroup toFileGroup(){
        return FileGroup.valueOf(this.name().toUpperCase());
    }
}
