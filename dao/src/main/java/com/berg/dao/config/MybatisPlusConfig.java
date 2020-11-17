package com.berg.dao.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan({"com.berg.dao.*.*.mapper","com.berg.dao.*.mapper"})
public class MybatisPlusConfig {

    @Bean
    public SqlInjector sqlInjector() {
        return new SqlInjector();
    }
}
