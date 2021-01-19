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

import com.berg.generator.config.ConstVal;
import com.berg.generator.config.TemplateType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * 模板路径配置项
 */
@Data
@Accessors(chain = true)
public class TemplateConfig {

    private String entity = ConstVal.TEMPLATE_ENTITY_JAVA;

    private String service = ConstVal.TEMPLATE_SERVICE;

    private String serviceImpl = ConstVal.TEMPLATE_SERVICE_IMPL;

    private String mapper = ConstVal.TEMPLATE_MAPPER;

    private String xml = ConstVal.TEMPLATE_XML;

    private String controller = ConstVal.TEMPLATE_CONTROLLER;

    private String vo = ConstVal.TEMPLATE_VO_JAVA;

    private String editVo= ConstVal.TEMPLATE_EDIT_VO_JAVA;

    private String pageInVo= ConstVal.TEMPLATE_PAGE_IN_VO_JAVA;

    private String dao = ConstVal.TEMPLATE_DAO_JAVA;

    private String daoImpl = ConstVal.TEMPLATE_DAO_IMPL_JAVA;

    
    /**
     * 禁用模板
     *
     * @param templateTypes 模板类型
     * @return this
     * @since 3.3.2
     */
    public com.berg.generator.config.TemplateConfig disable(TemplateType... templateTypes) {
        if (templateTypes != null && templateTypes.length > 0) {
            for (TemplateType templateType : templateTypes) {
                switch (templateType) {
                    case XML:
                        setXml(null);
                        break;
                    case ENTITY:
                        setEntity(null);
                        break;
                    case MAPPER:
                        setMapper(null);
                        break;
                    case SERVICE:
                        setService(null).setServiceImpl(null).setVo(null).setEditVo(null).setPageInVo(null).setController(null);
                        break;
                    case VO:
                        setVo(null);
                        break;
                    case EDIT_VO:
                        setEditVo(null);
                        break;
                    case PAGE_IN_VO:
                        setPageInVo(null);
                        break;
                    case DAO:
                        setDao(null).setDaoImpl(null);
                        break;
                }
            }
        }
        return this;
    }
    
}
