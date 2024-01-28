package com.wth.cloudstorage.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wth.cloudstorage.constants.enums.FileLocationEnum;
import com.wth.cloudstorage.constants.enums.FileStatusEnum;
import com.wth.cloudstorage.domain.entity.FileInfo;
import com.wth.cloudstorage.domain.vo.req.FileInfoReq;
import com.wth.cloudstorage.mapper.FileInfoMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 文件DAO
 * @author wth
 * @since 2024-01-27
 */
@Repository
public class FileInfoDao extends ServiceImpl<FileInfoMapper, FileInfo> {

    @Autowired
    private FileInfoMapper fileInfoMapper;

    public Page pageFile(Page page, FileInfoReq filter) {
        return lambdaQuery()
                .eq(FileInfo::getUserId, filter.getUserId())
                .eq(FileInfo::getFileLocation, FileLocationEnum.NORMAL.getCode())
                .orderByDesc(FileInfo::getUpdateTime)
                .page(page);
    }

    public Long selectUseSpace(Long userId) {
        return fileInfoMapper.selectUseSpace(userId);
    }

    public FileInfo selectByMd5(String fileMd5) {
        return lambdaQuery()
                .eq(FileInfo::getFileMd5, fileMd5)
                .eq(FileInfo::getStatus, FileStatusEnum.SUCCESS.getCode())
                .one();
    }

    public Integer countByName(Long filePid, Long userId, String fileName) {
        return lambdaQuery()
                .eq(FileInfo::getParentId, filePid)
                .eq(FileInfo::getUserId, userId)
                .eq(FileInfo::getFileName, fileName)
                .eq(FileInfo::getStatus, FileStatusEnum.SUCCESS.getCode())
                .eq(FileInfo::getFileLocation, FileLocationEnum.NORMAL.getCode())
                .count();
    }
}
