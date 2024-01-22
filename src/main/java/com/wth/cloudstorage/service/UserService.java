package com.wth.cloudstorage.service;

import com.wth.cloudstorage.domain.vo.resp.UserResp;
import com.wth.cloudstorage.domain.vo.req.RegisterReq;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author wth
 * @since 2024-01-17
 */
public interface UserService {

    Long register(RegisterReq registerReq);

    UserResp login(String email, String password);
}
