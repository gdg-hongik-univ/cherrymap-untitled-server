package com.untitled.cherrymap.security.jwt.refresh;

import com.untitled.cherrymap.domain.member.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "refresh")
@Schema(description = "리프레시 토큰 엔티티")
public class Refresh {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refresh_id")
    @Schema(description = "토큰 ID (PK)", example = "1")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
	@Schema(description = "유저 ID (FK)", example = "1")
    private Member member;

    @Column(name = "refresh", length = 350, nullable = false, unique = true)
    @Schema(description = "리프레시 토큰 (UNIQUE)", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String refresh;

    @Column(name = "expiration", nullable = false)
    @Schema(description = "토큰 만료 시간", example = "2024-07-01T12:34:56")
    private LocalDateTime expiration;
}
