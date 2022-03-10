package com.example.userservice.domain.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

/**
 * Created by ShinD on 2022-03-11.
 */
@AllArgsConstructor
@Schema(title = "RequestUpdate", description = "회원수정 시 보내야 하는 데이터")
public class RequestUpdate {

    @Schema(title = "변경할 이름", defaultValue = "신동훈", required = false)
    String name;

    @Schema(title = "변경할 프로필 사진", required = false)
    MultipartFile profileImage;



    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }

    public Optional<MultipartFile> getProfileImage() {
        return Optional.ofNullable(profileImage);
    }
}
