package com.wth.cloudstorage.frame.aspect;

import com.wth.cloudstorage.constants.CommonConstant;
import com.wth.cloudstorage.constants.enums.ResponseCodeEnum;
import com.wth.cloudstorage.domain.vo.resp.UserDto;
import com.wth.cloudstorage.frame.exception.BusinessException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Author: wth
 * @Create: 2024/1/22 - 19:13
 */
@Aspect
@Order(0)
@Component
public class CheckLoginAspect {

    @Around("@annotation(com.wth.cloudstorage.frame.annotation.CheckLogin)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        UserDto UserDto = (UserDto) session.getAttribute(CommonConstant.SESSION_KEY);
        if (UserDto == null) {
            throw new BusinessException(ResponseCodeEnum.CODE_901);
        }
        return joinPoint.proceed();
    }
}
