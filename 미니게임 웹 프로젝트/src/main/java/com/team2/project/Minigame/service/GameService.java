package com.team2.project.Minigame.service;

import com.team2.project.Minigame.vo.GameVo;

import java.util.List;

public interface GameService {

    void saveGame(GameVo gameVo);

    GameVo gameDetail(int INDEX_NO);

    List<GameVo> getAllGames();
    GameVo getGameById(int INDEX_NO);

    void deleteGame(int INDEX_NO);

    void updateGame(GameVo gameVo);

    List<GameVo> getRecentGamesSortedByCreateDate();

    List<GameVo> getPopularGamesSortedByCount();

    void increaseGameViews(Integer INDEX_NO);


}
