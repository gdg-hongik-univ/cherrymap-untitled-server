package com.untitled.cherrymap.domain.emotion.api;

import com.untitled.cherrymap.domain.emotion.application.EmotionService;
import com.untitled.cherrymap.domain.emotion.dto.EmotionRequest;
import com.untitled.cherrymap.domain.emotion.dto.EmotionResponse;
import com.untitled.cherrymap.security.dto.CustomUserDetailsDTO;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/emotions")
@RequiredArgsConstructor
public class EmotionController {

    private final EmotionService emotionService;

    @PostMapping
    public ResponseEntity<EmotionResponse> saveEmotion(
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetailsDTO user,
            @RequestBody EmotionRequest request
    ) {
        return ResponseEntity.ok(
                emotionService.saveOrUpdateEmotion(request, user.getMember().getId())
        );
    }

    @GetMapping
    public ResponseEntity<EmotionResponse> getEmotionForRoute(
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetailsDTO user,
            @RequestParam Long routeId
    ) {
        return ResponseEntity.ok(
                emotionService.getEmotionByRoute(user.getMember().getId(), routeId)
        );
    }
}
