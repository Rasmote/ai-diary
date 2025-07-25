package rasmote.github.io.ai_diary.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping; //@Get()
import org.springframework.web.bind.annotation.PostMapping; //@Controller()
import org.springframework.web.bind.annotation.RequestBody; //클래스 생성자 생략.
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import rasmote.github.io.ai_diary.domain.Diary;
import rasmote.github.io.ai_diary.dto.DiaryRequestDto;
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

    @PostMapping("/api/diaries")    //1. 받아온 Body를 DiaryRequestDto로 변환하여
    public ResponseEntity<Diary> createDiary(@RequestBody DiaryRequestDto diaryRequestDto) {
        Diary createdDiary = diaryService.createDiary(diaryRequestDto); //2. Service로 전달
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDiary); //5. Service에서  생성된 Diary 객체를 응답으로 반환
    }
}
