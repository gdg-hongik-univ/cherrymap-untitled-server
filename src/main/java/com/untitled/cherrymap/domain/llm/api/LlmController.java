package com.untitled.cherrymap.domain.llm.api;

import com.untitled.cherrymap.domain.llm.application.LlmService;
import com.untitled.cherrymap.domain.llm.dto.LlmChatRequest;
import com.untitled.cherrymap.domain.llm.dto.LlmChatResponse;
import com.untitled.cherrymap.security.dto.CustomUserDetailsDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "LLM Chat", description = "LLM 챗봇 API")
public class LlmController {

    private final LlmService llmService;

    @PostMapping("/chat")
    @Operation(
        summary = "LLM 챗봇 대화",
        description = "발달장애인을 위한 내비게이션 챗봇과 대화합니다. 길 이탈, 대중교통 놓침 등의 상황에 대한 도움을 제공합니다.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "챗봇 응답 성공",
                content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = LlmChatResponse.class)
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "500",
                description = "LLM 서버 통신 오류"
            )
        }
    )
    public ResponseEntity<LlmChatResponse> chat(
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetailsDTO user,
            @RequestBody LlmChatRequest request
    ) {    
        Long memberId = user.getMember().getId();
        log.info("사용자 {} (ID: {}) 챗봇 요청: {}", user.getNickname(), memberId, request.message().substring(0, Math.min(50, request.message().length())));
        
        return ResponseEntity.ok(llmService.processChatRequest(request, memberId));
    }
} 