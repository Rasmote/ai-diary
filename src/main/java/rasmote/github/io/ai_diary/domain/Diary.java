package rasmote.github.io.ai_diary.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED) 
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키값 자동생성(Auto Increase)
    private Long Id;

    private String title;

    private String content;

    // 피드백, 생성 날짜 등 추가 예정.


    @Builder // 이 데코레이터를 사용함으로써, Diary 객체는 Dto에서 빌더를 사용하여 객체 생성이 가능함.
    public Diary(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
