package com.team2.project.Minigame.dao;

import com.team2.project.Minigame.vo.MemberVo;


public interface MemberDAO {
    void insertMember(MemberVo member);
    MemberVo getMemberById(String memberId);
    boolean isIdDuplicate(String memberId);
    boolean isPasswordCorrect(String memberId, String password);

    // 회원정보 수정 메서드
    void updateMypage(MemberVo member);

    // 이미지 추가 메서드
    void addProfileImage(String memberId, byte[] image);

    // 이미지 조회 메서드
    byte[] getProfileImage(String memberId);

    // 이미지 업데이트 메서드
    void updateProfileImage(String memberId, byte[] image);
    // 다른 데이터 액세스 메서드 구현
}