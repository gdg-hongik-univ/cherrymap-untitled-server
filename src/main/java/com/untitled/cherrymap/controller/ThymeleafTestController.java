package com.untitled.cherrymap.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

// 웹브라우저를 통한 카카오 로그인 api 테스트 목적으로 만든 테스트용 컨트롤러(프론트에 제공하지 않음)
@Controller
@Profile("dev") // 개발 환경에서만 활성화
public class ThymeleafTestController {

    @GetMapping("/{memberId}/home")
    public String testHomePage(Model model, @AuthenticationPrincipal OAuth2User user) {
        System.out.println("User Attributes: " + user.getAttributes()); // 전체 속성 출력 for logging
        String nickname = user.getAttribute("nickname");
        String email = user.getAttribute("email");

        System.out.println("Nickname: " + nickname); // 닉네임 출력 for logging
        System.out.println("Email: " + email);       // 이메일 출력 for logging

        model.addAttribute("nickname", nickname);
        model.addAttribute("email", email);
        return "home"; // home.html 렌더링
    }

    @GetMapping("/logout-success")
    public String logoutSuccess() {
        return "logout"; // logout.html 렌더링
    }
}

