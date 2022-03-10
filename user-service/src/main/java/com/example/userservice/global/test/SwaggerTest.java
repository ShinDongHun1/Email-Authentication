/*
package com.example.userservice.global.test;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Size;

*/
/**
 * Created by ShinD on 2022-03-07.
 *//*


*/
/**
 * name은 데이터 반환 형식에서 name : defaultValue
 * + title이 없을경우 name이 title
 *
 * title은 제목? 그냥 생각하는 그대로!
 *//*


@Data
@AllArgsConstructor
@Schema(title = "SwaggerTest Model", name = "끼잉",description = "Swagger가 어떻게 생성되는지 확인하기 위한 테스트용 객체")
public class SwaggerTest {

    private Long id;

    @Size(min = 2)
    @Schema(name = "name", title = "제목은 뭐냐", description = "설명은?", defaultValue = "신동훈")
    private String name;

    @Schema(name = "비번", title = "Password", description = "비번?", defaultValue = "1234")
    private Password password;

}
*/
