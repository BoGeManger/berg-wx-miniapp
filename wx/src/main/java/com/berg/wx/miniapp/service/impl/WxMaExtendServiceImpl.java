package com.berg.wx.miniapp.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import com.berg.wx.miniapp.service.WxMaExtendService;
import com.berg.wx.miniapp.service.WxMaOperationService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WxMaExtendServiceImpl implements WxMaExtendService {
    final WxMaService wxMaService;

    @Override
    public WxMaOperationService getOperationService(){
        return new WxMaOperationServiceImpl(wxMaService);
    }
}
