package com.berg.vo.system;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrganizationTreeVo {

    @ApiModelProperty(value = "组织id")
    Integer id;
    @ApiModelProperty(value = "父组织id")
    Integer parentId;
    @ApiModelProperty(value = "组织名称")
    String name;
    @ApiModelProperty(value = "组织描述")
    String remark;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    LocalDateTime createTime;
    @ApiModelProperty(value = "创建人")
    String createUser;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    LocalDateTime modifyTime;
    @ApiModelProperty(value = "更新人")
    String modifyUser;
    @ApiModelProperty(value = "子组织集合")
    List<OrganizationTreeVo> childs;
}
