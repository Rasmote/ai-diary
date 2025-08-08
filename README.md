#  프로젝트 이름 (Project Name)
Ai 다이어리

## 개요 (Overview)
일기를 작성하는 행위는 심리적인 안정, 자기개발 등의 이점이 있습니다.  
하지만 대부분의 사람들은 일기를 매일 쓰는것에 있어 부담감, 귀찮음을 느끼곤 합니다.  

이 프로젝트는 사용자가 단순히히 일기를 작성하는 것에서 그치지 않고 ai api를 활용하여 즉각적인 피드백을 받아 일기의 이점을 극대화하는 일기장 개발을 목표로 합니다.  
사용자가 부담없이 쉽게 사용할 수 있도록 설계하고 있습니다.

최대한 RESTFUL하게!

##  주요 기능 (Key Features)
-  **기능 1**: 로그인...뭐 etc
-  **기능 2**: 일기장 선택 가능. 심리적 안정을 위한 일기인지, 매일 간단하게라도 쓰는 일기인지, 자기개발용인지
-  **기능 3**: 일기장 분류에 걸맞는 적절한 피드백

## 기술 스택
- Java 17
- Spring Boot 3.x
- Spring Web
- Spring Data JPA
- H2 Database
- Lombok

<!--## 
⚙️ 실행 방법

1.  **프로젝트 클론**
    ```bash
    git clone [이 저장소 주소]
    ```

2.  **AI API 키 설정 (중요!)**
    `src/main/resources/application.properties` 파일에 아래 내용을 추가하고 본인의 API 키를 입력하세요.
    ```properties
    ai.api.key=여기에_당신의_API_키를_입력하세요
    ```

3.  **애플리케이션 실행**
    VSCode 또는 IntelliJ에서 `AiDiaryApplication.java` 파일을 열고 실행합니다.
-->

## 진행 상황 (Progress)
- [ ] 기획 및 설계  
- [ ] DB 스키마 설계  
- [ ] API 구현  
- [ ] 프론트엔드 개발  
- [ ] 배포 및 테스트  

## ⚙️ API 엔드포인트 (API Endpoints)
- swagger 참고

## 향후 계획 (To-Do List)
- [ ] 프론트엔드 개발 (React? 등 찾아봐야함)
- [ ] 사용자 인증 기능 개선 필요
- [ ] 사생활 침해가 되지 않도록 관리자가 일기장을 보지 않게끔 암호화하는방법
#- [ ] swagger 작성
- [ ] 사용자가 일기 작성에 동기부여를 얻을 수 있는 방법?
- [ ] 가능하다면 api를 사용하는것이 아닌 직접 ai 개발을 해보고싶음.

## 참고 자료 (References)


## 메모장
- Nest에서는 TypeORM을 활용하여 관계형 데이터베이스를 객체 지향적인 방식으로 다뤘음. Spring에서는 Spring Data JPA가 해당 역할을 수행.  
- Spring Data JPA는 JPA라는 표준 기술을 쉽게 쓰게 함.
- JPA : JAVA의 ORM 인터페이스. DB랑 연동하기 위해 필요한 것들의 규칙을 정해놓은 것.
- DB 관련 정보는 application.properties에 작성
- 의존성 관련 등등은 build.gradle에 작성
- Dto는 Entity를 보조하는 역할을 수행하기에, Dto에서 직접 Entity로 변환하는 메서드를 가져야 한다.
- 빌더 개념 정리 1. Entity에서 @Builder 데코레이터를 사용한 생성자 작성, 2. dto의 toEntity()에서 .builder 사용.
- @ 어노테이션이라고 한다 함.
- ResponseEntity : 상태 코드, 헤더, 본문을 포함한 Spring에서 제공하는 클래스.
- Entity에서 CreatedDate, LastModifiedDate같은 어노테이션들은 다음과 같은 과정을 거쳐야 함.
    1. ~~Application.java에 @EnableJpaAuditing 어노테이션 추가
    2. 필요로 하는 엔티티 파일에서@EntityListeners(AuditingEntityListener.class) 어노테이션 추가
- @Service 와 @Component의 차이 : Component는 비즈니스 로직을 갖지 않고, 범용적인 목적으로 사용됨.
- jwt 밑 두가지 코드들의 역할
    1. JwtUtil : jwt라는 토큰이 진짜인지 가짜인지 판단, 발급 등
    2. JwtAuthenticationFilter : 모든 http 요청의 jwt를  검사하고, 그 결과를 바탕으로 다음 절차를 진행
        - 이때 Filter가 JwtUtil의 진위여부 판단 메서드를 활용함
- **jwt 관련 요청 내부 동작 순서**
    1. 요청이 들어오면 컨트롤러에 도달하기 전 SecurityConfig에 등록된JwtAuthenticationFilter가 요청을 가로챔
    2. 추출된 토큰을 JwtUtil을 활용하여 서명을 검증하고, 만료 시간을 확인. 이후 필터는 토큰에서 사용자 이름 추출
    3. UserDetailsServicelmpl에서 추출한 사용자 이름을 userRepository에서 DB에서 찾음.
        - 조회된 User엔티티 정보를 바탕으로 Spring Security가 이해할 수 있는 UserDetails객체를 생성하여 필터로 반환
    4. 필터는 UserDetails 객체를 받아 Authentication(UsernamePasswordAuthenticationToken) 객체를 생성함. 
        - 해당 객체 내에는 사용자 정보와 권한 정보가 포함되어 있음.
        - SecurityContextHolder.getContext()로 생성된 Authentication 객체를 SecurityContext에 등록
        - SecurityContext는 요청이 처리되는 동안 인증 정보를 보관함.
    5. filterChain.doFilter(request, response)가 호출되고, 다음 필터(혹은 컨트롤러)로 전달  
    6. **결론 : 이 모든 과정이 SecurityContext에 올바르게 사용자 정보와 권한 정보를 저장하기 위함이었음.**

- 토큰 관련 클래스 및 역할정리
    1. JwtUtil (jwt 패키지)
        -jwt의 생성, 서명, 만료시간설정, 유효성검증, 정보 추출 등 jwt와 관련된 모든 것
    2. UserDetailsServicelmpl (service 패키지)
        - Spring Security의 요청에 맞게, User 엔티티를 UserDetails 객체로 변환하여 제공
    3. JwtAuthenticationFilter (jwt 패키지)
        - 모든 http 요청을 가로채서, Authorization 헤더의 jwt를 검사
        - 그 결과를 바탕으로 Spring Security 시스템에 인증 정보를 등록함
    4. SecurityConfig (config 패키지)
        - 어플리케이션의 보안 규칙 설정.
        - 어떤 경로는 허용하고 어떤 경로를 막을지, 필터를 어떤 순서로 배치할지 등
- 토큰 발급 과정 : 클라이언트 -> 컨트롤러 -> 서비스 -> JwtUtil -> 컨트롤러 -> 클라이언트
- 토큰 검증 과정 : 클라이언트 -> 필터 -> JwtUtil -> UserDetailsServicelmpl -> 필터 -> 컨트롤러...
- Mono는 WebClient에서 사용하는 비동기 통신의 결과를 담는 클래스


## 정리필요
- 리포지토리, 엔티티 개념 다시
- Config.java의 securityFilterChain 메서드 관련 개념 다시
- jwt 발급 및 인증과정 전부 다시


### 작성중...