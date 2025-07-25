package com.untitled.cherrymap.domain.emotion.domain;

import com.untitled.cherrymap.domain.member.domain.Member;
import com.untitled.cherrymap.domain.route.domain.Route;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "emotion", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"member_id", "route_id"})
})
public class Emotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emotion_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id", nullable = false)
    private Route route;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EmotionType emotion;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public Emotion(Member member, Route route, EmotionType emotion) {
        this.member = member;
        this.route = route;
        this.emotion = emotion;
        this.createdAt = LocalDateTime.now();
    }

    public void updateEmotion(EmotionType newEmotion) {
        this.emotion = newEmotion;
        this.createdAt = LocalDateTime.now();
    }
}
