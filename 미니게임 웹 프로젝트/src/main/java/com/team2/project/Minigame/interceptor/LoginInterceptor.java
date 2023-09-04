package com.team2.project.Minigame.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 세션에서 인증 정보 가져오기
        HttpSession session = request.getSession();
        Object authInfo = session.getAttribute("authInfo");

        // 인증 정보가 없을 경우 로그인 페이지로 이동
        if (authInfo == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return false; // 이후 컨트롤러의 핸들러 메서드 실행을 중지
        }

        return true; // 컨트롤러의 핸들러 메서드 실행을 계속 진행
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // Do something after the handler has been executed; e.g. ModelAndView
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // Do something after the complete request has finished; e.g. logging an exception
    }
}