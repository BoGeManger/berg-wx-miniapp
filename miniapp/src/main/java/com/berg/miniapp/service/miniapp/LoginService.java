package com.berg.miniapp.service.miniapp;

import com.berg.vo.miniapp.in.MaLoginInVo;
import com.berg.vo.miniapp.in.MaRefreshInVo;
import com.berg.vo.miniapp.out.MaLoginOutVo;

public interface LoginService {

    MaLoginOutVo login(MaLoginInVo input);

    MaLoginOutVo refresh(MaRefreshInVo input);

    void logout();
}
