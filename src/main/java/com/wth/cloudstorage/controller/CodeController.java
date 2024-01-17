package com.wth.cloudstorage.controller;


import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.wth.cloudstorage.domain.constants.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 验证码控制器
 * @author wth
 * @since 2024-01-17
 */
@RestController
@Slf4j
public class CodeController {


    /**
     * 验证码
     * @param httpServletResponse
     * @param session
     * @param type 0-登录注册 1-邮箱验证码
     * @throws IOException
     */
    @GetMapping("checkCode")
    public void checkCode(HttpServletResponse httpServletResponse, HttpSession session, Integer type) throws IOException {
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);
        log.info("验证码为: {}", lineCaptcha.getCode());
        if (type == null || type == 0) {
            session.setAttribute(Constant.CHECK_CODE_KEY, lineCaptcha.getCode());
        } else {
            session.setAttribute(Constant.CHECK_CODE_KEY_EMAIL, lineCaptcha.getCode());
        }
        ServletOutputStream outputStream = httpServletResponse.getOutputStream();
        lineCaptcha.write(outputStream);
        outputStream.close();
    }
}

