package ${cfg.packageService}.${cfg.serviceModule}.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.berg.dao.page.PageInfo;
import ${package.Entity}.${entity};
import ${package.Service}.${table.serviceName};
import com.berg.system.auth.JWTUtil;
import ${cfg.packageService}.${cfg.serviceModule}.${cfg.model}Service;
import ${cfg.packageVo}.${cfg.voModule}.${cfg.model}EditVo;
import ${cfg.packageVo}.${cfg.voModule}.${cfg.model}Vo;
import ${cfg.packageVo}.${cfg.voModule}.in.Get${cfg.model}PageInVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ${cfg.model}ServiceImpl implements ${cfg.model}Service {

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    ${entity}Dao ${cfg.entityName}Dao;

    /**
    * 获取${cfg.comment!}分页列表
    * @param input
    * @return
    */
    @Override
    public PageInfo<${cfg.model}Vo> get${cfg.model}Page(Get${cfg.model}PageInVo input) {
        return ${cfg.entityName}Dao.page(input,()->{
            LambdaQueryWrapper query = new LambdaQueryWrapper<${entity}>();
            return ${cfg.entityName}Dao.list(query);
        });
    }

    /**
    * 获取${cfg.comment!}
    * @param id
    * @return
    */
    @Override
    public ${cfg.model}EditVo get${cfg.model}(Integer id) {
        return ${cfg.entityName}Dao.getById(id,${cfg.model}EditVo.class);
    }

    /**
    * 新增${cfg.comment!}
    * @param input
    * @return
    */
    @Override
    public Integer add${cfg.model}(${cfg.model}EditVo input) {
        String operator = jwtUtil.getUsername();
        return addOrdUpdate${cfg.model}(input,"");
    }

    /**
    * 修改${cfg.comment!}
    * @param input
    * @return
    */
    @Override
    public Integer update${cfg.model}(${cfg.model}EditVo input) {
        String operator = jwtUtil.getUsername();
        return addOrdUpdate${cfg.model}(input,"");
    }

    /**
    * 新增或修改${cfg.comment!}
    * @param input
    * @param operator
    * @return
    */
    Integer addOrdUpdate${cfg.model}(${cfg.model}EditVo input,String operator){
        Boolean isAdd = input.getId()==0?true:false;
        LocalDateTime now = LocalDateTime.now();
        ${entity} ${cfg.entityName} = new ${entity}();
        BeanUtils.copyProperties(input,${cfg.entityName});
        if(isAdd){
            ${cfg.entityName}.setCreateTime(now);
            ${cfg.entityName}.setCreateUser(operator);
            ${cfg.entityName}.setIsdel(0);
        }
        ${cfg.entityName}.setModifyTime(now);
        ${cfg.entityName}Dao.saveOrUpdateById(${cfg.entityName});
        return ${cfg.entityName}.getId();
    }

    /**
    * 删除${cfg.comment!}
    * @param id
    */
    @Override
    public void del${cfg.model}(Integer id) {
        ${entity} ${cfg.entityName} = new ${entity}();
        ${cfg.entityName}.setId(id);
        ${cfg.entityName}.setDelTime(LocalDateTime.now());
        ${cfg.entityName}.setIsdel(1);
        ${cfg.entityName}Dao.updateById(${cfg.entityName});
    }
}
