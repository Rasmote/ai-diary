package rasmote.github.io.ai_diary.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity // Spring Security 설정 활성화, 웹 보안 설정 커스터마이징 가능하게
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 1. CSRF 보호 비활성화
        http.csrf((csrf) -> csrf.disable()); //REST API는 보통 세션을 안쓰니까 이거 꺼둔다고 함.

        // 2. 세션 관리 정책 설정 : JWT를 사용하기 때문에 세션을 사용하지 않음 (STATELESS)
        http.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // 3. HTTP 요청에 대한 인가 규칙 설정
        http.authorizeHttpRequests((authorize)->authorize
            .requestMatchers("/api/auth/**").permitAll() // 인증 없이 접근 허용
            .anyRequest().authenticated() // 나머지 요청은 인증 필요
        );

        // TODO : 4. JWT 인증 필터 추가

        return http.build(); 

    }
}
