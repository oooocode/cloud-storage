package com.wth.cloudstorage.controller;


import com.wth.cloudstorage.domain.vo.req.LoginReq;
import com.wth.cloudstorage.domain.vo.req.RegisterReq;
import com.wth.cloudstorage.domain.vo.resp.UserResp;
import com.wth.cloudstorage.frame.common.ApiResult;
import com.wth.cloudstorage.frame.exception.BusinessException;
import com.wth.cloudstorage.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;

import static com.wth.cloudstorage.constants.CommonConstant.*;

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
            session.removeAttribute(CHECK_CODE_KEY_EMAIL);
        }
    }

    /**
     * 登录
     */
    @PostMapping("login")
    public ApiResult<UserResp> login(@RequestBody @Valid LoginReq loginReq,
                                     HttpSession session) throws IOException {
        try {
            if (!loginReq.getEmailCode()
                    .equalsIgnoreCase((String) session.getAttribute(CHECK_CODE_KEY))) {
                throw new BusinessException("邮箱验证码错误");
            }
            UserResp userResp = userService.login(loginReq.getEmail(), loginReq.getPassword());
            session.setAttribute(SESSION_KEY, userResp);
            return ApiResult.success(userResp);
        } finally {
            session.removeAttribute(CHECK_CODE_KEY_EMAIL);
        }
    }


}

