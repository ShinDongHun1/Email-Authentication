package com.example.userservice.factory;

import com.example.userservice.domain.member.dto.request.RequestSignUp;
import com.example.userservice.domain.member.dto.request.RequestUpdate;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

/**
 * Created by ShinD on 2022-03-09.
 */
public class RequestUpdateFactory {
    public static RequestUpdate createRequestUpdate(){
        return new RequestUpdate(Optional.of("신동훈이름변경"), Optional.empty());
    }

    public static RequestUpdate createRequestUpdateWithName(String name){
        return new RequestUpdate(Optional.of(name), Optional.empty());
    }

    public static RequestUpdate createRequestUpdateWithImage(MultipartFile profileImage){
        return new RequestUpdate(Optional.of("신동훈이름변경"), Optional.of(profileImage));
    }
}
