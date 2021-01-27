package com.berg.miniapp.service.miniapp.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import com.berg.common.exception.FailException;
import com.berg.common.exception.ParamException;
import com.berg.auth.miniapp.service.AbstractService;
import com.berg.miniapp.service.miniapp.UserService;
import com.berg.vo.miniapp.in.MaSetUserInfoInVo;
import com.berg.vo.miniapp.in.MaUserPhoneInVo;
import com.berg.vo.miniapp.out.MaUserPhoneOutVo;
import com.berg.wx.utils.WxMaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends AbstractService implements UserService {

    @Autowired
    UserAsyncTask userAsyncTask;

    /**
     * 获取用户手机号码
     * @param input
     * @return
     */
    @Override
    public MaUserPhoneOutVo phone(MaUserPhoneInVo input){
        MaUserPhoneOutVo result = new MaUserPhoneOutVo();
        String appId = getAppId();
        String openId = getOpenId();
        String sessionKey = getSessionKey();
        try{
            WxMaService wxService = WxMaUtil.getService(appId);
            // 用户信息校验
            if (!wxService.getUserService().checkUserInfo(sessionKey, input.getRawData(), input.getSignature())) {
                throw new ParamException("用户信息校验失败");
            }
            // 解密
            WxMaPhoneNumberInfo phoneNoInfo = wxService.getUserService().getPhoneNoInfo(sessionKey, input.getEncryptedData(), input.getIv());
            result.setPhone(phoneNoInfo.getPurePhoneNumber());
            //注册会员更新绑定会员id
            userAsyncTask.addMemberAndUpdateBindMa(appId,openId,result.getPhone());
        }catch (Exception ex){
            throw new FailException("调用小程序解密获取手机号接口phone失败");
        }
        return result;
    }

    /**
     * 保存用户信息
     * @param input
     */
    @Override
    public void setUserInfo(MaSetUserInfoInVo input){
        String appId = getAppId();
        String openId = getOpenId();
        userAsyncTask.updateMaBindByUserInfo(appId,openId,input);
    }
}
