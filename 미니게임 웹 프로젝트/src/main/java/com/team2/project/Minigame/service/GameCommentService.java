package com.team2.project.Minigame.service;

import com.team2.project.Minigame.dao.GameCommentDAO;
import com.team2.project.Minigame.vo.GameCommentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameCommentService {

    private final GameCommentDAO gameCommentDao;

    @Autowired
    public GameCommentService(GameCommentDAO gameCommentDao){
        this.gameCommentDao = gameCommentDao;
    }

    // 게임 한줄평 저장
    public void saveGameComment(GameCommentVo gameComment){
        gameCommentDao.saveGameComment(gameComment);
    }

    // 게임 한줄평 삭제
    public void deleteGameComment(int commentID){
        gameCommentDao.deleteGameComment(commentID);
    }

    // 게임ID에 해당하는 모든 댓글 가져오기
    public List<GameCommentVo> getGameCommentsByGameID(int INDEX_NO){
        return gameCommentDao.getGameCommentsByGameID(INDEX_NO);
    }

}
