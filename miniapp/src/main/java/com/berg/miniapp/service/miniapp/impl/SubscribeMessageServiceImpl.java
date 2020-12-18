package com.berg.miniapp.service.miniapp.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.WxMaSubscribeService;
import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage;
import cn.hutool.json.JSONUtil;
import com.berg.common.constant.RedisKeyConstants;
import com.berg.common.exception.FailException;
import com.berg.miniapp.service.base.BaseService;
import com.berg.miniapp.service.miniapp.SubscribeMessageService;
import com.berg.vo.miniapp.MaTemplateInfoVo;
import com.berg.vo.miniapp.in.MaSendTemplateInVo;
import com.berg.wx.miniapp.utils.WxMaUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class SubscribeMessageServiceImpl extends BaseService implements SubscribeMessageService {

    @Resource
    RedisTemplate<String, MaTemplateInfoVo> redisTemplate;

    @Autowired
    SubscribeMessageAsyncTask subscribeMessageAsyncTask;

    /**
     * 获取当前帐号下的个人模板列表
     *
     * @return
     */
    @Override
    public List<MaTemplateInfoVo> getTemplateList() {
        String appId = getAppId();
        String key = String.format(RedisKeyConstants.Ma.MA_TEMPLATE_LIST,appId);
        List<MaTemplateInfoVo> list = redisTemplate.opsForList().range(key, 0, -1);
        if (list.size() == 0) {
            try {
                WxMaService wxService = WxMaUtil.getService(appId);
                List<WxMaSubscribeService.TemplateInfo> templateList = wxService.getSubscribeService().getTemplateList();
                templateList.forEach(item -> {
                    MaTemplateInfoVo maTemplateInfoVo = new MaTemplateInfoVo();
                    BeanUtils.copyProperties(item, maTemplateInfoVo);
                    list.add(maTemplateInfoVo);
                });
                redisTemplate.opsForList().leftPushAll(key,list);
                redisTemplate.expire(key,30, TimeUnit.DAYS);
            } catch (Exception ex) {
                throw new FailException("调用小程序获取小程序码接口get失败:" + ex.getMessage());
            }
        }
        return list;
    }

    /**
     * 发送订阅消息
     *
     * @param input
     */
    @Override
    public void send(MaSendTemplateInVo input) {
        String appId = jWTUtil.getAppId();
        String openId = jWTUtil.getOpenId();
        try {
            WxMaService wxService = WxMaUtil.getService(appId);
            WxMaSubscribeMessage wxMaSubscribeMessage = new WxMaSubscribeMessage();
            wxMaSubscribeMessage.setToUser(openId);
            wxMaSubscribeMessage.setTemplateId(input.getTemplateId());
            wxMaSubscribeMessage.setPage(input.getPage());
            wxMaSubscribeMessage.setMiniprogramState(input.getMiniprogramState());
            wxMaSubscribeMessage.setLang(input.getLang());
            List<cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage.Data> data = new ArrayList<>();
            input.getData().forEach(item -> {
                data.add(new cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage.Data(item.getName(), item.getValue()));
            });
            wxMaSubscribeMessage.setData(data);
            wxService.getMsgService().sendSubscribeMsg(wxMaSubscribeMessage);
            //新增消息记录
            subscribeMessageAsyncTask.addMsgRecord(appId,openId, JSONUtil.toJsonStr(wxMaSubscribeMessage.getData()),wxMaSubscribeMessage.getTemplateId()
                    ,wxMaSubscribeMessage.getPage(),wxMaSubscribeMessage.getMiniprogramState(),wxMaSubscribeMessage.getLang(),input.getRemark());
        } catch (Exception ex) {
            throw new FailException("调用小程序获取小程序码接口get失败:" + ex.getMessage());
        }
    }
}
