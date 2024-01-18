package com.wth.cloudstorage.controller;


import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.wth.cloudstorage.constants.CommonConstant;
import com.wth.cloudstorage.domain.vo.req.SendEmailReq;
import com.wth.cloudstorage.frame.common.ApiResult;
import com.wth.cloudstorage.frame.exception.BusinessException;
import com.wth.cloudstorage.service.CodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;

/**
 * 验证码控制器
 *
 * @author wth
 * @since 2024-01-17
 */
@RestController
@Slf4j
public class CodeController {

    @Autowired
    private CodeService codeService;


    /**
     * 生成验证码
     *
     * @param httpServletResponse
     * @param session
     * @param type                0-登录注册 1-邮箱验证码
     * @throws IOException
     */
    @GetMapping("checkCode")
    public void checkCode(HttpServletResponse httpServletResponse, HttpSession session, Integer type) throws IOException {
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);
        log.info("验证码为: {}", lineCaptcha.getCode());
        if (type == null || type == 0) {
            session.setAttribute(CommonConstant.CHECK_CODE_KEY, lineCaptcha.getCode());
        } else {
            session.setAttribute(CommonConstant.CHECK_CODE_KEY_EMAIL, lineCaptcha.getCode());
        }
        ServletOutputStream outputStream = httpServletResponse.getOutputStream();
        lineCaptcha.write(outputStream);
        outputStream.close();
    }

    /**
     * 发送邮箱验证码
     *
     * @throws IOException
     */
    @PostMapping("sendEmailCode")
    public ApiResult<Void> sendEmailCode(@RequestBody @Valid SendEmailReq sendEmailReq,
                                         HttpSession session) throws IOException {
        String checkCode = sendEmailReq.getCheckCode();
        try {
            if (!checkCode.equalsIgnoreCase((String) session.getAttribute(CommonConstant.CHECK_CODE_KEY_EMAIL))) {
                throw new BusinessException("邮箱验证码错误");
            }
        } finally {
            session.removeAttribute(CommonConstant.CHECK_CODE_KEY_EMAIL);
        }
        codeService.sendEmailCode(sendEmailReq.getEmail(), sendEmailReq.getType());
        return ApiResult.success();
    }
}

