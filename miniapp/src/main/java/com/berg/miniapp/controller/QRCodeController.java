package com.berg.miniapp.controller;

import com.berg.common.base.BaseController;
import com.berg.common.constant.Result;
import com.berg.miniapp.service.miniapp.QRCodeService;
import com.berg.vo.miniapp.in.MaCreateQRCodeInVo;
import com.berg.vo.miniapp.in.MaQRCodeGetInVo;
import com.berg.vo.miniapp.in.MaQRCodeGetUnlimitedInVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/qrcode")
@Api(tags = "微信小程序二维码")
public class QRCodeController extends BaseController {

    @Autowired
    QRCodeService qrCodeService;

    @ApiOperation(value = "获取小程序二维码",notes = "永久有效,有数量限制")
    @PostMapping(value = "createQRCode")
    public Result<byte[]> createQRCode(@RequestBody @Validated MaCreateQRCodeInVo input){
        return getSuccessResult("请求成功",qrCodeService.createQRCode(input));
    }

    @ApiOperation(value = "获取小程序码",notes = "永久有效,有数量限制")
    @PostMapping(value = "get")
    public Result<byte[]> get(@RequestBody @Validated MaQRCodeGetInVo input){
        return getSuccessResult("请求成功",qrCodeService.get(input));
    }

    @ApiOperation(value = "获取小程序码(场景)",notes = "永久有效,有数量限制")
    @PostMapping(value = "getUnlimited")
    public Result<byte[]> getUnlimited(@RequestBody @Validated MaQRCodeGetUnlimitedInVo input){
        return getSuccessResult("请求成功",qrCodeService.getUnlimited(input));
    }
}
