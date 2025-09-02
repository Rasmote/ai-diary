package rasmote.github.io.ai_diary.service;
//DiaryService 테스트코드 작성용도

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import rasmote.github.io.ai_diary.domain.Diary;
import rasmote.github.io.ai_diary.domain.User;
import rasmote.github.io.ai_diary.dto.DiaryRequestDto;
import rasmote.github.io.ai_diary.dto.DiaryResponseDto;
import rasmote.github.io.ai_diary.repository.DiaryRepository;
import rasmote.github.io.ai_diary.repository.UserRepository;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DiaryServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private DiaryRepository diaryRepository;
    @Mock
    private AiApiService aiApiService;

    @InjectMocks    //Mock으로 만든 객체들을 DiaryService에 주입
    private DiaryService diaryService;

    @Test
    @DisplayName("일기 생성 성공 테스트")
    void createDiary_Success() {
        // given (테스트 준비)
        // ----------------------------------------------------------------
        // 필요한 객체, Dto등 생성
        String username = "testUser";
        DiaryRequestDto requestDto = new DiaryRequestDto();
        requestDto.setTitle("테스트 일기");
        requestDto.setContent("테스트 내용입니다.");

        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(username)
                .password("password")
                .roles("USER")
                .build();

        User currentUser = User.builder() // 실제 User 엔티티
                .id(1L)
                .username(username)
                .password("encodedPassword")
                .build();

        String expectedFeedback = "AI가 생성한 멋진 피드백입니다.";

        //  가짜(Mock) 객체들의 행동을 정의
        // "만약 userRepository.findByUsername(username)이 호출되면,
        // Optional.of(currentUser)를 반환하라고 프로그래밍
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(currentUser));
        when(aiApiService.getAiFeedback(any(String.class))).thenReturn(Mono.just(expectedFeedback));
        when(diaryRepository.save(any(Diary.class))).thenAnswer(invocation -> invocation.getArgument(0));


        // when (테스트 실행 단계)
        // ----------------------------------------------------------------
        // 실제 테스트하려는 메소드를 호출함
        DiaryResponseDto resultDiary = diaryService.createDiary(requestDto, userDetails);


        // then (결과 검증 단계)
        // ----------------------------------------------------------------
        // AssertJ 라이브러리를 사용하여 결과가 예상과 같은지 확인
        assertThat(resultDiary).isNotNull();
        assertThat(resultDiary.getTitle()).isEqualTo(requestDto.getTitle());
        assertThat(resultDiary.getContent()).isEqualTo(requestDto.getContent());
        //assertThat(resultDiary.getUser().getUsername()).isEqualTo(username);
        assertThat(resultDiary.getAiFeedback()).isEqualTo(expectedFeedback);
    }
}

