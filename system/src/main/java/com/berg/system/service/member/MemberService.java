package com.berg.system.service.member;

import com.berg.dao.page.PageInfo;
import com.berg.vo.member.MaBindVo;
import com.berg.vo.member.MemberVo;
import com.berg.vo.member.in.GetMaBindPageInVo;
import com.berg.vo.member.in.GetMemberPageInVo;

public interface MemberService {

    PageInfo<MemberVo> getMemberPage(GetMemberPageInVo input);

    PageInfo<MaBindVo> getMaBindPage(GetMaBindPageInVo input);
}
