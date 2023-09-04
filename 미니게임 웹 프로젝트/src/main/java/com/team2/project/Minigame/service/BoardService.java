package com.team2.project.Minigame.service;

import com.team2.project.Minigame.vo.BoardVo;

import java.util.List;

public interface BoardService {

    // 게시글 저장
    void saveBoard(String boardTitle, String memberNick, String boardText);

    // 게시글 조회하기
    List<BoardVo> getBoardsByPage(int pageNumber, int pageSize);

    // 게시글 서치
    List<BoardVo> searchBoards(String keyword);

    List<BoardVo> searchBoardsByWriter(String keyword);

    List<BoardVo> searchBoardsByContents(String keyword);

    int getTotalPageCount(int pageSize);

    // 특정 게시글 상세보기
    BoardVo boardDetail(Integer board_idx);

    // 게시글 삭제
    void deleteBoard(Integer board_idx);
    
    // 게시글 조회
    BoardVo findBoard(Integer board_idx);

    // 게시글 수정
    void updateBoard(BoardVo boardVo);
    
    // 게시글 증가
    void increaseBoardViews(Integer board_idx);

    // 게시글 이전 버튼
    BoardVo getPrevBoard(Integer boardIdx);
    
    // 게시글 다음 버튼
    BoardVo getNextBoard(Integer boardIdx);

    // 내가 쓴 글 목록을 페이징하여 가져오는 메서드
    List<BoardVo> getMyBoardsPaging(String memberNick, int pageNumber, int pageSize);

    // 특정 사용자가 작성한 전체 게시글 수를 조회하는 메서드
    int getTotalMyBoardCount(String memberNick);
}
