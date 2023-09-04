package com.team2.project.Minigame.service;

import com.team2.project.Minigame.dao.QnADAO;
import com.team2.project.Minigame.vo.QnAVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QnAServiceImpl implements QnAService {

    private final QnADAO qnaDAO;

    @Autowired
    public QnAServiceImpl(QnADAO qnaDAO) {
        this.qnaDAO = qnaDAO;
    }

    @Override
    public void saveQnaBoard(String QNA_Title, String memberNick, String QNA_Text) {
        qnaDAO.saveQnaBoard(QNA_Title, memberNick, QNA_Text);
    }

    @Override
    public List<QnAVo> getQnAList() {
        return qnaDAO.findAll();
    }

    @Override
    public QnAVo findQnAById(Integer QNA_idx) {
        return qnaDAO.findQnAById(QNA_idx);
    }

    @Override
    public void updateQnABoard(QnAVo qnaVo) {
        qnaDAO.updateQnABoard(qnaVo);
    }

    @Override
    public void deleteQnABoard(Integer QNA_idx) {
        qnaDAO.deleteQnABoard(QNA_idx);
    }

    @Override
    public QnAVo QnADetail(Integer QNA_idx) {
        return qnaDAO.findQnAById(QNA_idx);
    }

    @Override
    public void increaseQnABoardViews(Integer QNA_idx) {
        qnaDAO.increaseQnABoardViews(QNA_idx);
    }

    @Override
    public QnAVo getPrevQnA(Integer QNA_idx) {
        return qnaDAO.getPrevQnA(QNA_idx);
    }

    @Override
    public QnAVo getNextQnA(Integer QNA_idx) {
        return qnaDAO.getNextQnA(QNA_idx);
    }

    @Override
    public QnAVo getQnADetail(Integer QNA_idx) {
        return qnaDAO.getQnADetail(QNA_idx);
    }

    @Override
    public List<QnAVo> getQnAByPage(int pageNumber, int pageSize) {
        int offset = (pageNumber - 1) * pageSize;
        return qnaDAO.getQnAByPage(offset, pageSize);
    }

    @Override
    public int getTotalQNACount(int pageSize) {
        int totalQNACount = qnaDAO.getQnABoardCount();
        return (int) Math.ceil((double) totalQNACount / pageSize);
    }
}
