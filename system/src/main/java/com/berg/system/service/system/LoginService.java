package com.berg.system.service.system;

import com.berg.vo.system.in.LoginInVo;
import com.berg.vo.system.out.LoginOutVo;

import java.util.Set;

public interface LoginService {

    LoginOutVo login(LoginInVo input);

    Set<String> getUserRoles(String userName);

    Set<String> getUserPerms(String userName);

    void logout();
}
