package com.untitled.cherrymap.domain.emotion.dto;

import com.untitled.cherrymap.domain.emotion.domain.Emotion;
import com.untitled.cherrymap.domain.emotion.domain.EmotionType;

import java.time.LocalDateTime;

public record EmotionResponse(
        Long id,
        Long routeId,
        EmotionType emotion,
        LocalDateTime createdAt
) {
    public static EmotionResponse from(Emotion e) {
        return new EmotionResponse(
                e.getId(),
                e.getRoute().getId(),
                e.getEmotion(),
                e.getCreatedAt()
        );
    }
}
