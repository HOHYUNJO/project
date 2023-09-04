package com.team2.project.Minigame.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team2.project.Minigame.dao.GameCommentDAO;
import com.team2.project.Minigame.service.GameCommentService;
import com.team2.project.Minigame.service.GameService;
import com.team2.project.Minigame.vo.*;
import com.team2.project.Minigame.vo.AuthInfo;
import com.team2.project.Minigame.vo.GameCommentVo;
import com.team2.project.Minigame.vo.GameVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class GameController {
    private final GameCommentDAO gameCommentDao;
    private final GameCommentService gameCommentService;
    private final GameService gameService;

    @Autowired
    public GameController(GameCommentDAO gameCommentDao, GameCommentService gameCommentService, GameService gameService) {
        this.gameCommentDao = gameCommentDao;
        this.gameCommentService = gameCommentService;
        this.gameService = gameService;
    }
    // 게임 상세 페이지를 보여주는 엔드포인트
    @GetMapping("/gameDetail")
    public String gameDetail(@RequestParam("INDEX_NO") Integer INDEX_NO, HttpSession session, Model model) {
        // 세션에서 인증 정보 가져오기
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");

        if (INDEX_NO == null) {
            // INDEX_NO가 없을 경우 메인 페이지로 리다이렉트
            return "redirect:/main";
        }

        if (authInfo == null) {
            // 인증 정보가 없으면 로그인 페이지로 리다이렉트
            return "redirect:/login";
        }

        // INDEX_NO를 사용하여 게임 정보 조회
        GameVo gameVo = gameService.getGameById(INDEX_NO);
        model.addAttribute("gameVo", gameVo);

        // 게임과 관련된 댓글 목록 가져오기
        List<GameCommentVo> gameComments = gameCommentService.getGameCommentsByGameID(INDEX_NO); // 인덱스명 변경
        model.addAttribute("gameComments", gameComments);

        // 로그인한 회원의 닉네임을 모델에 추가
        String memberNick = authInfo.getMemberNick();
        model.addAttribute("memberNick", memberNick);

        GameVo game = gameService.gameDetail(INDEX_NO);
        gameService.increaseGameViews(INDEX_NO); // 조회수 증가
        model.addAttribute("gameVo", game);

        double gameRating = gameCommentDao.calculateGameRating(INDEX_NO);
        model.addAttribute("gameRating", gameRating); // 평균 평점을 모델에 추가

        return "gameDetail"; // 게임 상세 페이지를 위한 Thymeleaf 템플릿 이름 반환
    }

    // 게임 등록 폼을 보여주는 엔드포인트
    @GetMapping("/gameWriteForm")
    public String showGameRegistrationForm(Model model) {
        // 필요한 경우 모델에 데이터를 추가할 수 있습니다.
        // 예를 들어 드롭다운 목록이나 체크박스를 채우려면 모델에 해당 데이터를 추가할 수 있습니다.

        return "gameWriteForm"; // 게임 등록 폼을 위한 Thymeleaf 템플릿 이름 반환.
    }

    // 게임 정보를 저장하는 엔드포인트
    @PostMapping("/saveGame")
    public String saveGame(@RequestParam("gameID") String gameID,
                           @RequestParam("gameTitle") String gameTitle,
                           @RequestParam("gameDetail") String gameDetail,
                           @RequestParam("gameMaker") String gameMaker) {
        // GameVo 객체를 생성하고 사용자가 입력한 데이터를 저장
        GameVo gameVo = new GameVo();
        gameVo.setGameID(gameID);
        gameVo.setGameTitle(gameTitle);
        gameVo.setGameDetail(gameDetail);
        gameVo.setGameMaker(gameMaker);

        // GameService를 사용하여 데이터 저장
        gameService.saveGame(gameVo);

        // 저장이 완료된 후 메인 페이지로 리다이렉트
        return "redirect:/main";

    }

    // 댓글 저장
    @RequestMapping(value ="/saveGameComment", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GameCommentVo> saveGameComment(@RequestBody Map<String, String> request, Model model, HttpSession session) {
        String commentText = request.get("commentText");
        Integer INDEX_NO = Integer.parseInt(request.get("INDEX_NO"));
        Double rating = Double.parseDouble(request.get("rating"));

        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        String memberNick = authInfo.getMemberNick();

        // GameCommentVo 객체 생성
        GameCommentVo gameComment = new GameCommentVo();
        gameComment.setCommentNick(memberNick);
        gameComment.setCommentText(commentText);
        gameComment.setINDEX_NO(INDEX_NO);
        gameComment.setRating(rating);

        gameCommentService.saveGameComment(gameComment);

        return new ResponseEntity<>(gameComment, HttpStatus.OK);
    }

    // 댓글 삭제
    @RequestMapping(value = "/deleteGameComment/{commentID}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteGameComment(@PathVariable("commentID") int commentID) {
        gameCommentService.deleteGameComment(commentID);
        return new ResponseEntity<>("한줄평이 삭제되었습니다.", HttpStatus.OK);
    }

    //게임 삭제
    @GetMapping("/gameDelete")
    public String gameDelete(@RequestParam(value = "INDEX_NO", required = false) Integer INDEX_NO, HttpSession session) {
        try {
            AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
            if (authInfo == null) {
                // 인증 정보가 없으면 로그인 페이지로 리다이렉트
                return "redirect:/login";
            }

            if (INDEX_NO != null) {
                // INDEX_NO 파라미터가 제대로 전달된 경우에만 게임 삭제 수행
                gameService.deleteGame(INDEX_NO);
            }

            return "redirect:/main";
        } catch (Exception e) {
            return "redirect:/login";
        }
    }
    @GetMapping("/gameUpdateForm")
    public String boardUpdate(@RequestParam("INDEX_NO") Integer INDEX_NO, Model model, HttpSession session) {
        // 세션에서 인증 정보 가져오기
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        if (authInfo == null) {
            // 인증 정보가 없으면 로그인 페이지로 리다이렉트
            return "redirect:/login";
        }

        GameVo gameVo = gameService.gameDetail(INDEX_NO);
        model.addAttribute("gameVo", gameVo);
        return "gameUpdateForm";
    }

    // 게시물 수정
    @PostMapping("/updateGame")
    public String gameUpdate(@RequestParam("INDEX_NO") Integer INDEX_NO,
                             @RequestParam("gameID") String gameID,
                             @RequestParam("gameTitle") String gameTitle,
                             @RequestParam("gameDetail") String gameDetail,
                             @RequestParam("gameMaker") String gameMaker,
                             Model model) {
        // INDEX_NO를 사용하여 게임 정보 조회
        GameVo gameVo = gameService.getGameById(INDEX_NO);

        if (gameVo != null) {
            // 게임 정보가 존재하는 경우에만 업데이트 수행
            gameVo.setGameID(gameID);
            gameVo.setGameTitle(gameTitle);
            gameVo.setGameDetail(gameDetail);
            gameVo.setGameMaker(gameMaker);

            // GameService를 사용하여 데이터 업데이트
            gameService.updateGame(gameVo);

            // 업데이트된 게임 정보를 모델에 추가
            model.addAttribute("gameVo", gameVo);
        }

        return "redirect:/gameDetail?INDEX_NO=" + INDEX_NO;
    }
}
