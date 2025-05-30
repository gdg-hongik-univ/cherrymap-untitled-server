package com.untitled.cherrymap.controller.auth;

import com.untitled.cherrymap.dto.auth.JoinDTO;
import com.untitled.cherrymap.repository.MemberRepository;
import com.untitled.cherrymap.service.auth.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class JoinController {
    private final JoinService joinService;
    private final MemberRepository memberRepository;

    @PostMapping("/join")
    public String joinProcess(@RequestBody JoinDTO joinDTO) {
        joinService.joinProcess(joinDTO);

        return "ok";
    }

    @GetMapping("/check-nickname")
    public ResponseEntity<?> checkNickname(@RequestParam String nickname) {
        boolean exists = memberRepository.existsByNickname(nickname);
        return ResponseEntity.ok(Map.of(
                "exists", exists,
                "message", exists ? "이미 사용 중인 닉네임입니다." : "사용 가능한 닉네임입니다."
        ));
    }

}
