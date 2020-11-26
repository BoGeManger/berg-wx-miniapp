package com.berg.system.service.member.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.berg.dao.page.PageInfo;
import com.berg.dao.system.mb.entity.MaBindTbl;
import com.berg.dao.system.mb.entity.MemberTbl;
import com.berg.dao.system.mb.service.MaBindTblDao;
import com.berg.dao.system.mb.service.MemberTblDao;
import com.berg.system.service.member.MemberService;
import com.berg.vo.member.MaBindVo;
import com.berg.vo.member.MemberVo;
import com.berg.vo.member.in.GetMaBindPageInVo;
import com.berg.vo.member.in.GetMemberPageInVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberTblDao memberTblDao;
    @Autowired
    MaBindTblDao maBindTblDao;

    /**
     * 获取会员分页列表
     * @param input
     * @return
     */
    @Override
    public PageInfo<MemberVo> getMemberPage(GetMemberPageInVo input){
        return memberTblDao.page(input,()->{
            LambdaQueryWrapper query = new LambdaQueryWrapper<MemberTbl>().like(StringUtils.isNotBlank(input.getPhone()),MemberTbl::getPhone,input.getPhone());
            return memberTblDao.list(query,MemberVo.class);
        });
    }

    /**
     * 获取会员微信小程序绑定分页列表
     * @param input
     * @return
     */
    @Override
    public PageInfo<MaBindVo> getMaBindPage(GetMaBindPageInVo input){
        return  maBindTblDao.page(input,()->{
           LambdaQueryWrapper query = new LambdaQueryWrapper<MaBindTbl>()
                   .eq(StringUtils.isNotBlank(input.getAppId()),MaBindTbl::getAppId,input.getAppId())
                   .like(StringUtils.isNotBlank(input.getNickName()),MaBindTbl::getNickName,input.getNickName());
           return maBindTblDao.list(query,MaBindVo.class);
        });
    }
}
