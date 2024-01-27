package com.wth.cloudstorage.constants.enums;

import lombok.Getter;

@Getter
public enum FileStatusEnum {

    /**
     * 文件状态枚举
     */
    SUCCESS(0,"转码成功"),
    PROCESS(1,"转码失败"),
    FAIL(2,"转码失败"),
    ;

    private final Integer code;
    private final String desc;


    FileStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
