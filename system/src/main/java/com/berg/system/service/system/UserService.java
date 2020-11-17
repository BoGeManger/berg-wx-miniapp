package com.berg.system.service.system;

import com.berg.dao.page.PageInfo;
import com.berg.vo.system.UserEditVo;
import com.berg.vo.system.UserVo;
import com.berg.vo.system.in.GetUserPageInVo;
import com.berg.vo.system.in.UpdatePasswordInVo;

public interface UserService {

    PageInfo<UserVo> getUserPage(GetUserPageInVo input);

    UserEditVo getUser(Integer id);

    Integer addUser(UserEditVo input);

    Integer updateUser(UserEditVo input);

    void delUser(Integer id);

    void lockUser(Integer id);

    void unlockUser(Integer id);

    void updatePassword(UpdatePasswordInVo input);
}
