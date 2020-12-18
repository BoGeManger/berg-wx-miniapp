package com.berg.dao.base;

import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Aspect
@Component
public class MapperAspect {

    @Pointcut("execution(public * com.berg.dao..*.mapper.*.*(..))")
    public void pointcut() {
    }

    @After("pointcut()")
    public void after(){
        //mapper手动调用动态数据源后需销毁处理
        String ds = DynamicDataSourceContextHolder.peek();
        boolean empty = StringUtils.isEmpty(ds);
        if (!empty) {
            DynamicDataSourceContextHolder.clear();
        }
    }
}
