package rasmote.github.io.ai_diary.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import rasmote.github.io.ai_diary.dto.DiaryUpdateRequestDto;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키값 자동생성(Auto Increase)
    private Long Id;

    private String title;

    private String content;

    @ManyToOne // Many(다이어리) to One(유저) 관계
    @JoinColumn(name = "user_id") // 외래키 설정, JPA가 자동으로 User의 PK를 참조하는 외래키를 생성
    // @JoinColumn(name = "author_username", referencedColumnName = "username") <-
    // 만약 username을 외래키로 사용하고 싶다면 이렇게 작성
    private User user; // User 객체

    // 피드백, 생성 날짜 등 추가 예정.
    @Column(columnDefinition = "TEXT")
    private String aiFeedback;

    @Builder // 이 데코레이터를 사용함으로써, Diary 객체는 Dto에서 빌더를 사용하여 객체 생성이 가능함.
    public Diary(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public void updateAiFeedback(String feedback) {
        this.aiFeedback = feedback;
    }

    public void updateDiary(DiaryUpdateRequestDto dto) {
        dto.getTitle().ifPresent(action -> this.title = action);
        dto.getContent().ifPresent(action -> this.content = action);
    }
}
