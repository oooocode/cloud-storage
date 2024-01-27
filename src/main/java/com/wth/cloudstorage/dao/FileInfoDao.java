package com.wth.cloudstorage.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wth.cloudstorage.constants.enums.FileLocationEnum;
import com.wth.cloudstorage.domain.entity.FileInfo;
import com.wth.cloudstorage.domain.vo.req.FileInfoReq;
import com.wth.cloudstorage.mapper.FileInfoMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 文件DAO
 * @author wth
 * @since 2024-01-27
 */
@Repository
public class FileInfoDao extends ServiceImpl<FileInfoMapper, FileInfo> {

    public Page pageFile(Page page, FileInfoReq filter) {
        return lambdaQuery()
                .eq(FileInfo::getUserId, filter.getUserId())
                .eq(FileInfo::getFileLocation, FileLocationEnum.NORMAL.getCode())
                .orderByDesc(FileInfo::getUpdateTime)
                .page(page);
    }
}
