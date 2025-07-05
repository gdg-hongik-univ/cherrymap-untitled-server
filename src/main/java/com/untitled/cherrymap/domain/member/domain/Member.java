package com.untitled.cherrymap.domain.member.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "member")
@Schema(description = "사용자 정보 엔티티")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    @Schema(description = "사용자 ID")
    private Long id;

    @Column(name = "nickname", nullable = false, length = 10, unique = true)
    @Schema(description = "유저 닉네임 (UNIQUE)", example = "cherryUser")
    private String nickname;

    @Column(name = "username", length = 10, nullable = false)
    @Schema(description = "유저 이름", example = "홍길동")
    private String username;

    @Column(name = "password", length = 100, nullable = false)
    @Schema(description = "비밀번호(암호화 저장)", example = "$2a$10$...")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    @Schema(description = "역할(회원가입시 부여)", example = "ROLE_USER", allowableValues = {"ROLE_USER", "ROLE_ADMIN", "ROLE_MODERATOR"})
    private UserRole role;

    @Column(name = "phone_number", length = 20)
    @Schema(description = "비상 연락처 번호", example = "010-1234-5678")
    private String phoneNumber;

    public Member(String nickname, String password, String username, String phoneNumber, UserRole role) {
        this.nickname = nickname;
        this.username = username;
        this.password = password;
        this.role = role;
        this.phoneNumber = phoneNumber;
    }
}
