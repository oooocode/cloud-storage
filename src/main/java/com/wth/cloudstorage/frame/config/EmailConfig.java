package com.wth.cloudstorage.frame.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: wth
 * @Create: 2024/1/18 - 22:33
 */
@ConfigurationProperties(prefix = "spring.mail")
@Data
@Component
public class EmailConfig {

    private String port;

    private String host;

    private String username;

    private String password;

}
