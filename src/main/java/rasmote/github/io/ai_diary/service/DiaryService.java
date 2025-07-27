package rasmote.github.io.ai_diary.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import rasmote.github.io.ai_diary.domain.Diary;
import rasmote.github.io.ai_diary.dto.DiaryRequestDto;
import rasmote.github.io.ai_diary.dto.DiaryResponseDto;
import rasmote.github.io.ai_diary.repository.DiaryRepository;

@Service
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;

    public String getHelloMessage() {
        return "Hello, AI Diary!";
    }

    // C : Create
    @Transactional
    public Diary createDiary(DiaryRequestDto diaryRequestDto) {
        Diary diary = diaryRequestDto.toEntity(); //3. 전달받은 Dto를 Entity로 변환
        return diaryRepository.save(diary); //4. 변환된 Entity를 리포지토리에 저장하고, 저장된 Entity를 반환
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
