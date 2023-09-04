package com.team2.project.Minigame.dao;


import com.team2.project.Minigame.vo.BoardVo;

import java.util.List;

public interface BoardDAO {
    void saveBoard(String boardTitle, String memberNick, String boardText); // 게시글 저장

    List<BoardVo> getBoardsByPage(int offset, int pageSize); // 페이지 단위로 게시글 목록을 조회
    int getBoardCount();  // 전체 게시글 수를 조회

    BoardVo findById(Integer board_idx);// 특정 게시글 상세보기

    void updateBoard(BoardVo boardVo);// 게시글 수정

    void deleteBoard(Integer boardIdx);// 게시글 삭제

    void increaseBoardViews(Integer board_idx);//게시글 조회수 증가

    BoardVo getPrevBoard(Integer boardIdx);//주어진 게시글 번호보다 작은 게시글 중 가장 최근 게시글을 조회.

    BoardVo getNextBoard(Integer boardIdx);//주어진 게시글 번호보다 큰 게시글 중 가장 이전 게시글을 조회.

    // 내가 쓴 글 목록을 페이징하여 가져오는 메서드
    List<BoardVo> getMyBoardsPaging(String memberNick, int offset, int pageSize);

    // 특정 사용자가 작성한 전체 게시글 수를 조회하는 메서드
    int getTotalMyBoardCount(String memberNick);

    List<BoardVo> searchBoards(String keyword);

    List<BoardVo> searchBoardsByWriter(String keyword);

    List<BoardVo> searchBoardsByContents(String keyword);
}
