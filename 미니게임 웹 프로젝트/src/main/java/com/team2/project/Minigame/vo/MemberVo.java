package com.team2.project.Minigame.vo;

import java.time.LocalDate;

public class MemberVo {
    private String memberId;
    private String memberName;
    private String memberNick;
    private String memberPassword;
    private String memberEmail;
    private LocalDate memberRegDate;
    private String isAdmin;
    private String confirmPass;

    // LONGBLOB 타입의 프로필 사진을 바이트 배열로 저장하는 필드 추가
    private byte[] profileImage;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberNick() {
        return memberNick;
    }

    public void setMemberNick(String memberNick) {
        this.memberNick = memberNick;
    }

    public String getMemberPassword() {
        return memberPassword;
    }

    public void setMemberPassword(String memberPassword) {
        this.memberPassword = memberPassword;
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public void setMemberEmail(String memberEmail) {
        this.memberEmail = memberEmail;
    }

    public LocalDate getMemberRegDate() {
        return memberRegDate;
    }

    public void setMemberRegDate(LocalDate memberRegDate) {
        this.memberRegDate = memberRegDate;
    }

    public String getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getConfirmPass() {
        return confirmPass;
    }

    public void setConfirmPass(String confirmPass) {
        this.confirmPass = confirmPass;
    }

    // 프로필 사진의 바이트 배열을 가져오는 메서드
    public byte[] getProfileImage() {
        return profileImage;
    }

    // 프로필 사진의 바이트 배열을 설정하는 메서드
    public void setProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
    }



}