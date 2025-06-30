package com.untitled.cherrymap.security;

import com.untitled.cherrymap.domain.Member;
import com.untitled.cherrymap.security.dto.JoinDTO;
import com.untitled.cherrymap.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JoinService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void joinProcess(JoinDTO joinDTO) {
        String nickname = joinDTO.getNickname();
        String password = joinDTO.getPassword();
        String username = joinDTO.getUsername();
        String phoneNumber = joinDTO.getPhoneNumber();

        if (memberRepository.existsByNickname(nickname)) {
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
        }

        Member data = Member.builder()
                .nickname(nickname)
                .password(bCryptPasswordEncoder.encode(password))
                .username(username)
                .phoneNumber(phoneNumber)
                .role("ROLE_ADMIN")
                .build();

        memberRepository.save(data);
    }
}

