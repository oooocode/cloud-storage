package com.wth.cloudstorage.domain.vo.req;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 文件上传分片请求
 */
@Data
public class UploadFileChunkReq implements Serializable {


    private static final long serialVersionUID = 1L;

    /**
     * 文件id
     */
    private Long fileId;
    /**
     * 文件名
     */
    @NotNull
    private String fileName;
    /**
     * 父级id
     */
    @NotBlank
    private Long filePid;

    /**
     * md5值
     */
    @NotBlank
    private String fileMd5;
    /**
     * 当前分片
     */
    @NotNull
    private Integer chunkIndex;
    /**
     * 总共分片
     */
    @NotNull
    private Integer chunks;


}