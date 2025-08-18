package rasmote.github.io.ai_diary.controller;

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
import rasmote.github.io.ai_diary.domain.User;
import rasmote.github.io.ai_diary.dto.LoginRequestDto;
import rasmote.github.io.ai_diary.dto.SignupRequestDto;
import rasmote.github.io.ai_diary.service.UserService;

@Tag(name = "User API", description = "사용자 관련 API")
@RestController
@RequestMapping("/api/auth") // 이 컨트롤러 내부의 모든 API는 /api/auth 경로로 시작
@RequiredArgsConstructor

public class UserController {
    private final UserService userService;
    
    //1. 회원가입
    @Operation(summary = "회원가입", description = "새로운 사용자를 등록합니다.")
    @PostMapping("/signup")
    public ResponseEntity<User> signup(@Valid @RequestBody SignupRequestDto requestDto) {
        User user = userService.signup(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    //2. 로그인
    @Operation(summary = "로그인", description = "사용자가 로그인하고 JWT 토큰을 발급받습니다.")
    @PostMapping("/login")
    public String login(@Valid @RequestBody LoginRequestDto requestDto, HttpServletResponse response) {
        String token = userService.login(requestDto);
        response.setHeader("Authorization", "Bearer " + token); // JWT 토큰을 응답 헤더에 설정
        return token; // 로그인 성공 시 JWT 토큰을 반환
    }
}
