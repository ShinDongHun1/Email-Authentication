package com.example.userservice.domain.member.service;

import com.example.userservice.domain.member.dto.request.RequestSignUp;
import com.example.userservice.domain.member.dto.request.RequestUpdate;
import com.example.userservice.domain.member.dto.request.RequestUpdatePassword;
import com.example.userservice.domain.member.dto.response.MemberDetail;
import com.example.userservice.domain.member.entity.Member;
import com.example.userservice.domain.member.repository.MemberRepository;
import com.example.userservice.global.file.FileService;
import com.example.userservice.global.security.SecurityService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import static com.example.userservice.factory.RequestSignUpFactory.*;
import static com.example.userservice.factory.RequestSignUpFactory.createRequestSignUpWithUsername;
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
class MemberServiceImplTestUsingMockBean {



    @Autowired private MemberService memberService;
    @Autowired private MemberRepository memberRepository;
    @Autowired private EntityManager em;
    @Autowired private PasswordEncoder pe;


    @MockBean private SecurityService securityService;



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
    @DisplayName("???????????? ??????")
    public void signUpSuccess() throws Exception {
        //given
        RequestSignUp requestSignUp = createRequestSignUp();

        //when
        memberService.signUp(requestSignUp);

        //then
        Member member = getMemberByExecuteJpqlWhereUsernameIs(requestSignUp.getUsername());


        assertThat(member.getName()).isEqualTo(requestSignUp.getName());
        assertThat(member.getPassword()).isNotEqualTo(requestSignUp.getPassword());
        assertThat(pe.matches(requestSignUp.getPassword(), member.getPassword())).isTrue();
    }



    @Test
    @DisplayName("???????????? ?????? - ????????? ??????")
    public void signUpFailDuplicateUsername() throws Exception {
        //given
        RequestSignUp requestSignUp = createRequestSignUp();
        memberService.signUp(requestSignUp);

        //when, then
        assertThrows(Exception.class, () -> memberService.signUp(requestSignUp));
    }




    @Test
    @DisplayName("???????????? ?????? - Null??? ????????? ??????")
    public void signUpFailHasNullField() throws Exception {

        //given, when, then
        assertThrows(Exception.class, () -> memberService.signUp(createRequestSignUpWithName(null)));
        assertThrows(Exception.class, () -> memberService.signUp(createRequestSignUpWithUsername(null)));
        assertThrows(Exception.class, () -> memberService.signUp(createRequestSignUpWithPassword(null)));
    }





    @Test
    @DisplayName("???????????? ??????")
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

        clear();//????????? ???????????? ?????????



        //then
        assertThat(member.getId()).isEqualTo(updateMember.getId());
        assertThat(member.getName()).isNotEqualTo(updateMember.getName());
    }


    @Test
    @DisplayName("???????????? ?????? ??????")
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
        clear();//????????? ???????????? ?????????

        //then
        Member updateMember = getMemberByExecuteJpqlWhereUsernameIs(requestSignUp.getUsername());
        assertThat(pe.matches("toBePassword", updateMember.getPassword())).isTrue();
        assertThat(pe.matches(requestSignUp.getPassword(), updateMember.getPassword())).isFalse();
    }

    @Test
    @DisplayName("?????? ?????? ??????")
    public void deleteSuccess() throws Exception {
        //given
        RequestSignUp requestSignUp = createRequestSignUp();
        memberService.signUp(requestSignUp);

        doReturn(requestSignUp.getUsername()).when(securityService).authenticatedUsername();


        //when
        memberService.delete(requestSignUp.getPassword());

        //then
        assertThrows(NoResultException.class, () -> getMemberByExecuteJpqlWhereUsernameIs(requestSignUp.getUsername()));
    }

    @Test
    @DisplayName("?????? ?????? ?????? - ???????????? ?????????")
    public void deleteFail() throws Exception {
        //given
        RequestSignUp requestSignUp = createRequestSignUp();
        memberService.signUp(requestSignUp);

        doReturn(requestSignUp.getUsername()).when(securityService).authenticatedUsername();


        //when
        assertThrows(IllegalStateException.class, () -> memberService.delete(requestSignUp.getPassword()+"123"));

        //then
        assertThat(getMemberByExecuteJpqlWhereUsernameIs(requestSignUp.getUsername())).isNotNull();
    }

    @Test
    @DisplayName("?????? ?????? ?????? - id??????")
    public void successGetMemberInfoById() throws Exception {
        //given
        RequestSignUp requestSignUp = createRequestSignUp();
        Member save = memberRepository.save(requestSignUp.toEntity());

        //when
        MemberDetail memberInfo = memberService.getMemberInfo(save.getId());

        //then
        assertThat(memberInfo.username()).isEqualTo(requestSignUp.getUsername());
        assertThat(memberInfo.name()).isEqualTo(requestSignUp.getName());

    }
    @Test
    @DisplayName("?????? ?????? ?????? - username??????")
    public void successGetMemberInfoByUsername() throws Exception {
        //given
        RequestSignUp requestSignUp = createRequestSignUp();
        memberService.signUp(requestSignUp);

        //when
        MemberDetail memberInfo = memberService.getMemberInfo(requestSignUp.getUsername());

        //then
        assertThat(memberInfo.username()).isEqualTo(requestSignUp.getUsername());
        assertThat(memberInfo.name()).isEqualTo(requestSignUp.getName());

    }





    private Member getMemberByExecuteJpqlWhereUsernameIs(String username) {
        return em.createQuery("select m from Member m where m.username =: username", Member.class).setParameter("username", username).getSingleResult();
    }

}