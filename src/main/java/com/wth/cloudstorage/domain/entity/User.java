package com.wth.cloudstorage.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author wth
 * @since 2024-01-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 昵称
     */
    @TableField("nick_name")
    private String nickName;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * qqOpenId
     */
    @TableField("qq_open_id")
    private String qqOpenId;

    /**
     * qq头像
     */
    @TableField("avatar")
    private String avatar;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 0-禁用 1-启用
     */
    @TableField("status")
    private Boolean status;

    /**
     * 最近一次登录时间
     */
    @TableField("last_login_time")
    private Date lastLoginTime;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 使用空间 单位byte
     */
    @TableField(value = "use_space")
    private Long useSpace;

    /**
     * 总空间 单位byte
     */
    @TableField(value = "total_space")
    private Long totalSpace;

    /**
     * 逻辑删除 0-存在 1-删除
     */
    @TableField("is_delete")
    private Boolean isDelete;


}
