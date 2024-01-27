package com.wth.cloudstorage.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wth.cloudstorage.dao.FileInfoDao;
import com.wth.cloudstorage.domain.entity.FileInfo;
import com.wth.cloudstorage.domain.vo.req.FileInfoReq;
import com.wth.cloudstorage.domain.vo.req.PageBaseReq;
import com.wth.cloudstorage.service.FileInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: wth
 * @Create: 2024/1/27 - 20:15
 */
@Service
public class FileInfoServiceImpl implements FileInfoService {


    @Autowired
    private FileInfoDao fileInfoDao;

    @Override
    public IPage<FileInfo> pageFileInfo(PageBaseReq<FileInfoReq> req) {
        Page pageParam = req.plusPage();
        FileInfoReq filter = req.getFilter();
        Page page = fileInfoDao.pageFile(pageParam, filter);
        return page;
    }
}
