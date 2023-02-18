package com.ujs.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ujs.shop.common.dto.GoodsPageDTO;
import com.ujs.shop.common.global.PageFormBean;
import com.ujs.shop.common.po.GoodsPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author mundo.wang
 * @date 2023/2/12 18:11
 */


@Mapper
@Repository
public interface GoodsMapper extends BaseMapper<GoodsPO> {

    IPage<GoodsPageDTO> goodsPage(@Param("page") Page<GoodsPageDTO> page,
                                  @Param("name") String name,
                                  @Param("categoryId") String categoryId);
}
