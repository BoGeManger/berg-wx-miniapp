package com.berg.generator;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.berg.generator.config.JdbcTypeHelper;
import org.apache.ibatis.builder.MapperBuilderAssistant;

/**
 * 根据表实体生成Mybatis Xml Insert方法
 */
public class XmlInsertGenerator extends AbstractGenerator {

    public static void main(String[] args) throws Exception {
        String[] classList = scanner("类名，多个英文逗号分割").split(StringPool.COMMA);
        for (String item : classList) {
            Class newClass = Class.forName(item);
            System.out.println(insert(newClass));
            System.out.println(insertBatch(newClass));
            System.out.println("==========================文本生成完成！！！==========================");
        }
    }

    public static String insert(Class<?> entityClass){
        TableInfo tableInfo = TableInfoHelper.initTableInfo(new MapperBuilderAssistant(new MybatisConfiguration(),null), entityClass);
        System.out.println("==========================insert==========================");
        System.out.println("void insert"+entityClass.getSimpleName()+"("+entityClass.getSimpleName()+" entity);");
        StringBuilder sql = new StringBuilder();
        sql.append("<insert id=\"insert").append(entityClass.getSimpleName()).append("\" ").append("parameterType=\"").append(entityClass.getName()).append("\">").append("\n");
        sql.append("INSERT INTO ").append(tableInfo.getTableName()).append("\n");
        sql.append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">").append("\n");;
        for (TableFieldInfo tableFieldInfo : tableInfo.getFieldList()) {
            sql.append(" ");
            sql.append("<if test=\"").append(tableFieldInfo.getProperty()).append(" != null\">").append("\n");
            sql.append("  ");
            sql.append(tableFieldInfo.getColumn() + ",").append("\n");
            sql.append(" ");
            sql.append("</if>").append("\n");
        }
        sql.append("</trim>").append("\n");
        sql.append(" VALUES ").append("\n");
        sql.append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">").append("\n");
        for (TableFieldInfo tableFieldInfo : tableInfo.getFieldList()) {
            sql.append(" ");
            sql.append("<if test=\"").append(tableFieldInfo.getProperty()).append(" != null\">").append("\n");
            sql.append("  ");
            sql.append("#{").append(tableFieldInfo.getProperty()).append(",jdbcType=").append(JdbcTypeHelper.getJdbcType(tableFieldInfo.getPropertyType().getSimpleName().toLowerCase())).append("},").append("\n");
            sql.append(" ");
            sql.append("</if>").append("\n");
        }
        sql.append("</trim>").append("\n");
        sql.append("</insert>");
        return sql.toString();
    }


    public static String insertBatch(Class<?> entityClass){
        TableInfo tableInfo = TableInfoHelper.initTableInfo(new MapperBuilderAssistant(new MybatisConfiguration(),null), entityClass);
        System.out.println("==========================insertBatch==========================");
        System.out.println("void insertBatch"+entityClass.getSimpleName()+"(List<"+entityClass.getSimpleName()+"> list);");
        StringBuilder sql = new StringBuilder();
        sql.append("<insert id=\"insertBatch").append(entityClass.getSimpleName()).append("\" ").append("parameterType=\"java.util.List\">").append("\n");
        sql.append("INSERT INTO ").append(tableInfo.getTableName()).append("\n");
        sql.append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">").append("\n");;
        for (TableFieldInfo tableFieldInfo : tableInfo.getFieldList()) {
            sql.append("  ");
            sql.append(tableFieldInfo.getColumn());
            sql.append(",");
            sql.append("\n");
        }
        sql.append("</trim>").append("\n");
        sql.append(" VALUES ").append("\n");
        sql.append("<foreach collection=\"list\" item=\"record\" separator=\",\">").append("\n");
        sql.append(" <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">").append("\n");
        for (TableFieldInfo tableFieldInfo : tableInfo.getFieldList()) {
            sql.append("  ");
            sql.append("#{record.").append(tableFieldInfo.getProperty()).append(",jdbcType=").append(JdbcTypeHelper.getJdbcType(tableFieldInfo.getPropertyType().getSimpleName().toLowerCase()));
            sql.append("},");
            sql.append("\n");
        }
        sql.append(" </trim>").append("\n");
        sql.append("</foreach>").append("\n");
        sql.append("</insert>");
        return sql.toString();
    }
}
