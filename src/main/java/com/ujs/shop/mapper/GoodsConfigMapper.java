package com.ujs.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ujs.shop.common.po.GoodsConfigPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author mundo.wang
 * @date 2023/2/12 18:11
 */


@Mapper
@Repository
public interface GoodsConfigMapper extends BaseMapper<GoodsConfigPO> {
}
