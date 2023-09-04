package com.team2.project.Minigame.controller;

import com.team2.project.Minigame.dao.CommentDAO;
import com.team2.project.Minigame.service.BoardService;
import com.team2.project.Minigame.service.CommentService;
import com.team2.project.Minigame.vo.AuthInfo;
import com.team2.project.Minigame.vo.BoardVo;
import com.team2.project.Minigame.vo.CommentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class BoardController {

    public enum SearchType {
        WRITER,
        CONTENTS,
        ALL
    }


    private final BoardService boardService;
    private final CommentDAO commentDAO;

    private CommentService commentService;

    @Autowired
    public BoardController(BoardService boardService, CommentDAO commentDAO, CommentService commentService) {
        this.boardService = boardService;
        this.commentDAO = commentDAO;
        this.commentService = commentService;
    }

    //게시글 검색 ajax
    @GetMapping("/search")
    public ResponseEntity<List<BoardVo>> searchBoards(@RequestParam("keyword") String keyword,
                                                      @RequestParam("type") SearchType type,Model model) {
        List<BoardVo> searchResults;
        if (type == SearchType.WRITER) {
            searchResults = boardService.searchBoardsByWriter(keyword);
        } else if (type == SearchType.CONTENTS) {
            searchResults = boardService.searchBoardsByContents(keyword);
        } else if (type == null || type == SearchType.ALL) {
            // 제목과 내용에서 검색 수행
            searchResults = boardService.searchBoards(keyword);
        } else {
            searchResults = boardService.searchBoards(keyword);
        }
        model.addAttribute("board",searchResults);
        return ResponseEntity.ok(searchResults);
    }

    //  게시글 Search
//    @GetMapping("/search")
//    public String searchBoards(@RequestParam("keyword") String keyword,
//                               @RequestParam("type") SearchType type,
//                               Model model) {
//        List<BoardVo> searchResults;
//        if (type == SearchType.WRITER) {
//            searchResults = boardService.searchBoardsByWriter(keyword);
//        } else if (type == SearchType.CONTENTS) {
//            searchResults = boardService.searchBoardsByContents(keyword);
//        } else if(type == null || type == SearchType.ALL){
//            // 제목과 내용에서 검색 수행
//            searchResults = boardService.searchBoards(keyword);
//        } else {
//            searchResults = boardService.searchBoards(keyword);
//        }
//
//        model.addAttribute("list", searchResults);
//        return "boardList"; // 검색 결과를 보여주는 뷰 페이지 반환
//    }


    @GetMapping("boardWrite")
    public String boardWrite(){
        return "boardWriteForm";
    }

    // 작성한 게시물 저장 후 boardList로 리다이렉트
    @PostMapping("/saveWriteBoard")
    public String saveWriteBoard(@RequestParam("boardTitle") String boardTitle,
                                 @RequestParam("memberNick") String memberNick,
                                 @RequestParam("boardText") String boardText) {
    //게시글을 데이터베이스에 저장
        boardService.saveBoard(boardTitle, memberNick, boardText);
        return "redirect:/boardList";
    }

    // boardList로 가기
    @GetMapping("/boardList")
    public String boardList(Model model, HttpSession session,
                            @RequestParam(value = "page", defaultValue = "1") int pageNumber,
                            @RequestParam(value = "size", defaultValue = "10") int pageSize) {

        // 세션에서 인증 정보 가져오기
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        if (authInfo == null) {
            // 인증 정보가 없으면 로그인 페이지로 리다이렉트
            return "redirect:/login";
        }

        // 전체 페이지 수 가져오기
        int totalPageCount = boardService.getTotalPageCount(pageSize);

        // 현재 페이지가 유효한 범위인지 확인
        pageNumber = Math.max(1, Math.min(pageNumber, totalPageCount));

        // 시작 페이지와 끝 페이지 계산
        int maxPageNumbers = 5;
        int startPage = Math.max(1, pageNumber - (maxPageNumbers / 2));
        int endPage = Math.min(totalPageCount, startPage + maxPageNumbers - 1);

        // 시작 페이지가 maxPageNumbers 개수만큼 표시되도록 보정
        if (endPage - startPage + 1 < maxPageNumbers) {
            startPage = Math.max(1, endPage - maxPageNumbers + 1);
        }

        // 현재 페이지에 해당하는 게시물 가져오기
        List<BoardVo> boards = boardService.getBoardsByPage(pageNumber, pageSize);

        // 모델에 데이터 추가
        model.addAttribute("list", boards);
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPageCount", totalPageCount);
        model.addAttribute("hasPrevPage", pageNumber > 1);
        model.addAttribute("hasNextPage", pageNumber < totalPageCount);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "boardList";
    }

    // 특정 게시글 상세보기
    @GetMapping("/boardDetail")
    public String boardDetail(Model model, @RequestParam("board_idx") Integer board_idx, HttpSession session) {
        // 세션에서 인증 정보 가져오기
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        if (authInfo == null) {
            // 인증 정보가 없으면 로그인 페이지로 리다이렉트
            return "redirect:/login";
        }

        BoardVo board = boardService.boardDetail(board_idx);
        boardService.increaseBoardViews(board_idx); // 조회수 증가
        model.addAttribute("board", board);

        // 이전 글과 다음 글 가져오기
        BoardVo prevBoard = boardService.getPrevBoard(board_idx);
        BoardVo nextBoard = boardService.getNextBoard(board_idx);
        model.addAttribute("prevBoard", prevBoard);
        model.addAttribute("nextBoard", nextBoard);

        // 댓글 목록 가져오기
        authInfo = (AuthInfo) session.getAttribute("authInfo");
        String memberNick = authInfo.getMemberNick();
        model.addAttribute("memberNick", memberNick);
        List<CommentVo> comments = commentDAO.getCommentsByBoardNum(board_idx);
        model.addAttribute("comments", comments);

        return "boardDetail";
    }

    // 댓글 저장
    @RequestMapping (value = "/saveComment", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveComment(@RequestBody Map<String, String> request, Model model, HttpSession session) {
        String commentText = request.get("commentText");
        Integer boardNum = Integer.parseInt(request.get("boardNum"));

        // 세션에서 AuthInfo 가져오기
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        String memberNick = authInfo.getMemberNick();

        // CommentVo 객체 생성
        CommentVo comment = new CommentVo();
        comment.setCommentNick(memberNick);
        comment.setCommentText(commentText);
        comment.setBoardNum(boardNum);

        // CommentService를 사용하여 댓글 저장
        commentService.saveComment(comment);

        model.addAttribute("comment", comment);

        return new ResponseEntity<>("댓글이 저장되었습니다.", HttpStatus.OK);
    }

    // 댓글 삭제
    @RequestMapping (value = "/deleteComment/{commentId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteComment(@PathVariable ("commentId") Integer commentId) {
        commentService.deleteComment(commentId);
        return new ResponseEntity<>("댓글이 삭제되었습니다.", HttpStatus.OK);
    }

    // 게시물 수정 페이지로 이동
    @GetMapping("/boardUpdateForm")
    public String boardUpdate(@RequestParam("board_idx") Integer board_idx, Model model, HttpSession session) {
        // 세션에서 인증 정보 가져오기
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        if (authInfo == null) {
            // 인증 정보가 없으면 로그인 페이지로 리다이렉트
            return "redirect:/login";
        }

        BoardVo board = boardService.boardDetail(board_idx);
        model.addAttribute("board", board);
        return "boardUpdateForm";
    }

    // 게시물 수정
    @PostMapping("/boardUpdate")
    public String boardUpdate(@RequestParam("board_idx") Integer board_idx,
                              @RequestParam("boardTitle") String boardTitle,
                              @RequestParam("memberNick") String memberNick,
                              @RequestParam("boardText") String boardText, Model model) {
        BoardVo boardVo = boardService.findBoard(board_idx);
        boardVo.setBoardTitle(boardTitle);
        boardVo.setBoardText(boardText);
        boardService.updateBoard(boardVo);
        model.addAttribute("board",boardVo);
        return "redirect:/boardList";
    }

    //게시물 삭제
    @GetMapping("/boardDelete")
    public String boardDelete(@RequestParam(value = "board_idx", required = false) Integer board_idx, HttpSession session) {
        try {
            AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
            if (authInfo == null) {
                // 인증 정보가 없으면 로그인 페이지로 리다이렉트
                return "redirect:/login";
            }

            if (board_idx != null) {
                // board_idx 파라미터가 제대로 전달된 경우에만 게시물 삭제 수행
                boardService.deleteBoard(board_idx);
            }

            return "redirect:/boardList";
        } catch (Exception e) {
            return "redirect:/login";
        }
    }

}