package com.berg.dao.base;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.berg.dao.constant.DataSource;
import org.springframework.core.annotation.AliasFor;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({java.lang.annotation.ElementType.TYPE, java.lang.annotation.ElementType.METHOD})
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Documented
@DS(DataSource.SYSTEM)
@Transactional
public @interface DSTransactional {

    @AliasFor(annotation = DS.class,attribute = "value")
    String value() default DataSource.SYSTEM;

    @AliasFor(annotation = Transactional.class,attribute = "value")
    String transactionalValue() default "";
    @AliasFor(annotation = Transactional.class)
    String transactionManager() default "";
    @AliasFor(annotation = Transactional.class)
    org.springframework.transaction.annotation.Propagation propagation() default org.springframework.transaction.annotation.Propagation.REQUIRED;
    @AliasFor(annotation = Transactional.class)
    org.springframework.transaction.annotation.Isolation isolation() default org.springframework.transaction.annotation.Isolation.DEFAULT;
    @AliasFor(annotation = Transactional.class)
    int timeout() default -1;
    @AliasFor(annotation = Transactional.class)
    boolean readOnly() default false;
    @AliasFor(annotation = Transactional.class)
    Class<? extends Throwable>[] rollbackFor() default {};
    @AliasFor(annotation = Transactional.class)
    String[] rollbackForClassName() default {};
    @AliasFor(annotation = Transactional.class)
    Class<? extends Throwable>[] noRollbackFor() default {};
    @AliasFor(annotation = Transactional.class)
    String[] noRollbackForClassName() default {};
}
