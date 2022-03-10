package com.example.userservice.domain.member.dto.request;

import com.example.userservice.domain.member.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * Created by ShinD on 2022-03-07.
 */
@Data
@AllArgsConstructor
@Schema(title = "RequestSignUp", description = "회원가입 시 보내야 하는 데이터")
public class RequestSignUp {


    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String name;

    @NotNull
    private String email;


    private Optional<MultipartFile> profileImage;


    @Schema(description = "아이디", required = true)//TODO : 이건 Getter에만 적용된다
    public String getUsername() {
        return username;
    }

    @Schema(description = "비밀번호", required = true)
    public String getPassword() {
        return password;
    }

    @Schema(description = "이름", required = true)
    public String getName() {
        return name;
    }

    @Schema(description = "이메일", required = true)
    public String getEmail() {
        return email;
    }


    @Schema(description = "프로필사진", required = false)
    //TODO : defaultValue가 적용이 안돼
    public Optional<MultipartFile> getProfileImage() {
        return profileImage;
    }

    public Member toEntity(){
        return Member.builder().username(username).name(name).password(password).email(email).build();
    }


}
