package rasmote.github.io.ai_diary.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping; //@Get()
import org.springframework.web.bind.annotation.RequestBody; //@Controller()
import org.springframework.web.bind.annotation.RestController; //클래스 생성자 생략.

import lombok.RequiredArgsConstructor;
import rasmote.github.io.ai_diary.domain.Diary;
import rasmote.github.io.ai_diary.domain.User;
import rasmote.github.io.ai_diary.dto.CommonResponseDto;
import rasmote.github.io.ai_diary.dto.DiaryRequestDto;
import rasmote.github.io.ai_diary.dto.DiaryResponseDto;
import rasmote.github.io.ai_diary.repository.UserRepository;
import rasmote.github.io.ai_diary.service.DiaryService;



@RestController
@RequiredArgsConstructor
public class DiaryController {
    private final DiaryService diaryService; //final 덕분에 생성자 생략 가능
    private final UserRepository userRepository;
    //public DiaryController(DiaryService diaryService) { ... } 생략

    @GetMapping("/api/hello")
    public String hello() {
        return diaryService.getHelloMessage();
    }

    // 1 . 일기 생성
    @PostMapping("/api/diaries")  
    public ResponseEntity<CommonResponseDto<Diary>> createDiary
        (@RequestBody DiaryRequestDto diaryRequestDto,  @AuthenticationPrincipal UserDetails userDetails ) {        //UserDetails는 현재 로그인한 사용자의 정보를 담고 있음
            
        User currentUser = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        
        Diary createdDiary = diaryService.createDiary(diaryRequestDto, currentUser);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(CommonResponseDto.success(createdDiary)); 
    }

    // 2-1. 전체 일기 목록 조회
    @GetMapping("/api/diaries")
    public ResponseEntity<CommonResponseDto<List<DiaryResponseDto>>> getAllDiaries() {
        List<DiaryResponseDto> diaries = diaryService.getAllDiaries(); //1. Service에서 전체 일기 목록을 조회
        return ResponseEntity.ok(CommonResponseDto.success(diaries)); //2. 조회된 일기 목록을 응답으로 반환
    }
    
    // 2-2. 특정 일기 조회
    @GetMapping("/api/diaries/{id}")
    public ResponseEntity<CommonResponseDto<DiaryResponseDto>> getDiaryById(@PathVariable("id") Long id) {   //@pathVariable은 URL 경로에서 변수를 추출
        DiaryResponseDto diary = diaryService.getDiaryById(id); //1. Service에서 특정 일기를 조회
        return ResponseEntity.ok(CommonResponseDto.success(diary)); //2. 조회된 일기를 응답으로 반환
    }
}
