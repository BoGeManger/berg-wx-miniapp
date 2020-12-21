package com.berg.miniapp.config;

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
    public Docket customImplementation() {
        ParameterBuilder ticketPar = new ParameterBuilder();
        ParameterBuilder ticketPar1 = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        ticketPar.name("token").description("请求校验")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(true).build();
        ticketPar1.name("appId").description("微信小程序唯一标识")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(true).build();
        pars.add(ticketPar.build());
        pars.add(ticketPar1.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo("小程序接口文档"))
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
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
