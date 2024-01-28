package com.wth.cloudstorage.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wth.cloudstorage.constants.RedisKey;
import com.wth.cloudstorage.constants.enums.FileStatusEnum;
import com.wth.cloudstorage.constants.enums.ResponseCodeEnum;
import com.wth.cloudstorage.dao.FileInfoDao;
import com.wth.cloudstorage.dao.UserDao;
import com.wth.cloudstorage.domain.dto.UserSpaceDto;
import com.wth.cloudstorage.domain.entity.FileInfo;
import com.wth.cloudstorage.domain.vo.req.FileInfoReq;
import com.wth.cloudstorage.domain.vo.req.PageBaseReq;
import com.wth.cloudstorage.domain.vo.req.UploadFileChunkReq;
import com.wth.cloudstorage.domain.vo.resp.UploadFileResp;
import com.wth.cloudstorage.domain.vo.resp.UserDto;
import com.wth.cloudstorage.frame.exception.BusinessException;
import com.wth.cloudstorage.service.FileInfoService;
import com.wth.cloudstorage.service.UserService;
import com.wth.cloudstorage.utils.RedisUtils;
import com.wth.cloudstorage.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.wth.cloudstorage.constants.CommonConstant.USER_SPACE_EXPIRE;
import static com.wth.cloudstorage.constants.RedisKey.USER_SPACE;

/**
 * @Author: wth
 * @Create: 2024/1/27 - 20:15
 */
@Service
public class FileInfoServiceImpl implements FileInfoService {


    @Autowired
    private FileInfoDao fileInfoDao;

    @Autowired
    private UserService userService;
    @Autowired
    private UserDao userDao;

    @Override
    public IPage<FileInfo> pageFileInfo(PageBaseReq<FileInfoReq> req) {
        Page pageParam = req.plusPage();
        FileInfoReq filter = req.getFilter();
        Page page = fileInfoDao.pageFile(pageParam, filter);
        return page;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UploadFileResp uploadFile(MultipartFile multipartFile,
                                     UploadFileChunkReq fileChunkReq,
                                     UserDto loginUser) {
        UploadFileResp uploadFileResp = new UploadFileResp();
        Long fileId = fileChunkReq.getFileId();
        if (Objects.isNull(fileId)) {
            fileId = 1L;
        }
        uploadFileResp.setFileId(fileId);
        Long userId = loginUser.getUserId();
        UserSpaceDto useSpace = userService.getUseSpace(userId);
        Integer chunkIndex = fileChunkReq.getChunkIndex();
        // 初始化分片
        if (chunkIndex == 0) {
            // 查询是否有存在的文件
            FileInfo dbFile = fileInfoDao.selectByMd5(fileChunkReq.getFileMd5());
            // 存在就走秒传
            if (Objects.nonNull(dbFile)) {
                if (dbFile.getSize() + useSpace.getUseSpace() > useSpace.getTotalSpace()) {
                    throw new BusinessException(ResponseCodeEnum.CODE_904);
                }
                dbFile.setId(fileId);
                dbFile.setParentId(fileChunkReq.getFilePid());
                dbFile.setUserId(userId);
                dbFile.setStatus(FileStatusEnum.SUCCESS.getCode());
                // 文件重命名
                dbFile.setFileName(autoRename(fileChunkReq.getFilePid(), userId, fileChunkReq.getFileName()));
                fileInfoDao.save(dbFile);
                // 更新用户使用空间
                updateUserSpace(userId, dbFile.getSize());
            }
        }
        return uploadFileResp;
    }

    private void updateUserSpace(Long userId, Long size) {
        Integer res = userDao.updateUserSpace(userId, size);
        if (res <= 0) {
            throw new BusinessException(ResponseCodeEnum.CODE_904);
        }
        UserSpaceDto useSpace = userService.getUseSpace(userId);
        useSpace.setUseSpace(useSpace.getUseSpace() + size);
        // 同步至Redis
        String key = RedisKey.getKey(USER_SPACE, userId);
        RedisUtils.set(key, useSpace, USER_SPACE_EXPIRE, TimeUnit.MINUTES);
    }

    private String autoRename(Long filePid, Long userId, String fileName) {
        Integer userFileNameNum = fileInfoDao.countByName(filePid, userId, fileName);
        if (userFileNameNum > 0) {
            fileName = StringUtil.rename(fileName);
        }
        return fileName;
    }


}
