package com.wth.cloudstorage.constants.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public enum FileCategoryEnum {

    /**
     * 文件分类枚举
     */
    VIDEO(0,"video", "视频"),
    MUSIC(1,"music", "音乐"),
    IMAGE(2,"image", "图片"),
    DOC(3,"doc", "文档"),
    OTHERS(4,"others", "其他"),
    ;


    private final Integer category;
    private final String code;
    private final String desc;

    private final static Map<String, FileCategoryEnum> CACHE = Arrays.stream(values()).collect(Collectors.toMap(item->item.getCode(), Function.identity()));

    FileCategoryEnum(Integer category, String code, String desc) {
        this.category = category;
        this.code = code;
        this.desc = desc;
    }

    public static FileCategoryEnum of(String code) {
        return CACHE.get(code);
    }
}
