package com.berg.system.service.system.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.berg.constant.RedisKeyConstants;
import com.berg.dao.system.sys.entity.ComponentTbl;
import com.berg.dao.system.sys.entity.UserTbl;
import com.berg.dao.system.sys.service.ComponentTblDao;
import com.berg.dao.system.sys.service.RoleTblDao;
import com.berg.dao.system.sys.service.UserTblDao;
import com.berg.exception.UserFriendException;
import com.berg.system.service.system.LoginService;
import com.berg.system.auth.JWTToken;
import com.berg.system.auth.JWTUtil;
import com.berg.system.constant.SystemConstants;
import com.berg.utils.DateUtil;
import com.berg.utils.DesUtil;
import com.berg.vo.system.UserVo;
import com.berg.vo.system.in.LoginInVo;
import com.berg.vo.system.out.LoginOutVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    JWTUtil jWTUtil;
    @Autowired
    SystemConstants systemConstans;
    @Autowired
    StringRedisTemplate stringTemplate;

    @Autowired
    RoleTblDao roleTblDao;
    @Autowired
    UserTblDao userTblDao;
    @Autowired
    ComponentTblDao componentTblDao;

    /**
     * 用户登录
     *
     * @param input
     * @return
     */
    @Override
    public LoginOutVo login(LoginInVo input) {
        LoginOutVo result = new LoginOutVo();
        //用户登录验证
        UserTbl userTbl = loginValid(input);
        //生成JWT
        JWTToken jwtToken = getJwt(userTbl);
        //生成token缓存信息
        String key = String.format(RedisKeyConstants.System.SYSTEM_TOKEN, jwtToken.getToken());
        stringTemplate.opsForValue().set(key, jwtToken.getToken(), systemConstans.getExpireTime(), TimeUnit.SECONDS);
        //生成返回信息
        result.setToken(jwtToken.getToken());
        result.setExipreTime(jwtToken.getExipreAt());
        result.setRoles(getUserRoles(userTbl.getUsername()));
        result.setPermissions(getUserPerms(userTbl.getUsername()));
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(userTbl, userVo);
        result.setUser(userVo);
        return result;
    }

    /**
     * 用户登录验证
     * @param input
     */
    UserTbl loginValid(LoginInVo input) {
        LambdaQueryWrapper query = new LambdaQueryWrapper<UserTbl>()
                .eq(UserTbl::getUsername,StringUtils.lowerCase(input.getUsername()))
                .eq(UserTbl::getPassword,DigestUtils.md5DigestAsHex(input.getPassword().getBytes()))
                .eq(UserTbl::getIsdel,0);
        UserTbl userTbl =userTblDao.getOne(query);
        if (userTbl == null) {
            throw new UserFriendException("用户名或密码错误");
        } else if (userTbl.getIslock().equals(1)) {
            throw new UserFriendException("账号已被锁定,请联系管理员");
        }
        return userTbl;
    }

    /**
     * 生成JWT
     * @param userTbl
     * @return
     */
    JWTToken getJwt(UserTbl userTbl) {
        String token = DesUtil.encrypt(jWTUtil.sign(userTbl.getUsername(), userTbl.getPassword()));
        Date expireTime = DateUtil.addDateBySecond(new Date(), systemConstans.getExpireTime());
        String expireTimeStr = DateUtil.format(expireTime, "yyyyMMddHHmmss");
        return new JWTToken(token, expireTimeStr);
    }

    /**
     * 获取用户角色
     * @param userName
     * @return
     */
    @Override
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
    @Override
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

    /**
     * 退出登录
     */
    @Override
    public void logout(){
        String token = jWTUtil.getToken();
        if(StringUtils.isNotBlank(token)){
            String key = String.format(RedisKeyConstants.System.SYSTEM_TOKEN, token);
            stringTemplate.delete(key);
        }
    }

}
