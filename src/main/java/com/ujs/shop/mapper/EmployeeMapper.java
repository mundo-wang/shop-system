package com.ujs.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ujs.shop.common.dto.EmployeePageDTO;
import com.ujs.shop.common.po.EmployeePO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author mundo.wang
 * @date 2023/2/6 17:43
 * <p>
 * 员工表对应mapper接口
 */


@Mapper
@Repository
public interface EmployeeMapper extends BaseMapper<EmployeePO> {


    IPage<EmployeePageDTO> employeePage(@Param("page") Page<EmployeePageDTO> page,
                                        @Param("realName") String realName);

}
