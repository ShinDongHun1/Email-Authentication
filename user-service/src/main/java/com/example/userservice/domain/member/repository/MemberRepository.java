package com.example.userservice.domain.member.repository;

import com.example.userservice.domain.member.dto.request.SearchCondition;
import com.example.userservice.domain.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by ShinD on 2022-03-07.
 */
public interface MemberRepository extends JpaRepository<Member, Long> , QueryMemberRepository{
    Optional<Member> findByUsername(String username);

    @EntityGraph(attributePaths = {"email"})
    Optional<Member> findWithEmailByEmailId(Long emailId);
}
