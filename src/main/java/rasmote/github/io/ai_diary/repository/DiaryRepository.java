package rasmote.github.io.ai_diary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rasmote.github.io.ai_diary.domain.Diary;

public interface DiaryRepository extends JpaRepository<Diary, Long> { // <다룰 Entity, PK 타입>
    // Spring Data JPA가 실행 시점에 이 인터페이스의 구현체를 자동으로 만들어준다함? 진짜에요?
}