package com.wth.cloudstorage.constants.enums;

import lombok.Getter;

@Getter
public enum FileLocationEnum {

    /**
     * 文件位置枚举
     */
    NORMAL(0,"正常"),
    RUBBISH(1,"回收站"),
    ;

    private final Integer code;
    private final String desc;


    FileLocationEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
