package com.berg.system.service;

import com.berg.auth.system.auth.AuthenticationUtil;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractService {

    @Autowired
    public AuthenticationUtil authenticationUtil;

    public String getUsername(){
        return authenticationUtil.getUsername();
    }

    public String getUsername(String token){
        return authenticationUtil.getUsername(token);
    }

    public String getToken(){
        return authenticationUtil.getToken();
    }

}
