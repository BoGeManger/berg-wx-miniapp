package com.berg.common;
import org.springframework.util.CollectionUtils;

import javax.validation.*;
import javax.validation.groups.Default;
import java.util.Set;

public class ValidatorUtil {

    public static ValidatorFactory getValidatorFactory() {
        return Validation.buildDefaultValidatorFactory();
    }

    public static Validator getValidator() {
        return getValidatorFactory().getValidator();
    }

    /**
     * 获取校验信息
     *
     * @param entity
     * @param <T>
     * @return
     */
    public static <T> Set<ConstraintViolation<T>> getConstraintViolationSet(T entity) {
        return getValidator().validate(entity, Default.class);
    }

    /**
     * 校验并抛出异常
     * @param entity
     * @param <T>
     */
    public static <T> void validator(T entity){
        Set<ConstraintViolation<T>> constraintViolationSet = getConstraintViolationSet(entity);
        if (!CollectionUtils.isEmpty(constraintViolationSet)) {
            throw new ConstraintViolationException(constraintViolationSet);
        }
    }
}
