package com.berg.vo.miniapp.out;

import com.berg.vo.miniapp.MaImgSizeVo;
import com.berg.vo.miniapp.MaAiCropResultVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class MaAiCropOutVo {

    @ApiModelProperty(value = "图片尺寸")
    MaImgSizeVo imgSize;
    @ApiModelProperty(value = "智能裁剪结果")
    List<MaAiCropResultVo> results;
}
