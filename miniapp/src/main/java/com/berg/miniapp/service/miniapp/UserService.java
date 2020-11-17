package com.berg.miniapp.service.miniapp;

import com.berg.vo.miniapp.in.MaSetUserInfoInVo;
import com.berg.vo.miniapp.in.MaUserPhoneInVo;
import com.berg.vo.miniapp.out.MaUserPhoneOutVo;

public interface UserService {

    MaUserPhoneOutVo phone(MaUserPhoneInVo input);

    void setUserInfo(MaSetUserInfoInVo input);
}
