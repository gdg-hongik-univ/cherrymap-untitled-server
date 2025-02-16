package com.untitled.cherrymap.controller;

import com.untitled.cherrymap.domain.Member;
import com.untitled.cherrymap.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/api/{providerId}/info")
    public ResponseEntity<Member> getMember(@PathVariable String providerId) {
        Member member = memberService.getMemberByProviderId(providerId);
        return ResponseEntity.ok().body(member);
    }
}