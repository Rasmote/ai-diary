package rasmote.github.io.ai_diary.service;

import java.util.Collections;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import rasmote.github.io.ai_diary.domain.User;
import rasmote.github.io.ai_diary.repository.UserRepository;


@Service
@RequiredArgsConstructor
public class UserDetailsServicelmpl implements UserDetailsService {
    // UserDetailsService 인터페이스를 구현하는 클래스
    // Spring Security의 요청에 따라, User엔티티를 UserDetails 객체로 변환하는 역할을 수행
    private final UserRepository userRepository;

    @Override   // UserDetailsService 인터페이스의 메서드 재정의
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username));

        return new org.springframework.security.core.userdetails.User
                        (user.getUsername(), 
                        user.getPassword(), 
                        Collections.emptyList()); // User 엔티티를 UserDetails 객체로 변환하여 반환
    }
    
}
