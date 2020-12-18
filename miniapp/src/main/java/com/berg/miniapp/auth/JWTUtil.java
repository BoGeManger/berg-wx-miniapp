package com.berg.miniapp.auth;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.berg.vo.miniapp.MaUserInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class JWTUtil {

    public static final String KEY = "password";

    public static final cn.hutool.crypto.symmetric.DES DES = SecureUtil.des(KEY.getBytes());

    /**
     * 从token中获取openId
     * @param token
     * @return
     */
    public String getOpenId(String token) {
        return getField(token,"openId");
    }

    /**
     * 从 token中获取appId
     */
    public String getAppId() {
        return getField("appId");
    }

    public String getAppId(String token) {
        return getField(token,"appId");
    }

    /**
     * 从 token中获取openId
     */
    public String getOpenId() {
        return getField("openId");
    }

    /**
     * 从 token中获取unionId
     */
    public String getUnionId() {
        return getField("unionId");
    }

    /**
     * 从 token中获取memberId
     */
    public String getMemberId() {
        return getField("memberId");
    }

    /**
     * 从 token中获取maBindId
     */
    public Integer getMaBindId() {
        return Integer.parseInt(getField("maBindId"));
    }

    /**
     * 从 token中获取createTime
     */
    public LocalDateTime getCreateTime() {
        try{
            return LocalDateTimeUtil.parse(getField("createTime"),"yyyy-MM-dd HH:mm:ss,SSS");
        }catch (Exception ex){
            return null;
        }
    }

    /**
     * 从 token中获取modifyTime
     */
    public LocalDateTime getModifyTime() {
        try{
            return LocalDateTimeUtil.parse(getField("modifyTime"),"yyyy-MM-dd HH:mm:ss,SSS");
        }catch (Exception ex){
            return null;
        }
    }

    /**
     * 从 token中获取userinfo
     */
    public MaUserInfoVo getUserinfo() {
        try{
            return JSONUtil.toBean(getField("userinfo"),MaUserInfoVo.class);
        }catch (Exception ex){
            return null;
        }
    }

    /**
     * 从 token中获取memberId
     */
    public String getSessionKey() {
        return getField("sessionKey");
    }

    /**
     * 从token中获取字段
     * @param token
     * @param fieldName
     * @return
     */
    public String getField(String token,String fieldName){
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(fieldName).asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 从token中获取字段
     * @param fieldName
     * @return
     */
    public String getField(String fieldName){
        try {
            Subject subject = SecurityUtils.getSubject();
            DecodedJWT jwt = JWT.decode(subject.getPrincipal().toString());
            return jwt.getClaim(fieldName).asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 获取token
     * @return
     */
    public String getToken(){
        try {
            Subject subject = SecurityUtils.getSubject();
            return DES.encryptHex(subject.getPrincipal().toString());
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 生成token
     * @param appId
     * @param openId
     * @param unionId
     * @param memberId
     * @param maBindId
     * @param createTime
     * @param modifyTime
     * @param userInfoVo
     * @param sessionKey
     * @return
     */
    public String sign(String appId, String openId, String unionId, String memberId, Integer maBindId, LocalDateTime createTime, LocalDateTime modifyTime, MaUserInfoVo userInfoVo, String sessionKey) {
        try {
            openId = StringUtils.lowerCase(openId);
            Algorithm algorithm = Algorithm.HMAC256(sessionKey);
            return JWT.create()
                    .withClaim("appId", appId)
                    .withClaim("openId", openId)
                    .withClaim("unionId", openId)
                    .withClaim("memberId", memberId)
                    .withClaim("maBindId", maBindId)
                    .withClaim("createTime", LocalDateTimeUtil.format(createTime,"yyyy-MM-dd HH:mm:ss,SSS"))
                    .withClaim("modifyTime",  LocalDateTimeUtil.format(modifyTime,"yyyy-MM-dd HH:mm:ss,SSS"))
                    .withClaim("userinfo", JSONUtil.toJsonStr(userInfoVo))
                    .withClaim("sessionKey", sessionKey)
                    .sign(algorithm);
        } catch (Exception e) {
            return null;
        }
    }
}
