package com.team2.project.Minigame.controller;

import com.team2.project.Minigame.service.QnAService;
import com.team2.project.Minigame.vo.QnAVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class QNAController {

    private final QnAService qnaService;

    @Autowired
    public QNAController(QnAService qnaService) {
        this.qnaService = qnaService;
    }

    @GetMapping("/QnAWrite") // 게시글 작성 페이지
    public String QnAWriteForm() {
        return "QnAWriteForm";
    }

    @PostMapping("/saveQnaBoard")
    public String saveQnaBoard(@RequestParam("QNA_Title") String QNA_Title,
                               @RequestParam("memberNick") String memberNick,
                               @RequestParam("QNA_Text") String QNA_Text,
                               RedirectAttributes redirectAttributes) {
        // QnA글 데이터베이스에 저장
        qnaService.saveQnaBoard(QNA_Title, memberNick, QNA_Text);

        // 새로운 글 목록 가져와서 리다이렉트 시에 전달
        List<QnAVo> qnaList = qnaService.getQnAList();
        redirectAttributes.addFlashAttribute("qnaList", qnaList);

        return "redirect:/QnAPage";
    }

    @GetMapping("/QnAPage")
    public String QnAPage(Model model) {
        List<QnAVo> qnaList = qnaService.getQnAList();
        model.addAttribute("qnaList", qnaList);
        return "QnAPage";
    }

    @GetMapping("/QnADetail")
    public String QnADetail(Model model, @RequestParam("QNA_idx") Integer QNA_idx, HttpSession session) {
        QnAVo qnaVo = qnaService.getQnADetail(QNA_idx);
        qnaService.increaseQnABoardViews(QNA_idx);
        model.addAttribute("qna", qnaVo);

        // 이전 글과 다음 글 가져오기
        QnAVo prevQnA = qnaService.getPrevQnA(QNA_idx);
        QnAVo nextQnA = qnaService.getNextQnA(QNA_idx);
        model.addAttribute("prevQnA", prevQnA);
        model.addAttribute("nextQnA", nextQnA);

        return "QnADetail";
    }

    @GetMapping("/QnABoardUpdateForm")
    public String updateQnABoard(@RequestParam("QNA_idx") Integer QNA_idx, Model model) {
        QnAVo qnAVo = qnaService.findQnAById(QNA_idx);
        model.addAttribute("qna", qnAVo);
        return "QnAUpdateForm";
    }

    @PostMapping("/QnAUpdate")
    public String updateQnABoard(@RequestParam("QNA_idx") Integer QNA_idx,
                                 @RequestParam("QNA_Title") String QNA_Title,
                                 @RequestParam("memberNick") String memberNick,
                                 @RequestParam("QNA_Text") String QNA_Text) {
        QnAVo qnAVo = qnaService.findQnAById(QNA_idx);
        if (qnAVo != null) {
            qnAVo.setQNA_Title(QNA_Title);
            qnAVo.setQNA_Text(QNA_Text);
            qnaService.updateQnABoard(qnAVo);
        }
        return "redirect:/QnAPage";
    }

    @GetMapping("/QnAboardDelete")
    public String deleteQnABoard(@RequestParam("QNA_idx") Integer QNA_idx) {
        qnaService.deleteQnABoard(QNA_idx);
        return "redirect:/QnAPage";
    }
}
