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

    @Transactional(readOnly = true)
    public Member getMemberByMemberId(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(ErrorMessage.MEMBER_NOT_FOUND_WITH + id));
        return member;
    }

    @Transactional(readOnly = true)
    public String getPhoneNumber(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(ErrorMessage.MEMBER_NOT_FOUND_WITH + id));
        return member.getPhoneNumber(); // null일 수도 있음
    }

    @Transactional
    public void updatePhoneNumber(Long id, String phoneNumber) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(ErrorMessage.MEMBER_NOT_FOUND_WITH + id));
        member.setPhoneNumber(phoneNumber);
    }

    @Transactional
    public void deletePhoneNumber(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(ErrorMessage.MEMBER_NOT_FOUND_WITH + id));
        member.setPhoneNumber(null);
    }

}
