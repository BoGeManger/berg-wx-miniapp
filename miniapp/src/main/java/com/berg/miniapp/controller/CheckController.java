package com.berg.miniapp.controller;

import com.berg.common.controller.AbstractController;
import com.berg.common.constant.Result;
import com.berg.miniapp.service.miniapp.CheckService;
import com.berg.vo.miniapp.in.MaMsgSecCheckInVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/check")
@Api(tags = "微信小程序内容安全")
public class CheckController extends AbstractController {

    @Autowired
    CheckService checkService;

    @ApiOperation(value = "校验图片是否含有违法违规内容")
    @PostMapping(value = "imgSecCheck",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result<Boolean> imgSecCheck(@ApiParam(value = "文件",required = true) @RequestPart(value = "file") MultipartFile file){
        return getSuccessResult("请求成功",checkService.imgSecCheck(file));
    }

    @ApiOperation(value = "校验文本是否含有违法违规内容")
    @PostMapping(value = "msgSecCheck")
    public Result<Boolean> msgSecCheck(@RequestBody @Validated MaMsgSecCheckInVo input){
        return getSuccessResult("请求成功",checkService.msgSecCheck(input));
    }
}
