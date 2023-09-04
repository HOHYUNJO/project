package com.team2.project.Minigame.controller;

import com.team2.project.Minigame.service.MemberService;
import com.team2.project.Minigame.vo.MemberVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/signup")
public class ValidationController {
    private final MemberService memberService;

    @Autowired
    public ValidationController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public String processSignupForm(@ModelAttribute("member") MemberVo member, Model model) {
        // 비밀번호 유효성 검사
        String password = member.getMemberPassword();
        boolean isValidPassword = validatePassword(password);

        if (!isValidPassword) {
            model.addAttribute("error", "비밀번호는 8자 이상이며, 숫자, 문자, 특수문자를 최소한 하나씩 포함해야 합니다.");
            return "login";
        }

        // 아이디가 중복인지 확인
        if (memberService.isIdDuplicate(member.getMemberId())) {
            model.addAttribute("duplicateErrorMessage", "아이디가 이미 존재합니다.");
            return "login";
        }

        // 비밀번호 확인과 비밀번호가 맞지 않을 경우
        if (!member.getMemberPassword().equals(member.getConfirmPass())) {
            model.addAttribute("passwordErrorMessage", "비밀번호가 일치하지 않습니다.");
            return "login";
        }

        // Perform signup
        memberService.signup(member);
        return "redirect:/login"; // 로그인 페이지로 리다이렉트
    }

    @GetMapping("/checkIdDuplicate")
    @ResponseBody
    public boolean checkIdDuplicate(@RequestParam("memberId") String memberId) {
        return memberService.isIdDuplicate(memberId);
    }

    @GetMapping("/checkPasswordValidity")
    @ResponseBody
    public boolean checkPasswordValidity(@RequestParam("password") String password) {
        return validatePassword(password);
    }

    private boolean validatePassword(String password) {
        // 비밀번호가 8자 이상, 숫자와 문자를 포함해야 함
        String regex = "^(?=.*[0-9])(?=.*[a-zA-Z]).{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}