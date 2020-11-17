package com.berg.system.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.berg.dao.system.sys.entity.ComponentTbl;
import com.berg.dao.system.sys.entity.RoleComponentTbl;
import com.berg.dao.system.sys.service.ComponentTblDao;
import com.berg.dao.system.sys.service.RoleComponentTblDao;
import com.berg.system.service.system.ComponentService;
import com.berg.system.auth.JWTUtil;
import com.berg.vo.common.ListVo;
import com.berg.vo.system.ComponentEditVo;
import com.berg.vo.system.ComponentTreeVo;
import com.berg.vo.system.OperatorBatchComVo;
import com.berg.vo.system.in.OperatorBatchComInVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ComponentServiceImpl implements ComponentService {

    @Autowired
    JWTUtil jWTUtil;
    @Autowired
    ComponentTblDao componentTblDao;
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
            //添加子集
            tree.setChilds(getComTreeChild(item.getId()));
            list.add(tree);
        });
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
            //添加子集
            tree.setChilds(getComTreeChild(item.getId()));
            result.add(tree);
        });
        return result;
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
        return result;
    }

    /**
     * 批量操作组件(新增,修改,删除)
     *
     * @param input
     * @return
     */
    @Override
    public void operatorBatchCom(OperatorBatchComInVo input) {
        String operator = jWTUtil.getUsername();
        //新增或修改组件
        input.getComs().forEach(item -> {
            Integer id = addOrUpdateCom(item, operator);
            //设置子组件
            item.getChilds().forEach(child -> {
                setChildsComs(operator, child, id);
            });
        });
        //删除组件
        input.getDelIds().forEach(id -> {
            delCom(id);
        });
    }

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
        componentTbl.setPath(input.getPath());
        componentTbl.setIcon(input.getIcon());
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
        return componentTbl.getId();
    }

    /**
     * 删除组件
     *
     * @param id
     */
    void delCom(Integer id) {
        LocalDateTime now = LocalDateTime.now();
        ComponentTbl componentTbl = componentTblDao.getById(id);
        if (componentTbl != null) {
            String operator = jWTUtil.getUsername();
            componentTbl.setIsdel(1);
            componentTbl.setDelTime(now);
            componentTbl.setDelUser(operator);
            componentTblDao.updateById(componentTbl);
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
