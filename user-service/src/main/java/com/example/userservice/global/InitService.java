/*
package com.example.userservice.global;

import com.example.userservice.domain.member.entity.Member;
import com.example.userservice.domain.member.repository.MemberRepository;
import com.example.userservice.global.security.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

*/
/**
 * Created by ShinD on 2022-03-09.
 *//*

@Component
@RequiredArgsConstructor
public class InitService {

    private final Init init;
    private final SecurityService securityService;
    private final MemberRepository memberRepository;

    @PostConstruct
    public void init(){
        init.init();
    }


    @Component
    @RequiredArgsConstructor
    private static class Init {
        private final MemberRepository memberRepository;
        private final PasswordEncoder passwordEncoder;

        @Transactional
        public void init(){
            memberRepository.save(Member.builder().username("huipulco").password(passwordEncoder.encode("1234")).name("신동훈").build());
        }
    }
}
*/
