package com.example.userservice.domain.member.service;

import com.example.userservice.domain.member.dto.request.*;
import com.example.userservice.domain.member.dto.response.MemberDetail;
import com.example.userservice.domain.member.dto.response.MemberInfoList;
import org.springframework.data.domain.Pageable;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

/**
 * Created by ShinD on 2022-03-07.
 */
public interface MemberService {

    void signUp(RequestSignUp requestSignUp) throws MessagingException, UnsupportedEncodingException;

    void update(RequestUpdate requestUpdate);
    void updatePassword(RequestUpdatePassword requestUpdatePassword);
    void reIssuePassword(RequestReIssuePassword requestReIssuePassword);

    void delete(String password);


    MemberDetail getMemberInfo(String username);
    MemberDetail getMemberInfo(Long id);
    MemberInfoList search(Pageable pageable, SearchCondition searchCondition);

}
