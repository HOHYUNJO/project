package com.team2.project.Minigame.service;

import com.team2.project.Minigame.vo.FAQVo;

public interface FAQService {
    FAQVo getFAQById(int FAQ_idx);
    void increaseFAQBoardViews(int FAQ_idx);
}
