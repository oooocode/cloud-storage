package com.wth.cloudstorage.domain.vo.resp;

import com.wth.cloudstorage.domain.dto.UserSpaceDto;
import lombok.Data;

/**
 * @Author: wth
 * @Create: 2024/1/22 - 15:52
 */
@Data
public class UserResp {

    private Long userId;
    private String nickName;
    private Boolean admin;
    private UserSpaceDto userSpaceDto;
}
