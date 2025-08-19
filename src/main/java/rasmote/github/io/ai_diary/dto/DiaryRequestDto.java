package rasmote.github.io.ai_diary.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import rasmote.github.io.ai_diary.domain.Diary;

@Getter
public class DiaryRequestDto {
    @NotBlank(message = "일기의 제목은 필수 입력입니다..")
    @Size(max = 40, message = "제목 길이 제한을 초과하였습니다.")
    private String title;

    @NotBlank(message = "일기의 내용은 필수 입력입니다.")
    private String content;

    //Entity에서 생성자에 @Builder가 붙어있기에, Dto에서도 Builder 패턴을 사용하여 Entity로 변환할 수 있다.
    public Diary toEntity() {   // Dto는 Entity를 보조만 하기 때문에, Entity 변환하는 메서드를 가져야한다.
        return Diary.builder()  //Build를 사용하여 Diary 객체 생성을 시작한다.
                .title(title)
                .content(content)
                .build();   // Build를 마치고 완성된 Diary 객체(Entity)를 반환한다.
    }
}
