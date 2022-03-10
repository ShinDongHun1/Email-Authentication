package com.example.userservice.global.config;


import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by ShinD on 2022-03-07.
 */

@Configuration
public class SwaggerConfig {


    //http://localhost:8080/swagger-ui/index.html
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("CNU-cyber-campus-public")
                .pathsToMatch("/**")
                .build();
    }


    /**
     * http://localhost:8080/swagger-ui/index.html로 들어오면 API 확인 가능!
     */
    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("CNU-Cyber-Campus-API")
                        .description("CNU USER SERVICE")
                        .version("v0.0.1")
                        .license(new License().name("NO LICENSE").url("https://ttl-blog.tistory.com/")))
                .externalDocs(new ExternalDocumentation()
                        .description("NO External Docs")
                        .url("https://ttl-blog.tistory.com/"));
    }
}
