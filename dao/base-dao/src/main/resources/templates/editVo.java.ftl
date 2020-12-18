package ${cfg.packageVo}.${cfg.voModule};

import java.time.LocalDateTime;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ${cfg.model}EditVo {

<#list table.fields as field>
    <#if field.keyFlag><#-- 主键校验 -->
    @Min(value = 0,message = "${field.comment}不能小于0")
    </#if>
    <#if field.customMap["Null"]=="NO"><#-- 非空校验 -->
     <#if field.propertyType=="String">
    @NotBlank(message = "${field.comment}不能为空")
     <#else>
    @NotNull(message = "${field.comment}不能为空")
     </#if>
    </#if>
    <#if field.propertyType=="String"><#-- 长度校验 -->
    <#assign size=field.type?replace("varchar(","")?replace(")","")/>
    @Size(max = ${size}, message = "${field.comment}长度不能超过${size}个字符")
    </#if>
    @ApiModelProperty(value = "${field.comment}")
    ${field.propertyType} ${field.propertyName};
</#list>

}
