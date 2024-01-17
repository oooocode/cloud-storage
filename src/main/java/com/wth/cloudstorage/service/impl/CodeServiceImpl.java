package com.wth.cloudstorage.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.wth.cloudstorage.dao.UserDao;
import com.wth.cloudstorage.domain.constants.Constant;
import com.wth.cloudstorage.domain.constants.RedisKey;
import com.wth.cloudstorage.domain.entity.User;
import com.wth.cloudstorage.frame.exception.BusinessException;
import com.wth.cloudstorage.service.CodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @Author: wth
 * @Create: 2024/1/17 - 23:41
 */
@Service
@Slf4j
public class CodeServiceImpl implements CodeService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public void sendEmailCode(String email, Integer type) {
        if (type == 0) {
            User user = userDao.getByEmail(email);
            if (Objects.nonNull(user)) {
                throw new BusinessException("邮箱已存在");
            }
        }
        // todo 发送邮箱
        String key = RedisKey.getKey(RedisKey.EMAIL_CODE_KEY, email);
        String emailCode = RandomUtil.randomNumbers(Constant.EMAIL_CODE_LENGTH);
        log.info("邮箱验证码为: {}", emailCode);
        stringRedisTemplate.opsForValue().set(key, emailCode, Constant.EMAIL_CODE_EXPIRE, TimeUnit.MINUTES);
    }


}
