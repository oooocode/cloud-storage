package com.wth.cloudstorage.frame.exception;

import com.wth.cloudstorage.constants.enums.ResponseCodeEnum;
import lombok.Getter;

/**
 * @Author: wth
 * @Create: 2023/11/25 - 23:35
 */
@Getter
public class BusinessException extends RuntimeException{

    protected Integer errorCode;
    protected String errorMsg;
    public BusinessException(String errorMsg) {
        super(errorMsg);
        this.errorCode = 500;
        this.errorMsg = errorMsg;
    }

    public BusinessException(Integer errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public BusinessException(ResponseCodeEnum responseCodeEnum, String errorMsg) {
        super(errorMsg);
        this.errorCode = responseCodeEnum.getCode();
        this.errorMsg = errorMsg;
    }

    public BusinessException(ResponseCodeEnum responseCodeEnum) {
        super(responseCodeEnum.getDesc());
        this.errorCode = responseCodeEnum.getCode();
        this.errorMsg = responseCodeEnum.getDesc();
    }
}
