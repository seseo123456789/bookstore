package com.green.shop.member.controller;

import com.green.shop.member.service.MemberService;
import com.green.shop.member.service.MemberServiceImpl;
import com.green.shop.member.vo.MemberVo;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/member")
public class MemberController {

    @Resource(name = "memberService")
    private MemberServiceImpl memberService;

    //회원등록 저장
    @PostMapping("/insertMember")
    public String insertMember(MemberVo memberVo){

        // ----연락처 및 이메일 세팅-----
        // , 를 - 로 바꾸기 --- memberVo.getMemberTel().replace(",","-");
        memberVo.setMemberTel(memberVo.getMemberTel().replace(",","-"));
        memberVo.setMemberEmail(memberVo.getMemberEmail().replace(",",""));
        memberService.insertMember(memberVo);
        return "redirect:/item/list";
    }

    //로그인 창 열기
    @GetMapping("/loginForm")
    public String loginForm(){
        return "content/member/login";
    }

    // 로그인 하기
    @PostMapping("/login")
    public String login(MemberVo memberVo, HttpSession session){
        MemberVo loginInfo= memberService.selectMember(memberVo);
        if(loginInfo !=null){
            session.setAttribute("loginInfo", loginInfo);
        }
        return "content/member/result_login";
    }
    // 로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("loginInfo");
        return "redirect:/item/list";
    }

    //비동기 로그인(공부)
    @ResponseBody
    @PostMapping("/loginFetch")
    public String loginFetch(MemberVo memberVo, HttpSession session){
        MemberVo loginInfo= memberService.selectMember(memberVo);
        if(loginInfo !=null){
            session.setAttribute("loginInfo", loginInfo);
        }
        return loginInfo == null ? "" : loginInfo.getMemberId();
    }

    // 메뉴의 '지금,책' 소개 페이지 이동
    @GetMapping("/intro")
    public String intro(){
        return "/content/member/site_intro";
    }




}
