package com.example.userservice.domain.member.service;

import com.example.userservice.domain.mail.Email;
import com.example.userservice.domain.mail.repository.EmailRepository;
import com.example.userservice.domain.mail.service.EmailService;
import com.example.userservice.domain.member.dto.request.*;
import com.example.userservice.domain.member.dto.response.MemberDetail;
import com.example.userservice.domain.member.dto.response.MemberInfoList;
import com.example.userservice.domain.member.entity.Member;
import com.example.userservice.domain.member.repository.MemberRepository;
import com.example.userservice.global.file.FileService;
import com.example.userservice.global.security.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

/**
 * Created by ShinD on 2022-03-07.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final EmailRepository emailRepository;

    private final PasswordEncoder passwordEncoder;
    private final FileService fileService;
    private final SecurityService securityService;
    private final EmailService mailService;


    @Override
    public void signUp(RequestSignUp requestSignUp) throws MessagingException, UnsupportedEncodingException {

        Member member = requestSignUp.toEntity();

        checkUsernameDuplicated(member.getUsername());
        checkEmailDuplicated(member.getEmail());

        member.encodePassword(passwordEncoder);

        updateProfileImage(requestSignUp.getProfileImage(), member);

        memberRepository.save(member);

        mailService.sendMail(member.getEmail());

    }




    private void checkEmailDuplicated(Email email) {
        if(emailRepository.findByEmail(email.getEmail()).isPresent()) throw new IllegalStateException("이메일이 중복됩니다.");
    }



    @Override
    public void authenticateByEmail(Long emailId, String key) {
        Member member = memberRepository.findWithEmailByEmailId(emailId).orElseThrow(() -> new IllegalStateException("잘못된 인증 접근입니다"));

        if(!member.getEmail().matchKey(key)) throw new IllegalStateException("잘못된 인증 접근입니다");

        member.authenticated();
    }




    private void checkUsernameDuplicated(String username) {
        if(memberRepository.findByUsername(username).isPresent()) throw new IllegalStateException("아이디가 중복됩니다.");
    }




    private void updateProfileImage(Optional<MultipartFile> profileImage, Member member) {

        member.getProfileImagePath().ifPresent(fileService::delete);

        profileImage.ifPresent(image -> member.changeProfileImage(fileService.saveAndReturnPath(image)));
    }





    @Override
    public void update(RequestUpdate requestUpdate) {
        Member member = authenticatedMember();//이렇게 해도 관리 당연히 된다.

        requestUpdate.name().ifPresent(member::changeName);

        updateProfileImage(requestUpdate.profileImage(), member);
    }





    @Override
    public void updatePassword(RequestUpdatePassword requestUpdatePassword) {
        Member member = authenticatedMember();
        checkPassword(requestUpdatePassword.asIsPassword(), member);

        member.changePassword(requestUpdatePassword.toBePassword(), passwordEncoder);
    }


    private void checkPassword(String asIsPassword, Member member) {
        if (!member.matchPassword(asIsPassword, passwordEncoder)){
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }
    }





    @Override
    public void reIssuePassword(RequestReIssuePassword requestReIssuePassword) {
        //TODO 이메일 인증해서 가입하고, 이메일로 보내기
    }







    @Override
    public void delete(String password) {
        Member member = authenticatedMember();

        checkPassword(password, member);

        memberRepository.delete(member);
    }





    @Override
    public MemberDetail getMemberInfo(String username) {
        return MemberDetail.of(memberRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("회원이 없져요"))
        );
    }



    @Override
    public MemberDetail getMemberInfo(Long id) {
        return MemberDetail.of(memberRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("회원이 없져요"))
        );
    }




    @Override
    public MemberInfoList search(Pageable pageable, SearchCondition searchCondition) {
        //TODO : 구현해야 함
        return MemberInfoList.of(memberRepository.findAll(pageable, searchCondition));
    }

    private Member authenticatedMember() {
        return memberRepository.findByUsername(securityService.authenticatedUsername()).orElseThrow(() -> new IllegalStateException("회원이 없습니다"));
    }


}
