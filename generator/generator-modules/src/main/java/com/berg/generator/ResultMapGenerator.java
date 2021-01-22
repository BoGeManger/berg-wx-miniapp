package com.berg.generator;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.berg.generator.config.JdbcTypeHelper;
import com.berg.generator.config.rules.NamingStrategy;

import java.lang.reflect.Field;

/**
 * 根据实体生成Mybatis ResultMap文本
 */
public class ResultMapGenerator extends AbstractGenerator{

    public static void main(String[] args) throws Exception {
        NamingStrategy namingStrategy = NamingStrategy.camel_to_underline;
        //NamingStrategy namingStrategy = null;
        String[] classList = scanner("类名，多个英文逗号分割").split(StringPool.COMMA);
        for (String item : classList) {
            Class newClass = Class.forName(item);
            System.out.println(getResultMap(newClass,namingStrategy));
        }
    }

    /**
     * 获取ResultMap
     * @param clazz
     * @param namingStrategy column命名规则,默认驼峰转下划线,其他按字段名称输出
     * @return
     */
    public static String getResultMap(Class<?> clazz,NamingStrategy namingStrategy) {
        Object obj = null;
        try {
            obj = clazz.newInstance();
        } catch (Exception e) {
            return "反射生成实体异常#";
        }

        String clazzName = clazz.getSimpleName();
        String resultMapId = clazzName.charAt(0) + clazzName.substring(1) + "Map";
        String pkgName = clazz.getName();

        StringBuilder resultMap = new StringBuilder();
        resultMap.append("<resultMap id=\"");
        resultMap.append(resultMapId);
        resultMap.append("\" type=\"");
        resultMap.append(pkgName);
        resultMap.append("\">\n");

        Field[] fields = clazz.getDeclaredFields();
        for (Field f : fields) {
            String property = f.getName();
            String javaType = f.getType().getSimpleName();
            if ("serialVersionUID".equals(property)) {
                continue;//忽略掉这个属性
            }
            resultMap.append("  <result column=\"");
            if(NamingStrategy.underline_to_camel == namingStrategy){
                resultMap.append(NamingStrategy.camelToUnderline(property));
            }else{
                resultMap.append(property);
            }
            resultMap.append("\" property=\"");
            resultMap.append(property);
            resultMap.append("\" jdbcType=\"");
            resultMap.append(JdbcTypeHelper.getJdbcType(javaType.toLowerCase()));
            resultMap.append("\" />\n");
        }
        resultMap.append("</resultMap>");
        return resultMap.toString();
    }


}
