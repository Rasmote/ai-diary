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


## 정리필요
- 리포지토리, 엔티티 개념 다시
- Config.java의 securityFilterChain 메서드 관련 개념 다시


### 작성중...