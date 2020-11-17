package com.berg.vo.miniapp.out;

import com.berg.vo.miniapp.MaImgSizeVo;
import com.berg.vo.miniapp.MaScanQRCodeResultVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class MaScanQRCodeOutVo {

    @ApiModelProperty(value = "图片尺寸")
    MaImgSizeVo imgSize;
    @ApiModelProperty(value = "识别结果")
    List<MaScanQRCodeResultVo> codeResults;
}
