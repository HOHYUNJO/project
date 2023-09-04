package com.team2.project.Minigame.dao;

import com.team2.project.Minigame.vo.QnAVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class QnADAOImpl implements QnADAO {

    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public QnADAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveQnaBoard(String QNA_Title, String memberNick, String QNA_Text) {
        // 현재 최대 글 번호를 가져옴
        String maxIdxSql = "SELECT MAX(QNA_idx) FROM qna";
        Integer maxIdx = jdbcTemplate.queryForObject(maxIdxSql, Integer.class);
        int newIdx = maxIdx != null ? maxIdx + 1 : 1; // 새로운 글 번호를 계산

        // 글 번호를 조정한 후 저장
        String sql = "INSERT INTO qna (QNA_idx, QNA_Title, memberNick, QNA_Text) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, newIdx, QNA_Title, memberNick, QNA_Text);

        // 새로운 글이 추가되었으므로 이후의 글 번호를 조정
        String updateSql = "UPDATE qna SET QNA_idx = QNA_idx + 1 WHERE QNA_idx >= ? AND QNA_idx <> ?";
        jdbcTemplate.update(updateSql, newIdx, newIdx);

    }

    // 페이지 단위로 게시글 목록을 조회하는 메서드입니다.
    public List<QnAVo> getQnAByPage(int offset, int pageSize) {
        String sql = "SELECT QNA_idx, QNA_Title, memberNick, CREATED_AT " +
                "FROM qna " +
                "ORDER BY QNA_idx DESC " +
                "LIMIT ?, ?";
        return jdbcTemplate.query(sql, new Object[]{offset, pageSize}, new BeanPropertyRowMapper<>(QnAVo.class));
    }

    //전체 QnA글 수를 조회하는 메서드입니다.
    @Override
    public int getQnABoardCount() {
        String query = "SELECT COUNT(*) FROM qna";
        return jdbcTemplate.queryForObject(query, Integer.class);
    }

    // 특정 게시글 상세보기
    @Override
    public QnAVo findQnAById(Integer QNA_idx) {
        String sql = "SELECT * FROM qna WHERE QNA_idx = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(QnAVo.class), QNA_idx);
    }

    // 수정 메서드
    @Override
    public void updateQnABoard(QnAVo qnAVo) {
        String sql = "UPDATE qna SET QNA_Title = ?, memberNick = ?, QNA_Text = ? WHERE QNA_idx = ?";
        jdbcTemplate.update(sql, qnAVo.getQNA_Title(), qnAVo.getMemberNick(), qnAVo.getQNA_Text(), qnAVo.getQNA_idx());
    }

    // 삭제 메서드
    @Override
    public void deleteQnABoard(Integer QNA_idx) {
        String sql = "DELETE FROM qna WHERE QNA_idx = ?";
        jdbcTemplate.update(sql, QNA_idx);

        String updateSql = "UPDATE qna SET QNA_idx = QNA_idx - 1 WHERE QNA_idx > ?";
        jdbcTemplate.update(updateSql, QNA_idx);
    }

    // 조회수 증가 메서드
    @Override
    public void increaseQnABoardViews(Integer QNA_idx) {
        String sql = "UPDATE qna SET QNACount = QNACount + 1 WHERE QNA_idx = ?";
        jdbcTemplate.update(sql, QNA_idx);
    }

    //이전글 다음글 버튼을 위한
    public QnAVo getPrevQnA(Integer QNA_idx) {
        String sql = "SELECT * FROM qna WHERE QNA_idx < ? ORDER BY QNA_idx DESC LIMIT 1";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{QNA_idx}, new BeanPropertyRowMapper<>(QnAVo.class));
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public QnAVo getNextQnA(Integer QNA_idx) {
        String sql = "SELECT * FROM qna WHERE QNA_idx > ? ORDER BY QNA_idx ASC LIMIT 1";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{QNA_idx}, new BeanPropertyRowMapper<>(QnAVo.class));
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }


    @Override
    public List<QnAVo> findAll() {
        String sql = "SELECT * FROM qna";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(QnAVo.class));
    }

    @Override
    public QnAVo getQnADetail(Integer QNA_idx) {
        String sql = "SELECT * FROM qna WHERE QNA_idx = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{QNA_idx}, new BeanPropertyRowMapper<>(QnAVo.class));
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

}
