package com.untitled.cherrymap.domain.member.api;

import com.untitled.cherrymap.common.dto.SuccessResponse;
import com.untitled.cherrymap.domain.member.application.MemberService;
import com.untitled.cherrymap.domain.member.domain.Member;
import com.untitled.cherrymap.domain.member.dto.MemberResponse;
import com.untitled.cherrymap.domain.member.dto.PhoneNumberRequest;
import com.untitled.cherrymap.domain.member.dto.PhoneNumberResponse;
import com.untitled.cherrymap.security.dto.CustomUserDetailsDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Tag(name="Member-Controller",description = "사용자 정보 및 비상연락처 관리 API")
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "사용자 정보 요청", description = "로그인한 사용자의 정보를 json 형태로 반환.")
    @GetMapping("/info")
    public ResponseEntity<SuccessResponse<MemberResponse>> getMember(
            @AuthenticationPrincipal CustomUserDetailsDTO user) {
        return ResponseEntity.ok(
                SuccessResponse.success(200, MemberResponse.from(user.getMember()))
        );
    }

    @Operation(summary = "비상연락처 조회", description = "로그인한 사용자의 비상연락처를 조회합니다.")
    @GetMapping("/phone")
    public ResponseEntity<SuccessResponse<PhoneNumberResponse>> getPhoneNumber(
            @AuthenticationPrincipal CustomUserDetailsDTO user) {
        PhoneNumberResponse response = memberService.getPhoneNumber(user.getMember());
        return ResponseEntity.ok(
                SuccessResponse.success(200, response)
        );
    }

    @Operation(summary = "비상연락처 등록 및 변경", description = "비상연락처를 등록 및 변경합니다. xxx-xxxx-xxxx(휴대폰) 또는 xxx-xxx-xxxx(유선전화) 형식으로 입력해주세요.")
    @PutMapping("/phone")
    public ResponseEntity<SuccessResponse<Map<String, Object>>> updatePhoneNumber(
            @AuthenticationPrincipal CustomUserDetailsDTO user,
            @RequestBody @Valid PhoneNumberRequest request) {
        memberService.updatePhoneNumber(user.getMember(), request.getPhoneNumber());
        return ResponseEntity.ok(
                SuccessResponse.success(200, Map.of(
                        "message", "비상연락처가 성공적으로 등록/변경되었습니다."
                ))
        );
    }

    @Operation(summary = "비상연락처 삭제", description = "비상연락처를 삭제합니다.")
    @DeleteMapping("/phone")
    public ResponseEntity<SuccessResponse<Map<String, Object>>> deletePhoneNumber(
            @AuthenticationPrincipal CustomUserDetailsDTO user) {
        memberService.deletePhoneNumber(user.getMember());
        return ResponseEntity.ok(
                SuccessResponse.success(200, Map.of(
                        "message", "비상연락처가 성공적으로 삭제되었습니다."
                ))
        );
    }
}