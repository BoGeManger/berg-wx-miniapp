package com.berg.miniapp.service.miniapp.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import com.berg.exception.FailException;
import com.berg.miniapp.auth.JWTUtil;
import com.berg.miniapp.service.base.BaseService;
import com.berg.miniapp.service.miniapp.CvImgService;
import com.berg.vo.miniapp.out.MaAiCropOutVo;
import com.berg.vo.miniapp.out.MaScanQRCodeOutVo;
import com.berg.vo.miniapp.out.MaSuperresolutionOutVo;
import com.berg.wx.miniapp.utils.WxMaUtil;
import me.chanjar.weixin.common.bean.imgproc.WxImgProcAiCropResult;
import me.chanjar.weixin.common.bean.imgproc.WxImgProcQrCodeResult;
import me.chanjar.weixin.common.bean.imgproc.WxImgProcSuperResolutionResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class CvImgServiceImpl extends BaseService implements CvImgService {

    /**
     * 图片智能裁剪
     * @param multipartFile
     */
    @Override
    public MaAiCropOutVo aiCrop(MultipartFile multipartFile){
        MaAiCropOutVo result = new MaAiCropOutVo();
        String appId = getAppId();
        File file = null;
        try {
            WxMaService wxService = WxMaUtil.getService(appId);
            String originalFilename = multipartFile.getOriginalFilename();
            String[] filename = originalFilename.split("\\.");
            file = File.createTempFile(filename[0], "."+filename[1]);
            multipartFile.transferTo(file);
            WxImgProcAiCropResult wxImgProcAiCropResult = wxService.getImgProcService().aiCrop(file);
            BeanUtils.copyProperties(wxImgProcAiCropResult,result);
        }catch (Exception ex){
            throw new FailException("调用小程序图片智能裁剪接口aiCrop失败:"+ex.getMessage());
        }finally {
            file.deleteOnExit();
        }
        return result;
    }

    /**
     * 小程序的条码/二维码识别
     * @param multipartFile
     * @return
     */
    @Override
    public MaScanQRCodeOutVo scanQRCode(MultipartFile multipartFile){
        MaScanQRCodeOutVo result = new MaScanQRCodeOutVo();
        String appId = getAppId();
        File file = null;
        try {
            WxMaService wxService = WxMaUtil.getService(appId);
            String originalFilename = multipartFile.getOriginalFilename();
            String[] filename = originalFilename.split("\\.");
            file = File.createTempFile(filename[0], "."+filename[1]);
            multipartFile.transferTo(file);
            WxImgProcQrCodeResult wxImgProcQrCodeResult = wxService.getImgProcService().qrCode(file);
            BeanUtils.copyProperties(wxImgProcQrCodeResult,result);
        }catch (Exception ex){
            throw new FailException("调用小程序的条码/二维码识别接口scanQRCode失败:"+ex.getMessage());
        }finally {
            file.deleteOnExit();
        }
        return result;
    }

    /**
     * 图片高清化
     * @param multipartFile
     * @return
     */
    @Override
    public MaSuperresolutionOutVo superresolution(MultipartFile multipartFile){
        MaSuperresolutionOutVo result = new MaSuperresolutionOutVo();
        String appId = getAppId();
        File file = null;
        try {
            WxMaService wxService = WxMaUtil.getService(appId);
            String originalFilename = multipartFile.getOriginalFilename();
            String[] filename = originalFilename.split("\\.");
            file = File.createTempFile(filename[0], "."+filename[1]);
            multipartFile.transferTo(file);
            WxImgProcSuperResolutionResult wxImgProcSuperResolutionResult = wxService.getImgProcService().superResolution(file);
            result.setMediaId(wxImgProcSuperResolutionResult.getMediaId());
        }catch (Exception ex){
            throw new FailException("调用小程序图片高清化接口superresolution失败:"+ex.getMessage());
        }finally {
            file.deleteOnExit();
        }
        return result;
    }
}
