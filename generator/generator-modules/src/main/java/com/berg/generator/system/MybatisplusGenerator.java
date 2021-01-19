package com.berg.generator.system;

import com.berg.generator.AbstractGenerator;
import com.berg.generator.AutoGenerator;
import com.berg.generator.config.*;
import com.berg.generator.config.rules.NamingStrategy;
import com.berg.generator.engine.FreemarkerTemplateEngine;
import com.berg.generator.system.config.GeneratorConfig;

/**
 * 通用CRUD生成
 */
public class MybatisplusGenerator extends AbstractGenerator {

    /**
     * RUN THIS
     */
    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + GeneratorConfig.PATH);//路径
        gc.setOpen(false);
        gc.setAuthor("");
        //gc.setSwagger2(true);
        gc.setBaseColumnList(true);
        gc.setBaseResultMap(true);
        gc.setFileOverride(GeneratorConfig.FILE_OVERRIDE);//是否覆盖
        gc.setDs(GeneratorConfig.DS);
        gc.setParentModuleName(GeneratorConfig.PARENT_MODULE_NAME);
        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(GeneratorConfig.URL);
        dsc.setDriverName(GeneratorConfig.DRIVER_NAME);
        dsc.setUsername(GeneratorConfig.USER_NAME);
        dsc.setPassword(GeneratorConfig.PASSWORD);
        if (GeneratorConfig.DRIVER.equals("postgresql")) {//postgresql使用
            dsc.setSchemaName(GeneratorConfig.SCHEMA_NAME);
        }
        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.berg.dao." + GeneratorConfig.PARENT_MODULE_NAME);
        if (GeneratorConfig.GENERATE_MODULE) {
            pc.setModuleName(scanner("模块名"));
        }
        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setChainModel(true);
        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");
        if (!GeneratorConfig.FILTER_MODULE_NAME) {//不启用名称模块过滤
            strategy.setTablePrefix("");
        }
        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        if (!GeneratorConfig.GENERATE_ENTITY) {
            templateConfig.setEntity(null);
        }
        if (!GeneratorConfig.GENERATE_MAPPER) {
            templateConfig.setMapper(null);
        }
        if (!GeneratorConfig.GENERATE_DAO) {
            templateConfig.setDao(null);
        }
        if (!GeneratorConfig.GENERATE_DAO_IMPL) {
            templateConfig.setDaoImpl(null);
        }
        if (!GeneratorConfig.GENERATE_XML) {
            templateConfig.setXml(null);
        }
        if (!GeneratorConfig.GENERATE_BUSINESS) {
            templateConfig.setVo(null);
            templateConfig.setEditVo(null);
            templateConfig.setPageInVo(null);
            templateConfig.setController(null);
            templateConfig.setService(null);
            templateConfig.setServiceImpl(null);
        }
        mpg.setGlobalConfig(gc);
        mpg.setDataSource(dsc);
        mpg.setPackageInfo(pc);
        mpg.setTemplate(templateConfig);
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        if(GeneratorConfig.ONLYSTR){
            mpg.executeStr();
        }else{
            mpg.execute();
        }
    }
    //endregion
}
