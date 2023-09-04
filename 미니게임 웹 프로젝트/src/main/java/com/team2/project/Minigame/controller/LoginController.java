package com.team2.project.Minigame.controller;

import com.team2.project.Minigame.vo.AuthInfo;
import com.team2.project.Minigame.exception.IdPasswordNotMatchingException;
import com.team2.project.Minigame.exception.MemberNotFoundException;
import com.team2.project.Minigame.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value={"/", "/login"})
public class LoginController {

    private final AuthService authService;

    @Autowired
    public LoginController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping
    public String loginForm(HttpSession session) {
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        if (authInfo != null) {
            return "redirect:/main";
        }
        return "login";
    }

    @PostMapping
    public String submitLogin(
            @RequestParam("memberId") String memberId,
            @RequestParam("memberPassword") String memberPassword,
            HttpSession session, HttpServletResponse response, Model model) {

        // 1. 아이디와 비밀번호가 입력되었는지 검증
        if (memberId == null || memberId.isEmpty() || memberPassword == null || memberPassword.isEmpty()) {
            session.setAttribute("loginErrorMessage", "아이디 또는 비밀번호를 입력해주세요.");
            return "redirect:/login";
        }

        try {
            // 2. 입력 검증이 되었다면 로그인 처리(서비스 객체를 주입)
            AuthInfo authInfo = authService.authenticate(memberId, memberPassword);

            // 3. 로그인 성공 후 세션 저장
            session.setAttribute("authInfo", authInfo);

            return "redirect:/main";

        } catch (MemberNotFoundException e) {
            // 3-1. 회원을 찾을 수 없는 경우 처리할 코드
            model.addAttribute("loginIdErrorMessage", "아이디가 없습니다.");
            return "login";
        } catch (IdPasswordNotMatchingException e) {
            // 3-2. 로그인 실패시 처리할 코드
            model.addAttribute("loginErrorMessage", "비밀번호가 일치하지 않습니다.");
            return "login";
        }
    }
}