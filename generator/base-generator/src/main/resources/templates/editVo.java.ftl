package ${package.EditVo};

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
* <p>
    * ${table.comment!} 编辑实体
    * </p>
*
* @author ${author}
* @since ${date}
*/
@Data
public class ${table.editVoName} {

<#list table.fields as field>
    <#if field.keyFlag><#-- 主键校验 -->
    @Min(value = 0,message = "${field.comment}不能小于0")
    </#if>
    <#if field.required==true><#-- 非空校验 -->
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
