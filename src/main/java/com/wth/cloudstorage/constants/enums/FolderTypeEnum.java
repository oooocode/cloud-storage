package com.wth.cloudstorage.constants.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public enum FolderTypeEnum {

    /**
     * 文件类型枚举
     */
    FILE(0,"文件"),
    FOLDER(1,"目录"),
    ;


    private final Integer code;
    private final String desc;


    FolderTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
