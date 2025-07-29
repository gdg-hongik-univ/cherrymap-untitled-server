package com.untitled.cherrymap.domain.llm.application;

import com.untitled.cherrymap.domain.llm.dto.LlmChatRequest;
import com.untitled.cherrymap.domain.llm.dto.LlmChatResponse;
import com.untitled.cherrymap.domain.llm.exception.LlmCommunicationException;
import com.untitled.cherrymap.domain.member.dao.MemberRepository;
import com.untitled.cherrymap.domain.member.domain.Member;
import com.untitled.cherrymap.domain.member.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class LlmService {

    private final WebClient webClient;
    private final MemberRepository memberRepository;
    
    @Value("${llm.server.url:http://ai.cherrymap.click}")
    private String llmServerUrl;

    public LlmChatResponse processChatRequest(LlmChatRequest request, Long memberId) {        
        // EmotionService와 동일한 방식으로 memberId 검증
        memberRepository.findById(memberId)
                .orElseThrow(() -> MemberNotFoundException.EXCEPTION);
                
        try {
            return webClient.post()
                    .uri(llmServerUrl + "/api/v1/chat")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(LlmChatResponse.class)
                    .block();
                    
        } catch (WebClientResponseException e) {
            throw LlmCommunicationException.EXCEPTION;
        } catch (Exception e) {
            throw LlmCommunicationException.EXCEPTION;
        }
    }
} 