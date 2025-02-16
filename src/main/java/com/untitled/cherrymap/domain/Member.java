package com.untitled.cherrymap.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "사용자 정보 엔티티")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "사용자 ID")
    private Long id;

    @Column(nullable = false, length = 50)
    @Schema(description = "사용자 닉네임", example = "cherryUser")
    private String nickname;

    @Column(nullable = false, unique = true, length = 100)
    @Schema(description = "사용자 이메일", example = "user@example.com")
    private String email;

    @Column(nullable = false, unique = true, length = 50)
    @Schema(description = "소셜 로그인 제공자의 고유 ID", example = "kakao_123456789")
    private String providerId; // 카카오 고유 ID
}
