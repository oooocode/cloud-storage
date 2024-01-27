package com.wth.cloudstorage.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wth.cloudstorage.domain.entity.User;
import com.wth.cloudstorage.constants.enums.UserStatusEnum;
import com.wth.cloudstorage.mapper.UserMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author wth
 * @since 2024-01-17
 */
@Repository
public class UserDao extends ServiceImpl<UserMapper, User> {

    public User getByEmail(String email) {
        return lambdaQuery()
                .eq(User::getEmail, email)
                .one();
    }

    public User getByEmailAndPassword(String email, String dbPassword) {
        return lambdaQuery()
                .eq(User::getEmail, email)
                .eq(User::getPassword, dbPassword)
                .eq(User::getStatus, UserStatusEnum.ENABLE.getStatus())
                .one();
    }
}
