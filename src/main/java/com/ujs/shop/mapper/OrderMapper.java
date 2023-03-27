package com.ujs.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ujs.shop.common.dto.OrderPageBackDTO;
import com.ujs.shop.common.dto.OrderPageDTO;
import com.ujs.shop.common.po.OrderPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * @author mundo.wang
 * @date 2023/3/27 13:22
 */

@Mapper
@Repository
public interface OrderMapper extends BaseMapper<OrderPO> {

    IPage<OrderPageDTO> orderPage(@Param("page") Page<OrderPageDTO> page,
                                  @Param("userId") String userId);

    IPage<OrderPageBackDTO> getOrderPage(@Param("page") Page<OrderPageBackDTO> page,
                                         @Param("number") String number,
                                         @Param("beginTime") LocalDateTime beginTime,
                                         @Param("endTime") LocalDateTime endTime);

}
