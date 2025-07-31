package rasmote.github.io.ai_diary.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import rasmote.github.io.ai_diary.domain.User;
import rasmote.github.io.ai_diary.dto.LoginRequestDto;
import rasmote.github.io.ai_diary.dto.SignupRequestDto;
import rasmote.github.io.ai_diary.service.UserService;

@RestController
@RequestMapping("/api/auth") // 이 컨트롤러 내부의 모든 API는 /api/auth 경로로 시작
@RequiredArgsConstructor

public class UserController {
    private final UserService userService;
    
    //1. 회원가입
    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody SignupRequestDto requestDto) {
        User user = userService.signup(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    //2. 로그인
    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDto requestDto, HttpServletResponse response) {
        String token = userService.login(requestDto);
        response.setHeader("Authorization", "Bearer " + token); // JWT 토큰을 응답 헤더에 설정
        return token; // 로그인 성공 시 JWT 토큰을 반환
    }
}
