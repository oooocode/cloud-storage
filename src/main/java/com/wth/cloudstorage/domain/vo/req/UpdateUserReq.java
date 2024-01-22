package com.wth.cloudstorage.domain.vo.req;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: wth
 * @Create: 2024/1/22 - 19:26
 */
@Data
public class UpdateUserReq implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 0-禁用 1-启用
     */
    private Boolean status;

    /**
     * 使用空间 单位byte
     */
    private Long useSpace;

    /**
     * 总空间 单位byte
     */
    private Long totalSpace;
}
