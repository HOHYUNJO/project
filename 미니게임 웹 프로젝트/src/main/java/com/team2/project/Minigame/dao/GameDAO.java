package com.team2.project.Minigame.dao;

import com.team2.project.Minigame.vo.GameVo;

import java.util.List;

public interface GameDAO {
    void saveGame(GameVo gameVo);
    List<GameVo> getAllGames();
    GameVo getGameById(int INDEX_NO);
    void deleteGame(int INDEX_NO);

    void updateGame(GameVo gameVo);

    List<GameVo> getRecentGamesSortedByCreateDate();

    List<GameVo> getPopularGamesSortedByCount();

    void increaseGameViews(Integer INDEX_NO);//게시글 조회수 증가
}
