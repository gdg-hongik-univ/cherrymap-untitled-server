package com.untitled.cherrymap.domain.member.application;

import com.untitled.cherrymap.domain.member.dao.MemberRepository;
import com.untitled.cherrymap.domain.member.domain.Member;
import com.untitled.cherrymap.domain.member.dto.PhoneNumberResponse;
import com.untitled.cherrymap.domain.member.exception.InvalidPhoneNumberException;
import com.untitled.cherrymap.domain.member.exception.MemberNotFoundException;
import com.untitled.cherrymap.domain.member.exception.PhoneNumberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public PhoneNumberResponse getPhoneNumber(Member member) {
        Member persistentMember = getPersistentMember(member);

        if (persistentMember.getPhoneNumber() == null || persistentMember.getPhoneNumber().isBlank()) {
            throw PhoneNumberNotFoundException.EXCEPTION;
        }

        return toPhoneNumberResponse(persistentMember);
    }

    @Transactional
    public void updatePhoneNumber(Member member, String phoneNumber) {
        validatePhoneNumberFormat(phoneNumber);

        Member persistentMember = getPersistentMember(member);
        persistentMember.setPhoneNumber(phoneNumber);
    }

    @Transactional
    public void deletePhoneNumber(Member member) {
        Member persistentMember = getPersistentMember(member);

        if (persistentMember.getPhoneNumber() == null || persistentMember.getPhoneNumber().isBlank()) {
            throw PhoneNumberNotFoundException.EXCEPTION;
        }

        persistentMember.setPhoneNumber(null);
    }
    // 영속 Member 조회
    private Member getPersistentMember(Member member) {
        return memberRepository.findById(member.getId())
                .orElseThrow(() -> MemberNotFoundException.EXCEPTION);
    }

    // 전화번호 형식 검증
    private void validatePhoneNumberFormat(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw InvalidPhoneNumberException.EXCEPTION;
        }
        
        // 휴대폰: xxx-xxxx-xxxx, 유선전화: xxx-xxx-xxxx 형식 검증
        if (!phoneNumber.matches("^(\\d{3}-\\d{4}-\\d{4}|\\d{3}-\\d{3}-\\d{4})$")) {
            throw InvalidPhoneNumberException.EXCEPTION;
        }
    }


    // 전화번호 타입 판별
    private String getPhoneType(String phoneNumber) {
        if (phoneNumber.matches("^\\d{3}-\\d{4}-\\d{4}$")) {
            return "휴대폰";
        } else if (phoneNumber.matches("^\\d{3}-\\d{3}-\\d{4}$")) {
            return "유선전화";
        }
        return "알 수 없음";
    }

    // PhoneNumberResponse DTO 변환
    private PhoneNumberResponse toPhoneNumberResponse(Member member) {
        return PhoneNumberResponse.builder()
                .memberId(member.getId())
                .nickname(member.getNickname())
                .phoneNumber(member.getPhoneNumber())
                .phoneType(getPhoneType(member.getPhoneNumber()))
                .build();
    }
}
