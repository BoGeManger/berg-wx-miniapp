package com.berg.miniapp.service.miniapp.impl;

import com.berg.dao.system.ma.entity.MsgRecordTbl;
import com.berg.dao.system.ma.service.MsgRecordTblDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SubscribeMessageAsyncTask {

    @Autowired
    MsgRecordTblDao msgRecordTblDao;

    /**
     * 新增消息记录
     * @param appId
     * @param openId
     * @param data
     * @param templateId
     * @param page
     * @param state
     * @param lang
     */
    @Async
    public void addMsgRecord(String appId,String openId,String data,String templateId,String page,String state,String lang,String remark){
        LocalDateTime now =LocalDateTime.now();
        MsgRecordTbl msgRecordTbl = new MsgRecordTbl();
        msgRecordTbl.setAppId(appId);
        msgRecordTbl.setOpenId(openId);
        msgRecordTbl.setData(data);
        msgRecordTbl.setTemplateId(templateId);
        msgRecordTbl.setPage(page);
        msgRecordTbl.setMiniappState(state);
        msgRecordTbl.setLang(lang);
        msgRecordTbl.setCreateTime(now);
        msgRecordTbl.setRemark(remark);
        msgRecordTblDao.save(msgRecordTbl);
    }
}
