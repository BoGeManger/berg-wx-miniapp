package com.berg.system.service.system.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.berg.dao.base.DSTransactional;
import com.berg.dao.system.sys.entity.UserComponentTbl;
import com.berg.dao.system.sys.service.UserComponentTblDao;
import com.berg.dao.system.sys.entity.UserRoleTbl;
import com.berg.dao.system.sys.entity.UserTbl;
import com.berg.dao.system.sys.service.UserRoleTblDao;
import com.berg.dao.system.sys.service.UserTblDao;
import com.berg.dao.page.PageInfo;
import com.berg.constant.RedisKeyConstants;
import com.berg.exception.UserFriendException;
import com.berg.system.service.system.UserService;
import com.berg.system.auth.JWTUtil;
import com.berg.vo.system.UserEditVo;
import com.berg.vo.system.UserVo;
import com.berg.vo.system.in.GetUserPageInVo;
import com.berg.vo.system.in.UpdatePasswordInVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    JWTUtil jWTUtil;
    @Autowired
    UserTblDao userTblDao;
    @Autowired
    UserRoleTblDao userRoleTblDao;
    @Autowired
    UserComponentTblDao userComponentTblDao;
    @Autowired
    RedisTemplate<String, String> stringTemplate;

    //默认密码为123456
    final String DEFAULT_PASSWORD = "123456";

    /**
     * 获取用户分页列表
     *
     * @param input
     * @return
     */
    @Override
    public PageInfo<UserVo> getUserPage(GetUserPageInVo input) {
        PageInfo<UserVo> page = userTblDao.page(input,()->{
            QueryWrapper query = new QueryWrapper<UserTbl>().eq("isdel", 0);
            if (StringUtils.isNotBlank(input.getUsername())) {
                query.like("username", input.getUsername());
            }
            query.orderByDesc("modify_time");
            return userTblDao.list(query,UserVo.class);
        });
        return page;
    }

    /**
     * 编辑页面查询
     *
     * @param id
     * @return
     */
    @Override
    public UserEditVo getUser(Integer id) {
        UserEditVo result = userTblDao.getById(id,UserEditVo.class);
        if(result!=null){
            LambdaQueryWrapper roleQuery = new QueryWrapper<UserRoleTbl>().select("role_id").lambda()
                    .eq(UserRoleTbl::getIsdel, 0)
                    .eq(UserRoleTbl::getUserId, id);
            result.setRoldIds(userRoleTblDao.listObjs(roleQuery));
            LambdaQueryWrapper comQuery = new QueryWrapper<UserComponentTbl>().select("com_id").lambda()
                    .eq(UserComponentTbl::getIsdel, 0)
                    .eq(UserComponentTbl::getUserId, id);
            result.setComIds(userComponentTblDao.listObjs(comQuery));
        }
        return result;
    }

    /**
     * 新增用户
     *
     * @param input
     * @return
     */
    @DSTransactional
    @Override
    public Integer addUser(UserEditVo input) {
        String operator = jWTUtil.getUsername();
        Integer userId = addOrUpdateUser(input, operator);
        if (input.getRoldIds().size() > 0) {
            addOrUpdateUserRole(userId, input.getRoldIds(), operator);
        }
        if(input.getComIds().size() > 0){
            addOrUpdateUserCom(userId,input.getComIds(),operator);
        }
        return userId;
    }

    /**
     * 修改用户
     *
     * @param input
     * @return
     */
    @DSTransactional
    @Override
    public Integer updateUser(UserEditVo input) {
        String operator = jWTUtil.getUsername();
        Integer userId = addOrUpdateUser(input, operator);
        if (input.getRoldIds().size() > 0) {
            addOrUpdateUserRole(userId, input.getRoldIds(), operator);
        }
        if(input.getComIds().size() > 0){
            addOrUpdateUserCom(userId,input.getComIds(),operator);
        }
        delUserRoleCache(input.getUsername());
        return userId;
    }

    /**
     * 删除用户
     *
     * @param id
     */
    @DSTransactional
    @Override
    public void delUser(Integer id) {
        UserTbl userTbl = userTblDao.getById(id);
        if (userTbl != null) {
            if (userTbl.getIsdel().equals(1)) {
                throw new UserFriendException("该用户已被删除");
            }
            LocalDateTime now = LocalDateTime.now();
            String operator = jWTUtil.getUsername();
            userTbl.setIsdel(1);
            userTbl.setDelTime(now);
            userTbl.setDelUser(operator);
            userTblDao.updateById(userTbl);
            delUserRoleCache(userTbl.getUsername());
            //作废原有数据
            LambdaQueryWrapper roleQuery = new LambdaQueryWrapper<UserRoleTbl>()
                    .eq(UserRoleTbl::getIsdel, 0)
                    .eq(UserRoleTbl::getUserId, userTbl.getId());
            List<UserRoleTbl> roleUpdateList = userRoleTblDao.list(roleQuery);
            if (roleUpdateList.size() > 0) {
                roleUpdateList.forEach(item -> {
                    item.setIsdel(1);
                    item.setDelTime(now);
                    item.setDelUser(operator);
                });
                userRoleTblDao.updateBatchById(roleUpdateList);
            }
            //作废原有数据
            LambdaQueryWrapper comQuery = new LambdaQueryWrapper<UserComponentTbl>()
                    .eq(UserComponentTbl::getIsdel, 0)
                    .eq(UserComponentTbl::getUserId, userTbl.getId());
            List<UserComponentTbl> comUpdateList = userComponentTblDao.list(comQuery);
            if (comUpdateList.size() > 0) {
                comUpdateList.forEach(item -> {
                    item.setIsdel(1);
                    item.setDelTime(now);
                    item.setDelUser(operator);
                });
                userComponentTblDao.updateBatchById(comUpdateList);
            }
        }
    }

    /**
     * 锁定用户
     *
     * @param id
     */
    @Override
    public void lockUser(Integer id) {
        UserTbl userTbl = userTblDao.getById(id);
        if (userTbl != null) {
            if (userTbl.getIslock().equals(1)) {
                throw new UserFriendException("该用户已被锁定");
            }
            LocalDateTime now = LocalDateTime.now();
            String operator = jWTUtil.getUsername();
            userTbl.setIslock(1);
            userTbl.setLockTime(now);
            userTbl.setLockUser(operator);
            userTblDao.updateById(userTbl);
        }
    }

    /**
     * 解锁用户
     *
     * @param id
     */
    @Override
    public void unlockUser(Integer id) {
        UserTbl userTbl = userTblDao.getById(id);
        if (userTbl != null) {
            if (userTbl.getIsdel().equals(1)) {
                throw new UserFriendException("该用户已被删除");
            }
            LocalDateTime now = LocalDateTime.now();
            String operator = jWTUtil.getUsername();
            userTbl.setIslock(0);
            userTbl.setLockTime(now);
            userTbl.setLockUser(operator);
            userTblDao.updateById(userTbl);
        }
    }

    /**
     * 更新用户密码
     *
     * @param input
     */
    @Override
    public void updatePassword(UpdatePasswordInVo input) {
        UserTbl userTbl = userTblDao.getById(input.getId());
        if (userTbl != null) {
            if (userTbl.getIslock().equals(1)) {
                throw new UserFriendException("该用户已被锁定");
            }
            if (userTbl.getIsdel().equals(1)) {
                throw new UserFriendException("该用户已被删除");
            }
            LocalDateTime now = LocalDateTime.now();
            String operator = jWTUtil.getUsername();
            userTbl.setPassword(DigestUtils.md5DigestAsHex(input.getPassword().getBytes()));
            userTbl.setModifyTime(now);
            userTbl.setModifyUser(operator);
            userTblDao.updateById(userTbl);
        }
    }


    /**
     * 新增或修改用户
     */
    Integer addOrUpdateUser(UserEditVo input, String operator) {
        input.setUsername(StringUtils.lowerCase(input.getUsername()));
        //校验用户名是否存在
        if (!checkUsername(input.getId(), input.getUsername())) {
            throw new UserFriendException("用户名已存在");
        }
        LocalDateTime now = LocalDateTime.now();
        Boolean isAdd = input.getId() == 0 ? true : false;
        UserTbl userTbl = new UserTbl();
        userTbl.setId(input.getId());
        userTbl.setRealname(input.getRealname());
        userTbl.setOrganizationId(input.getOrganizationId());
        userTbl.setOrganizationName(input.getOrganizationName());
        userTbl.setModifyTime(now);
        userTbl.setModifyUser(operator);
        if (isAdd) {
            userTbl.setPassword(DigestUtils.md5DigestAsHex(DEFAULT_PASSWORD.getBytes()));
            userTbl.setUsername(input.getUsername());
            userTbl.setLastLoginTime(now);
            userTbl.setIslock(0);
            userTbl.setCreateTime(now);
            userTbl.setCreateUser(operator);
            userTbl.setIsdel(0);
        }
        userTblDao.saveOrUpdateById(userTbl);
        return userTbl.getId();
    }

    /**
     * 校验用户名是否存在
     *
     * @param username
     * @return
     */
    Boolean checkUsername(Integer id, String username) {
        Boolean flag = false;
        LambdaQueryWrapper query = new LambdaQueryWrapper<UserTbl>()
                .eq(UserTbl::getUsername, username)
                .eq(UserTbl::getIsdel, 0).ne(UserTbl::getId, id);
        UserTbl userTbl = userTblDao.getOne(query);
        if (userTbl == null) {
            flag = true;
        }
        return flag;
    }

    /**
     * 新增或修改用户角色信息
     *
     * @param userId
     * @param roldIds
     * @param operator
     */
    void addOrUpdateUserRole(Integer userId, List<Integer> roldIds, String operator) {
        LocalDateTime now = LocalDateTime.now();
        LambdaQueryWrapper query = new LambdaQueryWrapper<UserRoleTbl>()
                .eq(UserRoleTbl::getIsdel,0)
                .eq(UserRoleTbl::getUserId,userId);
        List<UserRoleTbl> updateList = userRoleTblDao.list(query);
        //作废原有数据
        if (updateList.size() > 0) {
            updateList.forEach(item -> {
                item.setIsdel(1);
                item.setDelTime(now);
                item.setDelUser(operator);
            });
            userRoleTblDao.updateBatchById(updateList);
        }
        List<UserRoleTbl> addList = new ArrayList<>();
        //新增授权数据
        roldIds.forEach(item -> {
            UserRoleTbl sysUserRoleTbl = new UserRoleTbl();
            sysUserRoleTbl.setRoleId(item);
            sysUserRoleTbl.setUserId(userId);
            sysUserRoleTbl.setCreateTime(now);
            sysUserRoleTbl.setCreateUser(operator);
            sysUserRoleTbl.setIsdel(0);
            addList.add(sysUserRoleTbl);
        });
        userRoleTblDao.saveBatch(addList);
    }

    /**
     * 新增或修改用户授权信息
     *
     * @param userId
     * @param comIds
     * @param operator
     */
    void addOrUpdateUserCom(Integer userId, List<Integer> comIds, String operator) {
        LocalDateTime now = LocalDateTime.now();
        LambdaQueryWrapper query = new LambdaQueryWrapper<UserComponentTbl>()
                .eq(UserComponentTbl::getIsdel,0)
                .eq(UserComponentTbl::getUserId,userId);
        List<UserComponentTbl> updateList = userComponentTblDao.list(query);
        //作废原有数据
        if (updateList.size() > 0) {
            updateList.forEach(item -> {
                item.setIsdel(1);
                item.setDelTime(now);
                item.setDelUser(operator);
            });
            userComponentTblDao.updateBatchById(updateList);
        }
        List<UserComponentTbl> addList = new ArrayList<>();
        //新增授权数据
        comIds.forEach(item -> {
            UserComponentTbl userComponentTbl = new UserComponentTbl();
            userComponentTbl.setComId(item);
            userComponentTbl.setUserId(userId);
            userComponentTbl.setCreateTime(now);
            userComponentTbl.setCreateUser(operator);
            userComponentTbl.setIsdel(0);
            addList.add(userComponentTbl);
        });
        userComponentTblDao.saveBatch(addList);
    }

    /**
     * 删除用户权限缓存
     *
     * @param userName
     */
    void delUserRoleCache(String userName) {
        //用户权限
        String permsKey = String.format(RedisKeyConstants.System.USER_PERMS, userName);
        stringTemplate.delete(permsKey);
        //用户角色
        String roleKey = String.format(RedisKeyConstants.System.USER_ROLE, userName);
        stringTemplate.delete(roleKey);
    }
}
