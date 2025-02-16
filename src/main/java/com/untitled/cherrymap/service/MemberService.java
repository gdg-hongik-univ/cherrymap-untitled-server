package com.untitled.cherrymap.service;

import com.untitled.cherrymap.domain.Member;
import com.untitled.cherrymap.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member getMemberByProviderId(String providerId) {
        Member member = memberRepository.findByProviderId(providerId);
        if (member == null) {
            throw new RuntimeException("Member not found with providerId: " + providerId);
        }
        return member;
    }
}
