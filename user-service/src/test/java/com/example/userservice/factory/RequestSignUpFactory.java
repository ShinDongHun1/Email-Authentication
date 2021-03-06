package com.example.userservice.factory;

import com.example.userservice.domain.member.dto.request.RequestSignUp;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

/**
 * Created by ShinD on 2022-03-09.
 */
public class RequestSignUpFactory {
    public static RequestSignUp createRequestSignUp(){
        return new RequestSignUp("shinD", "password1234@", "신동훈", "sample@email", Optional.empty());
    }
    public static RequestSignUp createRequestSignUp(String username, String password, String name,String email, MultipartFile profileImage){
        return new RequestSignUp(username, password, name, email,Optional.of(profileImage));
    }
    public static RequestSignUp createRequestSignUpWithUsername(String username){
        return new RequestSignUp(username, "password1234@", "신동훈","sample@email", Optional.empty());
    }
    public static RequestSignUp createRequestSignUpWithPassword(String password){
        return new RequestSignUp("shinD", password, "신동훈", "sample@email",Optional.empty());
    }
    public static RequestSignUp createRequestSignUpWithName(String name){
        return new RequestSignUp("shinD", "password1234@", name, "sample@email",Optional.empty());
    }
    public static RequestSignUp createRequestSignUpWithImage(MultipartFile profileImage){
        return new RequestSignUp("shinD", "password1234@", "신동훈", "sample@email",Optional.of(profileImage));
    }
    public static RequestSignUp createRequestSignUpWithImage(String email){
        return new RequestSignUp("shinD", "password1234@", "신동훈", email,Optional.empty());
    }
}
