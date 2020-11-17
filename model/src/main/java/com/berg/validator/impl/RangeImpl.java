package com.berg.validator.impl;

import com.berg.validator.Range;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class RangeImpl implements ConstraintValidator<Range, Number> {

    Long min;
    Long max;

    long[] values;

    @Override
    public void initialize(Range range) {
        this.min = range.min();
        this.max = range.max();
        this.values = range.values();
    }

    @Override
    public boolean isValid(Number value, ConstraintValidatorContext context) {
        if ( value == null ) {
            return false;
        }
        for (Long item:values){
            if(item.equals(value.longValue())){
                return true;
            }
        }
        if ( value instanceof Double ) {
            return ((Double) value).compareTo(min.doubleValue())>=0 && ((Double) value).compareTo(max.doubleValue())<=0;
        }
        if ( value instanceof Float ) {
            return ((Float) value).compareTo(min.floatValue())>=0 && ((Float) value).compareTo(max.floatValue())<=0;
        }
        if ( value instanceof BigDecimal) {
            return ((BigDecimal) value).compareTo(new BigDecimal(min))>=0 && ((BigDecimal) value).compareTo(new BigDecimal(max))<=0;
        }
        if ( value instanceof Byte ) {
            return ((Byte) value).compareTo(min.byteValue())>=0 && ((Byte) value).compareTo(max.byteValue())<=0;
        }
        if ( value instanceof Short ) {
            return ((Short) value).compareTo(min.shortValue())>=0 && ((Short) value).compareTo(max.shortValue())<=0;
        }
        if ( value instanceof Integer ) {
            return ((Integer) value).compareTo(min.intValue())>=0 && ((Integer) value).compareTo(max.intValue())<=0;
        }
        if ( value instanceof Long ) {
            return ((Long) value).compareTo(min.longValue())>=0 && ((Long) value).compareTo(max.longValue())<=0;
        }
        return true;
    }

}
