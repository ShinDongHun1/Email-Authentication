package com.example.userservice.domain.member.controller;

import com.example.userservice.domain.member.dto.request.RequestSignUp;
import com.example.userservice.domain.member.dto.request.RequestUpdate;
import com.example.userservice.domain.member.dto.request.RequestUpdatePassword;
import com.example.userservice.domain.member.dto.response.MemberDetail;
import com.example.userservice.domain.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;

import java.io.UnsupportedEncodingException;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

/**
 * Created by ShinD on 2022-03-07.
 */
@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;



    @Operation(summary = "Create user", description = "회원가입", tags = { "User" })
    @ApiResponses(value = {@ApiResponse(description = "successful operation", responseCode = "201")})
    @PostMapping(value= "/users" ,consumes = { "application/x-www-form-urlencoded" })
    public ResponseEntity<Void> signUp(@Valid @ModelAttribute RequestSignUp requestSignUp) throws MessagingException, UnsupportedEncodingException {
        memberService.signUp(requestSignUp);
        return ResponseEntity.status(CREATED).build();
    }






    @Operation(summary = "Update user info", description = "회원정보수정", tags = { "User" })
    @ApiResponses(value = {@ApiResponse(description = "successful operation", responseCode = "200")})
    @PutMapping(value= "/users" ,consumes = { "application/x-www-form-urlencoded" })
    public ResponseEntity<Void> update(@Valid @ModelAttribute RequestUpdate requestUpdate){
        memberService.update(requestUpdate);
        return ResponseEntity.status(OK).build();
    }




    @Operation(summary = "Update user password", description = "비밀번호 수정", tags = { "User" })
    @ApiResponses(value = {@ApiResponse(description = "successful operation", responseCode = "200")})
    @PutMapping(value= "/users/password" ,consumes = { "application/json" })
    public ResponseEntity<Void> updatePassword(@Valid @RequestBody RequestUpdatePassword requestUpdatePassword){
        memberService.updatePassword(requestUpdatePassword);
        return ResponseEntity.status(OK).build();
    }





    @Operation(summary = "Delete user password", description = "회원탈퇴", tags = { "User" })
    @ApiResponses(value = {@ApiResponse(description = "successful operation", responseCode = "200")})
    @DeleteMapping(value= "/users" ,consumes = { "application/json" })
    public ResponseEntity<Void> delete(        @Schema(description = "현재 비밀번호", defaultValue = "12345678",required = true)
                                               @RequestBody String password){
        memberService.delete(password);
        return ResponseEntity.status(OK).build();
    }


    @Operation(summary = "Get user detail", description = "회원정보 검색", tags = { "User" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = MemberDetail.class)) }),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content) })
    @GetMapping(value= "/users" ,consumes = { "application/json" })
    public ResponseEntity<MemberDetail> getInfo( @RequestBody String username){
        return ResponseEntity.ok(memberService.getMemberInfo(username));
    }



    @Operation(summary = "Get user detail", description = "회원정보 검색", tags = { "User" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = MemberDetail.class)) }),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content) })
    @GetMapping("/users/{id}")
    public ResponseEntity<MemberDetail> getInfo(@PathVariable("id") Long id){
        return ResponseEntity.ok(memberService.getMemberInfo(id));
    }
}
