package com.untitled.cherrymap.controller;

import com.untitled.cherrymap.domain.Member;
import com.untitled.cherrymap.repository.MemberRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class FrontendApiController {

    private final MemberRepository memberRepository;

    public FrontendApiController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @GetMapping("/{memberId}/info")
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal OAuth2User user) {
        if (user == null) {
            return ResponseEntity.badRequest().body("User not authenticated");
        }

        String providerId = user.getAttribute("id");
        Member member = memberRepository.findByProviderId(providerId);

        if (member == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(Map.of(
                "nickname", member.getNickname(),
                "email", member.getEmail()
        ));
    }
}