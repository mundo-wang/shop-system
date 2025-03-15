package com.ujs.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ujs.shop.common.dto.CategoryPageDTO;
import com.ujs.shop.common.po.CategoryPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author mundo.wang
 * @date 2023/2/11 19:16
 * <p>
 * <p>
 * 分类表对应mapper接口
 */


@Mapper
@Repository
public interface CategoryMapper extends BaseMapper<CategoryPO> {

    IPage<CategoryPageDTO> categoryPage(@Param("page") Page<CategoryPageDTO> page,
                                        @Param("type") Boolean type,
                                        @Param("name") String name);

}
