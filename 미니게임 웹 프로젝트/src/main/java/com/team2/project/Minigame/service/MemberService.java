package com.team2.project.Minigame.service;

import com.team2.project.Minigame.vo.MemberVo;

public interface MemberService {
    //주어진 memberId가 중복되는지 확인하는 메서드입니다. 반환값은 중복 여부를 나타내는 boolean 값입니다
    boolean isIdDuplicate(String memberId);

    //MemberVo 객체를 받아서 회원 가입을 처리하는 메서드입니다. 회원 가입 절차에 따라서 필요한 로직을 구현하면 됩니다.
    void signup(MemberVo member);

}