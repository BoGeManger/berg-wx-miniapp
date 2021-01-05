package com.berg.system.controller;

import com.berg.common.constant.Result;
import com.berg.common.controller.AbstractController;
import com.berg.dao.page.PageInfo;
import com.berg.system.service.system.FileService;
import com.berg.vo.system.FilePathVo;
import com.berg.vo.system.FileVo;
import com.berg.vo.system.in.DelFileByNameInVo;
import com.berg.vo.system.in.GetFilePageInVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/file")
@Api(tags = "文件管理")
public class FileController extends AbstractController {

    @Autowired
    FileService fileService;

    @ApiOperation("获取文件列表")
    @GetMapping(value = "getFilePage")
    public Result<PageInfo<FileVo>> getFilePage(@Validated GetFilePageInVo input){
        return getSuccessResult("请求成功",fileService.getFilePage(input));
    }


    @ApiOperation("上传文件")
    @PostMapping(value = "uploadFile",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result<FilePathVo> uploadFile(@ApiParam(value = "文件",required = true) @RequestPart(value = "file") MultipartFile file,
                                         @ApiParam(value = "名称",required = true) @RequestParam(value = "name")String name,
                                         @ApiParam(value = "编码") @RequestParam(value = "code",required = false)String code,
                                         @ApiParam(value = "类型(0 模板文件 1 其他)") @RequestParam(value = "type",required = false,defaultValue = "1")Integer type){
        return getSuccessResult("请求成功",fileService.uploadFile(file,name,code,type));
    }

    @ApiOperation("异步上传文件")
    @PostMapping(value = "uploadFileAsync",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result<Boolean> uploadFileAsync(@ApiParam(value = "文件",required = true) @RequestPart(value = "file") MultipartFile file,
                                           @ApiParam(value = "名称",required = true) @RequestParam(value = "name")String name,
                                           @ApiParam(value = "编码") @RequestParam(value = "code",required = false)String code,
                                           @ApiParam(value = "类型(0 模板文件 1 其他)") @RequestParam(value = "type",required = false,defaultValue = "1")Integer type){
        fileService.uploadFileAsync(file,name,code,type);
        return getSuccessResult("请求成功",true);
    }

    @ApiOperation(value = "删除文件")
    @DeleteMapping(value = "delFileByName")
    public Result delFileByName(@RequestBody @Validated DelFileByNameInVo input){
        fileService.delFileByName(input.getName());
        return getSuccessResult("请求成功", null);
    }
}
