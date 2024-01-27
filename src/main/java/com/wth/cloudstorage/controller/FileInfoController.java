package com.wth.cloudstorage.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wth.cloudstorage.domain.entity.FileInfo;
import com.wth.cloudstorage.domain.vo.req.FileInfoReq;
import com.wth.cloudstorage.domain.vo.req.PageBaseReq;
import com.wth.cloudstorage.domain.vo.resp.PageBaseResp;
import com.wth.cloudstorage.domain.vo.resp.UserResp;
import com.wth.cloudstorage.frame.common.ApiResult;
import com.wth.cloudstorage.service.FileInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.File;

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


    /**
     * 分页查询文件
     */
    @PostMapping("loadDataList")
    public PageBaseResp<FileInfo> loadDataList(@RequestBody FileInfoReq req, HttpSession httpSession) {
        UserResp user = (UserResp) httpSession.getAttribute(SESSION_KEY);
        req.setUserId(user.getUserId());
        IPage<FileInfo> fileInfoPage = fileInfoService.pageFileInfo(req);
        return PageBaseResp.init(fileInfoPage);
    }


}

