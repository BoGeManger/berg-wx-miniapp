package com.berg.miniapp.service.miniapp.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaCodeLineColor;
import cn.hutool.json.JSONUtil;
import com.berg.common.exception.FailException;
import com.berg.auth.miniapp.service.AbstractService;
import com.berg.miniapp.service.miniapp.QRCodeService;
import com.berg.vo.miniapp.in.MaCreateQRCodeInVo;
import com.berg.vo.miniapp.in.MaQRCodeGetInVo;
import com.berg.vo.miniapp.in.MaQRCodeGetUnlimitedInVo;
import com.berg.wx.utils.WxMaUtil;
import org.springframework.stereotype.Service;

@Service
public class QRCodeServiceImpl extends AbstractService implements QRCodeService {

    /**
     * 获取小程序二维码,永久有效,有数量限制
     * @param input
     * @return
     */
    @Override
    public byte[] createQRCode(MaCreateQRCodeInVo input){
        byte[] file = null;
        String appId = getAppId();
        try {
            WxMaService wxService = WxMaUtil.getService(appId);
            file = wxService.getQrcodeService().createQrcodeBytes(input.getPath(),input.getWidth());
        }catch (Exception ex){
            throw new FailException("调用小程序获取小程序二维码接口createQRCode失败:"+ex.getMessage());
        }
        return file;
    }

    /**
     * 获取小程序码,永久有效,有数量限制
     * @param input
     * @return
     */
    @Override
    public byte[]  get(MaQRCodeGetInVo input){
        byte[]  file = null;
        String appId = getAppId();
        try {
            WxMaService wxService = WxMaUtil.getService(appId);
            WxMaCodeLineColor wxMaCodeLineColor = JSONUtil.toBean(input.getLineColor(),WxMaCodeLineColor.class);
            file = wxService.getQrcodeService().createWxaCodeBytes(input.getPath(),input.getWidth(),input.getAutoColor(),wxMaCodeLineColor,input.getIshyaline());
        }catch (Exception ex){
            throw new FailException("调用小程序获取小程序码接口get失败:"+ex.getMessage());
        }
        return file;
    }

    /**
     * 获取小程序码,永久有效,数量暂无限制
     * @param input
     * @return
     */
    @Override
    public byte[]  getUnlimited(MaQRCodeGetUnlimitedInVo input){
        byte[]  file = null;
        String appId = getAppId();
        try {
            WxMaService wxService = WxMaUtil.getService(appId);
            WxMaCodeLineColor wxMaCodeLineColor = JSONUtil.toBean(input.getLineColor(),WxMaCodeLineColor.class);
            file = wxService.getQrcodeService().createWxaCodeUnlimitBytes(input.getScene(),input.getPath(),input.getWidth(),input.getAutoColor(),wxMaCodeLineColor,input.getIshyaline());
        }catch (Exception ex){
            throw new FailException("调用小程序获取小程序码接口get失败:"+ex.getMessage());
        }
        return file;
    }
}
