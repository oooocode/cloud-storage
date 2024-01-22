package com.wth.cloudstorage.service.impl;

import cn.hutool.core.bean.copier.BeanCopier;
import cn.hutool.crypto.digest.DigestUtil;
import com.wth.cloudstorage.constants.CommonConstant;
import com.wth.cloudstorage.constants.RedisKey;
import com.wth.cloudstorage.domain.enums.ResponseCodeEnum;
import com.wth.cloudstorage.domain.vo.req.UpdatePasswordReq;
import com.wth.cloudstorage.domain.vo.req.UpdateUserReq;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.wth.cloudstorage.constants.CommonConstant.SESSION_KEY;
import static com.wth.cloudstorage.constants.CommonConstant.USER_SPACE_EXPIRE;
import static com.wth.cloudstorage.constants.RedisKey.USER_SPACE;

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
        RedisUtils.set(RedisKey.getKey(USER_SPACE,
                user.getId()), userSpaceDto,
                USER_SPACE_EXPIRE,
                TimeUnit.MINUTES);
        userResp.setUserSpaceDto(userSpaceDto);
        return userResp;
    }

    @Override
    public void resetPwd(String email, String emailCode, String password) {
        User user = userDao.getByEmail(email);
        if (Objects.isNull(user)) {
            throw new BusinessException("用户不存在");
        }
        codeService.checkCode(email, emailCode);
        user.setPassword(DigestUtil.md5Hex(password));
        userDao.updateById(user);
    }

    @Override
    public UserSpaceDto getUseSpace(Long userId) {
        return Optional.ofNullable(
                    RedisUtils.get(RedisKey.getKey(USER_SPACE, userId), UserSpaceDto.class)
                ).orElse(new UserSpaceDto());
    }

    @Override
    public Boolean updateUserInfo(UpdateUserReq updateUserReq) {
        if (updateUserReq == null) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        User user = new User();
        BeanUtils.copyProperties(updateUserReq, user);
        return userDao.updateById(user);
    }

    @Override
    public Boolean updatePassword(UpdatePasswordReq updatePasswordReq) {
        User user = new User();
        user.setId(updatePasswordReq.getUserId());
        user.setPassword(DigestUtil.md5Hex(updatePasswordReq.getPassword()));
        return userDao.updateById(user);
    }

    @Override
    public UserResp getLoginUser(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        Object userObj = request.getSession().getAttribute(SESSION_KEY);
        if (userObj == null) {
            throw new BusinessException(ResponseCodeEnum.CODE_901);
        }
        return (UserResp) userObj;
    }
}
