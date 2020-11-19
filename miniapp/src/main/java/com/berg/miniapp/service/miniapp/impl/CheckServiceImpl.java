package com.berg.miniapp.service.miniapp.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import com.berg.exception.FailException;
import com.berg.miniapp.service.base.BaseService;
import com.berg.miniapp.service.miniapp.CheckService;
import com.berg.vo.miniapp.in.MaMsgSecCheckInVo;
import com.berg.wx.miniapp.utils.WxMaUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class CheckServiceImpl extends BaseService implements CheckService {

    /**
     * 校验图片是否含有违法违规内容
     * @param multipartFile
     * @return
     */
    @Override
    public Boolean imgSecCheck(MultipartFile multipartFile){
        Boolean flag = true;
        String appId = getAppId();
        File file = null;
        try {
            WxMaService wxService = WxMaUtil.getService(appId);
            String originalFilename = multipartFile.getOriginalFilename();
            String[] filename = originalFilename.split("\\.");
            file = File.createTempFile(filename[0], "."+filename[1]);
            multipartFile.transferTo(file);
            flag = wxService.getSecCheckService().checkImage(file);
        }catch (Exception ex){
            throw new FailException("调用小程序校验图片接口imgSecCheck失败:"+ex.getMessage());
        }finally {
            file.deleteOnExit();
        }
        return flag;
    }

    /**
     * 校验文本是否含有违法违规内容
     * @param input
     * @return
     */
    @Override
    public Boolean msgSecCheck(MaMsgSecCheckInVo input){
        Boolean flag = true;
        String appId = getAppId();
        try {
            WxMaService wxService = WxMaUtil.getService(appId);
            flag = wxService.getSecCheckService().checkMessage(input.getContent());
        }catch (Exception ex){
            throw new FailException("调用小程序校验文本接口msgSecCheck失败:"+ex.getMessage());
        }
        return flag;
    }
}
