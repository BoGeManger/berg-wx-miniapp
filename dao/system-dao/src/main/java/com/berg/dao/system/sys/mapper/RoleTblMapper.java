package com.berg.dao.system.sys.mapper;

import com.berg.dao.system.sys.entity.RoleTbl;
import com.berg.dao.base.BaseMapper;

import java.util.List;

/**
 * <p>
 * 系统角色表 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2020-06-05
 */
public interface RoleTblMapper extends BaseMapper<RoleTbl> {
    List<String> listUserRoleName(String userName);
}
