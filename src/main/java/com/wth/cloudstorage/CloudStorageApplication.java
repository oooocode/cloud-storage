package com.wth.cloudstorage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: wth
 * @Create: 2024/1/16 - 22:23
 */
@SpringBootApplication(scanBasePackages = {"com.wth.cloudstorage"})
@MapperScan({"com.wth.cloudstorage.mapper"})
public class CloudStorageApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudStorageApplication.class);
    }
}
