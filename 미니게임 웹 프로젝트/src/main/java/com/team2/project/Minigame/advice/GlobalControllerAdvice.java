package com.team2.project.Minigame.advice;

import com.team2.project.Minigame.vo.AuthInfo;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpSession;

@ControllerAdvice
public class GlobalControllerAdvice {

    //로그인하면 닉네임 뜨도록
    @ModelAttribute
    public void setSessionAttribute(HttpSession session) {
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        if (authInfo != null) {
            String memberNick = authInfo.getMemberNick();
            session.setAttribute("memberNick", memberNick);
        }
    }
}
