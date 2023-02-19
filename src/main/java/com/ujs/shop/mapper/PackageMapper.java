package com.ujs.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ujs.shop.common.dto.PackagePageDTO;
import com.ujs.shop.common.po.PackagePO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author mundo.wang
 * @date 2023/2/16 15:27
 */

@Mapper
@Repository
public interface PackageMapper extends BaseMapper<PackagePO> {

    IPage<PackagePageDTO> packagePage(@Param("page") Page<PackagePageDTO> page,
                                      @Param("name") String name,
                                      @Param("categoryId") String categoryId);
}
