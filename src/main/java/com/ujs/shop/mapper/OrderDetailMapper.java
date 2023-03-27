package com.ujs.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ujs.shop.common.po.OrderDetailPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author mundo.wang
 * @date 2023/3/27 13:30
 */


@Mapper
@Repository
public interface OrderDetailMapper extends BaseMapper<OrderDetailPO> {
}
