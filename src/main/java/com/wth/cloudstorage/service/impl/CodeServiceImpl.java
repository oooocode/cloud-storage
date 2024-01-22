package com.wth.cloudstorage.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.wth.cloudstorage.constants.CommonConstant;
import com.wth.cloudstorage.constants.EmailConstant;
import com.wth.cloudstorage.dao.UserDao;
import com.wth.cloudstorage.constants.RedisKey;
import com.wth.cloudstorage.domain.entity.User;
import com.wth.cloudstorage.frame.config.EmailConfig;
import com.wth.cloudstorage.frame.exception.BusinessException;
import com.wth.cloudstorage.frame.utils.RedisUtils;
import com.wth.cloudstorage.service.CodeService;
import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;
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

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private EmailConfig emailConfig;

    @Override
    public void sendEmailCode(String email, Integer type) {
        if (type == 0) {
            User user = userDao.getByEmail(email);
            if (Objects.nonNull(user)) {
                throw new BusinessException("邮箱已存在");
            }
        }
        String key = RedisKey.getKey(RedisKey.EMAIL_CODE_KEY, email);
        String emailCode = RandomUtil.randomNumbers(CommonConstant.EMAIL_CODE_LENGTH);
        sendEmail(email, emailCode);
        log.info("邮箱验证码为: {}", emailCode);
        // 设置code到Redis
        RedisUtils.set(key, emailCode,  CommonConstant.EMAIL_CODE_EXPIRE, TimeUnit.MINUTES);
    }

    @Override
    public Boolean checkCode(String email, String emailCode) {
        if (StringUtil.isBlank(email)) {
            throw new BusinessException("邮箱为空!");
        }
        String key = RedisKey.getKey(RedisKey.EMAIL_CODE_KEY, email);
        String code = RedisUtils.getStr(key);
        return StringUtils.equals(code, emailCode);
    }

    private void sendEmail(String email, String emailCode) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setSubject(EmailConstant.EMAIL_TITLE);
            helper.setFrom(emailConfig.getUsername());
            helper.setTo(email);
            helper.setText(String.format(EmailConstant.EMAIL_CONTENT, emailCode));
            helper.setSentDate(new Date());
            javaMailSender.send(message);
        } catch (MessagingException e) {
            log.error("发送 {} 邮件错误, {}", email, e);
            throw new BusinessException("邮件发送失败");
        }
    }


}
