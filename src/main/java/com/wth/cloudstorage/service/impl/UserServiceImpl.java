package com.wth.cloudstorage.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.wth.cloudstorage.constants.CommonConstant;
import com.wth.cloudstorage.constants.RedisKey;
import com.wth.cloudstorage.domain.vo.resp.UserResp;
import com.wth.cloudstorage.dao.UserDao;
import com.wth.cloudstorage.domain.dto.UserSpaceDto;
import com.wth.cloudstorage.domain.entity.User;
import com.wth.cloudstorage.domain.vo.req.RegisterReq;
import com.wth.cloudstorage.frame.config.AppConfig;
import com.wth.cloudstorage.frame.exception.BusinessException;
import com.wth.cloudstorage.frame.utils.RedisUtils;
import com.wth.cloudstorage.service.CodeService;
import com.wth.cloudstorage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.wth.cloudstorage.constants.CommonConstant.USER_SPACE_EXPIRE;

/**
 * @Author: wth
 * @Create: 2024/1/17 - 22:32
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private CodeService codeService;

    @Autowired
    private AppConfig appConfig;
    @Autowired
    private UserDao userDao;
    @Override
    public Long register(RegisterReq registerReq) {
        String emailCode = registerReq.getEmailCode();
        Boolean isCodeEqual = codeService.checkCode(registerReq.getEmail(), emailCode);
        if (!isCodeEqual) {
            throw new BusinessException("验证码错误!");
        }
        User user = new User();
        user.setNickName(registerReq.getNickName());
        user.setEmail(registerReq.getEmail());
        user.setPassword(DigestUtil.md5Hex(registerReq.getPassword()));
        user.setTotalSpace(CommonConstant.COMMON_TOTAL_SPACE);
        userDao.save(user);
        return user.getId();
    }

    @Override
    public UserResp login(String email, String password) {
        String dbPassword = DigestUtil.md5Hex(password);
        User user = userDao.getByEmailAndPassword(email, dbPassword);
        if (Objects.isNull(user)) {
            throw new BusinessException("用户不存在!");
        }
        UserResp userResp = new UserResp();
        userResp.setUserId(user.getId());
        userResp.setNickName(user.getNickName());
        boolean isAdmin = Arrays.asList(appConfig.getAdminEmail().split(",")).contains(email);
        userResp.setAdmin(isAdmin);
        UserSpaceDto userSpaceDto = new UserSpaceDto();
        // todo 文件表查询使用容量
        userSpaceDto.setUseSpace(0L);
        userSpaceDto.setTotalSpace(user.getTotalSpace());
        RedisUtils.set(RedisKey.getKey(RedisKey.USER_SPACE,
                user.getId()), userSpaceDto,
                USER_SPACE_EXPIRE,
                TimeUnit.MINUTES);
        userResp.setUserSpaceDto(userSpaceDto);
        return userResp;
    }
}
