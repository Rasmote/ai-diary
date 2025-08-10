package rasmote.github.io.ai_diary.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import rasmote.github.io.ai_diary.domain.Diary;
import rasmote.github.io.ai_diary.domain.User;
import rasmote.github.io.ai_diary.dto.DiaryRequestDto;
import rasmote.github.io.ai_diary.dto.DiaryResponseDto;
import rasmote.github.io.ai_diary.repository.DiaryRepository;

@Service
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final AiApiService aiApiService;

    public String getHelloMessage() {
        return "Hello, AI Diary!";
    }

    // C : Create
    @Transactional
    public Diary createDiary(DiaryRequestDto diaryRequestDto, User currentUser) {
        Diary diary = Diary.builder()
                .title(diaryRequestDto.getTitle())
                .content(diaryRequestDto.getContent())
                .user(currentUser)
                .build();

        Diary savedDiary = diaryRepository.save(diary);

        String feedback = aiApiService.getAiFeedback(diaryRequestDto.getContent())
            .block(); // <-- Mono가 결과를 가져올 때까지 여기서 멈춤!

        diary.updateAiFeedback(feedback);

        /* 
        aiApiService.getAiFeedback(diaryRequestDto.getContent())
            .subscribe(
                    aiFeedback -> { // AI 피드백이 성공적으로 도착했을 때
                        savedDiary.updateAiFeedback(aiFeedback); // 일기 엔티티에 피드백 업데이트
                        diaryRepository.save(savedDiary); // 업데이트된 일기 다시 저장
                    },
                    error -> { // AI 피드백 요청 중 오류가 발생했을 때
                        System.err.println("AI 피드백 생성 중 오류 발생: " + error.getMessage());
                        savedDiary.updateAiFeedback("AI 피드백 생성에 실패했습니다."); // 오류 메시지 저장
                        diaryRepository.save(savedDiary); // 오류 메시지로 업데이트된 일기 저장
                    }
            );
         */

        return diaryRepository.save(diary);
    }
    
    // R : Read
    // 1. 전체 일기 목록 조회
    @Transactional(readOnly = true)
    public List<DiaryResponseDto> getAllDiaries() {
        List<Diary> diaries = diaryRepository.findAll();
        return diaries.stream()  // <Diary>를 <ResponseDto> 로 변환하는 방법
                .map(DiaryResponseDto::new) // Diary 객체를 DiaryResponseDto로 변환
                .collect(Collectors.toList()); // 리스트로 수집
    }
    
    // 2. 특정 일기 조회
    @Transactional(readOnly = true)
    public DiaryResponseDto getDiaryById(Long id) {
        Diary diary = diaryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 일기를 찾을 수 없습니다. id : " + id));
        return new DiaryResponseDto(diary); // 조회된 Diary 객체를 DiaryResponseDto로 변환
    }

    // U : Update

    // D : Delete
}
