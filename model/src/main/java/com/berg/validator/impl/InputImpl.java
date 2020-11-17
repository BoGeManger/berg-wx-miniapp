package com.berg.validator.impl;

import com.berg.validator.Input;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class InputImpl implements ConstraintValidator<Input, String> {

    String[] values;

    @Override
    public void initialize(Input input) {
        this.values = input.values();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(!StringUtils.isNotBlank(value)){
            return false;
        }
        Boolean flag = false;
        for (String item:values){
            if(item.equals(value)){
                flag=true;
            }
        }
        if(!flag){
            return false;
        }
        return true;
    }
}
