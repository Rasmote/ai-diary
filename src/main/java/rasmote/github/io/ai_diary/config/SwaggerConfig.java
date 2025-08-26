package rasmote.github.io.ai_diary.config;
//Swagger에 보안 필요한 api 공통응답생성

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "AI 피드백 일기장 API 명세서",
                description = "JWT를 이용한 사용자 인증 및 AI 피드백 기능이 포함된 일기장 API",
                version = "v1.0.0")
)
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        // 1. Security Scheme 정의
        SecurityScheme bearerAuth = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");

        // 2. Security Requirement 정의
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("Bearer Authentication");

        return new OpenAPI()
                .components(new Components().addSecuritySchemes("Bearer Authentication", bearerAuth))
                .addSecurityItem(securityRequirement);
    }

    @Bean
    public OperationCustomizer customize() {
        return (operation, handlerMethod) -> {
            // 1. 공통 에러 응답 정의
            ApiResponses commonApiResponses = new ApiResponses();
            commonApiResponses.addApiResponse("401", new ApiResponse().description("인증되지 않은 사용자 (E003, E004 등)")
                    .content(new Content().addMediaType("application/json",
                            new MediaType().schema(new Schema<>().$ref("#/components/schemas/CommonResponseDto"))
                                    .example("{\"success\": false, \"error\": {\"code\": \"AUTH_ERROR\", \"message\": \"인증에 실패했습니다.\"}}"))));
            commonApiResponses.addApiResponse("500", new ApiResponse().description("서버 내부 오류 (E999)")
                    .content(new Content().addMediaType("application/json",
                            new MediaType().schema(new Schema<>().$ref("#/components/schemas/CommonResponseDto"))
                                    .example("{\"success\": false, \"error\": {\"code\": \"E999\", \"message\": \"예상치 못한 오류가 발생했습니다.\"}}"))));

            // 2. 기존 응답에 공통 에러 응답 병합
            operation.getResponses().putAll(commonApiResponses);

            return operation;
        };
    }
}