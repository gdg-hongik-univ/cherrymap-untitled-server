package com.untitled.cherrymap.security;

import com.untitled.cherrymap.security.jwt.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dev")
public class DevTokenController {

    private final JWTUtil jwtUtil;

    // 테스트용 JWT 발급 API
    @PostMapping("/token")
    public String createToken(@RequestParam(defaultValue = "access") String category,
                              @RequestParam(defaultValue = "1") String memberId,
                              @RequestParam(defaultValue = "ROLE_ADMIN") String role,
                              @RequestParam(defaultValue = "86400000") Long expiredMs) {
        return jwtUtil.createJwt(category, memberId, role, expiredMs);
    }
}
