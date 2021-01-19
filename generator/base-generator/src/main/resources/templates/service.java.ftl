package ${package.Service};

import com.berg.dao.page.PageInfo;
import ${package.EditVo}.${table.editVoName};
import ${package.Vo}.${table.voName};
import ${package.PageInVo}.${table.pageInVoName};

/**
* <p>
    * ${table.comment!} 业务服务接口
    * </p>
*
* @author ${author}
* @since ${date}
*/
public interface ${table.serviceName} {

    PageInfo<${table.voName}> get${table.entityName}Page(${table.pageInVoName} input);

    ${table.editVoName} get${table.entityName}(Integer id);

    Integer add${table.entityName}(${table.editVoName} input);

    Integer update${table.entityName}(${table.editVoName} input);

    void del${table.entityName}(Integer id);
}
