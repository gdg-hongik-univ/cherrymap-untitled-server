package com.untitled.cherrymap.domain.emotion.dto;

import com.untitled.cherrymap.domain.emotion.domain.EmotionType;

public record EmotionRequest(
        Long routeId,
        EmotionType emotion
) {}
