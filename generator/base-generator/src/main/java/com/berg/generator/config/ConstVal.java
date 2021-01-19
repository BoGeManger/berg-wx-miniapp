/*
 * Copyright (c) 2011-2020, baomidou (jobob@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.berg.generator.config;

import com.baomidou.mybatisplus.core.toolkit.StringPool;

import java.nio.charset.StandardCharsets;

/**
 * 定义常量
 */
public interface ConstVal {

    String MODULE_NAME = "ModuleName";

    String VO = "Vo";
    String EDIT_VO = "EditVo";
    String PAGE_IN_VO = "PageInVo";
    String DAO = "Dao";
    String DAO_IMPL = "DaoImpl";
    String ENTITY = "Entity";
    String SERVICE = "Service";
    String SERVICE_IMPL = "ServiceImpl";
    String MAPPER = "Mapper";
    String XML = "Xml";
    String CONTROLLER = "Controller";

    String VO_PATH = "vo_path";
    String EDIT_VO_PATH = "edit_vo_path";
    String PAGE_IN_VO_PATH = "page_in_vo_path";
    String DAO_PATH = "dao_path";
    String DAO_IMPL_PATH = "dao_impl_path";
    String ENTITY_PATH = "entity_path";
    String SERVICE_PATH = "service_path";
    String SERVICE_IMPL_PATH = "service_impl_path";
    String MAPPER_PATH = "mapper_path";
    String XML_PATH = "xml_path";
    String CONTROLLER_PATH = "controller_path";

    String JAVA_TMPDIR = "java.io.tmpdir";
    String UTF8 = StandardCharsets.UTF_8.name();
    String UNDERLINE = "_";

    String JAVA_SUFFIX = StringPool.DOT_JAVA;

    String XML_SUFFIX = ".xml";

    String TEMPLATE_VO_JAVA = "/templates/vo.java";
    String TEMPLATE_EDIT_VO_JAVA = "/templates/editVo.java";
    String TEMPLATE_PAGE_IN_VO_JAVA = "/templates/pageInVo.java";
    String TEMPLATE_DAO_JAVA = "/templates/dao.java";
    String TEMPLATE_DAO_IMPL_JAVA = "/templates/daoImpl.java";
    String TEMPLATE_ENTITY_JAVA = "/templates/entity.java";
    String TEMPLATE_MAPPER = "/templates/mapper.java";
    String TEMPLATE_XML = "/templates/mapper.xml";
    String TEMPLATE_SERVICE = "/templates/service.java";
    String TEMPLATE_SERVICE_IMPL = "/templates/serviceImpl.java";
    String TEMPLATE_CONTROLLER = "/templates/controller.java";

    String VM_LOAD_PATH_KEY = "file.resource.loader.class";
    String VM_LOAD_PATH_VALUE = "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader";

    String SUPER_MAPPER_CLASS = "com.berg.dao.base.BaseMapper";
    String SUPER_DAO_CLASS = "com.berg.dao.base.IService";
    String SUPER_DAO_IMPL_CLASS = "com.berg.dao.base.ServiceImpl";

}
