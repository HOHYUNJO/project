package com.team2.project.Minigame.dao;

import com.team2.project.Minigame.vo.CommentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class CommentDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CommentDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void deleteComment(Integer commentId) {
        String sql = "DELETE FROM BoardComments WHERE commentId = ?";
        jdbcTemplate.update(sql, commentId);
    }

    public void saveComment(CommentVo comment) {
//        String sql = "INSERT INTO BoardComments (boardNum, commentNick, commentText) VALUES (?, ?, ?)";
//        jdbcTemplate.update(sql, comment.getBoardNum(), comment.getCommentNick(), comment.getCommentText());
        String sql = "INSERT INTO BoardComments (boardNum, commentNick, commentText) VALUES (?, ?, ?)";

        // 쿼리 실행 시 자동 생성된 commentId를 가져오도록 옵션 추가
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, comment.getBoardNum());
            ps.setString(2, comment.getCommentNick());
            ps.setString(3, comment.getCommentText());
            return ps;
        }, keyHolder);

        // Get the generated commentId and set it back to the CommentVo object
        comment.setCommentId(keyHolder.getKey().intValue());
    }

    public List<CommentVo> getCommentsByBoardNum(int boardNum) {
        String sql = "SELECT * FROM BoardComments WHERE boardNum = ?";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(CommentVo.class), boardNum);
    }
}
