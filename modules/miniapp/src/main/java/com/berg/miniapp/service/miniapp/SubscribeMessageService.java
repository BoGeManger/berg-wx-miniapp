package com.berg.miniapp.service.miniapp;

import com.berg.vo.miniapp.MaTemplateInfoVo;
import com.berg.vo.miniapp.in.MaSendTemplateInVo;

import java.util.List;

public interface SubscribeMessageService {

    List<MaTemplateInfoVo> getTemplateList();

    void send(MaSendTemplateInVo input);
}
