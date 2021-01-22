package com.berg.generator;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.berg.generator.config.JdbcTypeHelper;
import org.apache.ibatis.builder.MapperBuilderAssistant;

/**
 * 根据表实体生成Mybatis Xml Update方法
 */
public class XmlUpdateGenerator extends AbstractGenerator {

    public static void main(String[] args) throws Exception {
        String[] classList = scanner("类名，多个英文逗号分割").split(StringPool.COMMA);
        for (String item : classList) {
            Class newClass = Class.forName(item);
            System.out.println(update(newClass));
            System.out.println("==========================文本生成完成！！！==========================");
        }
    }

    public static String update(Class<?> entityClass){
        TableInfo tableInfo = TableInfoHelper.initTableInfo(new MapperBuilderAssistant(new MybatisConfiguration(),null), entityClass);
        System.out.println("==========================insert==========================");
        System.out.println("void update"+entityClass.getSimpleName()+"("+entityClass.getSimpleName()+" entity);");
        StringBuilder sql = new StringBuilder();
        sql.append("<update id=\"update").append(entityClass.getSimpleName()).append("\" ").append("parameterType=\"").append(entityClass.getName()).append("\">").append("\n");
        sql.append("UPDATE ").append(tableInfo.getTableName()).append(" SET").append("\n");
        sql.append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">").append("\n");;
        for (TableFieldInfo tableFieldInfo : tableInfo.getFieldList()) {
            sql.append(" ");
            sql.append("<if test=\"").append(tableFieldInfo.getProperty()).append(" != null\">").append("\n");
            sql.append("  ");
            sql.append(tableFieldInfo.getColumn());
            sql.append(" = ");
            sql.append("#{").append(tableFieldInfo.getProperty()).append(",jdbcType=").append(JdbcTypeHelper.getJdbcType(tableFieldInfo.getPropertyType().getSimpleName().toLowerCase())).append("},").append("\n");
            sql.append(" ");
            sql.append("</if>").append("\n");
        }
        sql.append("</trim>").append("\n");
        sql.append("</update>");
        return sql.toString();
    }
}
