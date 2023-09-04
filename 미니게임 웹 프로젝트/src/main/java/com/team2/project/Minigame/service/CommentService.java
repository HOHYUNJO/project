package com.team2.project.Minigame.service;

import com.team2.project.Minigame.dao.CommentDAO;
import com.team2.project.Minigame.vo.CommentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentDAO commentDao;

    public CommentService(CommentDAO commentDao) {
        this.commentDao = commentDao;
    }

    // 댓글 저장
    public void saveComment(CommentVo comment) {
        commentDao.saveComment(comment);
    }

    // 댓글 삭제
    public void deleteComment(Integer commentId) {
        commentDao.deleteComment(commentId);
    }

    // 게시글 번호에 해당하는 모든 댓글 가져오기
    public List<CommentVo> getCommentsByBoardNum(Integer boardNum) {
        return commentDao.getCommentsByBoardNum(boardNum);
    }
}
