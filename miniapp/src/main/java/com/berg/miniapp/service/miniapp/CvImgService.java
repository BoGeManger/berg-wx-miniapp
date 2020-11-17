package com.berg.miniapp.service.miniapp;

import com.berg.vo.miniapp.out.MaAiCropOutVo;
import com.berg.vo.miniapp.out.MaScanQRCodeOutVo;
import com.berg.vo.miniapp.out.MaSuperresolutionOutVo;
import org.springframework.web.multipart.MultipartFile;

public interface CvImgService {

    MaAiCropOutVo aiCrop(MultipartFile multipartFile);

    MaScanQRCodeOutVo scanQRCode(MultipartFile multipartFile);

    MaSuperresolutionOutVo superresolution(MultipartFile multipartFile);
}
