package com.wth.cloudstorage.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wth.cloudstorage.domain.entity.FileInfo;
import com.wth.cloudstorage.domain.vo.req.FileInfoReq;
import com.wth.cloudstorage.domain.vo.req.PageBaseReq;
import com.wth.cloudstorage.domain.vo.req.UploadFileChunkReq;
import com.wth.cloudstorage.domain.vo.resp.PageBaseResp;
import com.wth.cloudstorage.domain.vo.resp.UploadFileResp;
import com.wth.cloudstorage.domain.vo.resp.UserDto;
import com.wth.cloudstorage.frame.annotation.CheckLogin;
import com.wth.cloudstorage.frame.common.ApiResult;
import com.wth.cloudstorage.service.FileInfoService;
import com.wth.cloudstorage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static com.wth.cloudstorage.constants.CommonConstant.SESSION_KEY;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wth
 * @since 2024-01-27
 */
@RestController
@RequestMapping("/fileInfo")
public class FileInfoController {

    @Autowired
    private FileInfoService fileInfoService;

    @Autowired
    private UserService userService;

    /**
     * 分页查询文件
     */
    @PostMapping("loadDataList")
    @CheckLogin
    public PageBaseResp<FileInfo> loadDataList(@RequestBody PageBaseReq<FileInfoReq> req, HttpSession httpSession) {
        UserDto loginUser = userService.getLoginUser(httpSession);
        req.getFilter().setUserId(loginUser.getUserId());
        IPage<FileInfo> fileInfoPage = fileInfoService.pageFileInfo(req);
        return PageBaseResp.init(fileInfoPage);
    }

    /**
     * 分片上传
     */
    @PostMapping("uploadFile")
    @CheckLogin
    public ApiResult<UploadFileResp> uploadFile(@Valid @RequestPart("file") MultipartFile multipartFile,
                                          UploadFileChunkReq fileChunkReq,
                                          HttpSession httpSession) {
        UserDto loginUser = userService.getLoginUser(httpSession);
        UploadFileResp uploadFileResp = fileInfoService.uploadFile(multipartFile, fileChunkReq, loginUser);
        return ApiResult.success(uploadFileResp);
    }


}

