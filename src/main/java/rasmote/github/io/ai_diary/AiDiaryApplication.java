package rasmote.github.io.ai_diary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class AiDiaryApplication {

	public static void main(String[] args) {
		SpringApplication.run(AiDiaryApplication.class, args);
	}

}
