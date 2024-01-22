package com.wth.cloudstorage.controller;


import com.wth.cloudstorage.domain.dto.UserSpaceDto;
import com.wth.cloudstorage.domain.entity.User;
import com.wth.cloudstorage.domain.vo.req.LoginReq;
import com.wth.cloudstorage.domain.vo.req.RegisterReq;
import com.wth.cloudstorage.domain.vo.req.ResetPwdReq;
import com.wth.cloudstorage.domain.vo.req.UpdatePasswordReq;
import com.wth.cloudstorage.domain.vo.resp.UserResp;
import com.wth.cloudstorage.frame.annotation.CheckLogin;
import com.wth.cloudstorage.frame.common.ApiResult;
import com.wth.cloudstorage.frame.exception.BusinessException;
import com.wth.cloudstorage.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;

import static com.wth.cloudstorage.constants.CommonConstant.CHECK_CODE_KEY;
import static com.wth.cloudstorage.constants.CommonConstant.SESSION_KEY;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author wth
 * @since 2024-01-17
 */
@RestController
@Slf4j
@RequestMapping("/userInfo")
public class UserController {

    @Autowired
    private UserService userService;


    /**
     * 注册
     */
    @PostMapping("register")
    public ApiResult<Long> register(@RequestBody @Valid RegisterReq registerReq,
                                    HttpSession session) throws IOException {
        String checkCode = registerReq.getCheckCode();
        try {
            if (!checkCode.equalsIgnoreCase((String) session.getAttribute(CHECK_CODE_KEY))) {
                throw new BusinessException("验证码错误");
            }
            return ApiResult.success(userService.register(registerReq));
        } finally {
            session.removeAttribute(CHECK_CODE_KEY);
        }
    }

    /**
     * 登录
     */
    @PostMapping("login")
    public ApiResult<UserResp> login(@RequestBody @Valid LoginReq loginReq,
                                     HttpSession session) throws IOException {
        try {
            if (!loginReq.getCheckCode()
                    .equalsIgnoreCase((String) session.getAttribute(CHECK_CODE_KEY))) {
                throw new BusinessException("图片验证码错误");
            }
            UserResp userResp = userService.login(loginReq.getEmail(), loginReq.getPassword());
            session.setAttribute(SESSION_KEY, userResp);
            return ApiResult.success(userResp);
        } finally {
            session.removeAttribute(CHECK_CODE_KEY);
        }
    }

    /**
     * 忘记密码
     */
    @PostMapping("resetPwd")
    public ApiResult<Void> resetPwd(@RequestBody @Valid ResetPwdReq resetPwdReq,
                                    HttpSession session) throws IOException {
        try {
            if (!resetPwdReq.getCheckCode()
                    .equalsIgnoreCase((String) session.getAttribute(CHECK_CODE_KEY))) {
                throw new BusinessException("图片验证码错误");
            }
            userService.resetPwd(resetPwdReq.getEmail(), resetPwdReq.getEmailCode(), resetPwdReq.getPassword());
            return ApiResult.success();
        } finally {
            session.removeAttribute(CHECK_CODE_KEY);
        }
    }

    /**
     * 获取头像 todo 数据库里获取
     */
    @GetMapping("/getAvatar/{userId}")
    @CheckLogin
    public ApiResult<String> getAvatar(@PathVariable("userId") Long userId) {
        return ApiResult.success("https://user-vatar-1315796988.cos.ap-beijing.myqcloud.com/user_avatar/4/4H4H3tF0-dog.jpg");
    }

    /**
     * 获取用户信息
     */
    @GetMapping("/getUserInfo")
    @CheckLogin
    public ApiResult<UserResp> getUserInfo(HttpSession httpSession) {
        return ApiResult.success((UserResp) httpSession.getAttribute(SESSION_KEY));
    }

    /**
     * 获取使用空间
     */
    @GetMapping("/getUseSpace")
    @CheckLogin
    public ApiResult<UserSpaceDto> getUseSpace(HttpSession httpSession) {
        UserResp userResp = (UserResp) httpSession.getAttribute(SESSION_KEY);
        return ApiResult.success(userService.getUseSpace(userResp.getUserId()));
    }

    /**
     * 修改用户信息
     */
    @PostMapping("/updateUserInfo")
    @CheckLogin
    public ApiResult<Boolean> updateUserInfo(@RequestBody User user) {
        return ApiResult.success(userService.updateUserInfo(user));
    }

    /**
     * 修改用户信息
     */
    @PostMapping("/updatePassword")
    @CheckLogin
    public ApiResult<Boolean> updatePassword(@RequestBody UpdatePasswordReq updatePasswordReq,
                                             HttpSession httpSession) {
        UserResp userResp = (UserResp) httpSession.getAttribute(SESSION_KEY);
        updatePasswordReq.setUserId(userResp.getUserId());
        return ApiResult.success(userService.updatePassword(updatePasswordReq));
    }


}

