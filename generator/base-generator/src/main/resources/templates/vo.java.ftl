package ${package.Vo};

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ${table.voName} {

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
