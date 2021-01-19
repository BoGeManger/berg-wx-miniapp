package ${package.PageInVo};

import com.berg.vo.common.PageInVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
* <p>
    * ${table.comment!} 请求分页列表实体
    * </p>
*
* @author ${author}
* @since ${date}
*/
@Data
public class ${table.pageInVoName} extends PageInVo {

    //添加需要分页查询的字段
    //@ApiModelProperty(value = "字段注释")
    //String field;
}