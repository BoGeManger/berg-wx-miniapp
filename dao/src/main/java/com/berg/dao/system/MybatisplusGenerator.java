package com.berg.dao.system;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.berg.dao.constant.DataSource;
import lombok.Getter;

import java.util.*;

public class MybatisplusGenerator {

    //region 配置
    @Getter
    public static class GeneratorConfig{
        //数据库主模块名称
        String parentModuleName = DataSource.SYSTEM;
        //多数据源标识
        String ds = "DataSource.SYSTEM";

        //是否文件覆盖(默认false)
        Boolean fileOverride = false;
        //是否输出Entity(默认true)
        Boolean generateEntity = true;
        //是否输出Mapper(默认true)
        Boolean generateMapper = true;
        //是否输出Dao(默认true)
        Boolean generateDao = true;
        //是否输出DaoImpl(默认true)
        Boolean generateDaoImpl = true;
        //是否输出Xml(默认true)
        Boolean generateXml = true;

        //是否模块化输出(默认true)
        Boolean generateModule  =true;
        //是否名称根据模块过滤(默认true)
        Boolean filterModuleName = true;

        //region 业务配置
        //是否输出Vo
        Boolean generateVo = false;
        //是否输出Service
        Boolean generateService = false;
        //是否输出Controller
        Boolean generateController = false;

        //Vo基础包
        String packageVo = "com.berg.vo";
        //Vo包
        String voModule ="system";
        //Vo生成基础路径
        String voBasePath = "/model/src/main/java/com/berg/vo/";

        //Service基础包
        String packageService = "com.berg.system.service";
        //Service包
        String serviceModule ="system";
        //Service生成基础路径
        String serviceBasePath = "/system/src/main/java/com/berg/system/service/";

        //Controller基础包
        String packageController = "com.berg.system.controller";
        //Controller生成基础路径
        String controllerBasePath = "/system/src/main/java/com/berg/system/controller/";
        //endregion

        //region 数据库配置
        //数据库类型
        String driver = "mysql";
        //数据库访问地址
        String url = "jdbc:mysql://localhost:3306/miniappdb?useUnicode=true&serverTimezone=GMT&useSSL=false&characterEncoding=utf8";
        //数据库驱动
        String driverName = "com.mysql.cj.jdbc.Driver";
        //数据库用户名
        String username = "root";
        //数据库密码
        String password = "123456";
        //endregion
    }
    //endregion

    //region 生成
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
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    /**
     * RUN THIS
     */
    public static void main(String[] args) {
        GeneratorConfig generatorConfig = new GeneratorConfig();

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/dao/src/main/java");//路径
        gc.setOpen(false);
        gc.setAuthor("");
        //gc.setSwagger2(true);
        gc.setBaseColumnList(true);
        gc.setBaseResultMap(true);
        gc.setServiceName("%sDao");
        gc.setServiceImplName("%sDaoImpl");
        gc.setFileOverride(generatorConfig.fileOverride);//是否覆盖

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(generatorConfig.url);
        dsc.setDriverName(generatorConfig.driverName);
        dsc.setUsername(generatorConfig.username);
        dsc.setPassword(generatorConfig.password);
        if(generatorConfig.driver.equals("postgresql")){//postgresql使用
            dsc.setSchemaName("public");
        }
        dsc.setDbQuery(new MySqlQuery() {
            /**
             * 重写父类预留查询自定义字段<br>
             */
            @Override
            public String[] fieldCustom() {
                return new String[]{"Null"};
            }
        });

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.berg.dao."+generatorConfig.parentModuleName);
        if(generatorConfig.generateModule){
            pc.setModuleName(scanner("模块名"));
        }

        // 自定义配置（自定义配置会被优先输出）
        InjectionConfig cfg = new InjectionConfig() {
            //自定义属性注入（cfg.访问）
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                this.setMap(map);
            }
            @Override
            public void initTableMap(TableInfo tableInfo) {
                //自定义属性变量（cfg.访问）
                Map<String, Object> map = new HashMap<>();
                String entity = tableInfo.getEntityName();
                String model = entity.contains("Tbl")?entity.replace("Tbl",""):entity;
                char[] entityChars = tableInfo.getEntityName().toCharArray();
                entityChars[0] +=32;
                char[] modelChars = model.toCharArray();
                modelChars[0] +=32;
                map.put("parentModuleName",generatorConfig.parentModuleName);
                map.put("ds",generatorConfig.ds);
                map.put("entityName",String.valueOf(entityChars));
                map.put("modelName",String.valueOf(modelChars));
                map.put("comment",tableInfo.getComment().replace("表",""));
                map.put("model",model);
                map.put("voModule",generatorConfig.voModule);
                map.put("packageVo",generatorConfig.packageVo);
                map.put("serviceModule",generatorConfig.serviceModule);
                map.put("packageService",generatorConfig.packageService);
                map.put("packageController",generatorConfig.packageController);
                this.setMap(map);
            }
        };
        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();

        //自定义xml配置
        String xmlTemplatePath = "/templates/mapper.xml.ftl";
        FileOutConfig xmlFileOutConfig = new FileOutConfig(xmlTemplatePath) {
            //重新输出路径
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + "/dao/src/main/resources/"+generatorConfig.parentModuleName+"/mapper/" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        };

        //自定义Vo配置
        //pageInVo配置
        String pageInVoTemplatePath = "/templates/pageInVo.java.ftl";
        FileOutConfig pageInVoFileOutConfig =new FileOutConfig(pageInVoTemplatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String entity = tableInfo.getEntityName();
                String model = entity.contains("Tbl")?entity.replace("Tbl",""):entity;
                String className = String.format("Get%sPageInVo",model);
                return projectPath + generatorConfig.voBasePath  + generatorConfig.voModule +  "/in/" + className + StringPool.DOT_JAVA;
            }
        };
        //editVo配置
        String editVoTemplatePath = "/templates/editVo.java.ftl";
        FileOutConfig editVoFileOutConfig = new FileOutConfig(editVoTemplatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String entity = tableInfo.getEntityName();
                String model = entity.contains("Tbl")?entity.replace("Tbl",""):entity;
                String className = String.format("%sEditVo",model);
                return projectPath + generatorConfig.voBasePath  + generatorConfig.voModule + "/" + className + StringPool.DOT_JAVA;
            }
        };
        //vo配置
        String voTemplatePath = "/templates/vo.java.ftl";
        FileOutConfig voFileOutConfig = new FileOutConfig(voTemplatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String entity = tableInfo.getEntityName();
                String model = entity.contains("Tbl")?entity.replace("Tbl",""):entity;
                String className = String.format("%sVo",model);
                return projectPath + generatorConfig.voBasePath  + generatorConfig.voModule + "/" + className + StringPool.DOT_JAVA;
            }
        };
        //自定义Service配置
        String serviceTemplatePath = "/templates/service.java.ftl";
        FileOutConfig serviceFileOutConfig = new FileOutConfig(serviceTemplatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String entity = tableInfo.getEntityName();
                String model = entity.contains("Tbl")?entity.replace("Tbl",""):entity;
                String className = String.format("%sService",model);
                return projectPath + generatorConfig.serviceBasePath  + generatorConfig.serviceModule + "/" + className + StringPool.DOT_JAVA;
            }
        };
        //自定义ServiceImpl配置
        String serviceImplTemplatePath = "/templates/serviceImpl.java.ftl";
        FileOutConfig serviceImplFileOutConfig = new FileOutConfig(serviceImplTemplatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String entity = tableInfo.getEntityName();
                String model = entity.contains("Tbl")?entity.replace("Tbl",""):entity;
                String className = String.format("%sServiceImpl",model);
                return projectPath + generatorConfig.serviceBasePath  + generatorConfig.serviceModule + "/impl/" + className + StringPool.DOT_JAVA;
            }
        };
        //自定义Controller配置
        String controllerTemplatePath = "/templates/controller.java.ftl";
        FileOutConfig controllerFileOutConfig = new FileOutConfig(controllerTemplatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String entity = tableInfo.getEntityName();
                String model = entity.contains("Tbl")?entity.replace("Tbl",""):entity;
                String className = String.format("%sController",model);
                return projectPath + generatorConfig.controllerBasePath  + "/" + className + StringPool.DOT_JAVA;
            }
        };

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setService("/templates/dao.java");
        templateConfig.setServiceImpl("/templates/daoImpl.java");
        templateConfig.setXml(null);
        templateConfig.setController(null);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setSuperMapperClass("com.berg.dao.base.BaseMapper");
        strategy.setSuperServiceImplClass("com.berg.dao.base.ServiceImpl");
        strategy.setSuperServiceClass("com.berg.dao.base.IService");
        strategy.setEntityLombokModel(true);
        strategy.setChainModel(true);
        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");
        if(!generatorConfig.filterModuleName){//不启用名称模块过滤
            strategy.setTablePrefix("");
        }

        if(!generatorConfig.generateEntity){
            templateConfig.setEntity(null);
        }
        if(!generatorConfig.generateMapper){
            templateConfig.setMapper(null);
        }
        if(!generatorConfig.generateDao){
            templateConfig.setService(null);
        }
        if(!generatorConfig.generateDaoImpl){
            templateConfig.setServiceImpl(null);
        }
        if(generatorConfig.generateXml){
            focList.add(xmlFileOutConfig);
        }
        if(generatorConfig.generateVo){
            focList.add(voFileOutConfig);
            focList.add(editVoFileOutConfig);
            focList.add(pageInVoFileOutConfig);
        }
        if(generatorConfig.generateService){
            focList.add(serviceFileOutConfig);
            focList.add(serviceImplFileOutConfig);
        }
        if(generatorConfig.generateController){
            focList.add(controllerFileOutConfig);
        }

        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
        mpg.setGlobalConfig(gc);
        mpg.setDataSource(dsc);
        mpg.setPackageInfo(pc);
        mpg.setTemplate(templateConfig);
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }
    //endregion
}
