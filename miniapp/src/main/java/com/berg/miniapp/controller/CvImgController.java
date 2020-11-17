package com.berg.miniapp.controller;

import com.berg.base.BaseController;
import com.berg.message.Result;
import com.berg.miniapp.service.miniapp.CvImgService;
import com.berg.vo.miniapp.out.MaAiCropOutVo;
import com.berg.vo.miniapp.out.MaScanQRCodeOutVo;
import com.berg.vo.miniapp.out.MaSuperresolutionOutVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/cdImg")
@Api(tags = "微信小程序图像处理")
public class CvImgController extends BaseController {

    @Autowired
    CvImgService cvImgService;

    @ApiOperation(value = "图片智能裁剪")
    @PostMapping(value = "aiCrop",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result<MaAiCropOutVo> aiCrop(@ApiParam(value = "文件",required = true) @RequestPart(value = "file") MultipartFile file){
        return getSuccessResult("请求成功",cvImgService.aiCrop(file));
    }

    @ApiOperation(value = "小程序的条码/二维码识别")
    @PostMapping(value = "scanQRCode",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result<MaScanQRCodeOutVo> scanQRCode(@ApiParam(value = "文件",required = true) @RequestPart(value = "file") MultipartFile file){
        return getSuccessResult("请求成功",cvImgService.scanQRCode(file));
    }

    @ApiOperation(value = "图片高清化")
    @PostMapping(value = "superresolution",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result<MaSuperresolutionOutVo> superresolution(@ApiParam(value = "文件",required = true) @RequestPart(value = "file") MultipartFile file){
        return getSuccessResult("请求成功",cvImgService.superresolution(file));
    }
}
