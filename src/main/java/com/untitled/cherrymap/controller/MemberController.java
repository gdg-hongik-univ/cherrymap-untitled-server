package com.untitled.cherrymap.controller;

import com.untitled.cherrymap.domain.Member;
import com.untitled.cherrymap.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name="Member-Controller",description = "유저 정보 조회 API")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "사용자 정보 요청", description = "providerId에 해당하는 유저 정보를 json 형태로 반환.")
    @GetMapping("/api/{providerId}/info")
    public ResponseEntity<Member> getMember(@PathVariable String providerId) {
        Member member = memberService.getMemberByProviderId(providerId);
        return ResponseEntity.ok().body(member);
    }
}