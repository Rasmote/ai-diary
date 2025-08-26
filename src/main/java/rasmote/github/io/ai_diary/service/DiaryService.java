package rasmote.github.io.ai_diary.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import rasmote.github.io.ai_diary.domain.Diary;
import rasmote.github.io.ai_diary.domain.User;
import rasmote.github.io.ai_diary.dto.DiaryRequestDto;
import rasmote.github.io.ai_diary.dto.DiaryResponseDto;
import rasmote.github.io.ai_diary.dto.DiaryUpdateRequestDto;
import rasmote.github.io.ai_diary.exception.CustomException;
import rasmote.github.io.ai_diary.exception.ErrorCode;
import rasmote.github.io.ai_diary.repository.DiaryRepository;
import rasmote.github.io.ai_diary.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final UserRepository userRepository;
    private final AiApiService aiApiService;

    public String getHelloMessage() {
        return "Hello, AI Diary!";
    }

    // C : Create
    @Transactional
    public DiaryResponseDto createDiary(DiaryRequestDto diaryRequestDto, UserDetails userDetails) {

        User currentUser = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        String feedback = aiApiService.getAiFeedback(diaryRequestDto.getContent())
            .onErrorReturn("AI 피드백 생성 중 오류가 발생했습니다.") // 
            .block();   

        Diary diary = Diary.builder()
                .title(diaryRequestDto.getTitle())
                .content(diaryRequestDto.getContent())
                .user(currentUser)
                .build();

        diary.updateAiFeedback(feedback);

        Diary SavedDiary = diaryRepository.save(diary);
        
        return new DiaryResponseDto(SavedDiary);
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
                .orElseThrow(() -> new CustomException(ErrorCode.DIARY_NOT_FOUND)); 
        return new DiaryResponseDto(diary); // 조회된 Diary 객체를 DiaryResponseDto로 변환
    }

    // U : Update
        
    @Transactional
    public DiaryResponseDto updateDiary(Long id, DiaryUpdateRequestDto dto, UserDetails userDetails) {
        User currentUser = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        
        Diary diary = diaryRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.DIARY_NOT_FOUND));
        
        if (!diary.getUser().getId().equals(currentUser.getId())) {
            throw new CustomException(ErrorCode.FORBIDDEN_ACCESS);
        }

        diary.updateDiary(dto);

        return new DiaryResponseDto(diary);
    }

    // D : Delete

    @Transactional
    public void deleteDiary(Long id, UserDetails userDetails) {
        User currentUser = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Diary diary = diaryRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.DIARY_NOT_FOUND));

        if (!diary.getUser().getId().equals(currentUser.getId())) {
            throw new CustomException(ErrorCode.FORBIDDEN_ACCESS);
        }

        diaryRepository.delete(diary);
    }
}
