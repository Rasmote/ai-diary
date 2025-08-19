package rasmote.github.io.ai_diary.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {
    @NotBlank(message = "사용자 이름은 필수 입력값입니다.")
    private String username;
    
    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    private String password; 
}
