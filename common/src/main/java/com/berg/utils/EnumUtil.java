package com.berg.utils;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

public class EnumUtil {

    static Map<Class,Object> map = new ConcurrentHashMap<>();

    public static <T> T getEnum(Class<T> className, Predicate<T> predicate) {
        if(!className.isEnum()){
            return null;
        }
        Object obj = map.get(className);
        T[] ts = null;
        if(obj == null){
            ts = className.getEnumConstants();
            map.put(className,ts);
        }else{
            ts = (T[])obj;
        }
        return Arrays.stream(ts).filter(predicate).findAny().get();
    }
}
