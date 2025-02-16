package com.untitled.cherrymap.service;

import com.untitled.cherrymap.domain.Member;
import com.untitled.cherrymap.repository.MemberRepository;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class KakaoOAuth2MemberService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    public KakaoOAuth2MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        // 카카오 계정 정보 추출
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = kakaoAccount != null ? (Map<String, Object>) kakaoAccount.get("profile") : null;

        String providerId = attributes.getOrDefault("id", "Unknown").toString();
        String nickname = profile != null ? profile.getOrDefault("nickname", "Unknown").toString() : "Unknown";
        String email = kakaoAccount != null ? kakaoAccount.getOrDefault("email", "Unknown").toString() : "Unknown";

        // 이메일 검증
        if (email.equals("Unknown")) {
            throw new IllegalArgumentException("이메일 정보가 없습니다.");
        }

        // User 저장 또는 업데이트
        Member member = memberRepository.findByProviderId(providerId);
        if (member == null) {
            member = Member.builder()
                    .providerId(providerId)
                    .nickname(nickname)
                    .email(email)
                    .build();
            System.out.println("새 사용자 등록: " + nickname + " (" + email + ")");
        } else {
            member.setNickname(nickname);
            member.setEmail(email);
            System.out.println("기존 사용자 업데이트: " + nickname + " (" + email + ")");
        }
        memberRepository.save(member);

        // 사용자 정보를 DefaultOAuth2User로 반환
        Map<String, Object> customAttributes = Map.of(
                "id", providerId,
                "nickname", member.getNickname(),
                "email", member.getEmail()
        );

        return new DefaultOAuth2User(
                oAuth2User.getAuthorities(),
                customAttributes,
                "id"
        );
    }
}


