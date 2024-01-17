package com.wth.cloudstorage.frame.common;

import com.wth.cloudstorage.domain.enums.ResponseCodeEnum;
import lombok.Data;

@Data
public class ApiResult<T> {
    private Boolean success;
    private Integer code;
    private String info;
    private T data;

    public static <T> ApiResult<T> success() {
        ApiResult<T> result = new ApiResult<T>();
        result.setData(null);
        result.setSuccess(Boolean.TRUE);
        result.setCode(ResponseCodeEnum.CODE_200.getCode());
        return result;
    }

    public static <T> ApiResult<T> success(T data) {
        ApiResult<T> result = new ApiResult<T>();
        result.setData(data);
        result.setSuccess(Boolean.TRUE);
        return result;
    }

    public static <T> ApiResult<T> fail(Integer code, String msg) {
        ApiResult<T> result = new ApiResult<T>();
        result.setSuccess(Boolean.FALSE);
        result.setCode(code);
        result.setInfo(msg);
        return result;
    }


}
