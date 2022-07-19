package com.suye.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author sj.w
 *
 * 自定义元数据对象处理器
 */

@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {

    /**
     * 插入操作，自动填充
     * 在这个MyMetaObjectHandler类中，无法获取session对象
     * 因为insertFill和updateFill都是重写MetaObjectHandler中的，不能在形参中加一个HttpServletRequest对象获取session
     * 自然也就获取不到用户的id
     * 我们采用ThreadLocal的方法，将id的值写入当前线程的变量中，便于获取
     * BaseContext就是我们自定义的基于ThreadLocal封装工具类
     * @param metaObject 元对象，可以获取和设置对象的属性值
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("公共字段自动填充 insert");
        log.info(metaObject.toString());
        metaObject.setValue("createTime", new Date());
        metaObject.setValue("updateTime", new Date());
        metaObject.setValue("createUser", BaseContext.getCurrentId());
        metaObject.setValue("updateUser", BaseContext.getCurrentId());
    }

    /**
     * 更新操作，自动填充
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("公共字段自动填充 update");
        log.info(metaObject.toString());

        long id = Thread.currentThread().getId();
        log.info("线程id为：" + id);
        metaObject.setValue("updateTime", new Date());
        metaObject.setValue("updateUser", BaseContext.getCurrentId());
    }
}
