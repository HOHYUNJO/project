package com.team2.project.Minigame.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Random;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public static final int VERIFICATION_CODE_LENGTH = 8;
    private String serverCode;

    private String generateVerificationCode() {
        StringBuilder verificationCode = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < VERIFICATION_CODE_LENGTH; i++) {
            int index = random.nextInt(3); // 0~2까지 랜덤

            switch (index) {
                case 0:
                    verificationCode.append((char) ((int) (random.nextInt(26)) + 97));
                    //  a~z  (ex. 1+97=98 => (char)98 = 'b')
                    break;
                case 1:
                    verificationCode.append((char) ((int) (random.nextInt(26)) + 65));
                    //  A~Z
                    break;
                case 2:
                    verificationCode.append(random.nextInt(10));
                    // 0~9
                    break;
            }
        }

        return verificationCode.toString();
    }

    private MimeMessage createMessage(String to, String verificationCode) throws Exception {
        System.out.println("보내는 대상: " + to);
        System.out.println("인증 번호: " + verificationCode);
        MimeMessage message = emailSender.createMimeMessage();

        message.addRecipients(Message.RecipientType.TO, to); // 보내는 대상
        message.setSubject("[MiniGameWorld] 이메일 인증"); // 제목

        String msgg = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Title</title>\n" +
                "    <style>\n" +
                "      *{\n" +
                "        margin: 0;\n" +
                "        padding: 0;\n" +
                "        box-sizing: border-box;\n" +
                "      }\n" +
                "      body{\n" +
                "        /*background-image: url(\"https://i.pinimg.com/originals/64/2a/f0/642af038ade1177eea36aab98a9e546b.jpg\");*/\n" +
                "        background-color: rgba(169, 164, 164, 0.82);\n" +
                "      }\n" +
                "      .main{\n" +
                "        width: 100%;\n" +
                "        margin: 0 auto;\n" +
                "      }\n" +
                "      .logoImg{\n" +
                "        width: 120px;\n" +
                "        height: 120px;\n" +
                "        margin: 0 auto;\n" +
                "        /*margin-top: 30px;*/\n" +
                "        border-radius: 150px;\n" +
                "      }\n" +
                "      .LogoH1{\n" +
                "        margin-top: -10px;\n"+
                "        height: 100%;\n" +
                "        font-size: 2.2em;\n" +
                "      }\n" +
                "      .Logo-area{\n" +
                "        width: 100%;\n" +
                "        margin-top: 15px;\n" +
                "        /*border: 1px solid black;*/\n" +
                "        text-align: center;\n" +
                "      }\n" +
                "      .Contents{\n" +
                "        border: 1px solid black;\n" +
                "        /*margin-top: 30px;*/\n" +
                "        width: 820px;\n" +
                "        margin: 30px auto 0 auto;\n" +
                "        background-color: rgba(211, 209, 209, 0.75);\n" +
                "        /*height: 700px;*/\n" +
                "        padding: 20px;\n" +
                "      }\n" +
                "      .codeBox{\n" +
                "        /*border: 2px solid #fff;*/\n" +
                "        border-radius: 5px;\n" +
                "        padding: 22px 2px 22px 2px;\n" +
                "        width: 220px;\n" +
                "        margin: 15px auto;\n" +
                "        background: linear-gradient(270deg, #00daef, #bc67ff);\n" +
                "      }\n" +
                "      #verificationCode{\n" +
                "        text-align: center;\n" +
                "          color: #fff;\n" +
                "      }\n" +
                "      .vertical-Line{\n" +
                "        border: 3px #929aa3 solid;\n" +
                "        border-radius: 3px;\n" +
                "        width: 98%;\n" +
                "        margin: 20px auto;\n" +
                "      }\n" +
                "      .warning-text{\n" +
                "        color: red;\n" +
                "        font-weight: bold;\n" +
                "        font-size: 9pt;\n" +
                "      }\n" +
                "      .emailTag{\n" +
                "          font-size: 9pt;\n" +
                "          color: rgb(98, 45, 45);\n" +
                "      }\n" +
                "      .alert-message{\n" +
                "          font-size: 11pt;\n" +
                "          font-weight: 700;\n" +
                "          margin-bottom: 1.5px;\n" +
                "      }\n" +
                "      .custom_Atag{\n" +
                "          color: rgb(98,45,45);\n" +
                "      }\n" +
                "      .custom_Atag:hover{\n" +
                "          cursor: pointer;\n" +
                "      }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "  <div class=\"main\">\n" +
                "    <div class=\"Logo-area\">\n" +
                "      <img class=\"logoImg\" src=\"https://i.pinimg.com/originals/64/2a/f0/642af038ade1177eea36aab98a9e546b.jpg\">\n" +
                "      <h1 class=\"LogoH1\">MiniGameWorld!</h1>\n" +
                "    </div>\n" +
                "    <div class=\"Contents\">\n" +
                "      <h2 style=\"margin-bottom: 15px;\">안녕하세요"+to+"님!</h2>\n" +
                "      <p style=\"margin-bottom: 1.5px;\">저희 MiniGameWorld에 가입해 주셔서 감사합니다!</p>\n" +
                "      <p style=\"margin-bottom: 1.5px;\">시작하기전! 본인 확인을 위해 아래의 인증코드를 이메일확인란에 입력해주세요 :)</p>\n" +
                "      <p class=\"warning-text\">*운영자는 인증코드를 요구하지않습니다.</p>\n" +
                "      <div class=\"codeBox\">\n" +
                "        <h2 id=\"verificationCode\">"+verificationCode+"</h2>\n" +
                "      </div>\n" +
                "      <div class=\"vertical-Line\"></div>\n" +
                "      <p class=\"alert-message\">도움이 필요하신가요? <span class=\"emailTag\">Team2EamilService@gmail.com</span> 으로 문의해주세요!</p>\n" +
                "      <p class=\"alert-message\">피드백을 주고 싶으신가요? 저희의 <a class=\"custom_Atag\" onclick=\"alert('준비중!')\">피드백 사이트</a>에서 당신의 생각을 들려주세요!</p>\n" +
                "      <p class=\"alert-message\">개인정보관리 책임자 : 조호현 (Team2EmailService@gmail.com)</p>\n" +
                "      <p class=\"alert-message\">근무시간: 월 ~ 금 9:00 ~ 19:00 | 점심시간 : 12:00 ~ 13:00</p>\n" +
                "    </div>\n" +
                "  </div>\n" +
                "</body>\n" +
                "</html>";


        message.setText(msgg, "utf-8", "html"); // 내용
        message.setFrom(new InternetAddress("Team2EmailService@gmail.com", "MiniGameWorld™")); // 보내는 사람



        return message;
    }

    @Override
    public String sendSimpleMessage(String to) throws Exception {
        String verificationCode = generateVerificationCode();
        serverCode = generateVerificationCode();
        MimeMessage message = createMessage(to, verificationCode);

        try {
            emailSender.send(message);
        } catch (MailException ex) {
            ex.printStackTrace();
            throw new IllegalArgumentException("이메일 전송에 실패하였습니다.");
        }

        serverCode = verificationCode; // 서버의 인증 코드 값 설정

        return verificationCode;
    }

    @Override
    public String getServerCode() {
        return serverCode;
    }

}
