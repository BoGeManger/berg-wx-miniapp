package com.berg.system.service.system.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.berg.dao.page.PageInfo;
import com.berg.dao.system.sys.entity.RoleComponentTbl;
import com.berg.dao.system.sys.entity.RoleTbl;
import com.berg.dao.system.sys.service.RoleComponentTblDao;
import com.berg.dao.system.sys.service.RoleTblDao;
import com.berg.system.service.system.RoleService;
import com.berg.system.auth.JWTUtil;
import com.berg.vo.system.RoleEditVo;
import com.berg.vo.system.RoleVo;
import com.berg.vo.system.in.GetRolePageInVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    JWTUtil jWTUtil;
    @Autowired
    RoleTblDao roleTblDao;
    @Autowired
    RoleComponentTblDao roleComponentTblDao;

    /**
     * 获取角色分页列表
     * @param input
     * @return
     */
    @Override
    public PageInfo<RoleVo> getRolePage(GetRolePageInVo input){
        PageInfo<RoleVo> page = roleTblDao.page(input,()->{
            QueryWrapper query = new QueryWrapper<RoleTbl>()
                    .eq("isdel",0);
            if(StringUtils.isNotBlank(input.getName())){
                query.like("name",input.getName());
            }
            query.orderByDesc("modify_time");
            return roleTblDao.list(query,RoleVo.class);
        });
        return page;
    }

    /**
     * 获取角色
     * @param id
     * @return
     */
    @Override
    public RoleEditVo getRole(Integer id){
        RoleEditVo result = new RoleEditVo();
        RoleTbl roleTbl = roleTblDao.getById(id);
        if(roleTbl!=null){
            BeanUtils.copyProperties(roleTbl,result);
            LambdaQueryWrapper query = new QueryWrapper<RoleComponentTbl>().select("com_id").lambda()
                    .eq(RoleComponentTbl::getRoleId,id)
                    .eq(RoleComponentTbl::getIsdel,0);
            result.setComIds(roleComponentTblDao.listObjs(query));
        }
        return  result;
    }

    /**
     *  新增角色
     * @param input
     * @return
     */
    @DS("system")
    @Transactional
    @Override
    public Integer addRole(RoleEditVo input){
        String operator = jWTUtil.getUsername();
        Integer roleId = addOrUpdateRole(input,operator);
        if(input.getComIds().size()>0){
            addOrUpdateRoleCom(roleId,input.getComIds(),operator);
        }
        return  roleId;
    }

    /**
     * 修改角色
     * @param input
     * @return
     */
    @DS("system")
    @Transactional
    @Override
    public Integer updateRole(RoleEditVo input){
        String operator = jWTUtil.getUsername();
        Integer roleId = addOrUpdateRole(input,operator);
        if(input.getComIds().size()>0){
            addOrUpdateRoleCom(roleId,input.getComIds(),operator);
        }
        return  roleId;
    }

    /**
     * 新增或修改角色
     * @param input
     * @param operator
     * @return
     */
    Integer addOrUpdateRole(RoleEditVo input,String operator){
        LocalDateTime now = LocalDateTime.now();
        Boolean isAdd = input.getId()==0?true:false;
        RoleTbl roleTbl = new RoleTbl();

        roleTbl.setId(input.getId());
        roleTbl.setName(input.getName());
        roleTbl.setRemark(input.getRemark());
        roleTbl.setModifyTime(now);
        roleTbl.setModifyUser(operator);
        if(isAdd){
            roleTbl.setCreateTime(now);
            roleTbl.setCreateUser(operator);
            roleTbl.setIsdel(0);
        }
        roleTblDao.saveOrUpdateById(roleTbl);
        return roleTbl.getId();
    }

    /**
     * 新增或修改授权信息
     * @param roleId
     * @param comIds
     * @param operator
     */
    void addOrUpdateRoleCom(Integer roleId, List<Integer> comIds,String operator){
        LocalDateTime now = LocalDateTime.now();
        LambdaQueryWrapper query = new LambdaQueryWrapper<RoleComponentTbl>()
                .eq(RoleComponentTbl::getRoleId,roleId)
                .eq(RoleComponentTbl::getIsdel,0);
        List<RoleComponentTbl> updateList = roleComponentTblDao.list(query);
        //作废原有数据
        if(updateList.size()>0){
            updateList.forEach(item->{
                item.setIsdel(1);
                item.setDelTime(now);
                item.setDelUser(operator);
            });
            roleComponentTblDao.updateBatchById(updateList);
        }
        List<RoleComponentTbl> addList = new ArrayList<>();
        //新增授权数据
        comIds.forEach(item->{
            RoleComponentTbl roleComponentTbl = new RoleComponentTbl();
            roleComponentTbl.setComId(item);
            roleComponentTbl.setRoleId(roleId);
            roleComponentTbl.setCreateTime(now);
            roleComponentTbl.setCreateUser(operator);
            roleComponentTbl.setIsdel(0);
            addList.add(roleComponentTbl);
        });
        roleComponentTblDao.saveBatch(addList);
    }

    /**
     * 删除角色
     * @param id
     */
    @DS("system")
    @Transactional
    @Override
    public void delRole(Integer id){
        LocalDateTime now = LocalDateTime.now();
        String operator = jWTUtil.getUsername();
        RoleTbl roleTbl = roleTblDao.getById(id);
        if(roleTbl!=null){
            roleTbl.setDelTime(now);
            roleTbl.setDelUser(operator);
            roleTbl.setIsdel(1);
            roleTblDao.updateById(roleTbl);
            //作废原有授权数据
            LambdaQueryWrapper query = new LambdaQueryWrapper<RoleComponentTbl>()
                    .eq(RoleComponentTbl::getRoleId,id)
                    .eq(RoleComponentTbl::getIsdel,0);
            List<RoleComponentTbl> updateList =  roleComponentTblDao.list(query);
            if(updateList.size()>0){
                updateList.forEach(item->{
                    item.setIsdel(1);
                    item.setDelTime(now);
                    item.setDelUser(operator);
                });
                roleComponentTblDao.updateBatchById(updateList);
            }
        }
    }
}
