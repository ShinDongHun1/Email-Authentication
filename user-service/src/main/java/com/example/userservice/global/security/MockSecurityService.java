package com.example.userservice.global.security;

import com.example.userservice.domain.member.entity.Member;
import com.example.userservice.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Created by ShinD on 2022-03-08.
 */
@Service
@RequiredArgsConstructor
public class MockSecurityService implements SecurityService{

    private final MemberRepository memberRepository;

    @Override
    public String authenticatedUsername() {
        return "huipulco";
    }

}
