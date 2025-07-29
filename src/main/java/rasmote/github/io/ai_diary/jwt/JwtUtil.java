package rasmote.github.io.ai_diary.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

// JwtUtil 클래스는 JWT 토큰을 생성하고 검증하는 유틸리티 클래스로 사용

@Component
public class JwtUtil {
    
    @Value("${jwt.secret}")
    private String secret;
    
    @Value("${jwt.access-token-validity-in-seconds}")
    private long accessTokenValiditySeconds;
    
    @Value("${jwt.refresh-token-validity-in-seconds}")
    private long refreshTokenValiditySeconds;
    
     /*
     private final String secret = "RasmoteAI_DiaryProject_SecretKeyForJWT_Authentication_2025";
     private final long accessTokenValiditySeconds = 36000;
     private final long refreshTokenValiditySeconds = 86400;
     */
    
    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String createAccessToken(String username) {
    return createToken(username, accessTokenValiditySeconds * 1000L);
    }

    public String createRefreshToken(String username) {
        return createToken(username, refreshTokenValiditySeconds * 1000L);
    }
    public String createToken(String username, long validityMiliseconds) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityMiliseconds);

        return Jwts.builder()
                .subject(username) // 토큰의 주체 (사용자 이름)
                .issuedAt(now) //토큰 발급 시간
                .expiration(validity) //토큰 만료 시간
                .signWith(secretKey) //사용할 암호화 키
                .compact(); //토큰 생성
    }

    
    //TODO : 검증 및 정보 추출
}
