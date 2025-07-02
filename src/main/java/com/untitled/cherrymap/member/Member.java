package com.untitled.cherrymap.member;

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
    @Column(name = "member_id")
    @Schema(description = "사용자 ID")
    private Long id;

    @Column(name = "nickname", nullable = false, length = 10)
    @Schema(description = "사용자 닉네임", example = "cherryUser")
    private String nickname;

    @Column(name = "username", length = 50, nullable = false)
    private String username;

    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "phone_number", length = 20)
    @Schema(description = "사용자 비상 연락처", example = "010-1234-5678")
    private String phoneNumber;

    public Member(String nickname, String password, String username, String phoneNumber, String role) {
        this.nickname = nickname;
        this.username = username;
        this.password = password;
        this.role = role;
        this.phoneNumber = phoneNumber;
    }
}
