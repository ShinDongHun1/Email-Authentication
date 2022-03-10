package com.example.userservice.domain.member.entity;

/**
 * Created by ShinD on 2022-03-07.
 */

import com.example.userservice.domain.BaseTimeEntity;
import com.example.userservice.domain.mail.Email;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.view.script.ScriptTemplateConfig;

import javax.persistence.*;

import java.util.Optional;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;


@Table(name = "member")
@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = true)
    private String profileImagePath;


    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "email_id")
    private Email email;

    @Column(nullable = false)
    private boolean isAuthenticated = false;



    public Optional<String> getProfileImagePath() {
        return Optional.ofNullable(profileImagePath);
    }

    @Builder
    private Member(String username, String name, String password, String email) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.email = Email.of(email);
    }

    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(getPassword());
    }

    public void changeProfileImage(String profileImagePath){
        this.profileImagePath = profileImagePath;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void changePassword(String password, PasswordEncoder passwordEncoder) {
        this.password = password;

        encodePassword(passwordEncoder);
    }

    public void changeEmail(String email){
        this.email.changeMail(email);
    }
    public void authenticated(){
        this.isAuthenticated = true;
    }



    public boolean matchPassword(String asIsPassword, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(asIsPassword, getPassword());
    }


}
