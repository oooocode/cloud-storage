package com.wth.cloudstorage.domain.vo.req;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


/**
 * @Author: wth
 * @Create: 2024/1/18 - 23:24
 */
@Data
public class SendEmailReq {

    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String checkCode;
    /**
     * 0-登录注册 1-找回密码
     */
    @NotNull
    private Integer type;
}
