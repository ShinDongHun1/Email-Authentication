package com.example.userservice.domain.member.repository;

import com.example.userservice.domain.member.dto.request.SearchCondition;
import com.example.userservice.domain.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by ShinD on 2022-03-08.
 */
public interface QueryMemberRepository {

    Page<Member> findAll(Pageable pageable, SearchCondition searchCondition);
}
