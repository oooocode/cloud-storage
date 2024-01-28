package com.wth.cloudstorage.mapper;

import com.wth.cloudstorage.domain.entity.FileInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wth
 * @since 2024-01-27
 */
@Repository
public interface FileInfoMapper extends BaseMapper<FileInfo> {

    Long selectUseSpace(@Param("userId") Long userId);
}
