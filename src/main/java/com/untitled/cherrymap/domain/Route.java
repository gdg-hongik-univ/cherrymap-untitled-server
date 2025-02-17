package com.untitled.cherrymap.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "route")
@Schema(description = "사용자의 경로 정보 엔티티")
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "route_id")
    @Schema(description = "경로 ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    @Schema(description = "사용자 정보 (회원 ID)")
    private Member member;

    @Column(name = "route_name", nullable = false, length = 20)
    @Schema(description = "경로 이름", example = "홍익대학교")
    private String routeName;

    @Column(name = "start_name", nullable = false, length = 20)
    @Schema(description = "출발지 이름", example = "강남역")
    private String startName;

    @Column(name = "start_lat", nullable = false)
    @Schema(description = "출발지 위도", example = "37.4979")
    private double startLat;

    @Column(name = "start_lng", nullable = false)
    @Schema(description = "출발지 경도", example = "127.0276")
    private double startLng;

    @Column(name = "end_name", nullable = false, length = 20)
    @Schema(description = "도착지 이름", example = "서울역")
    private String endName;

    @Column(name = "end_lat", nullable = false)
    @Schema(description = "도착지 위도", example = "37.5547")
    private double endLat;

    @Column(name = "end_lng", nullable = false)
    @Schema(description = "도착지 경도", example = "126.9706")
    private double endLng;
}
