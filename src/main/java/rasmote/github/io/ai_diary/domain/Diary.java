package rasmote.github.io.ai_diary.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키값 자동생성(Auto Increase)
    private Long Id;

    private String title;

    private String content;

    // 피드백, 생성 날짜 등 추가 필요.
}
