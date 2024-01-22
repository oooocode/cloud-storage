package com.wth.cloudstorage.domain.vo.req;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @Author: wth
 * @Create: 2024/1/22 - 15:08
 */
@Data
public class RegisterReq {
    @Email
    @NotBlank
    private String email;
    /**
     * 邮箱验证码
     */
    @NotBlank
    private String emailCode;
    /**
     * 图片验证码
     */
    @NotBlank
    private String checkCode;
    @NotBlank
    @Length(max = 15, min = 5)
    private String nickName;
    @NotBlank
    @Length(max = 20, min = 6)
    private String password;
}
