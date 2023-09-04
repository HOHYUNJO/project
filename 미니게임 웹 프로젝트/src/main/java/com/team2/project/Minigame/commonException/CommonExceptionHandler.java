package com.team2.project.Minigame.commonException;

import com.team2.project.Minigame.exception.MemberNotFoundException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice("com.team2")
public class CommonExceptionHandler { // 예외 처리 전용 클래스

    @ExceptionHandler(TypeMismatchException.class)  // id에 숫자가 아닌 값
    public String handlerTypeMismatchException(TypeMismatchException e) {
//      e.printStackTrace();
        return "login";
    }

    @ExceptionHandler(MemberNotFoundException.class)  // id에 존재하지 않는 번호
    public String handlerMemberNotFoundException(MemberNotFoundException e) {
//      e.printStackTrace();
        return "login";
    }

    @ExceptionHandler(RuntimeException.class)
    public String handlerRunTimeException(RuntimeException e) {  // 대부분 예외를 처리할 클래스
        System.out.println("에러 코드 : "+e.getMessage());
        System.out.println("---------------------------------");
        e.printStackTrace();
        return "login";
    }


}