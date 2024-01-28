package com.wth.cloudstorage.mapper;

import com.wth.cloudstorage.domain.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author wth
 * @since 2024-01-17
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    Integer updateUserSpace(Long userId, Long fileSize);
}
