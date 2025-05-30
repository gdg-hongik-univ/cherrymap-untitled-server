package com.untitled.cherrymap.repository;

import com.untitled.cherrymap.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Boolean existsByNickname(String nickname);

    Member findByNickname(String nickname);
}