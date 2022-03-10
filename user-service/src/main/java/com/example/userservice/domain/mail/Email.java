package com.example.userservice.domain.mail;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Email {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "email_id")
    private Long id;

    @Column(nullable = false)
    private String email;

    private String authKey;

    public static Email of(String email){
        return new Email(email);
    }

    public Email(String email) {
        this.email = email;
        this.authKey = issueKey();
    }

    public void changeMail(String email){
        this.email = email;
    }
    public boolean matchKey(String authKey){
        return this.authKey.equals(authKey);
    }

    public String issueKey(){
        this.authKey = UUID.randomUUID().toString();
        return getAuthKey();
    }
}
