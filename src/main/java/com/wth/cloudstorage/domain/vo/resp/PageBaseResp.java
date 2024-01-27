package com.wth.cloudstorage.domain.vo.resp;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="https://github.com/zongzibinbin">abin</a>
 * @since 2023-03-19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageBaseResp<T> {

    private Integer pageNo;

    private Integer pageSize;

    private Long totalRecords;

    private List<T> list;


    public static <T> PageBaseResp<T> empty() {
        PageBaseResp<T> result = new PageBaseResp<>();
        result.setPageNo(1);
        result.setPageSize(0);
        result.setTotalRecords(0L);
        result.setList(new ArrayList<>());
        return result;
    }

    public static <T> PageBaseResp<T> init(Integer pageNo, Integer pageSize, Long totalRecords, List<T> list) {
        return new PageBaseResp<>(pageNo, pageSize, totalRecords, list);
    }

    public static <T> PageBaseResp<T> init(IPage<T> page) {
        return init((int) page.getCurrent(), (int) page.getSize(), page.getTotal(), page.getRecords());
    }

    public static <T> PageBaseResp<T> init(IPage page, List<T> list) {
        return init((int) page.getCurrent(), (int) page.getSize(), page.getTotal(), list);
    }

    public static <T> PageBaseResp<T> init(PageBaseResp resp, List<T> list) {
        return init(resp.getPageNo(), resp.getPageSize(), resp.getTotalRecords(), list);
    }

}
