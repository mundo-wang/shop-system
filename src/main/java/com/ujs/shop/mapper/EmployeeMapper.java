package com.ujs.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ujs.shop.common.po.EmployeePO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author mundo.wang
 * @date 2023/2/6 17:43
 *
 * 员工表对应mapper接口
 */


@Mapper
@Repository
public interface EmployeeMapper extends BaseMapper<EmployeePO> {
}
