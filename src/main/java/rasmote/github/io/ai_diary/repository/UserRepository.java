package rasmote.github.io.ai_diary.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import rasmote.github.io.ai_diary.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    // save, findAll, findById, delete 등 기본적인 CRUD 메서드가 제공된다.

    Optional<User> findByUsername(String username);
    //Optional<user> : user엔티티를 찾고, 없으면 null 대신 Optional.empty()를 반환한다.
}
