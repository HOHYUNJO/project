package com.team2.project.Minigame.service;

import com.team2.project.Minigame.exception.IdPasswordNotMatchingException;
import com.team2.project.Minigame.exception.MemberNotFoundException;
import com.team2.project.Minigame.vo.AuthInfo;
import com.team2.project.Minigame.vo.MemberVo;
import com.team2.project.Minigame.dao.MemberDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service
public class AuthService {

    private final MemberDAO memberDao;

     //회원 인증을 수행하여 인증된 사용자 정보를 반환합니다.
     //@param id       사용자 아이디
     //@param password 사용자 비밀번호
     //@return 인증된 사용자의 정보 (AuthInfo 객체)
     //@throws MemberNotFoundException    해당 아이디에 해당하는 회원을 찾을 수 없는 경우 발생하는 예외
     //@throws IdPasswordNotMatchingException 비밀번호가 일치하지 않는 경우 발생하는 예외
    @Autowired
    public AuthService(@Qualifier("memberDAOImpl") MemberDAO memberDao) {
        this.memberDao = memberDao;
    }

    public AuthInfo authenticate(String id, String password) {
        // 사용자 아이디로 회원 정보 조회
        MemberVo member = memberDao.getMemberById(id);
        // 회원 객체(member)에서 비밀번호를 가져오는 코드입니다.


        // 회원 정보가 null인 경우, 해당 아이디에 해당하는 회원을 찾을 수 없음
        if (member == null) {
            throw new MemberNotFoundException(); // 회원을 찾을 수 없는 경우 예외 발생
        }
        String memberPassword = member.getMemberPassword();
        // 입력된 비밀번호와 회원 정보의 비밀번호를 비교하여 일치하지 않으면 예외 발생
        if (memberPassword == null || !memberPassword.equals(password)) {
            throw new IdPasswordNotMatchingException(); // 비밀번호가 일치하지 않는 경우 예외 발생
        }
        return new AuthInfo(member.getMemberId(), member.getMemberEmail(), member.getMemberNick(), member.getMemberName(), member.getMemberPassword(), member.getIsAdmin());

        // 인증에 성공한 경우, AuthInfo 객체를 생성하여 반환

    }

}