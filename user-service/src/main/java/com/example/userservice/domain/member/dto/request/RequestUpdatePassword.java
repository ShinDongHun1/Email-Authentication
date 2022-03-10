package com.example.userservice.domain.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;

/**
 * Created by ShinD on 2022-03-08.
 */
@Schema(title = "RequestUpdatePassword", description = "비밀번호 변경 시 보내야 하는 데이터")
public record RequestUpdatePassword(
        @NotNull
        @Schema(description = "현재 비밀번호", defaultValue = "현재 비밀번호", required = true)
        String asIsPassword,

        @NotNull
        @Schema(description = "변경할 비밀번호", defaultValue = "바꿀 비밀번호", required = true)
        String toBePassword) {
}
