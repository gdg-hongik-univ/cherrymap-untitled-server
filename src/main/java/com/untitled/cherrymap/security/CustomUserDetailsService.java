package com.untitled.cherrymap.security;

import com.untitled.cherrymap.domain.member.domain.Member;
import com.untitled.cherrymap.domain.member.exception.MemberNotFoundException;
import com.untitled.cherrymap.security.dto.CustomUserDetailsDTO;
import com.untitled.cherrymap.domain.member.dao.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {
        Member member = memberRepository.findByNickname(nickname);

        if (member == null) {
            throw MemberNotFoundException.EXCEPTION;
        }

        System.out.println("✅ [로그인 사용자 역할] " + member.getNickname() + "의 역할: " + member.getRole());

        return new CustomUserDetailsDTO(member);
    }
}

