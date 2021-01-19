package ${package.ServiceImpl};

import ${package.Entity}.${entity};
import ${package.Service}.${table.serviceName};
import ${package.EditVo}.${table.editVoName};
import ${package.Vo}.${table.voName};
import ${package.PageInVo}.${table.pageInVoName};
import ${package.Dao}.${table.daoName};
import com.berg.dao.page.PageInfo;
import com.berg.system.service.AbstractService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
* <p>
    * ${table.comment!} 业务服务
    * </p>
*
* @author ${author}
* @since ${date}
*/
@Service
public class ${table.serviceImplName} extends AbstractService implements ${table.serviceName} {

    @Autowired
    ${table.daoName}  ${table.daoNmaeVariable};

    /**
    * 获取${table.comment!}分页列表
    * @param input
    * @return
    */
    @Override
    public PageInfo<${table.voName}> get${table.entityName}Page(${table.pageInVoName} input) {
        return ${table.daoNmaeVariable}.page(input,()->{
            LambdaQueryWrapper query = new LambdaQueryWrapper<${entity}>();
            return ${table.daoNmaeVariable}.list(query);
        });
    }

    /**
    * 获取${table.comment!}
    * @param id
    * @return
    */
    @Override
    public ${table.editVoName} get${table.entityName}(Integer id) {
        return ${table.daoNmaeVariable}.getById(id,${table.editVoName}.class);
    }

    /**
    * 新增${table.comment!}
    * @param input
    * @return
    */
    @Override
    public Integer add${table.entityName}(${table.editVoName} input) {
        String operator = super.getUsername();
        return addOrdUpdate${table.entityName}(input,"");
    }

    /**
    * 修改${table.comment!}
    * @param input
    * @return
    */
    @Override
    public Integer update${table.entityName}(${table.entityName} input) {
        String operator = super.getUsername();
        return addOrdUpdate${table.entityName}(input,"");
    }

    /**
    * 新增或修改${table.comment!}
    * @param input
    * @param operator
    * @return
    */
    Integer addOrdUpdate${table.entityName}(${table.editVoName} input,String operator){
        Boolean isAdd = input.getId()==0?true:false;
        LocalDateTime now = LocalDateTime.now();
        ${entity} ${table.entityNameVariable} = new ${entity}();
        BeanUtils.copyProperties(input,${table.entityNameVariable});
        if(isAdd){
            ${table.entityNameVariable}.setCreateTime(now);
            ${table.entityNameVariable}.setCreateUser(operator);
            ${table.entityNameVariable}.setIsdel(0);
        }
        ${table.entityNameVariable}.setModifyTime(now);
        ${table.daoNmaeVariable}.saveOrUpdateById(${table.entityNameVariable});
        return ${table.entityNameVariable}.getId();
    }

    /**
    * 删除${table.comment!}
    * @param id
    */
    @Override
    public void del${table.entityName}(Integer id) {
        ${entity} ${table.entityNameVariable} = new ${entity}();
        ${table.entityNameVariable}.setId(id);
        ${table.entityNameVariable}.setDelTime(LocalDateTime.now());
        ${table.entityNameVariable}.setIsdel(1);
        ${table.daoNmaeVariable}.updateById(${table.entityNameVariable});
    }
}
