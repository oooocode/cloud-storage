package com.wth.cloudstorage.service;

/**
 * @Author: wth
 * @Create: 2024/1/17 - 23:41
 */
public interface CodeService {


    void sendEmailCode(String email, Integer type);
}
