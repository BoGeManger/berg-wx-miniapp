package com.berg.auth.system.auth;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.berg.auth.system.constant.AuthConstants;
import com.berg.common.constant.RedisKeyConstants;
import com.berg.dao.system.sys.entity.ComponentTbl;
import com.berg.dao.system.sys.entity.UserTbl;
import com.berg.dao.system.sys.service.ComponentTblDao;
import com.berg.dao.system.sys.service.RoleTblDao;
import com.berg.dao.system.sys.service.UserTblDao;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class AuthenticationRealm extends AuthorizingRealm {

    @Autowired
    StringRedisTemplate stringTemplate;

    @Autowired
    AuthenticationUtil jWTUtil;
    @Autowired
    AuthConstants systemConstans;
    @Lazy
    @Autowired
    RoleTblDao roleTblDao;
    @Lazy
    @Autowired
    ComponentTblDao componentTblDao;
    @Lazy
    @Autowired
    UserTblDao userTblDao;

    //region 从数据库获取授权信息
    /**
     * 获取用户角色
     * @param userName
     * @return
     */
    public Set<String> getUserRoles(String userName) {
        List<String> list = new ArrayList<>();
        String key = String.format(RedisKeyConstants.System.USER_ROLE, userName);
        String value = stringTemplate.opsForValue().get(key);
        if (StringUtils.isNotBlank(value)) {
            list = JSON.parseArray(stringTemplate.opsForValue().get(key), String.class);
        } else {
            list = roleTblDao.getMapper().listUserRoleName(userName);
            stringTemplate.opsForValue().set(key,JSON.toJSONString(list),systemConstans.getExpireTime(), TimeUnit.SECONDS);
        }
        return list.stream().collect(Collectors.toSet());
    }

    /**
     * 获取用户权限
     * @param userName
     * @return
     */
    public Set<String> getUserPerms(String userName) {
        List<String> list = new ArrayList<>();
        String key = String.format(RedisKeyConstants.System.USER_PERMS, userName);
        String value = stringTemplate.opsForValue().get(key);
        if (StringUtils.isNotBlank(value)) {
            list = JSON.parseArray(value, String.class);
        } else {
            if(checkAccount(userName)){
                LambdaQueryWrapper query = new QueryWrapper<ComponentTbl>().select("perms").lambda()
                        .eq(ComponentTbl::getIsdel,0);
                list = componentTblDao.listObjs(query);
            }else {
                list = componentTblDao.getMapper().listUserPerms(userName);
            }
            stringTemplate.opsForValue().set(key,JSON.toJSONString(list),systemConstans.getExpireTime(), TimeUnit.SECONDS);
        }
        return list.stream().collect(Collectors.toSet());
    }

    /**
     * 判断是否超级用户
     * @param userName
     * @return
     */
    Boolean checkAccount(String userName){
        Boolean flag = false;
        String[] accounts = StringUtils.splitByWholeSeparatorPreserveAllTokens(systemConstans.getShiroAccounts(), ",");
        for (String a : accounts) {
            if (a.equals(userName))
                flag = true;
        }
        return  flag;
    }
    //endregion

    @Override
    public boolean supports(org.apache.shiro.authc.AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 授权模块，判断是否验证授权
     * @param principals
     * @param permission
     * @return
     */
    @Override
    public  boolean isPermitted(PrincipalCollection principals, String permission){
        Boolean flag = false;
        String username = jWTUtil.getUsername(principals.getPrimaryPrincipal().toString());
        String[] accounts = StringUtils.splitByWholeSeparatorPreserveAllTokens(systemConstans.getShiroAccounts(), ",");
        for (String a : accounts) {
            if (a.equals(username))
                flag = true;
        }
        return super.isPermitted(principals,permission) || flag;
    }

    /**`
     * 授权模块，获取用户角色和权限
     *
     * @param token token
     * @return AuthorizationInfo 权限信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection token) {
        String username = jWTUtil.getUsername(token.toString());
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        // 获取用户角色集
        Set<String> roleSet = getUserRoles(username);
        simpleAuthorizationInfo.setRoles(roleSet);
        // 获取用户权限集
        Set<String> permissionSet = getUserPerms(username);
        simpleAuthorizationInfo.setStringPermissions(permissionSet);
        return simpleAuthorizationInfo;
    }

    /**
     * 用户认证
     *
     * @param authenticationToken 身份认证 token
     * @return AuthenticationInfo 身份认证信息
     * @throws AuthenticationException 认证相关异常
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken authenticationToken) throws AuthenticationException {
        //这里的token是从JWTFilter 的 executeLogin 方法传递过来的，已经经过了解密
        String token = (String) authenticationToken.getCredentials();
        //从redis里获取这个token
        String encryptToken = jWTUtil.DES.encryptHex(token);
        String key = String.format(RedisKeyConstants.System.SYSTEM_TOKEN, encryptToken.toUpperCase());
        if(!stringTemplate.hasKey(key)){
            throw new AuthenticationException("token已经过期");
        }

        String username = jWTUtil.getUsername(token);
        if (StringUtils.isBlank(username))
            throw new AuthenticationException("token校验不通过");

        // 通过用户名查询用户信息
        UserTbl userTbl = userTblDao.getOne(new LambdaQueryWrapper<UserTbl>().eq(UserTbl::getUsername,username));


        if (userTbl == null)
            throw new AuthenticationException("用户名或密码错误");
        if (!jWTUtil.verify(token, username, userTbl.getPassword()))
            throw new AuthenticationException("token校验不通过");
        return new SimpleAuthenticationInfo(token, token, "shiro_realm");
    }
}
