package com.green.shop.member.service;

import com.green.shop.member.vo.MemberVo;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("memberService")
public class MemberServiceImpl implements MemberService {

    @Autowired
    private SqlSessionTemplate sqlSession;

    //회원가입
    @Override
    public void insertMember(MemberVo memberVo) {
        sqlSession.insert("memberMapper.insertMember", memberVo);
    }
    //로그인하기
    @Override
    public MemberVo selectMember(MemberVo memberVo) {
        return sqlSession.selectOne("memberMapper.selectMember", memberVo);
    }
}
