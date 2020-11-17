package com.berg.miniapp.auth;

import com.berg.constant.RedisKeyConstants;
import com.berg.utils.DesUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    StringRedisTemplate stringTemplate;

    @Autowired
    JWTUtil jWTUtil;

    /**
     * 授权模块，判断是否验证授权
     * @param principals
     * @param permission
     * @return
     */
    @Override
    public  boolean isPermitted(PrincipalCollection principals, String permission){
        return true;
    }

    /**`
     * 授权模块，获取用户角色和权限
     *
     * @param token token
     * @return AuthorizationInfo 权限信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection token) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        return simpleAuthorizationInfo;
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 用户认证
     *
     * @param authenticationToken 身份认证 token
     * @return AuthenticationInfo 身份认证信息
     * @throws AuthenticationException 认证相关异常
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //这里的token是从JWTFilter 的 executeLogin 方法传递过来的，已经经过了解密
        String token = (String) authenticationToken.getCredentials();
        String encryptToken = DesUtil.encrypt(token);
        String appId = jWTUtil.getAppId(token);
        String openId = jWTUtil.getOpenId(token);
        if (StringUtils.isBlank(openId)){
            throw new AuthenticationException("token校验不通过");
        }
        String key = String.format(RedisKeyConstants.Ma.MA_TOKEN,appId.toLowerCase(),openId.toLowerCase());
        String value = stringTemplate.opsForValue().get(key);
        if(!StringUtils.isNotBlank(value)){
            throw new AuthenticationException("token已经过期");
        }
        if(!value.equals(encryptToken)){
            throw new AuthenticationException("token已经过期");
        }
        return new SimpleAuthenticationInfo(token, token, "shiro_realm");
    }
}
