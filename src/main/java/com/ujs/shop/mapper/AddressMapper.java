package com.ujs.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ujs.shop.common.po.AddressPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author mundo.wang
 * @date 2023/3/22 1:00
 */

@Mapper
@Repository
public interface AddressMapper extends BaseMapper<AddressPO> {

}
