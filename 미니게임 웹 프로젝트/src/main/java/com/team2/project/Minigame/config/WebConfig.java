package com.team2.project.Minigame.config;

import com.team2.project.Minigame.interceptor.LoginInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // LoginInterceptor를 등록하여 모든 요청에 대해 인터셉터를 실행
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/boardWrite", "/saveWriteBoard", "/boardList", "/boardDetail", "/saveComment", "/deleteComment", "/boardUpdateForm", "/boardUpdate", "/boardDelete", "/myPage", "/confirmPassword", "/myPageUpdateForm", "/updateMyPage", "/QnAWrite", "/saveQnaBoard", "/QnAPage", "/QnADetail","FAQDetail")
                .excludePathPatterns("/login", "/signup", "/loginProcess");
    }
}