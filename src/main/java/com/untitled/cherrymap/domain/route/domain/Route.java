package com.untitled.cherrymap.domain.route.domain;

import com.untitled.cherrymap.domain.member.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import com.untitled.cherrymap.domain.route.domain.RouteMode;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "route")
@Schema(description = "저장된 경로 엔티티")
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "route_id")
    @Schema(description = "경로 ID (PK)", example = "1")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    @Schema(description = "유저 ID (FK)", example = "1")
    private Member member;

    @Column(name = "route_name", length = 50, nullable = true)
    @Schema(description = "경로명", example = "집에서 학교")
    private String routeName;

    @Enumerated(EnumType.STRING)
    @Column(name = "mode", nullable = false)
    @Schema(description = "경로 모드", example = "도보", allowableValues = {"도보", "대중교통"})
    private RouteMode mode;

    @Column(name = "end_name", length = 50, nullable = false)
    @Schema(description = "도착지 이름", example = "홍익대학교")
    private String endName;

    @Column(name = "end_lat", nullable = false)
    @Schema(description = "도착지 위도", example = "37.5547")
    private Double endLat;

    @Column(name = "end_lng", nullable = false)
    @Schema(description = "도착지 경도", example = "126.9706")
    private Double endLng;

    @Column(name = "created_at", nullable = false)
    @Schema(description = "생성 일시", example = "2024-07-01T12:34:56")
    private LocalDateTime createdAt;
}
