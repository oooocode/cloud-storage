package com.wth.cloudstorage.domain.vo.req;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @Author: wth
 * @Create: 2024/1/22 - 17:14
 */
@Data
public class ResetPwdReq {

    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String checkCode;
    @NotBlank
    private String emailCode;
}
