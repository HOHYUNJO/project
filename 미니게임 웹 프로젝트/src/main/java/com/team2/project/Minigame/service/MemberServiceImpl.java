package com.team2.project.Minigame.service;

import com.team2.project.Minigame.dao.MemberDAO;
import com.team2.project.Minigame.vo.MemberVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberDAO memberDAO;

    @Autowired
    public MemberServiceImpl(MemberDAO memberDAO) {
        this.memberDAO = memberDAO;
    }


     // 아이디 중복 여부를 확인합니다.
     //@param memberId 확인할 아이디
     // @return 중복 여부 (true: 중복된 아이디, false: 중복되지 않은 아이디)
    @Override
    public boolean isIdDuplicate(String memberId) {
        return memberDAO.isIdDuplicate(memberId);
    }


    //회원 가입을 처리합니다
    //@param member 가입할 회원 정보
    @Override
    public void signup(MemberVo member) {
        memberDAO.insertMember(member);
    }

    // 다른 서비스 메서드 구현
}