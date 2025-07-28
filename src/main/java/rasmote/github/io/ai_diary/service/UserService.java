package rasmote.github.io.ai_diary.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import rasmote.github.io.ai_diary.domain.User;
import rasmote.github.io.ai_diary.dto.SignupRequestDto;
import rasmote.github.io.ai_diary.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    //회원가입, 로그인 등 사용자 관련 서비스 메소드들을 정의

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User signup(SignupRequestDto requestDto) {
        //중복확인
        if (userRepository.findByUsername(requestDto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 사용자 이름입니다.");
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
}
