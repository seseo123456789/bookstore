package com.green.shop.member.service;

import com.green.shop.member.vo.MemberVo;

public interface MemberService {

    // 회원가입 저장
    void insertMember(MemberVo memberVo);

    // 로그인 하기 조회
    MemberVo selectMember(MemberVo memberVo);




}

