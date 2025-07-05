package com.untitled.cherrymap.security;

import com.untitled.cherrymap.common.dto.SuccessResponse;
import com.untitled.cherrymap.domain.member.exception.DuplicateNicknameException;
import com.untitled.cherrymap.security.dto.JoinDTO;
import com.untitled.cherrymap.domain.member.dao.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin
public class JoinController {
    private final JoinService joinService;
    private final MemberRepository memberRepository;

    @PostMapping("/join")
    public ResponseEntity<SuccessResponse> joinProcess(@RequestBody JoinDTO joinDTO) {
        joinService.joinProcess(joinDTO);
        return ResponseEntity.ok(
                SuccessResponse.success(201, Map.of("nickname", joinDTO.getNickname(), "message", "회원가입 완료"))
        );
    }

    @GetMapping("/check-nickname")
    public ResponseEntity<SuccessResponse<Map<String, Object>>> checkNickname(@RequestParam String nickname) {
        boolean exists = memberRepository.existsByNickname(nickname);

        if (exists) {
            throw DuplicateNicknameException.EXCEPTION;
        }

        return ResponseEntity.ok(
                SuccessResponse.success(200, Map.of(
                        "message", "사용 가능한 닉네임입니다."
                ))
        );
    }


}
