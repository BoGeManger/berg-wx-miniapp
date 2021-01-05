package com.berg.auth.miniapp.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.shiro.authc.AuthenticationToken;

@Data
public class JWTToken implements AuthenticationToken {

    static final long serialVersionUID = 1282057025599826155L;

    String token;

    String appId;

    String openId;

    Integer exipreAt;

    public JWTToken(String token) {
        this.token = token;
    }

    public JWTToken(String token,String appId,String openId, Integer exipreAt) {
        this.token = token;
        this.appId = appId;
        this.openId = openId;
        this.exipreAt = exipreAt;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

}
