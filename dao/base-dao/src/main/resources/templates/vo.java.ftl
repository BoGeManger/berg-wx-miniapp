package ${cfg.packageVo}.${cfg.voModule};

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import lombok.Data;

@Data
public class ${cfg.model}Vo {

<#list table.fields as field>
    <#if field.propertyType == "LocalDateTime">
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    </#if>
    <#if field.comment!?length gt 0>
    @ApiModelProperty(value = "${field.comment}")
    </#if>
    ${field.propertyType} ${field.propertyName};
</#list>

}
