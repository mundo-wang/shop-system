package com.ujs.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ujs.shop.common.po.CategoryPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author mundo.wang
 * @date 2023/2/11 19:16
 *
 *
 * 分类表对应mapper接口
 */


@Mapper
@Repository
public interface CategoryMapper extends BaseMapper<CategoryPO> {

}
