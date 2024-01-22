package com.wth.cloudstorage.domain.enums;

import lombok.Getter;

/**
 * @Author: wth
 * @Create: 2024/1/22 - 15:58
 */
@Getter
public enum UserStatusEnum {

    /**
     * 用户状态
     */
    ENABLE(0, "启用"),
    DISABLE(1, "禁用"),
    ;

    private final int status;
    private final String desc;

    UserStatusEnum(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

}
