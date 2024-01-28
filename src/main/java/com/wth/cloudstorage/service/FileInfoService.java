package com.wth.cloudstorage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wth.cloudstorage.domain.entity.FileInfo;
import com.wth.cloudstorage.domain.vo.req.FileInfoReq;
import com.wth.cloudstorage.domain.vo.req.PageBaseReq;
import com.wth.cloudstorage.domain.vo.req.UploadFileChunkReq;
import com.wth.cloudstorage.domain.vo.resp.UploadFileResp;
import com.wth.cloudstorage.domain.vo.resp.UserDto;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wth
 * @since 2024-01-27
 */
public interface FileInfoService {

    IPage<FileInfo> pageFileInfo(PageBaseReq<FileInfoReq> req);

    /**
     * 分片上传
     * @param multipartFile
     * @param fileChunkReq
     */
    UploadFileResp uploadFile(MultipartFile multipartFile, UploadFileChunkReq fileChunkReq, UserDto userDto);

}
