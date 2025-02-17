package com.untitled.cherrymap.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// 웹브라우저를 통한 카카오 로그인 api 테스트 목적으로 만든 테스트용 컨트롤러(프론트에 제공하지 않음)
@Controller
@Profile("dev") // 개발 환경에서만 활성화
public class ThymeleafTestController {

    @GetMapping("/{providerId}/home")
    public String testHomePage(@PathVariable String providerId, Model model) {
        System.out.println("Provider ID: " + providerId); // providerId 로깅

        model.addAttribute("providerId", providerId); // providerId 전달
        return "home"; // home.html 렌더링
    }

    @GetMapping("/logout-success")
    public String logoutSuccess() {
        return "logout"; // logout.html 렌더링
    }
}

