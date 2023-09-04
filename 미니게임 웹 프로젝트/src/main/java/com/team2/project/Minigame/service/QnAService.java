package com.team2.project.Minigame.service;

import com.team2.project.Minigame.vo.BoardVo;
import com.team2.project.Minigame.vo.QnAVo;

import java.util.List;

public interface QnAService {

    void saveQnaBoard(String QNA_Title, String memberNick, String QNA_Text);

    List<QnAVo> getQnAList();

    QnAVo findQnAById(Integer QNA_idx);

    void updateQnABoard(QnAVo qnaVo);

    void deleteQnABoard(Integer QNA_idx);

    QnAVo QnADetail(Integer QNA_idx);

    void increaseQnABoardViews(Integer QNA_idx);

    QnAVo getPrevQnA(Integer QNA_idx);

    QnAVo getNextQnA(Integer QNA_idx);

    QnAVo getQnADetail(Integer QNA_idx);

    // QnA글 조회하기
    List<QnAVo> getQnAByPage(int pageNumber, int pageSize);

    int getTotalQNACount(int pageSize);
}
