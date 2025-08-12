package rasmote.github.io.ai_diary.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping; //@Get()
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody; //@Controller()
import org.springframework.web.bind.annotation.RestController; //클래스 생성자 생략.

import lombok.RequiredArgsConstructor;
import rasmote.github.io.ai_diary.domain.Diary;
import rasmote.github.io.ai_diary.domain.User;
import rasmote.github.io.ai_diary.dto.CommonResponseDto;
import rasmote.github.io.ai_diary.dto.DiaryRequestDto;
import rasmote.github.io.ai_diary.dto.DiaryResponseDto;
import rasmote.github.io.ai_diary.dto.DiaryUpdateRequestDto;
import rasmote.github.io.ai_diary.exception.CustomException;
import rasmote.github.io.ai_diary.exception.ErrorCode;
import rasmote.github.io.ai_diary.repository.UserRepository;
import rasmote.github.io.ai_diary.service.DiaryService;



@RestController
@RequiredArgsConstructor
public class DiaryController {
    private final DiaryService diaryService; //final 덕분에 생성자 생략 가능
    //public DiaryController(DiaryService diaryService) { ... } 생략

    @GetMapping("/api/hello")
    public String hello() {
        return diaryService.getHelloMessage();
    }

    // C
    // 1 . 일기 생성
    @PostMapping("/api/diaries")  
    public ResponseEntity<CommonResponseDto<DiaryResponseDto>> createDiary
    (@RequestBody DiaryRequestDto diaryRequestDto, @AuthenticationPrincipal UserDetails userDetails) { //UserDetails는 현재 로그인한 사용자의 정보를 담고 있음

        DiaryResponseDto createdDiary = diaryService.createDiary(diaryRequestDto, userDetails);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponseDto.success(createdDiary));
    }

    // R 
    // 2-1. 전체 일기 목록 조회
    @GetMapping("/api/diaries")
    public ResponseEntity<CommonResponseDto<List<DiaryResponseDto>>> getAllDiaries() {
        List<DiaryResponseDto> diaries = diaryService.getAllDiaries(); //1. Service에서 전체 일기 목록을 조회
        return ResponseEntity.ok(CommonResponseDto.success(diaries)); //2. 조회된 일기 목록을 응답으로 반환
    }
    
    // 2-2. 특정 일기 조회
    @GetMapping("/api/diaries/{id}")
    public ResponseEntity<CommonResponseDto<DiaryResponseDto>> getDiaryById(@PathVariable("id") Long id) { //@pathVariable은 URL 경로에서 변수를 추출
        DiaryResponseDto diary = diaryService.getDiaryById(id); //1. Service에서 특정 일기를 조회
        return ResponseEntity.ok(CommonResponseDto.success(diary)); //2. 조회된 일기를 응답으로 반환
    }
    
    //U
    @PutMapping("/api/diaries/{id}")    //PutMapping : 업데이트 요청 처리
    public ResponseEntity<CommonResponseDto<DiaryResponseDto>> updateDiary(
            @PathVariable("id") Long id,
            @RequestBody DiaryUpdateRequestDto dto,
            @AuthenticationPrincipal UserDetails userDetails) {

        DiaryResponseDto diaryResponseDto = diaryService.updateDiary(id, dto, userDetails);
        return ResponseEntity.ok(CommonResponseDto.success(diaryResponseDto));
    }
}