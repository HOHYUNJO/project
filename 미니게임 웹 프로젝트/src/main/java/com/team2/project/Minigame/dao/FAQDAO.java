package com.team2.project.Minigame.dao;

import com.team2.project.Minigame.vo.FAQVo;

public interface FAQDAO {
    FAQVo getFAQById(int FAQ_idx);
    void increaseFAQBoardViews(int FAQ_idx);
}
