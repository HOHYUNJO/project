package com.team2.project.Minigame.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.internet.MimeMessage;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;
    private String verificationCode;

//    @PostMapping("/emailConfirm")
//    public String emailConfirm(@RequestParam String email) throws Exception {
//        String confirmationCode = emailService.sendSimpleMessage(email);
//        return confirmationCode;
//    }

    @PostMapping("/emailConfirm")
    public ResponseEntity<String> emailConfirm(@RequestParam String email) {
        try {
            String confirmationCode = emailService.sendSimpleMessage(email);
            return ResponseEntity.ok(confirmationCode);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("이메일 전송에 실패하였습니다.");
        }
    }

    @PostMapping("/codeConfirm")
    public ResponseEntity<String> codeConfirm(@RequestParam String verificationCode) {
        if (verificationCode.equals(emailService.getServerCode())) {
            return ResponseEntity.ok("인증 성공");
        } else {
            return ResponseEntity.badRequest().body("인증 실패");
        }
    }
}