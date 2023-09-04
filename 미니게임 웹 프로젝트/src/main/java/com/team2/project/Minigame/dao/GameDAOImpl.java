package com.team2.project.Minigame.dao;

import com.team2.project.Minigame.vo.GameVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GameDAOImpl implements GameDAO {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void saveGame(GameVo gameVo) {
        // 현재 최대 인덱스를 가져옴
        String maxIdxSql = "SELECT MAX(INDEX_NO) FROM games";
        Integer maxIdx = jdbcTemplate.queryForObject(maxIdxSql, Integer.class);
        int newIdx = maxIdx != null ? maxIdx + 1 : 1; // 새로운 인덱스를 계산

        // 인덱스를 조정한 후 저장
        String sql = "INSERT INTO games (INDEX_NO,gameID, gameTitle, gameMaker, gameDetail) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, newIdx,gameVo.getGameID(), gameVo.getGameTitle(), gameVo.getGameMaker(), gameVo.getGameDetail());

        // 새로운 게임이 추가되었으므로 이후의 인덱스를 조정
        String updateSql = "UPDATE games SET INDEX_NO = INDEX_NO + 1 WHERE INDEX_NO >= ? AND INDEX_NO <> ?";
        jdbcTemplate.update(updateSql, newIdx, newIdx);
    }

    @Override
    public List<GameVo> getAllGames() {
        // 게임 목록을 가져오는 SQL 쿼리를 작성합니다.
        String sql = "SELECT INDEX_NO, gameID, gameTitle, gameDetail, gameMaker,gameCount, CREATE_AT FROM games";
        // 게임 목록을 조회하여 GameVo 객체의 리스트로 변환하여 반환합니다.
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            GameVo gameVo = new GameVo();
            gameVo.setINDEX_NO(rs.getInt("INDEX_NO"));
            gameVo.setGameID(rs.getString("gameID"));
            gameVo.setGameTitle(rs.getString("gameTitle"));
            gameVo.setGameDetail(rs.getString("gameDetail"));
            gameVo.setGameMaker(rs.getString("gameMaker"));
            gameVo.setCREATE_AT(rs.getDate("CREATE_AT"));
            gameVo.setGameCount(rs.getInt("gameCount"));
            return gameVo;
        });
    }


    @Override
    public GameVo getGameById(int INDEX_NO) {
        // 게임 정보를 가져오는 SQL 쿼리
        String gameSql = "SELECT INDEX_NO, gameID, gameTitle, gameDetail, gameMaker,gameCount, CREATE_AT FROM games WHERE INDEX_NO = ?";
        // INDEX_NO를 파라미터로 하여 게임 정보를 조회
        // 단일 행을 반환하므로 queryForObject를 사용
        GameVo gameVo = jdbcTemplate.queryForObject(gameSql, new Object[]{INDEX_NO}, (rs, rowNum) -> {
            GameVo game = new GameVo();
            game.setINDEX_NO(rs.getInt("INDEX_NO"));
            game.setGameID(rs.getString("gameID"));
            game.setGameTitle(rs.getString("gameTitle"));
            game.setGameDetail(rs.getString("gameDetail"));
            game.setGameMaker(rs.getString("gameMaker"));
            game.setCREATE_AT(rs.getDate("CREATE_AT"));
            game.setGameCount(rs.getInt("gameCount"));
            return game;
        });

         //댓글들의 평균 별점을 가져오는 SQL 쿼리
        String ratingSql = "SELECT AVG(rating) AS averageRating FROM comments WHERE INDEX_NO = ?";
        // INDEX_NO를 파라미터로 하여 댓글들의 평균 별점을 조회
        // 단일 행과 단일 열을 반환하므로 queryForObject를 사용하며, 결과는 Double 타입으로 매핑
        Double averageRating = jdbcTemplate.queryForObject(ratingSql, new Object[]{INDEX_NO}, Double.class);

        // 평균 별점을 GameVo 객체에 설정
        gameVo.setRating(averageRating);

        return gameVo;
    }

//    //조회
//    @Override
//    public GameVo getGameById(int INDEX_NO) {
//        // INDEX_NO를 기반으로 해당 게임을 가져오는 SQL 쿼리를 작성합니다.
//        String sql = "SELECT INDEX_NO, gameID, gameTitle, gameDetail, gameMaker, CREATE_AT FROM games WHERE INDEX_NO = ?";
//        // INDEX_NO를 파라미터로 하여 게임 정보를 조회합니다.
//        // 단일 행을 반환하므로 queryForObject를 사용합니다.
//        return jdbcTemplate.queryForObject(sql, new Object[]{INDEX_NO}, (rs, rowNum) -> {
//            GameVo gameVo = new GameVo();
//            gameVo.setINDEX_NO(rs.getInt("INDEX_NO"));
//            gameVo.setGameID(rs.getString("gameID"));
//            gameVo.setGameTitle(rs.getString("gameTitle"));
//            gameVo.setGameDetail(rs.getString("gameDetail"));
//            gameVo.setGameMaker(rs.getString("gameMaker"));
//            gameVo.setCREATE_AT(rs.getDate("CREATE_AT"));
//            return gameVo;
//        });
//    }

    //삭제
    @Override
    public void deleteGame(int INDEX_NO) {
        String sql = "DELETE FROM games WHERE INDEX_NO = ?";
        jdbcTemplate.update(sql, INDEX_NO);

        String updateSql = "UPDATE boards SET INDEX_NO = INDEX_NO - 1 WHERE INDEX_NO > ?";
        jdbcTemplate.update(updateSql, INDEX_NO);
    }

    //수정
    @Override
    public void updateGame(GameVo gameVo) {
        // 게임 정보를 데이터베이스에 업데이트하는 쿼리를 작성합니다.
        String sql = "UPDATE games SET gameID = ?, gameTitle = ?, gameDetail = ?, gameMaker = ? WHERE INDEX_NO = ?";
        jdbcTemplate.update(sql, gameVo.getGameID(), gameVo.getGameTitle(), gameVo.getGameDetail(), gameVo.getGameMaker(), gameVo.getINDEX_NO());
    }
    @Override
    public List<GameVo> getRecentGamesSortedByCreateDate() {
        String query = "SELECT * FROM games ORDER BY CREATE_AT DESC LIMIT 3";

        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(GameVo.class));
    }

    @Override
    public List<GameVo> getPopularGamesSortedByCount() {
        String query = "SELECT * FROM games ORDER BY gameCount DESC LIMIT 3";

        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(GameVo.class));
    }

    @Override
    public void increaseGameViews(Integer INDEX_NO) {
        String sql = "UPDATE games SET gameCount = gameCount + 1 WHERE INDEX_NO = ?";
        jdbcTemplate.update(sql, INDEX_NO);
    }
}
