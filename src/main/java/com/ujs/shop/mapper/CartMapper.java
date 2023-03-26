package com.ujs.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ujs.shop.common.po.CartPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author mundo.wang
 * @date 2023/3/26 19:33
 */


@Mapper
@Repository
public interface CartMapper extends BaseMapper<CartPO> {

}
