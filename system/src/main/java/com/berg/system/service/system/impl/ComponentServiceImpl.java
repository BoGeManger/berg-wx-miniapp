package com.berg.system.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.berg.dao.base.DSTransactional;
import com.berg.dao.page.PageInfo;
import com.berg.dao.system.sys.entity.ComponentTbl;
import com.berg.dao.system.sys.entity.RoleComponentTbl;
import com.berg.dao.system.sys.entity.RouterTbl;
import com.berg.dao.system.sys.service.ComponentTblDao;
import com.berg.dao.system.sys.service.RoleComponentTblDao;
import com.berg.dao.system.sys.service.RouterTblDao;
import com.berg.system.service.AbstractService;
import com.berg.system.service.system.ComponentService;
import com.berg.system.service.system.LoginService;
import com.berg.vo.common.ListVo;
import com.berg.vo.system.*;
import com.berg.vo.system.in.GetComPageInVo;
import com.berg.vo.system.in.OperatorBatchComInVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ComponentServiceImpl extends AbstractService implements ComponentService {

    @Autowired
    LoginService loginService;
    @Autowired
    ComponentTblDao componentTblDao;
    @Autowired
    RouterTblDao routerTblDao;
    @Autowired
    RoleComponentTblDao roleComponentTblDao;

    /**
     * 获取组件树形列表
     *
     * @return
     */
    @Override
    public ListVo<ComponentTreeVo> getComTree() {
        ListVo<ComponentTreeVo> result = new ListVo<>();
        List<ComponentTreeVo> list = new ArrayList<>();
        //查找根组件
        QueryWrapper queryWrapper = new QueryWrapper<ComponentTbl>()
                .eq("isdel",0)
                .eq("parent_id",0)
                .orderByAsc("no");
        List<ComponentTbl> componentTblList = componentTblDao.list(queryWrapper);
        componentTblList.forEach(item -> {
            ComponentTreeVo tree = new ComponentTreeVo();
            BeanUtils.copyProperties(item, tree);
            if(tree.getType()==0){
                LambdaQueryWrapper routerQuery = Wrappers.<RouterTbl>lambdaQuery().eq(RouterTbl::getComponentId,tree.getId());
                tree.setRouter(routerTblDao.getOneLimit(routerQuery, RouterVo.class));
            }
            //添加子集
            tree.setChildren(getComTreeChild(item.getId()));
            list.add(tree);
        });
        result.setList(list);
        return result;
    }

    /**
     * 根据用户获取组件树形列表
     * @return
     */
    public ListVo<ComponentTreeVo> getComTreeByUser(){
        Set<String> perms = loginService.getUserPerms(getUsername());
        ListVo<ComponentTreeVo> result = new ListVo<>();
        List<ComponentTreeVo> list = new ArrayList<>();
        //查找根组件
        QueryWrapper queryWrapper = new QueryWrapper<ComponentTbl>()
                .eq("isdel",0)
                .eq("parent_id",0)
                .orderByAsc("no");
        List<ComponentTbl> componentTblList = componentTblDao.list(queryWrapper);
        for (ComponentTbl item : componentTblList) {
            if(!perms.contains(item.getPerms())){
                continue;
            }
            ComponentTreeVo tree = new ComponentTreeVo();
            BeanUtils.copyProperties(item, tree);
            if(tree.getType()==0){
                LambdaQueryWrapper routerQuery = Wrappers.<RouterTbl>lambdaQuery().eq(RouterTbl::getComponentId,tree.getId());
                tree.setRouter(routerTblDao.getOneLimit(routerQuery, RouterVo.class));
            }
            //添加子集
            tree.setChildren(getComTreeChild(item.getId(),perms));
            list.add(tree);
        }
        result.setList(list);
        return result;
    }

    /**
     * 获取组件树形子集列表
     *
     * @param id
     * @return
     */
    List<ComponentTreeVo> getComTreeChild(Integer id) {
        List<ComponentTreeVo> result = new ArrayList<>();
        QueryWrapper queryWrapper = new QueryWrapper<ComponentTbl>()
                .eq("isdel",0)
                .eq("parent_id",id)
                .orderByAsc("no");
        List<ComponentTbl> componentTblList = componentTblDao.list(queryWrapper);
        componentTblList.forEach(item -> {
            ComponentTreeVo tree = new ComponentTreeVo();
            BeanUtils.copyProperties(item, tree);
            if(tree.getType()==0){
                LambdaQueryWrapper routerQuery = Wrappers.<RouterTbl>lambdaQuery().eq(RouterTbl::getComponentId,tree.getId());
                tree.setRouter(routerTblDao.getOneLimit(routerQuery, RouterVo.class));
            }
            //添加子集
            tree.setChildren(getComTreeChild(item.getId()));
            result.add(tree);
        });
        return result;
    }

    /**
     * 获取组件树形子集列表
     *
     * @param id
     * @return
     */
    List<ComponentTreeVo> getComTreeChild(Integer id,Set<String> perms) {
        List<ComponentTreeVo> result = new ArrayList<>();
        QueryWrapper queryWrapper = new QueryWrapper<ComponentTbl>()
                .eq("isdel",0)
                .eq("parent_id",id)
                .orderByAsc("no");
        List<ComponentTbl> componentTblList = componentTblDao.list(queryWrapper);
        for (ComponentTbl item : componentTblList) {
            if(!perms.contains(item.getPerms())){
                continue;
            }
            ComponentTreeVo tree = new ComponentTreeVo();
            BeanUtils.copyProperties(item, tree);
            if(tree.getType()==0){
                LambdaQueryWrapper routerQuery = Wrappers.<RouterTbl>lambdaQuery().eq(RouterTbl::getComponentId,tree.getId());
                tree.setRouter(routerTblDao.getOneLimit(routerQuery, RouterVo.class));
            }
            //添加子集
            tree.setChildren(getComTreeChild(item.getId()));
            result.add(tree);
        }
        return result;
    }

    /**
     * 获取组件分页列表
     * @param input
     * @return
     */
    @Override
    public PageInfo<ComponentVo> getComPage(GetComPageInVo input){
        return componentTblDao.page(input,()->{
            LambdaQueryWrapper query  = new LambdaQueryWrapper<ComponentTbl>()
                    .like(StringUtils.isNotBlank(input.getName()),ComponentTbl::getName,input.getName())
                    .like(StringUtils.isNotBlank(input.getPerms()),ComponentTbl::getPerms,input.getPerms())
                    .like(StringUtils.isNotBlank(input.getRemark()),ComponentTbl::getRemark,input.getRemark())
                    .eq(input.getType()==null,ComponentTbl::getType,input.getType());
            return componentTblDao.list(query,ComponentVo.class);
        });
    }

    /**
     * 获取组件
     *
     * @param id
     * @return
     */
    @Override
    public ComponentEditVo getCom(Integer id) {
        ComponentEditVo result = componentTblDao.getById(id,ComponentEditVo.class);
        LambdaQueryWrapper routerQuery = Wrappers.<RouterTbl>lambdaQuery().eq(RouterTbl::getComponentId,result.getId());
        result.setRouter(routerTblDao.getOneLimit(routerQuery, RouterEditVo.class));
        return result;
    }

    /**
     * 新增组件
     * @param input
     * @return
     */
    @DSTransactional
    @Override
    public Integer addCom(ComponentEditVo input){
        String operator = super.getUsername();
        return addOrUpdateCom(input,operator);
    }

    /**
     * 修改组件
     * @param input
     * @return
     */
    @DSTransactional
    @Override
    public Integer updateCom(ComponentEditVo input){
        String operator = super.getUsername();
        return addOrUpdateCom(input,operator);
    }

    /**
     * 批量操作组件(新增,修改,删除)
     *
     * @param input
     * @return
     */
//    @DSTransactional
//    @Override
//    public void operatorBatchCom(OperatorBatchComInVo input) {
//        String operator = super.getUsername();
//        //新增或修改组件
//        input.getComs().forEach(item -> {
//            Integer id = addOrUpdateCom(item, operator);
//            //设置子组件
//            item.getChilds().forEach(child -> {
//                setChildsComs(operator, child, id);
//            });
//        });
//        //删除组件
//        input.getDelIds().forEach(id -> {
//            delCom(id);
//        });
//    }

    /**
     * 设置子组件
     * @param operator
     * @param com
     * @param parentid
     */
    void setChildsComs(String operator, OperatorBatchComVo com, Integer parentid) {
        com.setParentId(parentid);
        Integer id = addOrUpdateCom(com, operator);
        com.getChilds().forEach(child -> {
            setChildsComs(operator, child, id);
        });
    }

    /**
     * 新增或修改组件
     *
     * @param input
     * @param operator
     * @return
     */
    Integer addOrUpdateCom(ComponentEditVo input, String operator) {
        LocalDateTime now = LocalDateTime.now();
        Boolean isAdd = input.getId() == 0 ? true : false;
        ComponentTbl componentTbl = new ComponentTbl();
        componentTbl.setId(input.getId());
        componentTbl.setParentId(input.getParentId());
        componentTbl.setName(input.getName());
        componentTbl.setPerms(input.getPerms());
        componentTbl.setRemark(input.getRemark());
        componentTbl.setType(input.getType());
        componentTbl.setNo(input.getNo());
        componentTbl.setModifyTime(now);
        componentTbl.setModifyUser(operator);
        if (isAdd) {
            componentTbl.setCreateTime(now);
            componentTbl.setCreateUser(operator);
            componentTbl.setIsdel(0);
        }
        componentTblDao.saveOrUpdateById(componentTbl);
        if(componentTbl.getType()==0){
            addOrUpdateRouter(componentTbl.getId(),input.getRouter());
        }
        return componentTbl.getId();
    }

    /**
     * 新增或修改路由
     * @param componentId
     * @param input
     */
    void addOrUpdateRouter(Integer componentId,RouterEditVo input){
        LambdaQueryWrapper routerQuery = Wrappers.<RouterTbl>lambdaQuery().eq(RouterTbl::getComponentId,componentId);
        RouterTbl routerTbl = routerTblDao.getOneLimit(routerQuery);
        if(routerTbl==null){
            routerTbl = new RouterTbl();
            routerTbl.setId(0);
            routerTbl.setComponentId(componentId);
        }
        routerTbl.setComponent(input.getComponent());
        routerTbl.setPath(input.getPath());
        routerTbl.setIcon(input.getIcon());
        routerTbl.setRedirect(input.getRedirect());
        routerTbl.setHidden(input.getHidden());
        routerTbl.setHideChildreninMenu(input.getHideChildreninMenu());
        routerTbl.setKeepAlive(input.getKeepAlive());
        routerTbl.setHiddenHeaderContent(input.getHiddenHeaderContent());
        routerTbl.setTarget(input.getTarget());
        routerTblDao.saveOrUpdateById(routerTbl);
    }

    /**
     * 删除组件
     *
     * @param id
     */
    @DSTransactional
    public void delCom(Integer id) {
        LocalDateTime now = LocalDateTime.now();
        ComponentTbl componentTbl = componentTblDao.getById(id);
        if (componentTbl != null) {
            String operator = super.getUsername();
            componentTbl.setIsdel(1);
            componentTbl.setDelTime(now);
            componentTbl.setDelUser(operator);
            componentTblDao.updateById(componentTbl);
            //删除路由信息
            if(componentTbl.getType()==0){
                LambdaQueryWrapper routerQuery = Wrappers.<RouterTbl>lambdaQuery().eq(RouterTbl::getComponentId,componentTbl.getId());
                routerTblDao.remove(routerQuery);
            }
            //作废原有授权数据
            RoleComponentTbl roleComponentTbl = new RoleComponentTbl();
            roleComponentTbl.setComId(id);
            roleComponentTbl.setIsdel(0);
            LambdaQueryWrapper queryWrapper = new LambdaQueryWrapper<RoleComponentTbl>()
                    .eq(RoleComponentTbl::getComId,id).eq(RoleComponentTbl::getIsdel,0);
            List<RoleComponentTbl> updateList = roleComponentTblDao.list(queryWrapper);
            if (updateList.size() > 0) {
                updateList.forEach(item -> {
                    item.setIsdel(1);
                    item.setDelTime(now);
                    item.setDelUser(operator);
                });
                roleComponentTblDao.updateBatchById(updateList);
            }
        }
    }
}
