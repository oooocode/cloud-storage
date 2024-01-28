package com.wth.cloudstorage.controller;

import cn.hutool.core.io.FileUtil;
import com.wth.cloudstorage.constants.enums.FileUploadBizEnum;
import com.wth.cloudstorage.constants.enums.ResponseCodeEnum;
import com.wth.cloudstorage.domain.vo.req.UploadFileReq;
import com.wth.cloudstorage.domain.vo.resp.UserDto;
import com.wth.cloudstorage.frame.annotation.CheckLogin;
import com.wth.cloudstorage.frame.common.ApiResult;
import com.wth.cloudstorage.frame.config.CosClientConfig;
import com.wth.cloudstorage.frame.exception.BusinessException;
import com.wth.cloudstorage.manager.CosManager;
import com.wth.cloudstorage.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Arrays;

/**
 * 文件接口
 */
@RestController
@RequestMapping("/file")
@Slf4j
public class FileController {

    @Resource
    private CosManager cosManager;

    @Resource
    private UserService userService;

    @Resource
    private CosClientConfig cosClientConfig;

    /**
     * 文件上传
     *
     * @param multipartFile
     * @param uploadFileRequest
     * @param httpSession
     * @return
     */
    @PostMapping("/upload")
    @CheckLogin
    public ApiResult<String> uploadFile(@RequestPart("file") MultipartFile multipartFile,
                                        UploadFileReq uploadFileRequest, HttpSession httpSession) {
        String biz = uploadFileRequest.getBiz();
        FileUploadBizEnum fileUploadBizEnum = FileUploadBizEnum.getEnumByValue(biz);
        if (fileUploadBizEnum == null) {
            throw new BusinessException(ResponseCodeEnum.CODE_600, "不支持此业务");
        }
        validFile(multipartFile, fileUploadBizEnum);
        UserDto loginUser = userService.getLoginUser(httpSession);
        // 文件目录：根据业务、用户来划分
        String uuid = RandomStringUtils.randomAlphanumeric(8);
        String filename = uuid + "-" + multipartFile.getOriginalFilename();
        String filepath = String.format("/%s/%s/%s", fileUploadBizEnum.getValue(), loginUser.getUserId(), filename);
        File file = null;
        try {
            // 上传文件
            file = File.createTempFile(filepath, null);
            multipartFile.transferTo(file);
            cosManager.putObject(filepath, file);
            String onlineAddress = cosClientConfig.getHost() + filepath;
            // 返回可访问地址
            return ApiResult.success(onlineAddress);
        } catch (Exception e) {
            log.error("file upload error, filepath = " + filepath, e);
            throw new BusinessException(ResponseCodeEnum.CODE_500.getCode(), "上传失败");
        } finally {
            if (file != null) {
                // 删除临时文件
                boolean delete = file.delete();
                if (!delete) {
                    log.error("file delete error, filepath = {}", filepath);
                }
            }
        }
    }

    /**
     * 校验文件
     *
     * @param multipartFile
     * @param fileUploadBizEnum 业务类型
     */
    private void validFile(MultipartFile multipartFile, FileUploadBizEnum fileUploadBizEnum) {
        // 文件大小
        long fileSize = multipartFile.getSize();
        // 文件后缀
        String fileSuffix = FileUtil.getSuffix(multipartFile.getOriginalFilename());
        final long ONE_M = 1024 * 1024L;
        if (FileUploadBizEnum.USER_AVATAR.equals(fileUploadBizEnum)) {
            if (fileSize > ONE_M) {
                throw new BusinessException(ResponseCodeEnum.CODE_600, "文件大小不能超过 1M");
            }
            if (!Arrays.asList("jpeg", "jpg", "svg", "png", "webp").contains(fileSuffix)) {
                throw new BusinessException(ResponseCodeEnum.CODE_600, "文件类型错误");
            }
        }
    }
}