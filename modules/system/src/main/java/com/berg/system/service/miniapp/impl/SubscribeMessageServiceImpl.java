package com.berg.system.service.miniapp.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.berg.common.constant.RedisKeyConstants;
import com.berg.dao.page.PageInfo;
import com.berg.dao.system.ma.entity.MsgRecordTbl;
import com.berg.dao.system.ma.service.MsgRecordTblDao;
import com.berg.system.service.miniapp.SubscribeMessageService;
import com.berg.vo.miniapp.MsgRecordVo;
import com.berg.vo.miniapp.in.GetMsgRecordPageInVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class SubscribeMessageServiceImpl implements SubscribeMessageService {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    MsgRecordTblDao msgRecordTblDao;

    /**
     * 获取模板消息发送分页列表
     * @param input
     * @return
     */
    @Override
    public PageInfo<MsgRecordVo> getMsgRecordPage(GetMsgRecordPageInVo input){
        return msgRecordTblDao.page(input,()->{
            LambdaQueryWrapper query = new LambdaQueryWrapper<MsgRecordTbl>()
                    .eq(StringUtils.isNotBlank(input.getAppId()),MsgRecordTbl::getAppId,input.getAppId())
                    .eq(StringUtils.isNotBlank(input.getTemplateId()),MsgRecordTbl::getTemplateId,input.getTemplateId())
                    .like(StringUtils.isNotBlank(input.getRemark()),MsgRecordTbl::getRemark,input.getRemark());
            return msgRecordTblDao.list(query,MsgRecordVo.class);
        });
    }

    /**
     * 删除模板缓存
     * @param appId
     */
    @Override
    public void delTemplateCache(String appId){
        String key = String.format(RedisKeyConstants.Ma.MA_TEMPLATE_LIST,appId);
        stringRedisTemplate.delete(key);
    }
}
