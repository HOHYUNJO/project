package com.team2.project.Minigame.controller;

import com.team2.project.Minigame.vo.MemberVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;




@Controller
public class MemberController {

    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("member", new MemberVo());
        return "login";
    }
}