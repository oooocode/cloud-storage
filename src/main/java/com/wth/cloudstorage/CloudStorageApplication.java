package com.wth.cloudstorage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Author: wth
 * @Create: 2024/1/16 - 22:23
 */
@EnableAsync
@EnableScheduling
@SpringBootApplication(scanBasePackages = {"com.wth.cloudstorage"})
@MapperScan({"com.wth.cloudstorage.mapper"})
public class CloudStorageApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudStorageApplication.class);
    }
}
