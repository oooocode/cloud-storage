package com.wth.cloudstorage.frame.exception;

import com.wth.cloudstorage.constants.enums.ResponseCodeEnum;
import com.wth.cloudstorage.frame.common.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ApiResult<?> notValidException(MethodArgumentNotValidException e) {
        StringBuilder errorMsg = new StringBuilder();
        e.getBindingResult().getFieldErrors().forEach(item -> errorMsg.append(item.getField())
                .append(item.getDefaultMessage())
                .append(","));
        String resultMsg = errorMsg.toString();
        return ApiResult.fail(ResponseCodeEnum.CODE_600.getCode(), resultMsg.substring(0, resultMsg.length() - 1));
    }

    @ExceptionHandler(value = BusinessException.class)
    public ApiResult<?> businessException(BusinessException e) {
        log.info("business exception, the reason is: {}", e.getMessage());
        return ApiResult.fail(e.getErrorCode(), e.getErrorMsg());
    }


    @ExceptionHandler(value = Throwable.class)
    public ApiResult<?> globalException(Throwable e) {
        log.error("system exception ! the reason is: {}", e.getMessage(), e);
        return ApiResult.fail(ResponseCodeEnum.CODE_500.getCode(), ResponseCodeEnum.CODE_500.getDesc());
    }
}
