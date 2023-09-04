package com.team2.project.Minigame.dao;

import com.team2.project.Minigame.dao.FAQDAO;
import com.team2.project.Minigame.vo.FAQVo;
import com.team2.project.Minigame.vo.QnAVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class FAQDAOImpl implements FAQDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FAQDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public FAQVo getFAQById(int FAQ_idx) {
        String sql = "SELECT FAQ_idx, FAQ_Count FROM faq WHERE FAQ_idx = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(FAQVo.class), FAQ_idx);
    }


    // 조회수 증가
    @Override
    public void increaseFAQBoardViews(int FAQ_idx) {
        String sql = "UPDATE faq SET FAQ_Count = FAQ_Count + 1 WHERE FAQ_idx = ?";
        jdbcTemplate.update(sql, FAQ_idx);
    }

}
