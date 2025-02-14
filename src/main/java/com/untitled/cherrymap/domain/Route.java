package com.untitled.cherrymap.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "route")
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long routeId;

    @ManyToOne
    @JoinColumn(name = "member_id",nullable = false)
    private Member member;

    @Column(nullable = false, length = 20)
    private String routeName;

    @Column(nullable = false, length = 20)
    private String startName;

    @Column(nullable = false)
    private double startLat;

    @Column(nullable = false)
    private double startLng;

    @Column(nullable = false, length = 20)
    private String endName;

    @Column(nullable = false)
    private double endLat;

    @Column(nullable = false)
    private double endLng;
}
