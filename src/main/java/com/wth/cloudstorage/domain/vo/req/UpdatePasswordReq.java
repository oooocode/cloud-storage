package com.wth.cloudstorage.domain.vo.req;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @Author: wth
 * @Create: 2024/1/22 - 18:24
 */
@Data
public class UpdatePasswordReq {

    private Long userId;

    @Length(max = 15, min = 6)
    @NotBlank
    private String password;
}
