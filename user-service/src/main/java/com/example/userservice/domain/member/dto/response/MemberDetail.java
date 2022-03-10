package com.example.userservice.domain.member.dto.response;

import com.example.userservice.domain.member.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Optional;

/**
 * Created by ShinD on 2022-03-08.
 */
@Schema(title = "MemberDetail", description = "회원정보 조회시 보여주는 데이터")
public record MemberDetail(
        @Schema(title = "회원의 아이디", defaultValue = "아이디")
        String username,

        @Schema(title = "회원의 이름", defaultValue = "신동훈")
        String name,

        @Schema(title = "회원의 프사 주소", defaultValue = "http://ttl-blog.tistory.com")
        Optional<String> profileImagePath) {


    public static MemberDetail of(Member member) {
        return new MemberDetail(member.getUsername(), member.getName(), member.getProfileImagePath());
    }
}
