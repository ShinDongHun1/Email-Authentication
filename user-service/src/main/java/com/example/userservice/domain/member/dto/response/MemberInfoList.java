package com.example.userservice.domain.member.dto.response;

import com.example.userservice.domain.member.entity.Member;
import org.springframework.data.domain.Page;

/**
 * Created by ShinD on 2022-03-08.
 */
public class MemberInfoList {

    public static MemberInfoList of(Page<Member> all) {
        return new MemberInfoList();
    }
}
