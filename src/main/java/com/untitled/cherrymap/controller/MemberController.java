package com.untitled.cherrymap.controller;

import com.untitled.cherrymap.domain.Member;
import com.untitled.cherrymap.dto.PhoneNumberRequest;
import com.untitled.cherrymap.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name="Member-Controller",description = "유저 정보 조회 API")
@RequestMapping("/api/{providerId}")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "사용자 정보 요청", description = "providerId에 해당하는 유저 정보를 json 형태로 반환.")
    @GetMapping("/info")
    public ResponseEntity<Member> getMember(@PathVariable String providerId) {
        Member member = memberService.getMemberByProviderId(providerId);
        return ResponseEntity.ok().body(member);
    }

    @Operation(summary = "비상연락처 조회", description = "사용자의 비상연락처를 조회합니다.")
    @GetMapping("/phone")
    public ResponseEntity<String> getPhoneNumber(@PathVariable String providerId) {
        String phoneNumber = memberService.getPhoneNumber(providerId);
        return ResponseEntity.ok(phoneNumber);
    }

    @Operation(summary = "비상연락처 등록 및 변경", description = "비상연락처를 등록 및 변경합니다.")
    @PutMapping("/phone")
    public ResponseEntity<Void> updatePhoneNumber(@PathVariable String providerId, @RequestBody @Valid PhoneNumberRequest request) {
        memberService.updatePhoneNumber(providerId, request.getPhoneNumber());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "비상연락처 삭제", description = "비상연락처를 삭제합니다.")
    @DeleteMapping("/phone")
    public ResponseEntity<Void> deletePhoneNumber(@PathVariable String providerId) {
        memberService.deletePhoneNumber(providerId);
        return ResponseEntity.noContent().build();
    }
}