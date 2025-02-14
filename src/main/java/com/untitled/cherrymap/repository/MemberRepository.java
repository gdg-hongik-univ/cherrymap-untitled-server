package com.untitled.cherrymap.repository;

import com.untitled.cherrymap.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByProviderId(String providerId);
}