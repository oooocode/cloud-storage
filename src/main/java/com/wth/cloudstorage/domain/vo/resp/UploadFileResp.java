package com.wth.cloudstorage.domain.vo.resp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wth.cloudstorage.domain.dto.UserSpaceDto;
import lombok.Data;

/**
 * @Author: wth
 * @Create: 2024/1/22 - 15:52
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UploadFileResp {

    private Long fileId;
    /**
     * 文件状态 0-转码成功 1-转码中 2-转码失败
     */
    private Integer status;
}
