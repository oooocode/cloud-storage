package com.wth.cloudstorage.domain.vo.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * 基础翻页请求
 * @since 2023-03-19
 */
@Data
public class PageBaseReq<T> {

    @Min(0)
    @Max(50)
    private Integer pageSize = 10;

    private Integer pageNo = 1;

    private T filter;

    /**
     * 获取mybatisPlus的page
     *
     * @return
     */
    public Page plusPage() {
        return new Page(pageNo, pageSize);
    }
}
