package com.berg.system.controller.member;


import com.berg.common.controller.AbstractController;
import com.berg.dao.page.PageInfo;
import com.berg.common.constant.Result;
import com.berg.system.service.member.MemberService;
import com.berg.vo.member.MaBindVo;
import com.berg.vo.member.MemberVo;
import com.berg.vo.member.in.GetMaBindPageInVo;
import com.berg.vo.member.in.GetMemberPageInVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@Api(tags = "会员管理")
public class MemberController extends AbstractController {

    @Autowired
    MemberService memberService;

    @ApiOperation("获取会员分页列表")
    @GetMapping(value = "getMemberPage")
    public Result<PageInfo<MemberVo>> getMemberPage(@Validated GetMemberPageInVo input){
        return success("请求成功",()->memberService.getMemberPage(input));
    }

    @ApiOperation("获取会员微信小程序绑定分页列表")
    @GetMapping(value = "getMaBindPage")
    public Result<PageInfo<MaBindVo>> getMaBindPage(@Validated GetMaBindPageInVo input){
        return success("请求成功",()->memberService.getMaBindPage(input));
    }
}
