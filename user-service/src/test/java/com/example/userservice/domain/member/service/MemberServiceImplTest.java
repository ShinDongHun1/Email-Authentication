package com.example.userservice.domain.member.service;

import com.example.userservice.domain.member.dto.request.RequestSignUp;
import com.example.userservice.domain.member.dto.request.RequestUpdate;
import com.example.userservice.domain.member.dto.request.RequestUpdatePassword;
import com.example.userservice.domain.member.entity.Member;
import com.example.userservice.domain.member.repository.MemberRepository;
import com.example.userservice.global.file.FileService;
import com.example.userservice.global.security.SecurityService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import static com.example.userservice.factory.RequestSignUpFactory.*;
import static com.example.userservice.factory.RequestUpdateFactory.createRequestUpdate;
import static com.example.userservice.factory.RequestUpdatePasswordFactory.createRequestUpdatePassword;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

/**
 * Created by ShinD on 2022-03-09.
 */
@SpringBootTest
@Transactional
class MemberServiceImplTest {



    private MemberService memberService;
    @Autowired private EntityManager em;
    @Autowired private PasswordEncoder pe;
    @Autowired private MemberRepository memberRepository;
    @Mock private FileService fileService;
    @Mock private SecurityService securityService;

    @PostConstruct
    public void init(){
        memberService= new MemberServiceImpl(memberRepository, pe, fileService, securityService);
    }


/**
 *    void signUp(RequestSignUp requestSignUp);
 *
 *     void update(RequestUpdate requestUpdate);
 *     void updatePassword(RequestUpdatePassword requestUpdatePassword);
 *     void reIssuePassword(RequestReIssuePassword requestReIssuePassword);
 *
 *     void delete(String password);
 *
 *
 *     MemberDetail getMemberInfo(String username);
 *     MemberDetail getMemberInfo(Long id);
 *     MemberInfoList search(Pageable pageable, SearchCondition searchCondition);
 */



    private void clear() {
        em.flush();
        em.clear();
    }




    @Test
    @DisplayName("회원가입 성공")
    public void signUpSuccess() throws Exception {
        //given
        RequestSignUp requestSignUp = createRequestSignUp();

        //when
        memberService.signUp(requestSignUp);

        //then
        Member member = em.createQuery("select m from Member m where m.username =: username", Member.class)
                .setParameter("username", requestSignUp.getUsername()).
                getSingleResult();


        assertThat(member.getName()).isEqualTo(requestSignUp.getName());
        assertThat(member.getPassword()).isNotEqualTo(requestSignUp.getPassword());
        assertThat(pe.matches(requestSignUp.getPassword(), member.getPassword())).isTrue();
    }



    @Test
    @DisplayName("회원가입 실패 - 아이디 중복")
    public void signUpFailDuplicateUsername() throws Exception {
        //given
        RequestSignUp requestSignUp = createRequestSignUp();
        memberService.signUp(requestSignUp);

        //when, then
        assertThrows(Exception.class, () -> memberService.signUp(requestSignUp));
    }




    @Test
    @DisplayName("회원가입 실패 - Null인 필드가 존재")
    public void signUpFailHasNullField() throws Exception {
        //given
        RequestSignUp requestSignUp1 = createRequestSignUpWithName(null);
        RequestSignUp requestSignUp2 = createRequestSignUpWithUsername(null);
        RequestSignUp requestSignUp3 = createRequestSignUpWithPassword(null);

        //when, then
        assertThrows(Exception.class, () -> memberService.signUp(requestSignUp1));
        assertThrows(Exception.class, () -> memberService.signUp(requestSignUp2));
        assertThrows(Exception.class, () -> memberService.signUp(requestSignUp3));
    }





    @Test
    @DisplayName("회원수정 성공")
    public void updateSuccess() throws Exception {
        //given
        RequestSignUp requestSignUp = createRequestSignUp();
        memberService.signUp(requestSignUp);
        Member member = getMemberByExecuteJpqlWhereUsernameIs(requestSignUp.getUsername());

        clear();

        doReturn(requestSignUp.getUsername()).when(securityService).authenticatedUsername();

        RequestUpdate requestUpdate = createRequestUpdate();

        //when
        memberService.update(requestUpdate);
        Member updateMember = em.createQuery("select m from Member m where m.username =: username", Member.class)
                .setParameter("username", requestSignUp.getUsername()).getSingleResult();

        clear();//영속성 컨텍스트 비우기



        //then
        assertThat(member.getId()).isEqualTo(updateMember.getId());
        assertThat(member.getName()).isNotEqualTo(updateMember.getName());
    }


    @Test
    @DisplayName("비밀번호 수정 성공")
    public void updatePasswordSuccess() throws Exception {
        //given
        RequestSignUp requestSignUp = createRequestSignUp();
        memberService.signUp(requestSignUp);
        Member member = getMemberByExecuteJpqlWhereUsernameIs(requestSignUp.getUsername());
        clear();

        RequestUpdatePassword requestUpdatePassword = createRequestUpdatePassword(requestSignUp.getPassword(), "toBePassword");
        doReturn(requestSignUp.getUsername()).when(securityService).authenticatedUsername();

        //when
        memberService.updatePassword(requestUpdatePassword);
        clear();//영속성 컨텍스트 비우기

        //then
        Member updateMember = getMemberByExecuteJpqlWhereUsernameIs(requestSignUp.getUsername());
        assertThat(pe.matches("toBePassword", updateMember.getPassword())).isTrue();
        assertThat(pe.matches(requestSignUp.getPassword(), updateMember.getPassword())).isFalse();
    }









    private Member getMemberByExecuteJpqlWhereUsernameIs(String username) {
        return em.createQuery("select m from Member m where m.username =: username", Member.class).setParameter("username", username).getSingleResult();
    }

}