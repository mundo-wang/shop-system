package com.ujs.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ujs.shop.common.po.PackagePO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author mundo.wang
 * @date 2023/2/16 15:27
 */

@Mapper
@Repository
public interface PackageMapper extends BaseMapper<PackagePO> {
}
