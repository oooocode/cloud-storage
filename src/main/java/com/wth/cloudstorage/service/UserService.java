package com.wth.cloudstorage.service;

import com.wth.cloudstorage.domain.vo.req.UpdatePasswordReq;
import com.wth.cloudstorage.domain.dto.UserSpaceDto;
import com.wth.cloudstorage.domain.entity.User;
import com.wth.cloudstorage.domain.vo.req.UpdateUserReq;
import com.wth.cloudstorage.domain.vo.resp.UserDto;
import com.wth.cloudstorage.domain.vo.req.RegisterReq;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

    UserDto login(String email, String password);

    void resetPwd(String email, String emailCode, String password);

    UserSpaceDto getUseSpace(Long userId);

    Boolean updateUserInfo(UpdateUserReq updateUserReq);

    Boolean updatePassword(UpdatePasswordReq updatePasswordReq);

    UserDto getLoginUser(HttpSession httpSession);

}
