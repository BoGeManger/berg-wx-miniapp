package com.berg.system.service.system;

import com.berg.dao.page.PageInfo;
import com.berg.vo.system.RoleEditVo;
import com.berg.vo.system.RoleVo;
import com.berg.vo.system.in.GetRolePageInVo;

public interface RoleService {

    PageInfo<RoleVo> getRolePage(GetRolePageInVo input);

    RoleEditVo getRole(Integer id);

    Integer addRole(RoleEditVo input);

    Integer updateRole(RoleEditVo input);

    void delRole(Integer id);
}
