package com.team2.project.Minigame.dao;

import com.team2.project.Minigame.vo.BoardVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public class BoardDAOImpl implements BoardDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BoardDAOImpl(JdbcTemplate jdbcTemplate) { // 생성자에 sqlSession 추가
        this.jdbcTemplate = jdbcTemplate;
    }

    // 게시글 저장 메서드
    @Override
    public void saveBoard(String boardTitle, String memberNick, String boardText) {
        // 현재 최대 글 번호를 가져옴
        String maxIdxSql = "SELECT MAX(board_idx) FROM boards";
        Integer maxIdx = jdbcTemplate.queryForObject(maxIdxSql, Integer.class);
        int newIdx = maxIdx != null ? maxIdx + 1 : 1; // 새로운 글 번호를 계산

        // 글 번호를 조정한 후 저장
        String sql = "INSERT INTO boards (board_idx, boardTitle, memberNick, boardText) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, newIdx, boardTitle, memberNick, boardText);

        // 새로운 글이 추가되었으므로 이후의 글 번호를 조정
        String updateSql = "UPDATE boards SET board_idx = board_idx + 1 WHERE board_idx >= ? AND board_idx <> ?";
        jdbcTemplate.update(updateSql, newIdx, newIdx);
    }

    // 페이지 단위로 게시글 목록을 조회하는 메서드입니다.
    public List<BoardVo> getBoardsByPage(int offset, int pageSize) {
        String sql = "SELECT board_idx, boardTitle, memberNick, CREATED_AT " +
                "FROM boards " +
                "ORDER BY board_idx DESC " +
                "LIMIT ?, ?";
        return jdbcTemplate.query(sql, new Object[]{offset, pageSize}, new BeanPropertyRowMapper<>(BoardVo.class));
    }
    
    //전체 게시글 수를 조회하는 메서드입니다
    @Override
    public int getBoardCount() {
        String query = "SELECT COUNT(*) FROM boards";
        return jdbcTemplate.queryForObject(query, Integer.class);
    }

    // 특정 게시글 상세보기
    @Override
    public BoardVo findById(Integer board_idx) {
        String sql = "SELECT * FROM boards WHERE board_idx = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(BoardVo.class), board_idx);
    }
    // 수정 메서드
    @Override
    public void updateBoard(BoardVo boardVo) {
        String sql = "UPDATE boards SET boardTitle = ?, memberNick = ?, boardText = ? WHERE board_idx = ?";
        jdbcTemplate.update(sql, boardVo.getBoardTitle(), boardVo.getMemberNick(), boardVo.getBoardText(), boardVo.getBoard_idx());
    }
    // 삭제 메서드
    @Override
    public void deleteBoard(Integer boardIdx) {
        String sql = "DELETE FROM boards WHERE board_idx = ?";
        jdbcTemplate.update(sql, boardIdx);

        String updateSql = "UPDATE boards SET board_idx = board_idx - 1 WHERE board_idx > ?";
        jdbcTemplate.update(updateSql, boardIdx);

    }
    // 조회수 증가 메서드
    @Override
    public void increaseBoardViews(Integer board_idx) {
        String sql = "UPDATE boards SET boardCount = boardCount + 1 WHERE board_idx = ?";
        jdbcTemplate.update(sql, board_idx);
    }

    //이전글 버튼을 위한
    public BoardVo getPrevBoard(Integer board_idx) {
        String sql = "SELECT * FROM boards WHERE board_idx < ? ORDER BY board_idx DESC LIMIT 1";
        List<BoardVo> results = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(BoardVo.class), board_idx);
        if (results.isEmpty()) {
            return null; // 결과가 없는 경우 null 반환
        }
        return results.get(0);
    }
    //다음글 버튼을 위한
    public BoardVo getNextBoard(Integer board_idx) {
        String sql = "SELECT * FROM boards WHERE board_idx > ? ORDER BY board_idx ASC LIMIT 1";
        List<BoardVo> results = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(BoardVo.class), board_idx);
        if (results.isEmpty()) {
            return null; // 결과가 없는 경우 null 반환
        }
        return results.get(0);
    }
    // 내가 쓴 글 목록을 페이징하여 가져오는 메서드 구현
    @Override
    public List<BoardVo> getMyBoardsPaging(String memberNick, int offset, int pageSize) {
        String sql = "SELECT board_idx, boardTitle, memberNick, CREATED_AT " +
                "FROM boards " +
                "WHERE memberNick = ? " +
                "ORDER BY board_idx DESC " +
                "LIMIT ?, ?";
        return jdbcTemplate.query(sql, new Object[]{memberNick, offset, pageSize}, new BeanPropertyRowMapper<>(BoardVo.class));
    }

    // 특정 사용자가 작성한 전체 게시글 수를 조회하는 메서드 구현
    @Override
    public int getTotalMyBoardCount(String memberNick) {
        String query = "SELECT COUNT(*) FROM boards WHERE memberNick = ?";
        return jdbcTemplate.queryForObject(query, Integer.class, memberNick);
    }

    @Override
    public List<BoardVo> searchBoards(String keyword) {
        String sql = "SELECT * FROM boards WHERE boardTitle LIKE ? OR boardText LIKE ? OR memberNick LIKE ?";
        String searchTerm = "%" + keyword + "%";
        List<BoardVo> searchResults = jdbcTemplate.query(sql, new Object[]{searchTerm, searchTerm, searchTerm}, new BeanPropertyRowMapper<>(BoardVo.class));

        return searchResults;
    }

    @Override
    public List<BoardVo> searchBoardsByWriter(String keyword) {
        String sql = "SELECT * FROM boards WHERE memberNick LIKE ?";
        String searchTerm = "%" + keyword + "%";
        return jdbcTemplate.query(sql, new Object[]{searchTerm}, new BeanPropertyRowMapper<>(BoardVo.class));
    }

    @Override
    public List<BoardVo> searchBoardsByContents(String keyword) {
        String sql = "SELECT * FROM boards WHERE boardText LIKE ?";
        String searchTerm = "%" + keyword + "%";
        return jdbcTemplate.query(sql, new Object[]{searchTerm}, new BeanPropertyRowMapper<>(BoardVo.class));
    }

}
