package com.team2.project.Minigame.dao;

import com.team2.project.Minigame.vo.QnAVo;

import java.util.List;

public interface QnADAO {

    QnAVo getQnADetail(Integer QNA_idx);

    void saveQnaBoard(String QNA_Title, String memberNick, String QNA_Text); // QnA글 저장

    List<QnAVo> getQnAByPage(int offset, int pageSize); // 페이지 단위로 게시글 목록을 조회
    int getQnABoardCount();  // 전체 QnA글 수를 조회
    QnAVo findQnAById(Integer QNA_idx); // 특정 QnA글 상세보기

    void updateQnABoard(QnAVo qnAVo);// QnA글 수정

    void deleteQnABoard(Integer QNA_idx);// QnA글 삭제

    void increaseQnABoardViews(Integer QNA_idx);// QnA글 조회수 증가

    QnAVo getPrevQnA(Integer QNA_idx);// 주어진 QnA글 번호보다 작은 QnA글 중 가장 최근 게시글을 조회.

    QnAVo getNextQnA(Integer QNA_idx);// 주어진 QnA글 번호보다 큰 QnA글 중 가장 이전 게시글을 조회.

    List<QnAVo> findAll();
}
