package com.untitled.cherrymap.domain.member.dao;

import com.untitled.cherrymap.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Boolean existsByNickname(String nickname);

    Member findByNickname(String nickname);
}