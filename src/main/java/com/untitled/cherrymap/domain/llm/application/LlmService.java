package com.untitled.cherrymap.domain.llm.application;

import com.untitled.cherrymap.domain.llm.dto.LlmChatRequest;
import com.untitled.cherrymap.domain.llm.dto.LlmChatResponse;
import com.untitled.cherrymap.domain.llm.exception.LlmCommunicationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class LlmService {

    private final WebClient webClient;
    
    @Value("${llm.server.url:http://ai.cherrymap.click}")
    private String llmServerUrl;

    public LlmChatResponse processChatRequest(LlmChatRequest request) {
        log.info("LLM 챗봇 요청 처리 시작. 메시지: {}", request.message().substring(0, Math.min(50, request.message().length())));
        
        try {
            return webClient.post()
                    .uri(llmServerUrl + "/api/v1/chat")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(LlmChatResponse.class)
                    .doOnSuccess(response -> log.info("LLM 챗봇 응답 수신. 액션: {}", response.actionType()))
                    .doOnError(error -> log.error("LLM 서버 통신 오류: {}", error.getMessage()))
                    .block();
                    
        } catch (WebClientResponseException e) {
            log.error("LLM 서버 HTTP 오류: {} - {}", e.getStatusCode(), e.getResponseBodyAsString());
            throw LlmCommunicationException.EXCEPTION;
        } catch (Exception e) {
            log.error("LLM 서버 통신 중 예상치 못한 오류: {}", e.getMessage());
            throw LlmCommunicationException.EXCEPTION;
        }
    }
} 