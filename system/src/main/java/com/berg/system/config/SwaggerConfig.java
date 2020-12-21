package com.berg.system.config;

import com.berg.common.swagger.ErrorEnum;
import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2
@EnableKnife4j
@ConditionalOnProperty(prefix = "swagger", name ="enabled" ,havingValue = "true",matchIfMissing = true)
public class SwaggerConfig {

    List responseMessageList = getResponseMessageList();

    List getResponseMessageList(){
        List responseMessageList = new ArrayList<>();
        Arrays.stream(ErrorEnum.values()).forEach(errorEnum -> {
            responseMessageList.add(
                    new ResponseMessageBuilder().code(errorEnum.getKey()).message(errorEnum.getValue()).responseModel(
                            new ModelRef(errorEnum.getValue())).build()
            );
        });
        return responseMessageList;
    }

    ApiInfo apiInfo(String name) {
        return new ApiInfoBuilder()
                .title(name)
                .version("1.0.0")
                .build();
    }

    @Bean
    public Docket systemDocket() {
        ParameterBuilder ticketPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        ticketPar.name("Authentication").description("登录校验")//name表示名称，description表示描述
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(true).build();//required表示是否必填，defaultvalue表示默认值
        pars.add(ticketPar.build());//添加完此处一定要把下边的带***的也加上否则不生效
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("系统接口文档")
                .apiInfo(apiInfo("system接口文档"))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.berg.system.controller.system"))
                .paths(PathSelectors.any())
                .build()
                .globalResponseMessage(RequestMethod.GET, responseMessageList)
                .globalResponseMessage(RequestMethod.POST, responseMessageList)
                .globalResponseMessage(RequestMethod.PUT, responseMessageList)
                .globalResponseMessage(RequestMethod.DELETE, responseMessageList)
                .globalResponseMessage(RequestMethod.PATCH, responseMessageList)
                .globalResponseMessage(RequestMethod.OPTIONS, responseMessageList)
                .globalResponseMessage(RequestMethod.HEAD, responseMessageList)
                .globalResponseMessage(RequestMethod.TRACE, responseMessageList)
                .globalOperationParameters(pars);
    }

    @Bean
    public Docket memberDocket() {
        ParameterBuilder ticketPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        ticketPar.name("Authentication").description("登录校验")//name表示名称，description表示描述
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(true).build();//required表示是否必填，defaultvalue表示默认值
        pars.add(ticketPar.build());//添加完此处一定要把下边的带***的也加上否则不生效
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("会员接口文档")
                .apiInfo(apiInfo("system接口文档"))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.berg.system.controller.member"))
                .paths(PathSelectors.any())
                .build()
                .globalResponseMessage(RequestMethod.GET, responseMessageList)
                .globalResponseMessage(RequestMethod.POST, responseMessageList)
                .globalResponseMessage(RequestMethod.PUT, responseMessageList)
                .globalResponseMessage(RequestMethod.DELETE, responseMessageList)
                .globalResponseMessage(RequestMethod.PATCH, responseMessageList)
                .globalResponseMessage(RequestMethod.OPTIONS, responseMessageList)
                .globalResponseMessage(RequestMethod.HEAD, responseMessageList)
                .globalResponseMessage(RequestMethod.TRACE, responseMessageList)
                .globalOperationParameters(pars);
    }

    @Bean
    public Docket miniappDocket() {
        ParameterBuilder ticketPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        ticketPar.name("Authentication").description("登录校验")//name表示名称，description表示描述
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(true).build();//required表示是否必填，defaultvalue表示默认值
        pars.add(ticketPar.build());//添加完此处一定要把下边的带***的也加上否则不生效
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("微信小程序接口文档")
                .apiInfo(apiInfo("system接口文档"))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.berg.system.controller.miniapp"))
                .paths(PathSelectors.any())
                .build()
                .globalResponseMessage(RequestMethod.GET, responseMessageList)
                .globalResponseMessage(RequestMethod.POST, responseMessageList)
                .globalResponseMessage(RequestMethod.PUT, responseMessageList)
                .globalResponseMessage(RequestMethod.DELETE, responseMessageList)
                .globalResponseMessage(RequestMethod.PATCH, responseMessageList)
                .globalResponseMessage(RequestMethod.OPTIONS, responseMessageList)
                .globalResponseMessage(RequestMethod.HEAD, responseMessageList)
                .globalResponseMessage(RequestMethod.TRACE, responseMessageList)
                .globalOperationParameters(pars);
    }

}
