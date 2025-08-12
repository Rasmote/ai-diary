package rasmote.github.io.ai_diary.dto;

import java.util.Optional;

import lombok.Getter;

@Getter
public class DiaryUpdateRequestDto {
    private Long id;
    private Optional<String> title;
    private Optional<String> content;
}
