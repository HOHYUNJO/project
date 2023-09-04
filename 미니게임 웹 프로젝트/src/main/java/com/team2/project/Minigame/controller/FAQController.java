package com.team2.project.Minigame.controller;

import com.team2.project.Minigame.dao.FAQDAO;
import com.team2.project.Minigame.service.FAQService;
import com.team2.project.Minigame.vo.FAQVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class FAQController {

    private final FAQService faqService;
    private final FAQDAO faqDao;

    public FAQController(FAQService faqService, FAQDAO faqDao) {
        this.faqService = faqService;
        this.faqDao = faqDao;
    }

    @GetMapping("/FAQDetail/{FAQ_idx}")
    public String getFAQDetail(@PathVariable("FAQ_idx") int FAQ_idx, Model model) {
        FAQVo faq = faqService.getFAQById(FAQ_idx);

        if (faq == null) {
            return "Error"; // 오류 페이지
        }

        // FAQ_idx에 따라 조회수 증가
        if (FAQ_idx == 1) {
            faqService.increaseFAQBoardViews(FAQ_idx);
        } else if (FAQ_idx == 2) {
            faqService.increaseFAQBoardViews(FAQ_idx);
        } else if (FAQ_idx == 3) {
            faqService.increaseFAQBoardViews(FAQ_idx);
        }

        model.addAttribute("faq", faq);
        return "FAQDetail";
    }

}
