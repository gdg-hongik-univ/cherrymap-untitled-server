package com.untitled.cherrymap.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "alert_message")
@Schema(description = "알림 메시지 엔티티")
public class AlertMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "알림 메시지 ID")
    private Long alertMessageId;

    @Column(nullable = false, length = 10)
    @Schema(description = "대중교통 모드 : subway or bus")
    private String mode;

    @Column(nullable = false, columnDefinition = "TEXT")
    @Schema(description = "알림 메시지 내용", example = "다음 역이 '~~역'인지 확인하고 탑승하세요.")
    private String alertText;
}
