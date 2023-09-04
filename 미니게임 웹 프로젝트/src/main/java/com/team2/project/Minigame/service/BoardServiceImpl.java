package com.team2.project.Minigame.service;

import com.team2.project.Minigame.dao.BoardDAO;
import com.team2.project.Minigame.vo.BoardVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService{

    private final BoardDAO boardDAO;
    private JdbcOperations jdbcTemplate;

    @Autowired
    public BoardServiceImpl(BoardDAO boardDAO) {
        this.boardDAO = boardDAO;
    }

    // 게시글 저장
    @Override
    public void saveBoard(String boardTitle, String memberNick, String boardText) {
        boardDAO.saveBoard(boardTitle, memberNick, boardText);
    }

    // 게시글 조회
    @Override
    public List<BoardVo> getBoardsByPage(int pageNumber, int pageSize) {
        int offset = (pageNumber - 1) * pageSize;
        return boardDAO.getBoardsByPage(offset, pageSize);
    }

    // 게시글 서치
    @Override
    public List<BoardVo> searchBoards(String keyword) {
        return boardDAO.searchBoards(keyword);
    }

    @Override
    public List<BoardVo> searchBoardsByWriter(String keyword) {
        return boardDAO.searchBoardsByWriter(keyword);
    }

    @Override
    public List<BoardVo> searchBoardsByContents(String keyword) {
        return boardDAO.searchBoardsByContents(keyword);
    }

    @Override
    public int getTotalPageCount(int pageSize) {
        int totalBoardCount = boardDAO.getBoardCount();
        return (int) Math.ceil((double) totalBoardCount / pageSize);
    }

    // 게시글 상세보기
    @Override
    public BoardVo boardDetail(Integer board_idx) {
        return boardDAO.findById(board_idx);
    }


    //게시글 삭제
    @Override
    public void deleteBoard(Integer board_idx) {
        boardDAO.deleteBoard(board_idx);
    }

    //특정 게시글 찾기

    @Override
    public BoardVo findBoard(Integer board_idx){
        return boardDAO.findById(board_idx);
    }

    //게시글 수정
    @Override
    public void updateBoard(BoardVo BoardVo) {
        BoardVo.setBoard_idx(BoardVo.getBoard_idx());
        BoardVo.setBoardTitle(BoardVo.getBoardTitle());
        BoardVo.setMemberNick(BoardVo.getMemberNick());
        BoardVo.setBoardText(BoardVo.getBoardText());
        boardDAO.updateBoard(BoardVo);
    }
    @Override
    public void increaseBoardViews(Integer board_idx) {
        boardDAO.increaseBoardViews(board_idx);
    }

    @Override
    public BoardVo getPrevBoard(Integer boardIdx) {
        return boardDAO.getPrevBoard(boardIdx);
    }

    @Override
    public BoardVo getNextBoard(Integer boardIdx) {
        return boardDAO.getNextBoard(boardIdx);
    }

    // 내가 쓴 글 목록을 페이징하여 가져오는 메서드 구현
    @Override
    public List<BoardVo> getMyBoardsPaging(String memberNick, int pageNumber, int pageSize) {
        int offset = (pageNumber - 1) * pageSize;
        return boardDAO.getMyBoardsPaging(memberNick, offset, pageSize);
    }

    // 특정 사용자가 작성한 전체 게시글 수를 조회하는 메서드 구현
    @Override
    public int getTotalMyBoardCount(String memberNick) {
        return boardDAO.getTotalMyBoardCount(memberNick);
    }

}
