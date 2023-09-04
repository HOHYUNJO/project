package com.team2.project.Minigame.controller;

import com.team2.project.Minigame.dao.MemberDAOImpl;
import com.team2.project.Minigame.service.BoardService;
import com.team2.project.Minigame.vo.AuthInfo;
import com.team2.project.Minigame.vo.BoardVo;
import com.team2.project.Minigame.vo.MemberVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class MyPageController {
    private final MemberDAOImpl memberDAO;
    private final BoardService boardService;

    @Autowired
    public MyPageController(MemberDAOImpl memberDAO, BoardService boardService) {
        this.memberDAO = memberDAO;
        this.boardService = boardService;
    }

    @GetMapping("/myPage")
    public String myPage(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
                         Model model, HttpSession session) {
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        if (authInfo == null) {
            return "redirect:/login";
        }

        String memberNick = authInfo.getMemberNick();
        int pageSize = 10; // Number of boards to show per page

        // 전체 게시글 개수를 가져옵니다.
        int totalBoardCount = boardService.getTotalMyBoardCount(memberNick);

        // 총 페이지 수 계산
        int totalPages = (int) Math.ceil((double) totalBoardCount / pageSize);

        // 페이지 번호를 1 이상으로 제한
        if (pageNumber <= 0) {
            pageNumber = 1;
        } else if (pageNumber > totalPages) {
            pageNumber = totalPages;
        }

        // 해당 페이지에 해당하는 게시글 목록을 가져옵니다.
        List<BoardVo> myBoards = boardService.getMyBoardsPaging(memberNick, pageNumber, pageSize);

        model.addAttribute("authInfo", authInfo);
        model.addAttribute("myBoards", myBoards);
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalPages", totalPages);

        return "myPage";
    }



    @PostMapping("/confirmPassword")
    @ResponseBody
    public ResponseEntity<?> confirmPassword(@RequestParam("password") String password, HttpSession session) {
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");

        if (memberDAO.isPasswordCorrect(authInfo.getMemberId(), password)) {
            session.setAttribute("isPasswordCorrect", true);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디 또는 비밀번호를 확인하세요");
        }
    }

    @GetMapping("/myPageUpdateForm")
    public String myPageUpdateForm(Model model, HttpSession session) {
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        model.addAttribute("authInfo", authInfo);
        return "myPageUpdateForm";
    }

    @PostMapping("/updateMyPage")
    public String myPageUpdate(@RequestParam("memberNick") String memberNick,
                               @RequestParam(value = "newPassword", required = false) String newPassword,
                               @RequestParam("memberEmail") String memberEmail,
                               @ModelAttribute("authInfo") AuthInfo authInfo,
                               Model model, HttpSession session) {
        // 회원 정보 업데이트
        MemberVo member = new MemberVo();
        member.setMemberNick(memberNick);

        // 비밀번호가 비어있는 경우 처리
        if (newPassword.isEmpty() || newPassword.isBlank()) {
            member.setMemberPassword(authInfo.getMemberPassword());
        } else {
            member.setMemberPassword(newPassword);
        }

        member.setMemberEmail(memberEmail);
        member.setMemberId(authInfo.getMemberId());

        // DAO를 사용하여 회원 정보 업데이트
        memberDAO.updateMypage(member);

        // 업데이트된 정보를 authInfo 객체에 반영
        authInfo.setMemberNick(member.getMemberNick());
        authInfo.setMemberEmail(member.getMemberEmail());

        // 세션과 모델에 authInfo 저장
        session.setAttribute("authInfo", authInfo);
        model.addAttribute("authInfo", authInfo);

        // myPage 페이지로 리다이렉트
        return "redirect:/myPage";
    }
}
