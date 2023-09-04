package com.team2.project.Minigame.dao;

import com.team2.project.Minigame.vo.GameCommentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class GameCommentDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GameCommentDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 게임 한줄평 삭제
    public void deleteGameComment(int commentID) {
        String sql = "DELETE FROM comments WHERE commentID = ? ";
        jdbcTemplate.update(sql, commentID);
    }

    // 게임 한줄평 저장
    public void saveGameComment(GameCommentVo gameComment) {
        String sql = "INSERT INTO comments (INDEX_NO, commentNick, commentText, rating) VALUES(?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, gameComment.getINDEX_NO());
            ps.setString(2, gameComment.getCommentNick());
            ps.setString(3, gameComment.getCommentText());
            ps.setDouble(4, gameComment.getRating());
            return ps;
        }, keyHolder);

        // 자동 생성된 키 값 조회
        gameComment.setCommentID(keyHolder.getKey().intValue());
    }

    public double calculateGameRating(int gameIndex) {
        String sql = "SELECT rating FROM comments WHERE INDEX_NO = ?";
        List<Double> ratings = jdbcTemplate.queryForList(sql, Double.class, gameIndex);

        double sum = 0.0;
        for (Double rating : ratings) {
            sum += rating;
        }

        if (ratings.isEmpty()) {
            return 0.0; // 댓글이 없으면 0을 반환하거나 원하는 다른 기본 평점 값을 반환
        } else {
            double averageRating = sum / ratings.size();
            // Round the averageRating to two decimal places
            return Math.round(averageRating * 100.0) / 100.0;
        }
    }

    public List<GameCommentVo> getGameCommentsByGameID(int INDEX_NO){
        String sql = "SELECT * FROM comments WHERE INDEX_NO = ?";
        return  jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(GameCommentVo.class), INDEX_NO);
    }


}
