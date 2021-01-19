package com.berg.system.service.miniapp.impl;

import com.berg.system.service.miniapp.AppService;
import com.berg.vo.miniapp.MaAppVo;
import com.berg.wx.properties.WxMaProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppServiceImpl implements AppService {

    @Autowired
    WxMaProperties properties;

    /**
     * 获取小程序应用列表
     * @return
     */
    @Override
    public List<MaAppVo> getAppList(){
        List<MaAppVo> list = new ArrayList<>();
        properties.getConfigs().forEach(item->{
            MaAppVo maAppVo = new MaAppVo();
            maAppVo.setAppId(item.getAppId());
            maAppVo.setName(item.getName());
        });
        return list;
    }
}
