package com.berg.miniapp.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
@ConditionalOnProperty(prefix = "swagger", name ="enabled" ,havingValue = "true",matchIfMissing = true)
public class SwaggerConfig {

    public static final String VERSION = "1.0.0";

    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("miniapp接口文档")
                .version(VERSION)
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
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars);
    }

}
