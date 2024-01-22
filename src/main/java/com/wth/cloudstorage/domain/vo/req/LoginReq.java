package com.wth.cloudstorage.domain.vo.req;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * @Author: wth
 * @Create: 2024/1/22 - 15:08
 */
@Data
public class LoginReq {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String emailCode;
}
