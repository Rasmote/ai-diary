package rasmote.github.io.ai_diary.dto;

import lombok.Getter;
import rasmote.github.io.ai_diary.domain.Diary;

@Getter
public class DiaryResponseDto {
    private Long id;
    private String title;
    private String content;

    public DiaryResponseDto(Diary diary) {
        this.id = diary.getId();
        this.title = diary.getTitle();
        this.content = diary.getContent();
    }
}
