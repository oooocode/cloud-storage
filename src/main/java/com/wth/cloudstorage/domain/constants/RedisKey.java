package com.wth.cloudstorage.domain.constants;

/**
 * @Author: wth
 * @Create: 2024/1/18 - 0:19
 */
public class RedisKey {

    private static final String BASE_KEY = "cloudStorage:";

    public static final String EMAIL_CODE_KEY = "code:email:%s";

    public static String getKey(String key, Object... o) {
        return BASE_KEY + String.format(key, o);
    }
}
