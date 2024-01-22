package com.wth.cloudstorage.constants;

/**
 * @Author: wth
 * @Create: 2024/1/17 - 23:12
 */
public class CommonConstant {

    public static final String CHECK_CODE_KEY = "check_code_key";
    public static final String CHECK_CODE_KEY_EMAIL = "check_code_key_email";
    public static final int EMAIL_CODE_LENGTH = 5;
    /**
     * 单位分钟
     */
    public static final int EMAIL_CODE_EXPIRE = 5;

    /**
     * 普通用户使用空间
     */
    public static final Long COMMON_TOTAL_SPACE = 1024 * 1024 * 1024 * 5L;

    /**
     * 高级用户使用空间
     */
    public static final Long SENIOR_TOTAL_SPACE = 1024 * 1024 * 1024 * 10L;

    /**
     * 单位分钟
     */
    public static final int USER_SPACE_EXPIRE = 5;
    public static final String SESSION_KEY = "session_key";
}
