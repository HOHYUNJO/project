package com.team2.project.Minigame.service;

import com.team2.project.Minigame.dao.GameDAO;
import com.team2.project.Minigame.vo.BoardVo;
import com.team2.project.Minigame.vo.GameVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private GameDAO gameDAO;

    @Override
    public void saveGame(GameVo gameVo) {
        // GameDAO를 사용하여 데이터베이스에 게임 정보를 저장
        gameDAO.saveGame(gameVo);
    }
    @Override
    public List<GameVo> getAllGames() {
        return gameDAO.getAllGames();
    }

    @Override
    public GameVo getGameById(int INDEX_NO) {
        return gameDAO.getGameById(INDEX_NO);
    }

    @Override
    public void deleteGame(int INDEX_NO) {
        gameDAO.deleteGame(INDEX_NO);
    }
    public GameVo gameDetail(int INDEX_NO) {
        return gameDAO.getGameById(INDEX_NO);
    }

    @Override
    public void updateGame(GameVo gameVo) {
        gameVo.setGameID(gameVo.getGameID());
        gameVo.setGameTitle(gameVo.getGameTitle());
        gameVo.setGameDetail(gameVo.getGameDetail());
        gameVo.setGameMaker(gameVo.getGameMaker());
        gameDAO.updateGame(gameVo);

    }

    @Override
    public List<GameVo> getRecentGamesSortedByCreateDate() {
        return gameDAO.getRecentGamesSortedByCreateDate();
    }

    @Override
    public List<GameVo> getPopularGamesSortedByCount() {
        return gameDAO.getPopularGamesSortedByCount();
    }

    @Override
    public void increaseGameViews(Integer INDEX_NO) {
        gameDAO.increaseGameViews(INDEX_NO);
    }



}
