package com.untitled.cherrymap.domain.emotion.application;

import com.untitled.cherrymap.domain.emotion.dao.EmotionRepository;
import com.untitled.cherrymap.domain.emotion.domain.Emotion;
import com.untitled.cherrymap.domain.emotion.dto.EmotionRequest;
import com.untitled.cherrymap.domain.emotion.dto.EmotionResponse;
import com.untitled.cherrymap.domain.emotion.exception.EmotionNotFoundException;
import com.untitled.cherrymap.domain.member.dao.MemberRepository;
import com.untitled.cherrymap.domain.member.domain.Member;
import com.untitled.cherrymap.domain.member.exception.MemberNotFoundException;
import com.untitled.cherrymap.domain.route.dao.RouteRepository;
import com.untitled.cherrymap.domain.route.domain.Route;
import com.untitled.cherrymap.domain.route.exception.RouteNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmotionService {

    private final EmotionRepository emotionRepository;
    private final MemberRepository memberRepository;
    private final RouteRepository routeRepository;

    public EmotionResponse saveOrUpdateEmotion(EmotionRequest request, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> MemberNotFoundException.EXCEPTION);

        Route route = routeRepository.findById(request.routeId())
                .orElseThrow(() -> RouteNotFoundException.EXCEPTION);

        Emotion emotion = emotionRepository.findByMemberIdAndRouteId(memberId, request.routeId())
                .map(e -> {
                    e.updateEmotion(request.emotion());
                    return e;
                })
                .orElseGet(() -> new Emotion(member, route, request.emotion()));

        return EmotionResponse.from(emotionRepository.save(emotion));
    }

    public EmotionResponse getEmotionByRoute(Long memberId, Long routeId) {
        Emotion emotion = emotionRepository.findByMemberIdAndRouteId(memberId, routeId)
                .orElseThrow(() -> EmotionNotFoundException.EXCEPTION);
        return EmotionResponse.from(emotion);
    }
}
