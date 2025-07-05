package com.untitled.cherrymap.domain.member.api;

import com.untitled.cherrymap.domain.member.domain.Member;
import com.untitled.cherrymap.domain.member.application.MemberService;
import com.untitled.cherrymap.domain.member.dto.PhoneNumberRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name="Member-Controller",description = "유저 정보 조회 API")
@RequestMapping("/api/{Id}")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "사용자 정보 요청", description = "member_Id에 해당하는 유저 정보를 json 형태로 반환.")
    @GetMapping("/info")
    public ResponseEntity<Member> getMember(@PathVariable Long id) {
        Member member = memberService.getMemberByMemberId(id);
        return ResponseEntity.ok().body(member);
    }

    @Operation(summary = "비상연락처 조회", description = "사용자의 비상연락처를 조회합니다.")
    @GetMapping("/phone")
    public ResponseEntity<String> getPhoneNumber(@PathVariable Long id) {
        String phoneNumber = memberService.getPhoneNumber(id);
        return ResponseEntity.ok(phoneNumber);
    }

    @Operation(summary = "비상연락처 등록 및 변경", description = "비상연락처를 등록 및 변경합니다.")
    @PutMapping("/phone")
    public ResponseEntity<Void> updatePhoneNumber(@PathVariable Long id, @RequestBody @Valid PhoneNumberRequest request) {
        memberService.updatePhoneNumber(id, request.getPhoneNumber());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "비상연락처 삭제", description = "비상연락처를 삭제합니다.")
    @DeleteMapping("/phone")
    public ResponseEntity<Void> deletePhoneNumber(@PathVariable Long id) {
        memberService.deletePhoneNumber(id);
        return ResponseEntity.noContent().build();
    }
}