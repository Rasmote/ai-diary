package rasmote.github.io.ai_diary.controller;

import org.springframework.web.bind.annotation.GetMapping; //@Get()
import org.springframework.web.bind.annotation.RestController; //@Controller()

import lombok.RequiredArgsConstructor; //클래스 생성자 생략.
import rasmote.github.io.ai_diary.service.DiaryService;

@RestController
@RequiredArgsConstructor
public class DiaryController {
    private final DiaryService diaryService;

    @GetMapping("/api/hello")
    public String hello() {
        return diaryService.getHelloMessage();
    }

}
