package com.team2.project.Minigame.dao;

import com.team2.project.Minigame.vo.MemberVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberDAOImpl implements MemberDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MemberDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 회원 추가 메서드
    @Override
    public void insertMember(MemberVo member) {
        String query = "INSERT INTO members (memberID, memberName, memberNick, memberPassword, memberEmail) " +
                "VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(query, member.getMemberId(), member.getMemberName(), member.getMemberNick(),
                member.getMemberPassword(), member.getMemberEmail());
    }

    // 아이디 조회하기 메서드
    @Override
    public MemberVo getMemberById(String memberId) {
        String query = "SELECT * FROM members WHERE memberID = ?";
        RowMapper<MemberVo> rowMapper = new BeanPropertyRowMapper<>(MemberVo.class);
        List<MemberVo> members = jdbcTemplate.query(query, new Object[]{memberId}, rowMapper);
        return members.isEmpty() ? null : members.get(0);
    }

    // 아이디 중복 확인 메서드
    @Override
    public boolean isIdDuplicate(String memberId) {
        String query = "SELECT COUNT(*) FROM members WHERE memberID = ?";
        int count = jdbcTemplate.queryForObject(query, new Object[]{memberId}, Integer.class);
        return count > 0;
    }

    // 비밀번호 맞는지 확인하는 메서드
    @Override
    public boolean isPasswordCorrect(String memberId, String password) {
        String query = "SELECT COUNT(*) FROM members WHERE memberID = ? AND memberPassword = ?";
        int count = jdbcTemplate.queryForObject(query, new Object[]{memberId, password}, Integer.class);
        return count > 0;
    }

    // 회원정보 수정 메서드
    @Override
    public void updateMypage(MemberVo member) {
        String query = "UPDATE members SET memberNick = ?, memberPassword = ?, memberEmail = ? WHERE memberId = ?";
        jdbcTemplate.update(query, member.getMemberNick(), member.getMemberPassword(), member.getMemberEmail(), member.getMemberId());
    }

    // 이미지 추가 메서드
    @Override
    public void addProfileImage(String memberId, byte[] image) {
        String query = "INSERT INTO profile_images (member_CD, memberID, image) VALUES (?, ?, ?)";
        jdbcTemplate.update(query, memberId, memberId, image);
    }

    // 이미지 조회 메서드
    @Override
    public byte[] getProfileImage(String memberId) {
        String query = "SELECT image FROM profile_images WHERE member_CD = ?";
        List<byte[]> images = jdbcTemplate.queryForList(query, new Object[]{memberId}, byte[].class);
        return images.isEmpty() ? null : images.get(0);
    }

    // 이미지 업데이트 메서드
    @Override
    public void updateProfileImage(String memberId, byte[] image) {
        String query = "UPDATE profile_images SET image = ? WHERE member_CD = ?";
        jdbcTemplate.update(query, image, memberId);
    }

}