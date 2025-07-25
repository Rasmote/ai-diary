package rasmote.github.io.ai_diary.service;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import rasmote.github.io.ai_diary.domain.Diary;
import rasmote.github.io.ai_diary.dto.DiaryRequestDto;
import rasmote.github.io.ai_diary.repository.DiaryRepository;

@Service
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;

    public String getHelloMessage() {
        return "Hello, AI Diary!";
    }

    @Transactional
    public Diary createDiary(DiaryRequestDto diaryRequestDto) { 
        Diary diary = diaryRequestDto.toEntity(); //3. 전달받은 Dto를 Entity로 변환
        return diaryRepository.save(diary); //4. 변환된 Entity를 리포지토리에 저장하고, 저장된 Entity를 반환
    }
}
