package com.team2.project.Minigame.vo;

public class AuthInfo {
    // 로그인 성공시 로그인 정보를 세션에 저장할 객체

    private String memberId;
    private String memberEmail;
    private String memberNick;
    private String memberName;
    private String memberPassword;
    private byte[] profileImage; // 프로필 이미지를 저장할 필드 추가
    private String isAdmin; // isAdmin 필드 추가

    public AuthInfo(String memberId, String memberEmail, String memberNick, String memberName, String memberPassword, String isAdmin) {
        this.memberId = memberId;
        this.memberEmail = memberEmail;
        this.memberNick = memberNick;
        this.memberName = memberName;
        this.memberPassword = memberPassword;
        this.isAdmin = isAdmin;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public void setMemberEmail(String memberEmail) {
        this.memberEmail = memberEmail;
    }

    public String getMemberNick() {
        return memberNick;
    }

    public void setMemberNick(String memberNick) {
        this.memberNick = memberNick;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberPassword() {
        return memberPassword;
    }

    public void setMemberPassword(String memberPassword) {
        this.memberPassword = memberPassword;
    }

    public byte[] getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
    }

    public String getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }
}