package com.untitled.cherrymap.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "alert_message")
public class AlertMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long alertMessageId;

    @Column(nullable = false, length = 10)
    private String mode;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String alertText;
}
