package com.team2.project.Minigame.service;

import com.team2.project.Minigame.dao.FAQDAO;
import com.team2.project.Minigame.vo.FAQVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FAQServiceImpl implements FAQService {

    private final FAQDAO faqDAO;

    @Autowired
    public FAQServiceImpl(FAQDAO faqDAO) {
        this.faqDAO = faqDAO;
    }

    @Override
    public FAQVo getFAQById(int FAQ_idx) {
        return faqDAO.getFAQById(FAQ_idx);
    }

    @Override
    public void increaseFAQBoardViews(int FAQ_idx) {
        faqDAO.increaseFAQBoardViews(FAQ_idx);
    }
}
