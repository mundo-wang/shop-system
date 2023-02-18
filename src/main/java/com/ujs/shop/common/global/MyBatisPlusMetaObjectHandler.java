package com.ujs.shop.common.global;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.ujs.shop.utils.LoginContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author mundo.wang
 * @date 2023/2/6 17:22
 *
 * mybatisplus对公共字段的填充
 */


@Component
@Slf4j
public class MyBatisPlusMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("insert 公共字段填充");
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("createUser", LoginContextUtil.getCurrentUserName());
        metaObject.setValue("updateUser", LoginContextUtil.getCurrentUserName());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("update 公共字段填充");
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("updateUser", LoginContextUtil.getCurrentUserName());
    }
}
