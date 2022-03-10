package com.example.userservice.global;

import com.example.userservice.domain.member.dto.request.RequestUpdate;
import com.example.userservice.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * Created by ShinD on 2022-03-09.
 */
@RestController
@RequiredArgsConstructor
public class TestController {

    private final MemberService memberService;

    @GetMapping("/test")
    public void d(){
        memberService.update(new RequestUpdate("바보",null));
    }


}
