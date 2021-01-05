package com.berg.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;

@Configuration
public class QuartzConfig implements SchedulerFactoryBeanCustomizer {

    @Autowired
    DataSourceProperties dataSourceProperties;

    @Override
    public void customize(SchedulerFactoryBean schedulerFactoryBean) {
        DataSource dataSource = dataSourceProperties.initializeDataSourceBuilder().build();
        schedulerFactoryBean.setDataSource(dataSource);
        schedulerFactoryBean.setTransactionManager(new DataSourceTransactionManager(dataSource));
    }
}
