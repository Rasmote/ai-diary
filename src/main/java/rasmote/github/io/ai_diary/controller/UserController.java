package rasmote.github.io.ai_diary.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
// import rasmote.github.io.ai_diary.domain.User; // User 객체를 직접 반환하지 않으므로 주석 처리 가능
import rasmote.github.io.ai_diary.dto.CommonResponseDto;
import rasmote.github.io.ai_diary.dto.LoginRequestDto;
import rasmote.github.io.ai_diary.dto.LoginResponseDto;
import rasmote.github.io.ai_diary.dto.SignupRequestDto;
import rasmote.github.io.ai_diary.service.UserService;

@Tag(name = "User API", description = "사용자 관련 API")
@SecurityRequirement(name = "none")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    //1. 회원가입
    @Operation(summary = "회원가입", description = "새로운 사용자를 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "회원가IP 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommonResponseDto.class),
                            examples = @ExampleObject(value = "{\"success\": true, \"data\": \"회원가입이 성공적으로 완료되었습니다.\"}"))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommonResponseDto.class),
                            examples = {
                                    @ExampleObject(name = "입력값 유효성 검증 실패 (INVALID_INPUT_VALUE)", value = "{\"success\": false, \"error\": {\"code\": \"INVALID_INPUT_VALUE\", \"message\": \"입력값이 올바르지 않습니다.\"}}"),
                                    @ExampleObject(name = "이미 존재하는 사용자 이름 (USERNAME_ALREADY_EXISTS)", value = "{\"success\": false, \"error\": {\"code\": \"USERNAME_ALREADY_EXISTS\", \"message\": \"이미 존재하는 사용자 이름입니다.\"}}")
                            }))
    })
    @PostMapping("/signup")
    public ResponseEntity<CommonResponseDto<String>> signup(@Valid @RequestBody SignupRequestDto requestDto) {
        userService.signup(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponseDto.success("회원가입이 성공적으로 완료되었습니다."));
    }

    //2. 로그인
    @Operation(summary = "로그인", description = "사용자가 로그인하고 JWT 토큰을 발급받습니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommonResponseDto.class),
                            examples = @ExampleObject(value = "{\"success\": true, \"data\": {\"accessToken\": \"eyJhbGciOi...\", \"refreshToken\": \"eyJhbGciOi...\"}}"))),
            @ApiResponse(responseCode = "401", description = "아이디 또는 비밀번호가 일치하지 않습니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommonResponseDto.class),
                            examples = @ExampleObject(name = "INVALID_CREDENTIALS", value = "{\"success\": false, \"error\": {\"code\": \"INVALID_CREDENTIALS\", \"message\": \"아이디 또는 비밀번호가 일치하지 않습니다.\"}}"))),
            @ApiResponse(responseCode = "404", description = "해당 사용자를 찾을 수 없습니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommonResponseDto.class),
                            examples = @ExampleObject(name = "USER_NOT_FOUND", value = "{\"success\": false, \"error\": {\"code\": \"USER_NOT_FOUND\", \"message\": \"해당 사용자를 찾을 수 없습니다.\"}}")))
    })
    @PostMapping("/login")
    public ResponseEntity<CommonResponseDto<LoginResponseDto>> login(@Valid @RequestBody LoginRequestDto requestDto, HttpServletResponse response) {
        LoginResponseDto tokens = userService.login(requestDto);
        response.setHeader("Authorization", "Bearer " + tokens); // JWT 토큰을 응답 헤더에 설정
        return ResponseEntity.ok(CommonResponseDto.success(tokens)); // 로그인 성공 시 JWT 토큰을 반환
    }
}