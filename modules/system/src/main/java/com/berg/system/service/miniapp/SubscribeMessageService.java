package com.berg.system.service.miniapp;

import com.berg.dao.page.PageInfo;
import com.berg.vo.miniapp.MsgRecordVo;
import com.berg.vo.miniapp.in.GetMsgRecordPageInVo;

public interface SubscribeMessageService {

    PageInfo<MsgRecordVo> getMsgRecordPage(GetMsgRecordPageInVo input);

    void delTemplateCache(String appId);
}
