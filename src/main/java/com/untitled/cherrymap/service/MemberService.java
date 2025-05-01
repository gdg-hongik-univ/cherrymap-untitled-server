package com.untitled.cherrymap.service;

import com.untitled.cherrymap.domain.Member;
import com.untitled.cherrymap.exception.BadRequestException;
import com.untitled.cherrymap.message.ErrorMessage;
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
            throw new BadRequestException(ErrorMessage.MEMBER_NOT_FOUND_WITH + providerId);
        }
        return member;
    }

    @Transactional(readOnly = true)
    public String getPhoneNumber(String providerId) {
        Member member = memberRepository.findByProviderId(providerId);
        if (member == null) {
            throw new BadRequestException(ErrorMessage.MEMBER_NOT_FOUND_WITH + providerId);
        }
        return member.getPhoneNumber(); // null일 수도 있음
    }

    @Transactional
    public void updatePhoneNumber(String providerId, String phoneNumber) {
        Member member = memberRepository.findByProviderId(providerId);
        if (member == null) {
            throw new BadRequestException(ErrorMessage.MEMBER_NOT_FOUND_WITH + providerId);
        }
        member.setPhoneNumber(phoneNumber);
    }

    @Transactional
    public void deletePhoneNumber(String providerId) {
        Member member = memberRepository.findByProviderId(providerId);
        if (member == null) {
            throw new BadRequestException(ErrorMessage.MEMBER_NOT_FOUND_WITH + providerId);
        }
        member.setPhoneNumber(null);
    }

}
