package com.berg.miniapp.service.miniapp.impl;
import java.time.LocalDateTime;
import java.util.UUID;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.berg.dao.system.mb.entity.MaBindTbl;
import com.berg.dao.system.mb.entity.MemberTbl;
import com.berg.dao.system.mb.service.MaBindTblDao;
import com.berg.dao.system.mb.service.MemberTblDao;
import com.berg.vo.miniapp.in.MaSetUserInfoInVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class UserAsyncTask {

    @Autowired
    MaBindTblDao maBindTblDao;
    @Autowired
    MemberTblDao memberTblDao;

    /**
     * 新增会员并更新绑定会员id
     * @param appId
     * @param openId
     * @param phone
     */
    @Async
    public void addMemberAndUpdateBindMa(String appId,String openId,String phone){
        MemberTbl memberTbl =memberTblDao.getOneLimit(new LambdaQueryWrapper<MemberTbl>().eq(MemberTbl::getPhone,phone));
        if(memberTbl==null){
            memberTbl = new MemberTbl();
            LocalDateTime now =LocalDateTime.now();
            memberTbl.setId(UUID.randomUUID().toString());
            memberTbl.setPhone(phone);
            memberTbl.setCreateTime(now);
            memberTbl.setModifyTime(now);
            memberTblDao.save(memberTbl);

            LambdaQueryWrapper query = new LambdaQueryWrapper<MaBindTbl>()
                    .eq(MaBindTbl::getAppId, appId)
                    .eq(MaBindTbl::getOpenId, openId);
            MaBindTbl maBindTbl = new MaBindTbl();
            maBindTbl.setMemberId(memberTbl.getId());
            maBindTbl.setModifyTime(now);
            maBindTblDao.update(maBindTbl, query);
        }
    }

    /**
     * 更新绑定会员信息
     * @param appId
     * @param openId
     * @param input
     */
    @Async
    public void updateMaBindByUserInfo(String appId, String openId, MaSetUserInfoInVo input){
        LambdaQueryWrapper query = new LambdaQueryWrapper<MaBindTbl>()
                .eq(MaBindTbl::getAppId, appId)
                .eq(MaBindTbl::getOpenId, openId);
        MaBindTbl maBindTbl = new MaBindTbl();
        BeanUtils.copyProperties(input,maBindTbl);
        maBindTbl.setModifyTime(LocalDateTime.now());
        maBindTblDao.update(maBindTbl, query);
    }
}
