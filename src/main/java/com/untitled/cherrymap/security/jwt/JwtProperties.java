package com.untitled.cherrymap.security.jwt;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    private Long accessTokenExpirationMs;
    private Long refreshTokenExpirationMs;

    public void setAccessTokenExpirationMs(Long value) {
        this.accessTokenExpirationMs = value;
    }

    public void setRefreshTokenExpirationMs(Long value) {
        this.refreshTokenExpirationMs = value;
    }
}

