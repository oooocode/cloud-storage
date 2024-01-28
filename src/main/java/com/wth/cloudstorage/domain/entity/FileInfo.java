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
 *
 * @author wth
 * @since 2024-01-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("file_info")
public class FileInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 文件名
     */
    @TableField("file_name")
    private String fileName;

    /**
     * 父id
     */
    @TableField("parent_id")
    private Long parentId;

    /**
     * 文件大小
     */
    @TableField("`size`")
    private Long size;

    /**
     * 文件md5值
     */
    @TableField("file_md5")
    private String fileMd5;

    /**
     * 文件封面
     */
    @TableField("cover")
    private String cover;

    /**
     * 文件路径
     */
    @TableField("path")
    private String path;

    /**
     * 目录类型 0-文件 1-目录
     */
    @TableField("folder_type")
    private Integer folderType;

    /**
     * 文件类型 0-视频 1-音频 2-图片 3-pdf 4-doc 5-excel 6-txt 7-code 8-zip 9-其他
     */
    @TableField("file_type")
    private Integer fileType;

    /**
     * 文件分类 0-视频 1-音频 2-图片 3-文档 4-其他
     */
    @TableField("category")
    private Integer category;

    /**
     * 文件状态 0-转码成功 1-转码中 2-转码失败
     */
    @TableField("status")
    private Integer status;

    /**
     * 0-正常 1-回收站
     */
    @TableField("file_location")
    private Integer fileLocation;

    /**
     * 进入回收站时间
     */
    @TableField("recover_time")
    private Date recoverTime;

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
     * 0-正常 1-删除
     */
    @TableField("is_delete")
    private Boolean isDelete;


}
