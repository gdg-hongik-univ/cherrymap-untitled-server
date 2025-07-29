package com.untitled.cherrymap.domain.llm.application;

import com.untitled.cherrymap.domain.llm.dto.LlmChatRequest;
import com.untitled.cherrymap.domain.llm.dto.LlmChatResponse;
import com.untitled.cherrymap.domain.llm.exception.LlmCommunicationException;
import com.untitled.cherrymap.domain.member.dao.MemberRepository;
import com.untitled.cherrymap.domain.member.domain.Member;
import com.untitled.cherrymap.domain.member.exception.MemberNotFoundException;
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
    private final MemberRepository memberRepository;
    
    @Value("${llm.server.url:http://ai.cherrymap.click}")
    private String llmServerUrl;

    public LlmChatResponse processChatRequest(LlmChatRequest request, Long memberId) {        
        // EmotionService와 동일한 방식으로 memberId 검증
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> MemberNotFoundException.EXCEPTION);
        
        log.info("사용자 {} (ID: {}) LLM 챗봇 요청 처리 시작", member.getNickname(), memberId);
        
        try {
            return webClient.post()
                    .uri(llmServerUrl + "/api/v1/chat")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(LlmChatResponse.class)
                    .doOnSuccess(response -> log.info("사용자 {} (ID: {}) LLM 챗봇 응답 수신. 액션: {}", member.getNickname(), memberId, response.actionType()))
                    .doOnError(error -> log.error("사용자 {} (ID: {}) LLM 서버 통신 오류: {}", member.getNickname(), memberId, error.getMessage()))
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