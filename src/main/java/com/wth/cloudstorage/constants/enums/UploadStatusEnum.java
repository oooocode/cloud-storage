package com.wth.cloudstorage.constants.enums;

import lombok.Getter;

@Getter
public enum UploadStatusEnum {

    /**
     * 上传文件状态枚举
     */
    UPLOAD_SUCCESS("upload_success","上传成功"),
    UPLOADING("uploading","上传失败"),
    UPLOAD_FINISH("upload_finish","上传完成"),
    ;

    private final String code;
    private final String desc;


    UploadStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
