package com.ujs.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ujs.shop.common.dto.OrderPageDTO;
import com.ujs.shop.common.po.OrderPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author mundo.wang
 * @date 2023/3/27 13:22
 */

@Mapper
@Repository
public interface OrderMapper extends BaseMapper<OrderPO> {

    IPage<OrderPageDTO> orderPage(@Param("page") Page<OrderPageDTO> page,
                                  @Param("userId") String userId);

}
