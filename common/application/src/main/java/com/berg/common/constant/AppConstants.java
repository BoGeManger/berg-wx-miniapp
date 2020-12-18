package com.berg.common.constant;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public  class AppConstants {

    @Value("${spring.application.name}")
    String appName;
    @Value("${server.port}")
    Integer port;

    @Value("${snowflake.id.worker.workerId:0}")
    String workerId;
    @Value("${snowflake.id.worker.datacenterId:0}")
    String datacenterId;
}
