package rasmote.github.io.ai_diary.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class DiaryController {
    @GetMapping("/api/hello")
    public String hello() {
        return "hello, User!";
    }

}
