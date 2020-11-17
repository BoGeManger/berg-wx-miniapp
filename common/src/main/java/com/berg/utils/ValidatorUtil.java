package com.berg.utils;

import com.berg.exception.ParamException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;
import java.util.Set;

public class ValidatorUtil {


    @Autowired
    Validator globalValidator;

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
     * 校验
     */
    public static <T> void validator(T entity) {
        Set<ConstraintViolation<T>> constraintViolationSet = getConstraintViolationSet(entity);
        if (constraintViolationSet.size() > 0) {
            throw new ParamException(constraintViolationSet.iterator().next().getMessage());
        }
    }
}
