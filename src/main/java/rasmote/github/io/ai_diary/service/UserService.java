package rasmote.github.io.ai_diary.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import rasmote.github.io.ai_diary.domain.User;
import rasmote.github.io.ai_diary.dto.LoginRequestDto;
import rasmote.github.io.ai_diary.dto.LoginResponseDto;
import rasmote.github.io.ai_diary.dto.SignupRequestDto;
import rasmote.github.io.ai_diary.exception.CustomException;
import rasmote.github.io.ai_diary.exception.ErrorCode;
import rasmote.github.io.ai_diary.jwt.JwtUtil;
import rasmote.github.io.ai_diary.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    //회원가입, 로그인 등 사용자 관련 서비스 메소드들을 정의

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // 회원가입 메소드
    @Transactional
    public User signup(SignupRequestDto requestDto) {
        //중복확인
        if (userRepository.findByUsername(requestDto.getUsername()).isPresent()) {
            throw new CustomException(ErrorCode.USERNAME_ALREADY_EXISTS);
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        // User 엔티티 생성
        User user = User.builder()
                .username(requestDto.getUsername())
                .password(encodedPassword) // 암호화된 비밀번호 설정
                .build();

        // User 저장
        return userRepository.save(user);
    }
    
    // 로그인 메소드
    @Transactional(readOnly = true)
    public LoginResponseDto login(LoginRequestDto dto) {
        User user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_CREDENTIALS);
        }

        String accessToken = jwtUtil.createAccessToken(user.getUsername());
        String refreshToken = jwtUtil.createRefreshToken(user.getUsername());

        return new LoginResponseDto(accessToken, refreshToken);
    }
}
