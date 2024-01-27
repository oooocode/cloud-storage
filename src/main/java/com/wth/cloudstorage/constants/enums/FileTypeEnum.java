package com.wth.cloudstorage.constants.enums;

import lombok.Getter;

@Getter
public enum FileTypeEnum {

    /**
     * 文件类型枚举
     */
    VIDEO(0,"视频"),
    AUDIO(1,"音频"),
    IMAGE(2,"图片"),
    PDF(3,"pdf"),
    DOC(4,"DOC"),
    EXCEL(5,"excel"),
    TXT(6,"txt"),
    CODE(7,"code"),
    ZIP(8,"zip"),
    OTHERS(9,"其他"),
    ;


    private final Integer code;
    private final String desc;


    FileTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
