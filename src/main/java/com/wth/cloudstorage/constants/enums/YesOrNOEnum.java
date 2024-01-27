package com.wth.cloudstorage.constants.enums;

import lombok.Getter;

@Getter
public enum YesOrNOEnum {

    /**
     * 通用枚举
     */
    YES(0,"是"),
    NO(1,"否"),
    ;

    private final Integer code;
    private final String desc;


    YesOrNOEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
