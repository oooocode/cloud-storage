package com.wth.cloudstorage.domain.vo.req;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author wth
 * @since 2024-01-27
 */
@Data
public class FileInfoReq extends PageBaseReq implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 父id
     */
    private Long parentId;

    /**
     * 文件大小
     */
    private Long size;

    /**
     * 文件md5值
     */
    private String fileMd5;

    /**
     * 文件封面
     */
    private String cover;

    /**
     * 文件路径
     */
    private String path;

    /**
     * 目录类型 0-文件 1-目录
     */
    private Boolean folderType;

    /**
     * 文件类型 0-视频 1-音频 2-图片 3-pdf 4-doc 5-excel 6-txt 7-code 8-zip 9-其他
     */
    private String fileType;

    /**
     * 文件分类 0-视频 1-音频 2-图片 3-文档 4-其他
     */
    private Boolean category;

    /**
     * 文件状态 0-转码成功 1-转码中 2-转码失败
     */
    private Boolean status;

    /**
     * 0-正常 1-回收站
     */
    private Boolean fileLocation;

    /**
     * 进入回收站时间
     */
    private Date recoverTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

}
