package com.wth.cloudstorage.domain.enums;

import lombok.Getter;

/**
 * @Author: wth
 * @Create: 2024/1/17 - 22:16
 */
@Getter
public enum ResponseCodeEnum {

    /**
     * 响应异常
     */
    CODE_200(200, "请求成功"),
    CODE_404(404, "接口不存在"),
    CODE_600(600, "请求参数错误"),
    CODE_500(500, "服务器出小差了"),
    CODE_601(601, "信息已经存在"),
    CODE_901(901, "登录超时，请稍后重试"),
    ;

    private final int code;
    private final String desc;

    ResponseCodeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }


}
