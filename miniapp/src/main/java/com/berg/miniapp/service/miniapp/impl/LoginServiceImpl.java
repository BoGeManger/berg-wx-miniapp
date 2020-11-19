package com.berg.miniapp.service.miniapp.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.berg.dao.system.mb.entity.MaBindTbl;
import com.berg.dao.system.mb.service.MaBindTblDao;
import com.berg.exception.FailException;
import com.berg.miniapp.auth.JWTToken;
import com.berg.miniapp.auth.JWTUtil;
import com.berg.miniapp.constant.MiniappConstants;
import com.berg.miniapp.service.base.BaseService;
import com.berg.miniapp.service.miniapp.LoginService;
import com.berg.utils.DesUtil;
import com.berg.vo.miniapp.MaUserInfoVo;
import com.berg.vo.miniapp.in.MaLoginInVo;
import com.berg.vo.miniapp.in.MaRefreshInVo;
import com.berg.vo.miniapp.out.MaLoginOutVo;
import com.berg.wx.miniapp.utils.WxMaUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl extends BaseService implements LoginService{

    @Autowired
    MiniappConstants miniappConstants;

    @Autowired
    LoginAsyncTask loginAsyncTask;

    @Autowired
    MaBindTblDao maBindTblDao;

    /**
     * 小程序登录
     * @param input
     * @return
     */
    @Override
    public MaLoginOutVo login(MaLoginInVo input){
        String appId = getAppId();
        WxMaJscode2SessionResult session = new WxMaJscode2SessionResult();
        //调用小程序接口
        session = getSession(appId,input.getCode());
        MaLoginOutVo result = setMaLoginOutVo(appId,session.getOpenid(),session.getUnionid(),session.getSessionKey());
        //生成JWT
        JWTToken jwtToken = getJwt(result);
        //生成token缓存信息
        loginAsyncTask.setTokenCache(jwtToken);
        result.setToken(jwtToken.getToken());
        return result;
    }

    /**
     * 刷新登录校验并获取最新用户信息
     * @param input
     * @return
     */
    @Override
    public MaLoginOutVo refresh(MaRefreshInVo input){
        String appId = getAppId();
        WxMaJscode2SessionResult session = new WxMaJscode2SessionResult();
        if(StringUtils.isNotBlank(input.getCode())){
            //调用小程序接口
            session = getSession(appId,input.getCode());
        }else{
            //删除已有token缓存信息
            loginAsyncTask.delTokenCache(jWTUtil.getToken());
            session.setSessionKey(jWTUtil.getSessionKey());
            session.setOpenid(jWTUtil.getOpenId());
            session.setUnionid(jWTUtil.getUnionId());
        }
        MaLoginOutVo result = setMaLoginOutVo(appId,session.getOpenid(),session.getUnionid(),session.getSessionKey());
        //生成JWT
        JWTToken jwtToken = getJwt(result);
        //生成token缓存信息
        loginAsyncTask.setTokenCache(jwtToken);
        result.setToken(jwtToken.getToken());
        return result;
    }

    /**
     * 调用小程序接口获取信息
     * @param appId
     * @param code
     * @return
     */
    WxMaJscode2SessionResult getSession(String appId,String code){
        WxMaJscode2SessionResult session = null;
        try {
            WxMaService wxService = WxMaUtil.getService(appId);
            session = wxService.getUserService().getSessionInfo(code);
        } catch (Exception ex) {
            throw new FailException("调用小程序登录接口getSession失败:"+ex.getMessage());
        }
        return session;
    }

    /**
     * 设置返回内容
     * @param appId
     * @param openId
     * @param unionId
     * @param sessionKey
     * @return
     */
    MaLoginOutVo setMaLoginOutVo(String appId,String openId,String unionId,String sessionKey){
        MaLoginOutVo maLoginOutVo = new MaLoginOutVo();
        //查询用户绑定信息
        LambdaQueryWrapper queryWrapper = new LambdaQueryWrapper<MaBindTbl>()
                .eq(MaBindTbl::getAppId,appId)
                .eq(MaBindTbl::getOpenId,openId);
        MaBindTbl maBindTbl = maBindTblDao.getOneLimit(queryWrapper);
        if(maBindTbl==null){
            loginAsyncTask.addMaBind(appId,openId,unionId);
        }else{
            loginAsyncTask.updateMaBindUnionId(appId,openId,unionId);
            maLoginOutVo.setMemberId(maBindTbl.getMemberId());
            maLoginOutVo.setMaBindId(maBindTbl.getId());
            maLoginOutVo.setCreateTime(maBindTbl.getCreateTime());
            maLoginOutVo.setModifyTime(maBindTbl.getModifyTime());
            MaUserInfoVo userInfoVo = new MaUserInfoVo();
            userInfoVo.setNickName(maBindTbl.getNickName());
            userInfoVo.setAvatarUrl(maBindTbl.getAvatarUrl());
            userInfoVo.setGender(maBindTbl.getGender());
            userInfoVo.setCountry(maBindTbl.getCountry());
            userInfoVo.setProvince(maBindTbl.getProvince());
            userInfoVo.setCity(maBindTbl.getCity());
            userInfoVo.setLanguage(maBindTbl.getLanguage());
            maLoginOutVo.setUserinfo(userInfoVo);
        }
        maLoginOutVo.setAppId(appId);
        maLoginOutVo.setOpenId(openId);
        maLoginOutVo.setUnionId(unionId);
        maLoginOutVo.setSessionKey(sessionKey);
        return maLoginOutVo;
    }

    /**
     * 生成JWT
     * @param input
     * @return
     */
    JWTToken getJwt(MaLoginOutVo input) {
        String token = DesUtil.encrypt(jWTUtil.sign(input.getAppId(),input.getOpenId(),input.getUnionId(), input.getMemberId()
                ,input.getMaBindId(),input.getCreateTime(),input.getModifyTime(),input.getUserinfo(),input.getSessionKey()));
        return new JWTToken(token, input.getAppId(),input.getOpenId(),miniappConstants.getExpireTime());
    }

    /**
     * 小程序退出登录
     */
    @Override
    public void logout(){
        loginAsyncTask.delTokenCache(jWTUtil.getToken());
    }
}
