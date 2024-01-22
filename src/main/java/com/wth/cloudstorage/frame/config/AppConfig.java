package com.wth.cloudstorage.frame.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author: wth
 * @Create: 2024/1/22 - 16:04
 */
@Component
@Data
public class AppConfig {

    @Value("${admin:emails}")
    private String adminEmail;


}
