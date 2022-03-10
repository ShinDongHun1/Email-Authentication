package com.example.userservice.domain.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * Created by ShinD on 2022-03-08.
 */
@Schema(title = "RequestUpdate", description = "회원수정 시 보내야 하는 데이터")
public record RequestUpdate(    @Schema(title = "변경할 이름", defaultValue = "신동훈", required = false)
                                Optional<String> name,

                                @Schema(title = "변경할 프로필 사진", required = false)
                                Optional<MultipartFile> profileImage) {
}
