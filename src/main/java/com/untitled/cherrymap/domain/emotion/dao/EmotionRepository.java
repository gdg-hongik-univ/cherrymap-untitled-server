package com.untitled.cherrymap.domain.emotion.dao;

import com.untitled.cherrymap.domain.emotion.domain.Emotion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmotionRepository extends JpaRepository<Emotion, Long> {
    Optional<Emotion> findByMemberIdAndRouteId(Long memberId, Long routeId);
}
