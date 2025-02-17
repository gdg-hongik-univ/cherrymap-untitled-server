package com.untitled.cherrymap.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "알림 메시지 엔티티")
public class AlertMessage {
    @Schema(description = "알림 메시지 ID")
    private Long alertMessageId;

    @Schema(description = "대중교통 모드",example = "subway")
    private String mode;

    @Schema(description = "알림 메시지 내용", example = "다음 역이 '~~역'인지 확인하고 탑승하세요.")
    private String alertText;
}
