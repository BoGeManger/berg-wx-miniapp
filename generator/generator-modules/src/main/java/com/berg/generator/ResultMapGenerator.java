package com.berg.generator;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 根据实体生成Mybatis ResultMap文本
 */
public class ResultMapGenerator {
    static Pattern humpPattern = Pattern.compile("[A-Z]");

    public static void main(String[] args) throws Exception {
        String[] classList = scanner("类名，多个英文逗号分割").split(",");
        //String[] classList = {"com.tcbj.maiyoucard.domain.vo.distributor.out.GetDistributorRecordPageOutVo","com.tcbj.maiyoucard.domain.vo.distributor.out.GetDistributorStatisticsPageOutVo"};
        for (String item : classList) {
            Class newClass = Class.forName(item);
            System.out.println(getResultMap(newClass));
        }
    }

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        return "请输入正确的" + tip;
    }


    /**
     * 获取ResultMap
     *
     * @param clazz 实体
     * @return
     */
    public static String getResultMap(Class<?> clazz) {
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
            String javaType = f.getType().getName();
            if ("serialVersionUID".equals(property)) {
                continue;//忽略掉这个属性
            }
            resultMap.append("  <result column=\"");
            resultMap.append(toUnderScore(property));
            resultMap.append("\" property=\"");
            resultMap.append(property);
            resultMap.append("\" jdbcType=\"");
            resultMap.append(javaType2jdbcType(javaType.toLowerCase()));
            resultMap.append("\" />\n");
        }
        resultMap.append("</resultMap>");
        return resultMap.toString();
    }


    /**
     * 下划线
     *
     * @param property
     * @return
     */
    static String toUnderScore(String property) {
        Matcher matcher = humpPattern.matcher(property);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 首字母小写
     *
     * @param property
     * @return
     */
    static String toLowerCase(String property) {
        return Character.toLowerCase(property.charAt(0)) + property.substring(1);
    }

    /**
     * 首字母大写
     *
     * @param property
     * @return
     */
    static String toUpperCase(String property) {
        return Character.toUpperCase(property.charAt(0)) + property.substring(1);
    }


    private static String javaType2jdbcType(String javaType) {
        if (javaType.contains("string")) {
            return "VARCHAR";
        } else if (javaType.contains("boolean")) {
            return "BIT";
        } else if (javaType.contains("byte")) {
            return "TINYINT";
        } else if (javaType.contains("short")) {
            return "SMALLINT";
        } else if (javaType.contains("int")) {
            return "INTEGER";
        } else if (javaType.contains("long")) {
            return "BIGINT";
        } else if (javaType.contains("double")) {
            return "DOUBLE";
        } else if (javaType.contains("float")) {
            return "REAL";
        } else if (javaType.contains("localdate")) {
            return "DATE";
        } else if (javaType.contains("localdatetime")) {
            return "TIMESTAMP";
        } else if (javaType.contains("date")) {
            return "TIMESTAMP";
        } else if (javaType.contains("timestamp")) {
            return "TIMESTAMP";
        } else if (javaType.contains("time")) {
            return "TIME";
        } else if (javaType.contains("bigdecimal")) {
            return "DECIMAL";
        } else {
            return "未知类型";
        }
    }
}
