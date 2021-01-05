package com.prgers.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 在操作数据库的时候根据语句来拦截，帮我们自动补充被拦截的语句的属性
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    //在执行insert语句的时候被拦截
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("version", 1, metaObject);
        this.setFieldValByName("deleted", 0, metaObject);
    }
    //在执行update语句的时候被拦截
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", new Date(), metaObject);
    }
}
