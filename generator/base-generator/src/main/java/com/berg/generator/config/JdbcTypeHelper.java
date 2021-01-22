package com.berg.generator.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class JdbcTypeHelper {

    private static final Map<String,String> TYPE_MAP = new HashMap<>();//java类型和jdbc类型的对应关系
    static {
        TYPE_MAP.put("string", "VARCHAR");
        TYPE_MAP.put("boolean", "BIT");
        TYPE_MAP.put("byte", "TINYINT");
        TYPE_MAP.put("byte[]", "BINARY");
        TYPE_MAP.put("short", "SMALLINT");
        TYPE_MAP.put("integer", "INTEGER");
        TYPE_MAP.put("int", "INTEGER");
        TYPE_MAP.put("long", "BIGINT");
        TYPE_MAP.put("float", "REAL");
        TYPE_MAP.put("double", "DOUBLE");
        TYPE_MAP.put("bigdecimal", "DECIMAL");
        TYPE_MAP.put("clob", "CLOB");
        TYPE_MAP.put("blob", "BLOB");
        TYPE_MAP.put("array", "Array");
        TYPE_MAP.put("date", "TIMESTAMP");
        TYPE_MAP.put("timestamp", "TIMESTAMP");
        TYPE_MAP.put("time", "TIME");
        TYPE_MAP.put("localdate","DATE");
        TYPE_MAP.put("localdatetime","TIMESTAMP");
    }

    /**
     * 获取JdbcType
     * @param javaType
     * @return
     */
    public static String getJdbcType(String javaType) {
        return Optional.ofNullable(TYPE_MAP.get(javaType)).orElseGet(()->{return "未知类型";});
    }
}
