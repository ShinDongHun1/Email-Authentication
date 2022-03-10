package com.example.userservice.factory;

import com.example.userservice.domain.member.dto.request.RequestUpdate;
import com.example.userservice.domain.member.dto.request.RequestUpdatePassword;

import java.util.Optional;

/**
 * Created by ShinD on 2022-03-09.
 */
public class RequestUpdatePasswordFactory {
    public static RequestUpdatePassword createRequestUpdatePassword(String asIsPassword, String toBePassword){
        return new RequestUpdatePassword(asIsPassword, toBePassword);
    }
}
