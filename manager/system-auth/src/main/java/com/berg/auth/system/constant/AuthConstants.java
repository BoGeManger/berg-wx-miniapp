package com.berg.auth.system.constant;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class AuthConstants {

    @Value("${shiro.urls:default}")
    String shiroUrls;

    @Value("${shiro.accounts:default}")
    String shiroAccounts;

    @Value("${shiro.expireTime:7200}")
    Integer expireTime;

}
