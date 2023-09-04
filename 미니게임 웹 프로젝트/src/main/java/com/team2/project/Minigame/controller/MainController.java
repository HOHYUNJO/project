package com.team2.project.Minigame.controller;

import com.team2.project.Minigame.service.GameService;
import com.team2.project.Minigame.vo.GameVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class MainController {
    private final GameService gameService;

    @Autowired
    public MainController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/main")
    public String mainPage(HttpSession session, Model model) {
        if (!isUserLoggedIn(session)) {
            return "redirect:/login";
        }

        List<GameVo> recentGames = gameService.getRecentGamesSortedByCreateDate();
        for (GameVo game : recentGames) {
            game.setImageFileName(getImageFileNameForGame(game)); // 이미지 파일 이름 설정
        }
        model.addAttribute("recentGames", recentGames);

//        List<GameVo> gameList = gameService.getAllGames();
//        for (GameVo game : gameList) {
//            game.setImageFileName(getImageFileNameForGame(game)); // 이미지 파일 이름 설정
//        }
//        model.addAttribute("gameList", gameList);

        List<GameVo> popularGames = gameService.getPopularGamesSortedByCount();
        for (GameVo game : popularGames) {
            game.setImageFileName(getImageFileNameForGame(game)); // 이미지 파일 이름 설정
        }
        model.addAttribute("popularGames", popularGames);

        return "main";
    }

    @GetMapping("/aboutUs")
    public String aboutUs(HttpSession session, Model model) {
        if (!isUserLoggedIn(session)) {
            return "redirect:/login";
        }
        return "aboutUs";
    }

    @GetMapping("/allGameList")
    public String allgamelist(HttpSession session, Model model) {
        if (!isUserLoggedIn(session)) {
            return "redirect:/login";
        }
        List<GameVo> gameList = gameService.getAllGames();
        for (GameVo game : gameList) {
            game.setImageFileName(getImageFileNameForGame(game)); // 이미지 파일 이름 설정
        }
        model.addAttribute("gameList", gameList);
        return "allGameList";
    }

    // 세션 검증을 수행하는 메서드
    private boolean isUserLoggedIn(HttpSession session) {
        Object authInfo = session.getAttribute("authInfo");
        return authInfo != null;
    }

    // 게임에 따른 이미지 파일 이름을 동적으로 반환하는 메서드 (여기서는 간단히 게임 ID로 파일 이름을 만들어 사용하는 예시)
    private String getImageFileNameForGame(GameVo game) {
        return game.getGameID() + ".jpg"; // 게임 ID와 확장자를 조합하여 이미지 파일 이름을 만듦
    }
}