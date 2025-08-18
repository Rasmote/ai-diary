package rasmote.github.io.ai_diary.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;
import rasmote.github.io.ai_diary.jwt.JwtAuthenticationFilter;
import rasmote.github.io.ai_diary.jwt.JwtUtil;
import rasmote.github.io.ai_diary.service.UserDetailsServicelmpl;


@Configuration
@EnableWebSecurity // Spring Security 설정 활성화, 웹 보안 설정 커스터마이징 가능하게
@RequiredArgsConstructor
public class SecurityConfig {

    //4-1. 필요한 의존성들을 선언함
    private final JwtUtil jwtUtil;
    private final UserDetailsServicelmpl userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .csrf(AbstractHttpConfigurer::disable)
        .formLogin(AbstractHttpConfigurer::disable)
        .httpBasic(AbstractHttpConfigurer::disable);

    // 2. 인가 규칙 설정
    http
        .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers(
                        "/api/auth/**",
                            "/v3/api-docs/**",
                            "/swagger-ui/**").permitAll()
            .anyRequest().authenticated()
        );

    // 3. JWT 필터 추가
    http
        .addFilterBefore(new JwtAuthenticationFilter(jwtUtil, userDetailsService),
                UsernamePasswordAuthenticationFilter.class);
        
    /*
    // 1-1. CSRF 보호 비활성화
    http.csrf((csrf) -> csrf.disable()); //REST API는 보통 세션을 안쓰니까 이거 꺼둔다고 함.
    
    // 1-2. HTTP Basic 인증 방식 비활성화
    http.httpBasic(AbstractHttpConfigurer::disable);
    
    // 1-3. Form 로그인 방식 비활성화
    http.formLogin(AbstractHttpConfigurer::disable);
    
    // 2. 세션 관리 정책 설정 : JWT를 사용하기 때문에 세션을 사용하지 않음 (STATELESS)
    http.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    
    // 3. HTTP 요청에 대한 인가 규칙 설정
    http.authorizeHttpRequests((authorize)->authorize
            .requestMatchers(
            "/api/auth/**",
                    "/v3/api-docs/**",
                    "/swagger-ui.html",
                    "/swagger-ui/**"
            ).permitAll() // 인증 없이 접근 허용
        .anyRequest().authenticated() // 나머지 요청은 인증 필요
    );
    
    // 4. JWT 인증 필터 추가
    // JwtAuthenticationFilter 를 Spring Security 필터 체인에 추가하기
    // UsernamePasswordAuthenticationFilter(로그인 처리 필터) 보다 먼저 실행되도록
    http.addFilterBefore(new JwtAuthenticationFilter(jwtUtil, userDetailsService), 
            UsernamePasswordAuthenticationFilter.class);
    
    
    // 5. 예외 처리 핸들러 설정 (존재하지 않는 일기 조회 오류메세지 반환 구현함)
    http.exceptionHandling(handler -> handler
        // 인증 실패(미인증 사용자) 시 처리
        .authenticationEntryPoint((request, response, authException) -> 
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "인증되지 않은 사용자입니다.")
        )
        // 인가 실패(권한 없는 사용자) 시 처리
        .accessDeniedHandler((request, response, accessDeniedException) -> 
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "접근 권한이 없습니다.")
        )
    );
    */
        return http.build(); 
    }
}
